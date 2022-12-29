package webserver;

import main.Component;
import main.Clock;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Component root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock

    new WebServer(root);
  }
}

