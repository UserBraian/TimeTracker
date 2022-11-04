import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
  /*---- ATRIBUTOS -----*/
  private static Clock clock;
  private LocalDateTime hour;
  private String name;
  private Timer timer;
  //Se crea el thread con esta clase

  /*---- CONSTRUCTOR ----*/
  //Constructor privado para no crear mas instancias
  private Clock(String name){
    this.name=name;
    this.timer=new Timer("Thread CLOCK");
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        tick();
      }
    }, 0, 2000);
  }

/*---- SINGLETON PATTERN ----*/
//Se ha seguido el patron singleton mas simple, sin tener en cuenta hilos
//posiblemente se tenga que modificar. (synchronized)
  public static Clock getInstance(){
    if (clock==null){ //lazy
      clock=new Clock("CLOCK");
    }
    return clock;
  }

  /*---- METODOS ----*/
  private void tick(){
    hour = LocalDateTime.now();
    setChanged();
    notifyObservers(hour);
  }

  public LocalDateTime getHour(){
    return this.hour;
  }
}

