package view;

import controller.Controller;

/**
 * The interface used to track all public methods of different NUPlanner views.
 */
public interface NUPlannerView {
  void setListener(Controller controller);

  void display();
  /*void render() throws IOException;*/
  
  
}
