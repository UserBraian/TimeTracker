package search;

import java.util.ArrayList;
import main.Component;
import main.Interval;
import main.Project;
import main.Task;
import main.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class SearchByTag implements Visitor {
  /*
  * La clase SearchByTag sirve para hacer una búsqueda recorriendo al completo el árbol,
  * para esto se implementa el patrón Visitor, el cual nos permite hacerlo sin tener que
  * implementarlo dentro de las propias clases Project y Task.
  * Se buscará el tag que recibe por parámetro, la búsqueda se hará en modo descendente
  * comenzando desde el proyecto root de la aplicación, ya que este contiene todos los
  * proyectos y tareas, con sus respectivos tags, que forman parte del árbol.
  */

  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Task.class);
  final Marker fita2 = MarkerFactory.getMarker("FITA2");

  private Component component;
  private String tagToSearch;
  private ArrayList<Component> tagInComponent;

  public SearchByTag(String tag) {
    this.tagToSearch = tag;
    this.tagInComponent = new ArrayList<>();
  }

  @Override
  public void visitProject(Project project, Component parent) {
    logger.trace(fita2, "Buscando en proyecto: " + project.getName()); //CAMBIAR O QUITAR
    for (String projectTag : project.getTags()) {
      if ((projectTag.toLowerCase()).equals(tagToSearch.toLowerCase())) {
        //guardar proyecto en array list
        this.tagInComponent.add(project);
        logger.info("Tag: " + tagToSearch + "\t" + "-> " + project.getName());
      }
    }

    for (Component child : project.children) {
      child.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task task, Component parent) {
    logger.trace(fita2, "Buscando en tarea: " + task.getName()); //CAMBIAR O QUITAR
    for (String taskTag : task.getTags()) {
      if ((taskTag.toLowerCase()).equals(tagToSearch.toLowerCase())) {
        //guardar task en array list
        this.tagInComponent.add(task);
        logger.info("Tag: " + tagToSearch + "\t" + "-> " + task.getName());
      }
    }
  }

  @Override
  public void visitInterval(Interval interval, Component parent) {
    //No se implementa, ya que intervalos no tiene tags
  }
}
