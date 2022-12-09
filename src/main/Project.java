package main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
* La clase Project, en este caso hereda de Component. A su vez forma parte del patrón
* Composite, a modo de container, por lo cual un proyecto puede tener como hijos otros
* proyectos y/o tareas.
*/

public class Project extends Component {
  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Project.class);
  final Marker fita1 = MarkerFactory.getMarker("FITA1");

  /*---- ATRIBUTOS ----*/
  public ArrayList<Component> children;

  /*---- CONSTRUCTOR ----*/
  public Project(String name, Component parent, ArrayList<String> tags) {
    super(name, parent, tags);

    //setName();
    children = new ArrayList<>();
    logger.info(fita1, "Creamos Proyecto: " + this.getName());
  }

  public Project(String name, LocalDateTime startDate, LocalDateTime endDate, Duration duration, Component parent) {
    super(name, parent, new ArrayList<String>());
    super.setStartDate(startDate);
    super.setEndDate(endDate);
    super.setDuration(duration);
  }

  /*---- MÉTODOS ----*/
  @Override
  public void addComponent(Component child) {
    if(this.children!=null)
      children.add(child);
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

  public void setChildren(ArrayList<Component> children) { this.children = children; }


  public void acceptVisitor(Visitor v) {
    v.visitProject(this, getParent());
  }
}
