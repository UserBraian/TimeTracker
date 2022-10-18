import java.time.LocalDateTime;
import java.util.ArrayList;

public class Component {
  private String name;
  private Component parent;
  private ArrayList<String> tags;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private LocalDateTime creationDate;
  private Double duration;

  public String getName() { return name; }
  public Component getParent() { return parent; }
  public ArrayList<String> getTags() { return tags; }
  public LocalDateTime getStartDate() { return startDate; }
  public LocalDateTime getEndDate() { return endDate; }
  public LocalDateTime getCreationDate() { return creationDate; }
  public Double getDuration() { return duration; }
  public void setName(String name) { this.name = name; }
  public void setParent(Component parent) { this.parent = parent; }
  public void setTags(ArrayList<String> tags) { this.tags = tags; }
  public void setStartDate(LocalDateTime startDate) {this.startDate = startDate; }
  public void setEndDate(LocalDateTime endDate) { this.endDate = endDate;}
  public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
  public void setDuration(Double duration) { this.duration = duration;}

  public void addComponent(){
    //TODO
  }
  public void deleteComponent(){
    //TODO
  }
  void addTag(String tag) {
    this.tags.add(tag);
  }
  void deleteTag(String tag){
    //TODO
  }
}

