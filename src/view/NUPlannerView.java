package view;

import java.io.IOException;

import controller.NUFeature;

/**
 * The interface used to track all public methods of different NUPlanner views.
 */
public interface NUPlannerView {

  /**
   * Allows the view to have access to the controller to delegate actions to a features interface.
   * @param      executer the controller to delegate actions to.
   */
  void setListener(NUFeature executer) throws IOException;

  /**
   * Sets this view to be visible.
   * Setting visible to be true.
   */
  void display() throws IOException;

}
