package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* La clase Task hereda de main.Component
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

  public void stopTask() {
    int last = intervals.size() - 1;
    Interval i = intervals.get(last);
    i.stop();
    //System.out.println("Paramos tarea: " + this.getName());
    logger.info("Paramos tarea: " + this.getName());
  }

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
