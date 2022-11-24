package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
* La clase Project, en este caso hereda de Component. A su vez forma parte del patrón
* Composite, a modo de container, por lo cual un proyecto puede tener como hijos otros
* proyectos y/o tareas.
* */

public class Project extends Component {
  /*---- ATRIBUTOS ----*/
  public ArrayList<Component> children;

  /*---- CONSTRUCTOR ----*/
  public Project(String name, Component parent, ArrayList<String> tags) {
    super(name, parent, tags);
    children = new ArrayList<>();
  }

  /*---- MÉTODOS ----*/
  @Override
  public void addComponent(Component child) {
    children.add((child));
  }

  @Override
  public void deleteComponent(Component child) {
    children.remove(child);
  }
 
  /* esta función forma parte de sistema para actualizar las fechas y la duración 
  * de en este caso los proyectos, y una vez acaba se ejecuta la misma función pero 
  * en su parent
  */
  public void updateTree(LocalDateTime start, LocalDateTime end) {
    if (this.getStartDate() == null) {
      setStartDate(start);
    }
    setEndDate(end);

    Duration auxTime = Duration.ZERO;

    for (Component child : children) {
      auxTime = auxTime.plus(child.getDuration());
    }
    setDuration(auxTime);

    if (getParent() != null) {
      getParent().updateTree(this.getStartDate(), this.getEndDate());
    }

  }

  public ArrayList<Component> getChild() {
    return this.children;
  }

  public void acceptVisitor(Visitor v) {
    v.visitProject(this, getParent());
  }
}
