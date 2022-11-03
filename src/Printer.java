import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor{

    private Project root;

    Printer(Component root){

    }

    @Override
    public void visitProject(Project project) {

    }

    @Override
    public void visitTask(Task task) {

    }

    @Override
    public void visitInterval(Interval interval) {

    }

    public void update(Observable o, Object arg) {

    }
}
