import java.time.LocalDateTime;
import java.time.Duration;
import static java.time.Duration.between;
import java.util.Observable;
import java.util.Observer;



public class Interval implements Observer {
  /*---- ATRIBUTOS ----*/
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private boolean end;

  /*---- CONSTRUCTOR ----*/
  public Interval(){
    //suscribirse como observador
    Clock.getInstance().addObserver(this);
    this.update(Clock.getInstance(),Clock.getInstance().getHour());
  }

  /*---- METODOS ----*/
  public void stop(){
    //No necesitamos saber más la hora, una vez paramos el intervalo
    Clock.getInstance().deleteObserver(this);

    endTime = startTime.plus(duration);
    end = true;
  }
  public LocalDateTime getStartTime() { return this.startTime; }
  public LocalDateTime getEndTime() { return  this.endTime; }
  public Duration getDuration() { return this.duration; }
  public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
  public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
  public void setDuration(Duration duration) { this.duration=duration ; }

  @Override
  public void update(Observable o, Object arg) {
    LocalDateTime timeAux = (LocalDateTime) arg;
    if(startTime == null) {
      startTime = timeAux;
    }
    //Cada vez que se actualiza el reloj, calculamos la duracion siempre que estemos suscritos (no hay stop)
    duration = between(startTime,timeAux);
  }

  public boolean hasEnded() {
    return end;
  }

  public void acceptVisitor(Visitor v) {
    v.visitInterval(this);
  }
}
