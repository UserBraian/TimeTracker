import javax.print.attribute.standard.PrinterName;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor{
    /*---- ATRIBUTOS ----*/
    private static Printer instance;
    private Component root;

    /*---- CONSTRUCTOR ----*/
    private Printer(Component root){
        this.root=root;
    }

    /*---- METODOS ----*/
    public static Printer getInstance(Component root){
        if(instance==null){
            instance=new Printer(root);
        }
        return instance;
    }
    public void print(){
        this.root.acceptVisitor(this);
        System.out.println("\n");
    }
    @Override
    public void visitProject(Project project) {
        System.out.println("Task "+project.getName() +"\t"+project.getStartDate() +"\t"+project.getEndDate() +"\t"+project.getDuration());
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("Task "+task.getName() +"\t"+task.getStartDate() +"\t"+task.getEndDate() +"\t"+task.getDuration());
    }

    @Override
    public void visitInterval(Interval interval) {
        System.out.println("Interval "+ "     " +"\t"+interval.getStartTime() +"\t"+interval.getEndTime() +"\t"+interval.getDuration());
    }

}
