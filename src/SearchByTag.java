import java.util.ArrayList;

public class SearchByTag implements Visitor {
  /*
  * La clase SearchByTag sirve para hacer una búsqueda recorriendo al completo el árbol,
  * para esto se implementa el patrón Visitor, el cual nos permite hacerlo sin tener que
  * implementarlo dentro de las propias clases Project y Task.
  * Se buscará el tag que recibe por parámetro, la búsqueda se hará en modo descendente
  * comenzando desde el proyecto root de la aplicación, ya que este contiene todos los
  * proyectos y tareas, con sus respectivos tags, que forman parte del árbol.
  */

  private Component component;
  private String tagToSearch;
  private ArrayList<Component> tagInComponent;

  public SearchByTag(String tag) {
    //this.component = root;
    this.tagToSearch = tag;
    this.tagInComponent = new ArrayList<>();
  }

  /*public ArrayList<Component> search() {
    //this.tagToSearch = tag;
    this.component.acceptVisitor(this);
    return tagInComponent;
  }*/

  @Override
  public void visitProject(Project project, Component parent) {
    for (String projectTag : project.getTags()) {
      if ((projectTag.toLowerCase()).equals(tagToSearch.toLowerCase())) {
        //guardar proyecto en array list
        this.tagInComponent.add(project);
      }
    }

    for (Component child : project.children) {
      child.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task task, Component parent) {
    for (String taskTag : task.getTags()) {
      if ((taskTag.toLowerCase()).equals(tagToSearch.toLowerCase())) {
        //guardar task en array list
        this.tagInComponent.add(task);
      }
    }
  }

  @Override
  public void visitInterval(Interval interval, Component parent) {
    //No se implementa, ya que intervalos no tiene tags
  }
}
