package main;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveToJSON implements Visitor {

  private JSONObject root;
  private JSONArray children;
  private JSONArray intervals;
  Logger logger = LoggerFactory.getLogger(Clock.class);

  /**
   * Consructor de la classe que inicialitza.
   * els elements de la classe amb JSONOBJECT.
   *
   */
  public SaveToJSON() {
    this.root = new JSONObject();
    this.children = new JSONArray();
    this.intervals = new JSONArray();
  }

  public JSONObject getRoot() {
    return this.root;
  }

  private JSONArray removeJSON(JSONArray jsonArray, ArrayList<Integer> indexes) {
    JSONArray result = new JSONArray();
    for (int i = 0; i < jsonArray.length(); i++) {
      if (!indexes.contains(i)) {
        result.put(jsonArray.get(i));
      }
    }

    return result;
  }

  /**
   * Aquesta es la funcio guarda a jerarquia en el fitxer que es passa per parametre.
   *
   */
  public void save(String fileName) {
    logger.trace("Se ha guardado la estructura de árbol en un archivo JSON");
    FileWriter file = null;

    try {
      file = new FileWriter(fileName);
      file.write(this.root.toString(2));
    } catch (IOException error) {
      error.printStackTrace();
    } finally {
      try {
        assert file != null;
        file.flush();
        file.close();
      } catch (IOException error) {
        error.printStackTrace();
      }
    }
  }

  @Override
  public void visitProject(Project project, Component parent) {
    for (Component child : project.getChild()) {
      child.acceptVisitor(this);
    }

    JSONObject projectDetails = new JSONObject();
    projectDetails.put("id", project.getId());
    projectDetails.put("name", project.getName());
    projectDetails.put("startDate", project.getStartDate());
    projectDetails.put("endDate", project.getEndDate());
    projectDetails.put("duration", project.getDuration().toSeconds());
    if (project.getParent() != null) {
      projectDetails.put("parent", project.getParent().getName());
    }
    JSONArray children = new JSONArray();

    ArrayList<Integer> indexesToRemove = new ArrayList<>();

    for (int i = 0; i < this.children.length(); i++) {
      JSONObject jsonChild = (JSONObject) this.children.get(i);

      if (jsonChild.getString("parent").equals(project.getName())) {
        children.put(jsonChild);
        indexesToRemove.add(i);
      }
    }

    this.children = this.removeJSON(this.children, indexesToRemove);

    projectDetails.put("children", children);

    if (project.getParent() == null) {
      this.root = projectDetails;
    } else {
      this.children.put(projectDetails);
    }
  }

  @Override
  public void visitTask(Task task, Component parent) {
    for (Interval interval : task.getIntervals()) {
      interval.acceptVisitor(this);
    }

    JSONObject taskDetails = new JSONObject();
    taskDetails.put("id", task.getId());
    taskDetails.put("name", task.getName());
    taskDetails.put("startDate", task.getStartDate());
    taskDetails.put("endDate", task.getEndDate());
    if (task.getParent() != null) {
      taskDetails.put("duration", task.getDuration().toSeconds());
    }
    taskDetails.put("parent", task.getParent().getName());

    JSONArray intervals = new JSONArray();

    ArrayList<Integer> indexesToRemove = new ArrayList<>();

    for (int i = 0; i < this.intervals.length(); i++) {
      JSONObject jsonChild = (JSONObject) this.intervals.get(i);

      if (jsonChild.getString("task").equals(task.getName())) {
        intervals.put(jsonChild);
        indexesToRemove.add(i);
      }
    }

    this.intervals = this.removeJSON(this.intervals, indexesToRemove);

    taskDetails.put("intervals", intervals);

    if (task.getParent() == null) {
      this.root = taskDetails;
    } else {
      this.children.put(taskDetails);
    }
  }

  @Override
  public void visitInterval(Interval interval, Component parent) {
    JSONObject intervalDetails = new JSONObject();
    //intervalDetails.put("id", interval.getId());
    intervalDetails.put("startTime", interval.getStartTime());
    intervalDetails.put("endTime", interval.getEndTime());
    intervalDetails.put("duration", interval.getDuration().toSeconds());
    intervalDetails.put("task", interval.getTaskParent().getName());

    this.intervals.put(intervalDetails);
  }



}

