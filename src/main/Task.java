package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* La clase Task hereda de Component. Esta clase almacena todos lo intérvalos que se han realizado para
* completar dicha tarea en un ArrayList<>. Sus funciones principales son empezar una tarea (startTask),
* para una tarea (stopTask) y por último actualizar el arbol de tareas (updateTree), pasando los datos 
* hacia la jerarquía superior, en este caso a su padre Project.
* */

public class Task extends Component {
  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Task.class);

  /*---- ATRIBUTOS ----*/
  private ArrayList<Interval> intervals;
  Duration durationTask = Duration.ZERO; //no lo usamos por ahora

  /*---- CONSTRUCTOR ----*/
  public Task(String name, Component parent, ArrayList<String> tags) {
    super(name, parent, tags);
    intervals = new ArrayList<Interval>();
  }

  /*---- MÉTODOS ----*/
  public ArrayList<Interval> getIntervals() {
    return this.intervals;
  }

  public void setIntervals(ArrayList<Interval> intervals) {
    this.intervals = intervals;
  }

  /*
  * El método startTask() antes de poder iniciar una tarea, se comprueba que no haya ningún intérvalo
  * en curso, o por otro lado que el ArrayList de intervalos esté vacio y este sea el primero.
  * Una vez hechas las comprobaciones se crea un nuevo intérvalo.
  */
  public void startTask() {
    if (intervals.isEmpty() || intervals.get(intervals.size() - 1).hasEnded()) {
      Interval i = new Interval(this);
      intervals.add(i);
    } else {
      System.out.println("Cannot start interval");
    }
    //System.out.println("Iniciamos tarea: " + this.getName());
    logger.info("Iniciamos tarea: " + this.getName());

  }

  /*
  * El método stopTask() para esta tarea llamando al último intérvalo de su lista, y a su vez
  * detiene dicho intérvalo.
  */
  public void stopTask() {
    int last = intervals.size() - 1;
    Interval i = intervals.get(last);
    i.stop();
    //System.out.println("Paramos tarea: " + this.getName());
    logger.info("Paramos tarea: " + this.getName());
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
}
