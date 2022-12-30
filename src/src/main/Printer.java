package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
* La clase Printer, nos sirve para recorrer el árbol de proyectos y tareas y sus respectivos
* intervalos para mostrar por pantalla, en tiempo real, los tiempos de estos. Se recorre el
* árbol de forma ascendente.
* Para hacer aplicar esta funcionalidad y recorrer el árbol al completo, se aplica el patrón
* Visitor, pues nos da la capacidad de tener funcionalidades extra sin tener que implementarlas
* propiamente dentro de los proyectos, tareas e intervalos.
*/

public class Printer implements Visitor {
  /*---- LOGGER ----*/
  static Logger logger = LoggerFactory.getLogger(Printer.class);
  final Marker fita1 = MarkerFactory.getMarker("FITA1");

  /*---- ATRIBUTOS ----*/
  private static Printer instance;

  /*---- CONSTRUCTOR ----*/
  private Printer() {
    instance = null;
  }

  /*---- MÉTODOS ----*/
  //Usando el patron singleton creamos un solo objeto printer
  public static Printer getInstance() {
    if (instance == null) {
      instance = new Printer();
    }
    return instance;
  }

  //llamamos al visitor para empezar a printar desde el intervalo activo
  public void print(Interval interval) {
    interval.acceptVisitor(this);
  }

  /*
  * Funciones del patron visitor, modificadas en printer para que impriman los nombres,
  * fecha de inicio y fecha final de cada proyecto/task/interval por el que pasen, y 
  * además redirigen el printer a su padre, y en caso de que ya se haya llegado al 
  * root entonces paran
  */
  @Override
  public void visitProject(Project project, Component parent) {
    logger.info(fita1, project.toString());
    if (parent != null) {
      parent.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task task, Component parent) {
    logger.info(fita1, task.toString());
    parent.acceptVisitor(this);
  }

  @Override
  public void visitInterval(Interval interval, Component parent) {
    logger.info(fita1, interval.toString());
    parent.acceptVisitor(this);
  }
}
