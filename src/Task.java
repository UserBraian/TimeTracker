import java.util.ArrayList;

public class Task extends Component {
  private ArrayList<Interval> intervals;

  public Task(String name, Component parent) {
    //DONE
    super(name, parent);
    intervals=new ArrayList<Interval>();
  }

  public ArrayList<Interval> getIntervals() { return this.intervals; }
  public void setIntervals(ArrayList<Interval> intervals) { this.intervals = intervals; }
  public void startTask() {
    if(intervals.isEmpty() || !intervals.get(intervals.size()-1).hasEnded()){
      Interval i = new Interval();
      intervals.add(i);
    }
    else {
      System.out.println("Cannot start interval");
    }
    /*else if (intervals.get(intervals.size()-1).getEndTime()!=null) {
      Interval i = new Interval();
      intervals.add(i);
    }*/
    //tmbn comprobar que no hayan intervalos en marcha endtime!=null
    //if(intervals.get(-1).getEndTime()!=null){
    //Interval i = new Interval();
    //intervals.add(i);
    //}

  }
  public void stopTask() {
    int last = intervals.size()-1;
    Interval i = intervals.get(last);
    i.stop();
    update();
  }
  public void update() {
    setStartDate(intervals.get(0).getStartTime());
    setEndDate(intervals.get(intervals.size()-1).getEndTime());
    getParent().update();
  }

  public void acceptVisitor(Visitor v) {
    v.visitTask(this);
  }
}
