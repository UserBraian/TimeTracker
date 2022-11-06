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
    setEndDate(end);

    Duration auxTime=Duration.ZERO;

    for(Component child: children){
      auxTime=auxTime.plus(child.getDuration());
    }
    setDuration(auxTime);

    if(getParent() != null){
      getParent().updateTree(this.getStartDate(),this.getEndDate());
    }

  }

  public ArrayList<Component> getChild(){
    return this.children;
  }

  public void acceptVisitor(Visitor v) {
    v.visitProject(this);
    if(getParent() != null){
      getParent().acceptVisitor(v);
    }
  }
}
