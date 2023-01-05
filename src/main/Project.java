package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
* La clase Project, en este caso hereda de Component. A su vez forma parte del patrón
* Composite, a modo de container, por lo cual un proyecto puede tener como hijos otros
* proyectos y/o tareas.
*/

public class Project extends Component {
  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Project.class);
  final Marker fita1 = MarkerFactory.getMarker("FITA1");

  /*---- ATRIBUTOS ----*/
  public ArrayList<Component> children;
  private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /*---- CONSTRUCTOR ----*/
  public Project(String name, Component parent, ArrayList<String> tags, int id) {
    super(name, parent, tags, id);

    //setName();
    children = new ArrayList<>();
    logger.info(fita1, "Creamos Proyecto: " + this.getName());
  }

  public Project(String name, LocalDateTime startDate, LocalDateTime endDate, Duration duration, Component parent, int id) {
    super(name, parent, new ArrayList<String>(), id);
    super.setStartDate(startDate);
    super.setEndDate(endDate);
    super.setDuration(duration);
  }

  /*---- MÉTODOS ----*/
  @Override
  public void addComponent(Component child) {
    if(this.children!=null)
      children.add(child);
  }

  @Override
  public void deleteComponent(Component child) {
    children.remove(child);
  }
 
  /* esta función forma parte de sistema para actualizar las fechas y la duración 
  * de en este caso los proyectos, y una vez acaba se ejecuta la misma función pero 
  * en su parent
  */
  public void updateTree(LocalDateTime start, LocalDateTime end) {
    if (this.getStartDate() == null) {
      setStartDate(start);
    }
    setEndDate(end);

    Duration auxTime = Duration.ZERO;

    for (Component child : children) {
      auxTime = auxTime.plus(child.getDuration());
    }
    setDuration(auxTime);

    if (getParent() != null) {
      getParent().updateTree(this.getStartDate(), this.getEndDate());
    }
  }

  public ArrayList<Component> getChild() {
    return this.children;
  }

  public void setChildren(ArrayList<Component> children) { this.children = children; }


  public void acceptVisitor(Visitor v) {
    v.visitProject(this, getParent());
  }

  @Override
  public JSONObject toJson(int level) {
    JSONObject json = new JSONObject();

    json.put("id", this.getId());
    json.put("type", this.getClass().getSimpleName());
    json.put("name", this.getName());
    json.put("startTime", this.getStartDate() == null ? null : DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).format(this.getStartDate()));
    json.put("endTime", this.getEndDate()== null ? null : DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).format(this.getEndDate()));
    json.put("duration", this.getDuration().toSeconds());
    if (this.getParent() != null) {
      json.put("parent", this.getParent().getName());
    }

    JSONArray jsonChildren = new JSONArray();
    if (level > 0) {
      for (Component child : this.getChild()) {
        jsonChildren.put(child.toJson(level - 1));
      }
    }
    json.put("children", jsonChildren);

    JSONArray jsonTags = new JSONArray();
    for (String tag : this.getTags()) {
      jsonTags.put(tag);
    }
    json.put("tags", jsonTags);

    return json;
  }
}
