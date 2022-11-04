import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.Duration;

public class Task extends Component {
  /*---- ATRIBUTOS ----*/
  private ArrayList<Interval> intervals;
  Duration duration_task = Duration.ZERO;//no lo usamos por ahora

  /*---- CONSTRUCTOR ----*/
  public Task(String name, Component parent, ArrayList<String> tags) {
    super(name,parent,tags);
    intervals=new ArrayList<Interval>();
  }

  /*---- METODOS ----*/
  public ArrayList<Interval> getIntervals() { return this.intervals; }
  public void setIntervals(ArrayList<Interval> intervals) { this.intervals = intervals; }
  public void startTask() {
    if(intervals.isEmpty() || intervals.get(intervals.size()-1).hasEnded()){
      Interval i = new Interval(this);
      intervals.add(i);
    }
    else {
      System.out.println("Cannot start interval");
    }
    System.out.println("Iniciamos tarea: "+this.getName());

  }
  public void stopTask() {
    int last = intervals.size()-1;
    Interval i = intervals.get(last);
    i.stop();
    System.out.println("Paramos tarea: "+this.getName());
    //update(); en interval
  }
  public void updateTree(LocalDateTime start, LocalDateTime end) {
    //cogemos el primero porque es la primera fecha, aunque luego haya ms intervalos
    //comprovamos que no este inicializado
    if(this.getStartDate()==null){
      this.setStartDate(this.intervals.get(0).getStartTime());
      //this.setDuration(Duration.ZERO);
    }
    Duration auxTime=Duration.ZERO;
    for(Interval interval: intervals){
      auxTime=auxTime.plus(interval.getDuration());
      //this.setDuration(this.getDuration().plus(child.getDuration()));
    }
    setDuration(auxTime);

    setEndDate(intervals.get(intervals.size()-1).getEndTime());

    getParent().updateTree(this.getStartDate(), this.getEndDate());
  }

  public void acceptVisitor(Visitor v) {
    v.visitTask(this);
  }
}




//Duration duration_task = Duration.ZERO;
//setStartDate(intervals.get(0).getStartTime());//comprovar que solo haya un intervalo
    /*for(Interval interval: intervals){
      duration_task = duration_task.plus(interval.getDuration());
    }
    setDuration(duration_task);
    setEndDate(getStartDate().plus(getDuration()));
    getParent().updateTree();
    duration_task=duration_task.plus(this.intervals.get(intervals.size()-1).getDuration());*/