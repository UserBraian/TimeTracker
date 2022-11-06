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
  }
  public void updateTree(LocalDateTime start, LocalDateTime end) {
    //cogemos el primero porque es la primera fecha, aunque luego haya mas intervalos
    //comprobamos que no este inicializado
    if(this.getStartDate()==null){
      this.setStartDate(this.intervals.get(0).getStartTime());
    }
    Duration auxTime=Duration.ZERO;
    for(Interval interval: intervals){
      auxTime=auxTime.plus(interval.getDuration());
    }
    setDuration(auxTime);

    setEndDate(intervals.get(intervals.size()-1).getEndTime());

    getParent().updateTree(this.getStartDate(), this.getEndDate());
  }

  public void acceptVisitor(Visitor v) {
    v.visitTask(this);
    getParent().acceptVisitor(v);
  }
}