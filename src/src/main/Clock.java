package main;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/*
* La clase Clock, se utiliza para guardar la fecha y hora exacta de cada proyecto,
* tarea e intervalo. Se obtiene mediante el patrón Observer, en est caso, es el
* objeto observable donde cada vez que se actualiza la hora se informa a los
* observadores implicados (principalmente intervalos).
* Por otro lado, para obtener esta información únicamente por una via se aplica el
* patrón Singleton. Este nos garantiza que solamente hay creado un único Clock.
*/

public class Clock extends Observable {
  /*---- ATRIBUTOS -----*/
  private static Clock clock;
  private LocalDateTime hour;
  private String name;
  private Timer timer;
  //Se crea el thread con esta clase

  /*---- CONSTRUCTOR ----*/
  //Constructor privado para no crear mas de una instancia
  private Clock(String name) {
    this.name = name;
    this.timer = new Timer("Thread CLOCK");
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        tick();
      }
    }, 0, 2000);
  }

  /*---- SINGLETON PATTERN ----*/
  /*
  * Se ha seguido el patron singleton más simple, sin tener en cuenta hilos
  * posiblemente se tenga que modificar. (synchronized)
  */
  public static Clock getInstance() {
    if (clock == null) { //lazy
      clock = new Clock("CLOCK");
    }
    return clock;
  }

  /*---- MÉTODOS ----*/
  /*
  * Patron Observer: una vez tenemos la fecha y hora actualizada, internamente
  * marcamos este cambio y se procede a notificar esta fecha y hora a todos
  * los observadores que haya interesados en esta información.
  */
  private void tick() {
    hour = LocalDateTime.now();
    setChanged();
    notifyObservers(hour);
  }

  public LocalDateTime getHour() {
    return this.hour;
  }
}

