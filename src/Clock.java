public class Clock {
  private static Clock clock;
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
}

