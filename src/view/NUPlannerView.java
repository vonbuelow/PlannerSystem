package view;

import java.io.IOException;

import controller.Features;
import controller.NUFeature;
import model.EventRep;

/**
 * The interface used to track all public methods of different NUPlanner views.
 */
public interface NUPlannerView {

  /**
   * Allows the view to have access to the controller to delegate actions to a features interface.
   * @param      executer the controller to delegate actions to.
   */
  void setListener(Features executer) throws IOException;

  /**
   * Sets this view to be visible.
   * Setting visible to be true.
   */
  void display() throws IOException;

  /**
   * Depending on what a user has clicked on open an event frame with the given event info.
   * This info will be displayed onto the event frame.
   *
   * @param event        The event to be displayed.
   * @param selectedUser
   */
  void openEventFrame(EventRep event, String selectedUser) throws IOException;
}
