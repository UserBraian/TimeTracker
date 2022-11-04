import java.time.LocalDateTime;
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

  public void updateTree(LocalDateTime start,LocalDateTime end){
    if(this.getStartDate()==null){
        setStartDate(start);
    }
    //this.setStartDate(this.children.get(0).getStartDate());
    setEndDate(end);
    Duration auxTime=Duration.ZERO;
    //setDuration(getDuration().plus(children.get(children.size()-1).getDuration()));
    for(Component child: children){
      auxTime=auxTime.plus(child.getDuration());
      //this.setDuration(this.getDuration().plus(child.getDuration()));
    }
    setDuration(auxTime);

    //setEndDate(children.get(children.size()-1).getEndDate());
    if(getParent() != null){
      getParent().updateTree(this.getStartDate(),this.getEndDate());
    }

  }

  public ArrayList<Component> getChild(){
    return this.children;
  }

  public void acceptVisitor(Visitor v) {
    v.visitProject(this);
  }
}

//updateTree antiguo
/*Duration duration_task = Duration.ZERO;
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
      getParent().updateTree();
    }*/