import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

public abstract class Component {
  /*---- ATRIBUTOS ----*/
  private String name;
  private Component parent;
  private ArrayList<String> tags;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  //private LocalDateTime creationDate;
  private Duration duration;

  /*---- CONSTRUCTOR ----*/
  public Component(String name, Component parent, ArrayList<String> tags ) {
    this.name = name;
    this.parent = parent;
    this.tags = tags;
    this.duration=Duration.ZERO;

    //Creamos el arbol de proyectos/tareas
    if (parent != null) {
      parent.addComponent(this);
    }
  }

  /*---- METODOS ----*/
  public String getName() { return name; }
  public Component getParent() { return parent; }
  public ArrayList<String> getTags() { return tags; }
  public LocalDateTime getStartDate() { return startDate; }
  public LocalDateTime getEndDate() { return endDate; }
  //public LocalDateTime getCreationDate() { return creationDate; }
  public Duration getDuration() { return duration; }
  public void setName(String name) { this.name = name; }
  public void setParent(Component parent) { this.parent = parent; }
  public void setTags(ArrayList<String> tags) { this.tags = tags; }
  public void setStartDate(LocalDateTime startDate) {this.startDate = startDate; }
  public void setEndDate(LocalDateTime endDate) { this.endDate = endDate;}
  //public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
  public void setDuration(Duration duration) { this.duration = duration;}

  public void addComponent(Component child){
    //ABSTRACT, implementado solo en Project, que es el unico que puede tener hijos.
  }

  public void deleteComponent(Component child){
    //ABSTRACT, implementado solo en Project, que es el unico que puede tener hijos.
  }

  void addTag(String tag) {
    this.tags.add(tag);
  }

  void deleteTag(String tag){
    //DONE
    int index = this.tags.indexOf(tag);
    if (index!=-1)
      this.tags.remove(index);
    else
      System.out.println(tag+" -> Doesn't exist, impossible to remove");
  }

  public abstract void acceptVisitor(Visitor v);

  public abstract void updateTree(LocalDateTime start, LocalDateTime end);
}



