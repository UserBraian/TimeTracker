public class main {

  public static void main(String args[]){
    Clock clock;
    //mirar como funcionan los threads y que se cree el reloj en este
    //si no queda en bucle infinito
    Clock.getInstance();

    Project root=new Project("root",null);
    System.out.println("nom del projecte: "+root.getName());
    System.out.println("pare del projecte: "+root.getParent());
    System.out.println("creation: "+root.getCreationDate());
    System.out.println("\n");

    Project p1 = new Project("P1", root);
    System.out.println("nom del projecte: "+p1.getName());
    System.out.println("pare del projecte: "+p1.getParent().getName());
    System.out.println("creation: "+p1.getCreationDate());
    System.out.println("\n");

    Project p2 = new Project("P2", root);
    System.out.println("nom del projecte: "+p2.getName());
    System.out.println("pare del projecte: "+p2.getParent().getName());
    System.out.println("creation: "+p2.getCreationDate());
    System.out.println("\n");

    Task t1 = new Task("T1", root);
    System.out.println("nom del projecte: "+t1.getName());
    System.out.println("pare del projecte: "+t1.getParent().getName());
    System.out.println("creation: "+t1.getCreationDate());
    System.out.println("\n");

    Task t2 = new Task("T2", p1);
    System.out.println("nom del projecte: "+t2.getName());
    System.out.println("pare del projecte: "+t2.getParent().getName());
    System.out.println("creation: "+t2.getCreationDate());
    System.out.println("\n");

    Task t3 = new Task("T3", p2);
    System.out.println("nom del projecte: "+t3.getName());
    System.out.println("pare del projecte: "+t3.getParent().getName());
    System.out.println("creation: "+t3.getCreationDate());
    System.out.println("\n");

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
