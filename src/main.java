import java.util.ArrayList;
import java.util.Arrays;
public class main {

  public static void main(String args[]) throws InterruptedException {
    //Clock clock;
    //mirar como funcionan los threads y que se cree el reloj en este
    //si no queda en bucle infinito
    Clock.getInstance();
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    //APENDICE A
    Project root=new Project("root",null, new ArrayList<String>());
    Project softwaredesign=new Project("softwaredesign",root, new ArrayList<String>( Arrays.asList("java", "flutter")));
    Project softwaretesting=new Project("sofwaretesting",root, new ArrayList<String>( Arrays.asList("c++", "Java", "python")));
    Project databases=new Project("databases",root, new ArrayList<String>( Arrays.asList("SQL", "python", "C++")));
    Task transportation = new Task("trasnportation",root, new ArrayList<String>());
    Project problems=new Project("problems",softwaredesign, new ArrayList<String>());
    Project timetracker=new Project("timetracker",softwaredesign, new ArrayList<String>());
    Task firstlist = new Task("firstlist", problems,  new ArrayList<String>( Arrays.asList("java")));
    Task secondlist = new Task("secondlist", problems, new ArrayList<String>(Arrays.asList("Dart")));
    Task readhandout = new Task("readhandout", timetracker, new ArrayList<String>());
    Task firstmilestone = new Task("firstmilestone", timetracker, new ArrayList<String>(Arrays.asList("Java", "IntelliJ")));

    //APENDIX B
    transportation.startTask();
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    transportation.stopTask();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    firstlist.startTask();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    secondlist.startTask();
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    firstlist.stopTask();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    secondlist.stopTask();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    transportation.startTask();
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    transportation.stopTask();
    /*
    t3.startTask();
    System.out.println("start time t3: "+t3.getIntervals().get(0).getStartTime());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    t3.stopTask();
    System.out.println("stop time t3: "+t3.getIntervals().get(0).getEndTime());
    System.out.println("duration t3: "+t3.getIntervals().get(0).getDuration());

    t2.startTask();
    System.out.println("start time t2: "+t2.getIntervals().get(0).getStartTime());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    t2.stopTask();*/
    /* Comprovar este caso tarea no puede ser padre
    Task t4=new Task("T4",t3);
    System.out.println("nom del projecte: "+t4.getName());
    System.out.println("pare del projecte: "+t4.getParent().getName());
    System.out.println("creation: "+t4.getCreationDate());
    System.out.println("\n");
    */
    Printer printer = new Printer(root);
  }
}
