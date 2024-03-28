package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.NUPlannerSystem;
import view.NUPlannerView;

/**
 * The controller represents what delegates the users model from the view.
 */
public class Controller implements ActionListener {
  private final NUPlannerSystem model;
  private final NUPlannerView view;

  /**
   * Controls the program with the model and the view.
   * @param     m the model the controller is handling
   * @param     v the view that the controller is communicating to.
   */
  public Controller(NUPlannerSystem m, NUPlannerView v) {
    this.model = m;
    this.view = v;
    this.view.setListener(this);
    this.view.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      //read from the input textfield
      case "Schedule Button":
        // do something when the schedule button is pressed
        break;
      case "Create Button":
        // do something when the create button is pressed
        break;
    }
  }
}
