package webserver;

import main.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Component root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock
    Clock.getInstance();

    new WebServer(root);
  }

  static Component makeTreeCourses() {
    IdGenerator idgen = IdGenerator.getInstance();
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
    return root;
  }
}

