package main;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import search.SearchByTag;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    /*---- LOGGERS ----*/
    Logger logger = LoggerFactory.getLogger(Main.class);
    final Marker fita1 = MarkerFactory.getMarker("FITA1");
    final Marker fita2 = MarkerFactory.getMarker("FITA2");

    logger.info("Comenzamos la ejecucion del Time Tracker\n");
    logger.info(fita1, "Comienza la Fita1:\n");

    Clock.getInstance();
    IdGenerator idgen = IdGenerator.getInstance();
    //Para evitar comportamientos extraños
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*---- APÉNDICE A ----*/
    Project root = new Project("root          ", null, new ArrayList<String>(), idgen.getId());

    Project softwaredesign = new Project("softwaredesign", root,
        new ArrayList<String>(Arrays.asList("java", "flutter")), idgen.getId());

    Project softwaretesting = new Project("sofwaretesting", root,
        new ArrayList<String>(Arrays.asList("c++", "Java", "python")), idgen.getId());

    Project databases = new Project("databases     ", root,
        new ArrayList<String>(Arrays.asList("SQL", "python", "C++")), idgen.getId());

    Task transportation = new Task("transportation", root, new ArrayList<String>(), idgen.getId());

    Project problems = new Project("problems      ", softwaredesign, new ArrayList<String>(), idgen.getId());

    Project timetracker = new Project("timetracker   ", softwaredesign, new ArrayList<String>(), idgen.getId());

    Task firstlist = new Task("firstlist     ", problems,
        new ArrayList<String>(Arrays.asList("java")), idgen.getId());

    Task secondlist = new Task("secondlist    ", problems,
        new ArrayList<String>(Arrays.asList("Dart")), idgen.getId());

    Task readhandout = new Task("readhandout   ", timetracker, new ArrayList<String>(), idgen.getId());

    Task firstmilestone = new Task("firstmilestone", timetracker,
        new ArrayList<String>(Arrays.asList("Java", "IntelliJ")), idgen.getId());

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*---- CARGAR ARBOL DESDE JSON  ----*/
    //LoadFromJSON loadJSON = new LoadFromJSON();
    //loadJSON.load("test.json");
    //Project root2 = loadJSON.getRoot();


    /*---- APÉNDICE B ----*/
    //cortado y guardado en textedit online

    /*---- GUARDAR ARBOL EN JSON  ----*/
    SaveToJSON savejson = new SaveToJSON();
    root.acceptVisitor(savejson);
    savejson.save("src/main/test2.json");

    logger.info(fita1, "Acaba la Fita1\n");

    logger.info(fita2, "Comienza la Fita2\n");

    /*---- BUSCAR TAG  ----*/
    /*
    logger.info(fita2, "Empezamos a la busqueda por tags: ");
    ArrayList<String> tags = new ArrayList<String>(
        Arrays.asList("java", "JAVA", "intellij", "c++", "python"));
    for (String tagToSearch : tags) {
      Visitor searchByTag = new SearchByTag(tagToSearch);
      root.acceptVisitor(searchByTag);
      System.out.println();
    }
    logger.info(fita2, "Fin de busqueda por tags :)");

    logger.info(fita2, "Acaba la Fita2");

    logger.info("Acaba la ejecucion del Time Tracker");*/
  }
}
