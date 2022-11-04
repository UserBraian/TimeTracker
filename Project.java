import java.util.ArrayList;
import java.time.Duration;

public class Project extends Component {
  /*---- ATRIBUTOS ----*/
  public ArrayList<Component> children;

  /*---- CONSTRUCTOR ----*/
  public Project(String name, Component parent, ArrayList<String> tags) {
    super(name,parent,tags);
    children=new ArrayList<>();
  }

  /*---- METODOS ----*/
  @Override
  public void addComponent(Component child){
    children.add((child));
  }

  @Override
  public void deleteComponent(Component child){
    children.remove(child);
  }

  public void update(){
    Duration duration_task = Duration.ZERO;
    for(Component component: children){
      if(component.getStartDate() != null) {
        if(getStartDate() == null){
          setStartDate(component.getStartDate());
        }
        if(component.getStartDate().isBefore(getStartDate())){
          setStartDate(component.getStartDate());
        }
        duration_task = duration_task.plus(component.getDuration());
      }
    }
    setDuration(duration_task);
    setEndDate(getStartDate().plus(getDuration()));
    if(getParent() != null){
      getParent().update();
    }
  }

  public ArrayList<Component> getChild(){
    return this.children;
  }

  public void acceptVisitor(Visitor v) {
    v.visitProject(this);
  }
}
