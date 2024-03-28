package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.NUPlannerSystem;
import view.NUPlannerView;

/**
 * The controller represents what delegates the users model from the view.
 */
public class Controller implements ActionListener {

  /**
   * Controls the program with the model and the view.
   * @param     m the model the controller is handling
   * @param     v the view that the controller is communicating to.
   */
  public Controller(NUPlannerSystem m, NUPlannerView v) {
    v.setListener(this);
    v.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Schedule Button")) {
      // do some stuff to schedule the event
    }
    else if (e.getActionCommand().equals("Create Button")) {
      // do something to create the event
    }
  }
}
