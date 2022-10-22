import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;

public class Clock extends Observable {
  private static Clock clock;
  private LocalDateTime hour;
  private String name;
  private Timer timer;  //powerpoint observer! //through a Timer object, tick() is periodically invoked
  //Se crea el thread con esta clase
  // !!!!----- USO DEL TIMER: https://www.chuidiang.org/java/timer/timer.php   ---------------!!!!!!!!!!!!!!!!!!!
 /*
 * Ya ques un constructor privado, no se genera por defecto por lo tanto
 * obligatoriamente tenemos que invocarlo
 * */
  private Clock(String name){
    this.name=name;
    this.timer=new Timer("Thread CLOCK");
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

  private void tick(){
    hour = LocalDateTime.now();
    setChanged();
    //valorar poner un if y comprobar que el vector observadores no esten vacios
    notifyObservers(hour); //
  }

  public LocalDateTime getHour(){
    return this.hour;
  }
}

