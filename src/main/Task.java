package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
* La clase Task hereda de Component. Esta clase almacena todos lo intérvalos que se han realizado
* para completar dicha tarea en un ArrayList<>. Sus funciones principales son empezar una tarea
* (startTask), parar una tarea (stopTask) y por último actualizar el árbol de tareas (updateTree),
* pasando los datos hacia la jerarquía superior, en este caso a su padre Project.
*/

public class Task extends Component {
  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Task.class);
  final Marker fita1 = MarkerFactory.getMarker("FITA1");

  /*---- ATRIBUTOS ----*/
  private ArrayList<Interval> intervals;
  Duration durationTask = Duration.ZERO; //no lo usamos por ahora
  private Boolean Active = false;
  private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /*---- CONSTRUCTOR ----*/
  public Task(String name, Component parent, ArrayList<String> tags, int id) {
    super(name, parent, tags, id);
    intervals = new ArrayList<Interval>();
    logger.info(fita1, "Creamos Task: " + this.getName());
  }

  public Task(String name, LocalDateTime startTime, LocalDateTime endTime,
              Duration duration, Component parent, int id) {
    super(name, parent, new ArrayList<String>(), id);
    super.setStartDate(startTime);
    super.setEndDate(endTime);
    super.setDuration(duration);
  }

  /*---- MÉTODOS ----*/
  public ArrayList<Interval> getIntervals() {
    return this.intervals;
  }

  public void setIntervals(ArrayList<Interval> intervals) {
    this.intervals = intervals;
  }

  /*
  * El método startTask() antes de poder iniciar una tarea, se comprueba que no haya ningún
  * intérvalo en curso o, por otro lado, que el ArrayList de intervalos esté vacío y este
  * sea el primero.
  * Una vez hechas las comprobaciones se crea un nuevo intérvalo.
  */
  public void startTask() {
    if (intervals.isEmpty() || intervals.get(intervals.size() - 1).hasEnded()) {
      Interval i = new Interval(this);
      intervals.add(i);
      this.Active = true;
    } else {
      logger.error(fita1, "Cannot start interval");
    }
    logger.info(fita1, "Iniciamos tarea: " + this.getName());

  }

  /*
  * El método stopTask() para esta tarea llamando al último intérvalo de su lista, y a su vez
  * detiene dicho intérvalo.
  */
  public void stopTask() {
    int last = intervals.size() - 1;
    Interval i = intervals.get(last);
    i.stop();
    this.Active = false;
    logger.info(fita1, "Paramos tarea: " + this.getName());
  }
  
  /* 
  * El método updateTree() forma parte de sistema para actualizar las fechas y la duración 
  * y una vez acaba se ejecuta la misma función pero en el parent. Se le pasan las variables
  * de inicio y final para que el proyecto padre recalcule las duraciones y la fecha final.
  */
  public void updateTree(LocalDateTime start, LocalDateTime end) {
    //cogemos el primero porque es la primera fecha, aunque luego haya más intervalos
    //comprobamos que no esté inicializado
    if (this.getStartDate() == null) {
      this.setStartDate(this.intervals.get(0).getStartTime());
    }
    Duration auxTime = Duration.ZERO;
    for (Interval interval : intervals) {
      auxTime = auxTime.plus(interval.getDuration());
    }
    setDuration(auxTime);

    setEndDate(intervals.get(intervals.size() - 1).getEndTime());

    getParent().updateTree(this.getStartDate(), this.getEndDate());
  }

  public void acceptVisitor(Visitor v) {
    v.visitTask(this, getParent());
  }

  public Boolean getActive() {
    return Active;
  }

  public void setActive(Boolean name) {
    this.Active = name;
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
    json.put("active", this.getActive());
    if (this.getParent() != null) {
      json.put("parent", this.getParent().getName());
    }

    JSONArray jsonIntervals = new JSONArray();
    if (level > 0) {
      for (Interval interval : this.getIntervals()) {
        jsonIntervals.put(interval.toJson());
      }
    }
    json.put("intervals", jsonIntervals);

    JSONArray jsonTags = new JSONArray();
    for (String tag : this.getTags()) {
      jsonTags.put(tag);
    }
    json.put("tags", jsonTags);

    return json;
  }

  private boolean invariants() {
    boolean check = !this.getName().isEmpty();

    if (this.getParent() == null) {
      check = false;
    } else if (this.getParent().getClass().getSimpleName().equals("Task")) {
      check = false;
    }

    if (this.getDuration().toSeconds() < 0) {
      check = false;
    }

    // Getintervals peta como palomita en el micro

    return check;
  }
}
