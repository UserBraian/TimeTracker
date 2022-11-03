import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor{

    private Project root;

    Printer(Component root){
        root.acceptVisitor(this);
    }

    @Override
    public void visitProject(Project project) {
        System.out.println("Task "+project.getName() +"\t"+project.getStartDate() +"\t"+project.getEndDate() +"\t"+project.getDuration());
        for (Component component : project.getChild()) {
            component.acceptVisitor(this);
        }
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("Task "+task.getName() +"\t"+task.getStartDate() +"\t"+task.getEndDate() +"\t"+task.getDuration());
        for (Interval interval : task.getIntervals()) {
            interval.acceptVisitor(this);
        }

    }

    @Override
    public void visitInterval(Interval interval) {
        System.out.println("Interval "+ "     " +"\t"+interval.getStartTime() +"\t"+interval.getEndTime() +"\t"+interval.getDuration());
    }


}
