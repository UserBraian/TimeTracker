import java.util.ArrayList;

public class Task extends Component {
  private ArrayList<Interval> intervals;

  public Task(String name, Component parent) {
    //DONE
    super(name, parent);
  }

  public ArrayList<Interval> getIntervals() { return this.intervals; }
  public void setIntervals(ArrayList<Interval> intervals) { this.intervals = intervals; }
  public void startTask() {
    //TODO
  }
  public void stopTask() {
    //TODO
  }
  void update() {
    //TODO
  }
}
