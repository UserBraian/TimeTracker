import java.awt.color.CMMException;

public interface Visitor {
  void visitProject(Project project, Component parent);
  void visitTask(Task task, Component parent);
  void visitInterval(Interval interval, Component parent );
}
