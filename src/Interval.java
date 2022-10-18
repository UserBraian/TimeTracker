import java.time.LocalDateTime;

public class Interval {
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Double duration;

  public void calculateTime(){
    //TODO
  }
  public LocalDateTime getStartTime() { return this.startTime; }
  public LocalDateTime getEndTime() { return  this.endTime; }
  public Double getDuration() { return this.duration; }
  public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
  public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
  public void setDuration(Double duration) { this.duration=duration ; }

}
