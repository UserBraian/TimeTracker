import javax.print.attribute.standard.PrinterName;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor{
    /*---- ATRIBUTOS ----*/
    private static Printer instance;

    /*---- CONSTRUCTOR ----*/
    private Printer(){
        instance=null;
    }

    /*---- METODOS ----*/
    public static Printer getInstance(){
        if(instance==null){
            instance=new Printer();
        }
        return instance;
    }

    public void print(Interval interval){
        interval.acceptVisitor(this);
    }
    @Override
    public void visitProject(Project project) {
        System.out.println("Project:  "+project.getName() + "\t"+project.getStartDate() +"\t"+project.getEndDate() +"\t"+project.getDuration());
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("Task:     "+task.getName() +"\t"+task.getStartDate() +"\t"+task.getEndDate() +"\t"+task.getDuration());
    }

    @Override
    public void visitInterval(Interval interval) {
        System.out.println("Interval: "+"              "+"\t"+interval.getStartTime() +"\t"+interval.getEndTime() +"\t"+interval.getDuration());
    }

}
