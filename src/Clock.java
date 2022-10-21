import java.time.LocalDateTime;
import java.util.Observable;

public class Clock extends Observable {
  private static Clock clock;
  private LocalDateTime hour;
  //añadir class observer para tener addobserver etc
  private String name;
 /*
 * Ya ques un constructor privado, no se genera por defecto por lo tanto
 * obligatoriamente tenemos que invocarlo
 * */
  private Clock(String name){
    this.name=name;
  }
/*SINGLETON PATTERN
* Se ha seguido el patron singleton mas simple, sin tener en cuenta hilos
* posiblemente se tenga que modificar.
*/
  public static Clock getInstance(){
    if (clock==null){
      clock=new Clock("CLOCK");
    }
    return clock;
  }

  public void timer(){
    while (true) { ///vigilar este while true
      hour = LocalDateTime.now();
      setChanged();
      //valorar poner un if y comprobar que el vector observadores no esten vacios
      notifyObservers(hour); //
    }
  }

}

