import javax.print.attribute.standard.PrinterName;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor{
    /*---- ATRIBUTOS ----*/
    private static Printer instance;
    private Printer(){
        instance=null;
    }
    public static Printer getInstance(){
        if(instance==null){
            instance=new Printer();
        }
        return instance;
    }

    /*---- CONSTRUCTOR ----*/

    /*Printer(Interval root){
        root.acceptVisitor(this);
    }*/

    /*---- METODOS ----*/
    public void print(Interval interval){
        interval.acceptVisitor(this);
    }
    @Override
    public void visitProject(Project project) {
        System.out.println("Task "+project.getName() +"\t"+project.getStartDate() +"\t"+project.getEndDate() +"\t"+project.getDuration());
        for (Component component : project.getChild()) {
            if(project.getParent()!=null) {
                project.getParent().acceptVisitor(this);
            }
        }
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("Task "+task.getName() +"\t"+task.getStartDate() +"\t"+task.getEndDate() +"\t"+task.getDuration());
        for (Interval interval : task.getIntervals()) {
            task.getParent().acceptVisitor(this);
        }

    }

    @Override
    public void visitInterval(Interval interval) {
        System.out.println("Interval "+ "     " +"\t"+interval.getStartTime() +"\t"+interval.getEndTime() +"\t"+interval.getDuration());
        interval.getTaskParent().acceptVisitor(this);
    }

}
