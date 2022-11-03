public interface Visitor {
    public void visitProject(Project project);
    public void visitTask(Task task);
    public void visitInterval(Interval interval);
}
