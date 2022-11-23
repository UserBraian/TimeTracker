package main;

import java.util.ArrayList;
import java.util.Arrays;
import search.SearchByTag;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    Clock.getInstance();
    //Para evitar comportamientos extraños
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*---- APÉNDICE A ----*/
    Project root = new Project("root          ", null, new ArrayList<String>());

    Project softwaredesign = new Project("softwaredesign", root,
        new ArrayList<String>(Arrays.asList("java", "flutter")));

    Project softwaretesting = new Project("sofwaretesting", root,
        new ArrayList<String>(Arrays.asList("c++", "Java", "python")));

    Project databases = new Project("databases     ", root,
        new ArrayList<String>(Arrays.asList("SQL", "python", "C++")));

    Task transportation = new Task("transportation", root, new ArrayList<String>());

    Project problems = new Project("problems      ", softwaredesign, new ArrayList<String>());

    Project timetracker = new Project("timetracker   ", softwaredesign, new ArrayList<String>());

    Task firstlist = new Task("firstlist     ", problems,
        new ArrayList<String>(Arrays.asList("java")));

    Task secondlist = new Task("secondlist    ", problems,
        new ArrayList<String>(Arrays.asList("Dart")));

    Task readhandout = new Task("readhandout   ", timetracker, new ArrayList<String>());

    Task firstmilestone = new Task("firstmilestone", timetracker,
        new ArrayList<String>(Arrays.asList("Java", "IntelliJ")));

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*---- APÉNDICE B ----*/
    /*-- Tarea: Transportation --*/
    transportation.startTask();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    transportation.stopTask();

    /*-- Esperamos 2 segundos --*/
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*-- Tarea: FirstList --*/
    firstlist.startTask();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*-- Tarea: SecondList --*/
    secondlist.startTask();
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*-- Paramos la tarea FirstList --*/
    firstlist.stopTask();

    /*-- Esperamos 2 segundos --*/
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*-- Paramos la tarea SecondList --*/
    secondlist.stopTask();

    /*-- Esperamos 2 segundos --*/
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    /*-- Tarea: Transportation --*/
    transportation.startTask();
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    transportation.stopTask();

    /*---- BUSCAR TAG  ----*/
    System.out.println();
    System.out.println("Empezamos a la busqueda por tags: ");
    ArrayList<String> tags = new ArrayList<String>(
        Arrays.asList("java", "JAVA", "intellij", "c++", "python"));
    for (String tagToSearch : tags) {
      Visitor searchByTag = new SearchByTag(tagToSearch);
      root.acceptVisitor(searchByTag);
      System.out.println();
    }
    System.out.println("Fin de busqueda por tags :)");
  }
}
