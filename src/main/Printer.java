package main;

/*
* La clase Printer, nos sirve para recorrer el árbol de proyectos y tareas y sus respectivos
* intervalos para mostrar por pantalla, en tiempo real, los tiempos de estos. Se recorre el
* árbol de forma ascendente.
* Para hacer aplicar esta funcionalidad y recorrer el árbol al completo, se aplica el patrón
* Visitor, pues nos da la capacidad de tener funcionalidades extra sin tener que implementarlas
* propiamente dentro de los proyectos, tareas e intervalos.
* */

public class Printer implements Visitor {
  /*---- ATRIBUTOS ----*/
  private static Printer instance;

  /*---- CONSTRUCTOR ----*/
  private Printer() {
    instance = null;
  }

  /*---- MÉTODOS ----*/
  public static Printer getInstance() {
    if (instance == null) {
      instance = new Printer();
    }
    return instance;
  }

  public void print(Interval interval) {
    interval.acceptVisitor(this);
  }

  @Override
  public void visitProject(Project project, Component parent) {
    System.out.println("main.Project:  " + project.getName() + "\t" + project.getStartDate() + "\t"
        + project.getEndDate() + "\t" + project.getDuration());

    if (parent != null) {
      parent.acceptVisitor(this);
    }

  }

  @Override
  public void visitTask(Task task, Component parent) {
    System.out.println("main.Task:     " + task.getName() + "\t" + task.getStartDate() + "\t"
        + task.getEndDate() + "\t" + task.getDuration());
    parent.acceptVisitor(this);
  }

  @Override
  public void visitInterval(Interval interval, Component parent) {
    System.out.println("main.Interval: " + "              " + "\t" + interval.getStartTime()
        + "\t" + interval.getEndTime() + "\t" + interval.getDuration());

    parent.acceptVisitor(this);
  }
}
