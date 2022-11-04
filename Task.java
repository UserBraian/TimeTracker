import java.util.ArrayList;
import java.time.Duration;

public class Task extends Component {
  /*---- ATRIBUTOS ----*/
  private ArrayList<Interval> intervals;

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
      Interval i = new Interval();
      intervals.add(i);
    }
    else {
      System.out.println("Cannot start interval");
    }

  }
  public void stopTask() {
    int last = intervals.size()-1;
    Interval i = intervals.get(last);
    i.stop();
    update();
  }
  public void update() {
    Duration duration_task = Duration.ZERO;
    setStartDate(intervals.get(0).getStartTime());
    for(Interval interval: intervals){
      duration_task = duration_task.plus(interval.getDuration());
    }
    setDuration(duration_task);
    setEndDate(getStartDate().plus(getDuration()));
    getParent().update();
  }

  public void acceptVisitor(Visitor v) {
    v.visitTask(this);
  }
}
