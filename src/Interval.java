import java.time.LocalDateTime;
import java.time.Duration;
import static java.time.Duration.between;
import java.util.Observable;
import java.util.Observer;


/*
* La clase Interval, nos sirve para generar el tiempo que se ha dedicado a cada tarea que la invoca.
* Se utiliza una fecha de inicio y una de final, para luego calcular la duración de un intervalo.
* Cada intervalo se inicia mediante un start y se finaliza mediante un stop.
* Se aplica el patron Observer, en este caso es nuestro observador, respecto al observable
* Clock, para ser notificado por este y asi recibir la hora actualizada cada periodo.
* */

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

  @Override
  public void update(Observable o, Object arg) {
    LocalDateTime timeAux = (LocalDateTime) arg;
    //Cada vez que se actualiza el reloj, calculamos la duración
    //siempre que estemos suscritos (no hay stop)
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
}
