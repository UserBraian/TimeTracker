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
public class SearchById implements Visitor{
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
    private int id;
    private Component found;

    public Component SearchById(Component root, int id) {
        this.id = id;
        this.found = null;
        root.acceptVisitor(this);
        return this.found;
    }

    @Override
    public void visitProject(Project project, Component parent) {
        logger.trace(fita2, "Buscando en proyecto: " + project.getName()); //CAMBIAR O QUITAR
        if (project.getId() == id) {
            this.found = project;
        }
        else{
            for (Component child : project.children) {
                child.acceptVisitor(this);
            }
        }
    }

    @Override
    public void visitTask(Task task, Component parent) {
        logger.trace(fita2, "Buscando en tarea: " + task.getName()); //CAMBIAR O QUITAR
        if (task.getId() == id) {
            this.found = task;
        }
    }

    @Override
    public void visitInterval(Interval interval, Component parent) {
    }
}