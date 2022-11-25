package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


/*
* La clase Component es la superclase respecto Project y Task pues ambas tienen características
* similares. Además, nos sirve para poder aplicar el patron Composite, donde la clase container
* puede generar un componente, es decir crear un proyecto o tarea.
*/

public abstract class Component {
  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Project.class);
  final Marker fita1 = MarkerFactory.getMarker("FITA1");

  /*---- ATRIBUTOS ----*/
  private String name;
  private Component parent;
  private ArrayList<String> tags;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Duration duration;

  /*---- CONSTRUCTOR ----*/
  public Component(String name, Component parent, ArrayList<String> tags) {
    this.name = name;
    this.parent = parent;
    this.tags = tags;
    this.duration = Duration.ZERO;

    //Creamos el árbol de proyectos/tareas
    if (parent != null) {
      parent.addComponent(this);
    }
  }

  /*---- MÉTODOS ----*/
  public String getName() {
    return name;
  }

  public Component getParent() {
    return parent;
  }

  public ArrayList<String> getTags() {
    return tags;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParent(Component parent) {
    this.parent = parent;
  }

  public void setTags(ArrayList<String> tags) {
    this.tags = tags;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public void addComponent(Component child) {
    //ABSTRACT, implementado solo en Project, que es el único que puede tener hijos.
  }

  public void deleteComponent(Component child) {
    //ABSTRACT, implementado solo en Project, que es el único que puede tener hijos.
  }

  void addTag(String tag) {
    this.tags.add(tag);
  }

  void deleteTag(String tag) {
    int index = this.tags.indexOf(tag);
    if (index != -1) {
      this.tags.remove(index);
    } else {
      logger.error(fita1, tag + " -> Doesn't exist, impossible to remove");
    }
  }

  @Override
  public String toString() {
    String clase = (this.getClass() == Project.class) ? "Project:  " : "Task:     ";
    return ("main." + clase +  this.getName() + "\t" + this.getStartDate() + "\t"
        + this.getEndDate() + "\t" + this.getDuration());
  }

  public abstract void acceptVisitor(Visitor v);

  public abstract void updateTree(LocalDateTime start, LocalDateTime end);
}



