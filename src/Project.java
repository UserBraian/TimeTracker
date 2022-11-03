import java.util.ArrayList;

public class Project extends Component {
  public ArrayList<Component> children;
  public Project(String name, Component parent) {
    //DONE
    super(name,parent);
    children=new ArrayList<>();
  }

  @Override
  public void addComponent(Component child){
    children.add((child));
  }

  @Override
  public void deleteComponent(Component child){
    children.remove(child);
  }

  public void update(){
    for(Component component: children){
      if(component.getStartDate() != null && component.getEndDate() != null) {
        if(component.getStartDate().isBefore(getStartDate())){
          setStartDate(component.getStartDate());
        } else if (component.getEndDate().isAfter(getEndDate())) {
          setEndDate(component.getEndDate());
        }
      }
    }
  }
  public ArrayList<Component> getChild(){
    return this.children;
  }

  public void acceptVisitor(Visitor v) {
    v.visitProject(this);
  }
}
