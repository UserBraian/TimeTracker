import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
public class Interval implements Observer {

  //constructor inicializar
  public Interval(){
    //suscribirse como observador
    Clock.getInstance().addObserver(this);
    startTime=LocalDateTime.now();

  }
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Double duration;

  private LocalDateTime aux;

  //tiene que haber una intancia reloj para añadir observador
  //clock getintance().addobserver(this)
  public void calculateTime(){
    //TODO
    //calcular duracion = end - start

  }

  public void stop(){
    //llamar observer, coger la hora y guardarlo en endTime
    //una vez esta parado llamamos a calculateTime para tener la duracion ya hecha
  }
  public LocalDateTime getStartTime() { return this.startTime; }
  public LocalDateTime getEndTime() { return  this.endTime; }
  public Double getDuration() { return this.duration; }
  public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
  public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
  public void setDuration(Double duration) { this.duration=duration ; }

  @Override
  public void update(Observable o, Object arg) {
    aux = (LocalDateTime) arg;
  }
}
