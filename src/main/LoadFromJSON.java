package main;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aquesta classe s'encarrega de càrregar les dades d'un JSON i importar-les a una estructura
 * d'arbre amb totes les seves propietats.
 *
 */

public class LoadFromJSON {
  private Project root;
  Logger logger = LoggerFactory.getLogger(Clock.class);

  public LoadFromJSON() {
  }

  public Project getRoot() {
    return this.root;
  }

  private int getintfromjson(JSONObject jsonObject, String key) {
    int num = 0;
    if (jsonObject.has(key)) {
      num = jsonObject.getInt(key);
    }

    return num;
  }
  private String getstringfromjson(JSONObject jsonObject, String key) {
    String text = "";
    if (jsonObject.has(key)) {
      text = jsonObject.getString(key);
    }

    return text;
  }

  private LocalDateTime getlocaldatetimefromjson(JSONObject jsonObject, String key) {
    LocalDateTime localDateTime = null;
    if (jsonObject.has(key)) {
      localDateTime = LocalDateTime.parse(jsonObject.getString(key));
    }

    return localDateTime;
  }

  private long getlongfromjson(JSONObject jsonObject, String key) {
    long data = 0;
    if (jsonObject.has(key)) {
      data = jsonObject.getLong(key);
    }

    return data;
  }

  private ArrayList<Component> loadChildren(Component parent,
                                            JSONArray jsonChildren) {
    ArrayList<Component> children = new ArrayList<>();

    for (Object childObject : jsonChildren) {
      JSONObject jsonChild = (JSONObject) childObject;

      int id = getintfromjson(jsonChild, "id");
      String name = getstringfromjson(jsonChild, "name");
      LocalDateTime startTime = getlocaldatetimefromjson(jsonChild, "startDate");
      LocalDateTime endTime = getlocaldatetimefromjson(jsonChild, "endDate");
      Duration duration = Duration.ofSeconds(getlongfromjson(jsonChild, "duration"));

      if (jsonChild.has("children")) {
        Project child = new Project(name, startTime, endTime, duration, parent, id);
        child.setChildren(loadChildren(child, jsonChild.getJSONArray("children")));
        children.add(child);
      }

      if (jsonChild.has("intervals")) {
        Task child = new Task(name, startTime, endTime, duration, parent, id);
        child.setIntervals(loadIntervals(child, jsonChild.getJSONArray("intervals")));
        children.add(child);
      }
    }

    return children;
  }

  private ArrayList<Interval> loadIntervals(Task task, JSONArray jsonIntervals) {
    ArrayList<Interval> intervals = new ArrayList<>();

    for (Object intervalObject : jsonIntervals) {
      JSONObject jsonInterval = (JSONObject) intervalObject;

      int id = getintfromjson(jsonInterval, "id");
      LocalDateTime startTime = getlocaldatetimefromjson(jsonInterval, "startTime");
      LocalDateTime endTime = getlocaldatetimefromjson(jsonInterval, "endTime");
      Duration duration = Duration.ofSeconds(getlongfromjson(jsonInterval, "duration"));

      Interval interval = new Interval(startTime, endTime, duration, task/*, id*/);
      intervals.add(interval);
    }

    return intervals;
  }

  /**
   * Funció principal de la classe. Rep el nom de l'arxiu JSON i fa totes les funcions necesàries
   * per importar tots els elements.
   *
   */
  public void load(String fileName) {
    logger.trace("Se ha cargado el JSON en una estructura de Arbol");
    InputStream is = LoadFromJSON.class.getResourceAsStream(fileName);
    if (is == null) {
      throw new NullPointerException("Cannot find resource file " + fileName);
    }

    JSONTokener tokenizer = new JSONTokener(is);
    JSONObject jsonObject = new JSONObject(tokenizer);

    int id = getintfromjson(jsonObject, "id");
    String name = getstringfromjson(jsonObject, "name");
    LocalDateTime startTime = getlocaldatetimefromjson(jsonObject, "starDate");
    LocalDateTime endTime = getlocaldatetimefromjson(jsonObject, "endDate");
    Duration duration = Duration.ofSeconds(getlongfromjson(jsonObject, "duration"));

    root = new Project(name, startTime, endTime, duration, null, id);

    if (jsonObject.has("children")) {
      root.setChildren(loadChildren(root, jsonObject.getJSONArray("children")));
    }

    //this.root = root;
  }
}

