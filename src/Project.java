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

  void update(){
    //TODO
  }
  public ArrayList<Component> getChild(){
    //TODO
    return null;
  }

  public void accept(Visitor v) {

  }
}
