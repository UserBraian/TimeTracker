package main;

/*La clase Visitor es la que sirve para poder recorrer el árbol de clases,
* también sirve como super-clase par crear los diversos tipos de subclases visitor 
* que recorreran el arbol de diversas maneras sin tener que añadir codigo a las
* otras clases, lo que ahorra duplicaciones.
*/
public interface Visitor {
  void visitProject(Project project, Component parent);

  void visitTask(Task task, Component parent);

  void visitInterval(Interval interval, Component parent);
}
