package main;

import static java.time.Duration.between;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/*
* La clase Interval, nos sirve para generar el tiempo que se ha dedicado a cada tarea que la invoca.
* Se utiliza una fecha de inicio y una de final, para luego calcular la duración de un intervalo.
* Cada intervalo se inicia mediante un start y se finaliza mediante un stop.
* Se aplica el patron Observer, en este caso es nuestro observador, respecto al observable
* Clock, para ser notificado por este y asi recibir la hora actualizada cada periodo.
*/

public class Interval implements Observer {
  /*---- ATRIBUTOS ----*/
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private boolean end;
  private Task taskParent;

  /*---- CONSTRUCTOR ----*/
  public Interval(Task parent) {
    //suscribirse como observador
    taskParent = parent;
    Clock.getInstance().addObserver(this);
    startTime = Clock.getInstance().getHour();
  }

  /*---- MÉTODOS ----*/
  public void stop() {
    //No necesitamos saber más la hora, una vez paramos el intervalo
    Clock.getInstance().deleteObserver(this);
    end = true;
  }

  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  public LocalDateTime getEndTime() {
    return  this.endTime;
  }

  public Duration getDuration() {
    return this.duration;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  /*
  * El método update() forma parte del patrón Observer, en este caso se acude a este para poder
  * establecer la fecha y hora en la variable endTime y posteriormente calcular la duración
  * del intérvalo. Puesto que ya tenemos inicializada la variable startTime desde el constructor
  * únicamente sobreescribimos el valor del endTime hasta que el intervalo en cuestión deje ya no
  * sea un observador y no reciba más actualizaciones de la hora, por lo tanto, la hora final será
  * la última que haya recibido hasta entonces. Finalmente, se calcula la duración del mismo modo.
  * Por otro lado, se hace la llamada al método updateTree(), para que estas fechas se actualicen
  * hacia la tarea padre y posteriormente se muestren por pantalla mediante la clase Printer.
  */
  @Override
  public void update(Observable o, Object arg) {
    LocalDateTime timeAux = (LocalDateTime) arg;
    /* Cada vez que se actualiza el reloj, calculamos la duración siempre que
    estemos suscritos (no hay stop)*/
    duration = between(startTime, timeAux);

    endTime = timeAux;

    getTaskParent().updateTree(this.getStartTime(), this.getEndTime());

    Printer.getInstance().print(this);
  }

  public Task getTaskParent() {
    return taskParent;
  }

  public boolean hasEnded() {
    return end;
  }

  public void acceptVisitor(Visitor v) {
    v.visitInterval(this, getTaskParent());
  }

  @Override
  public String toString() {
    return  ("main.Interval: " + "              " + "\t" + this.getStartTime()
        + "\t" + this.getEndTime() + "\t" + this.getDuration());
  }
}
