package controller;

import java.io.IOException;
import java.util.Objects;

import controller.strategy.ScheduleStrat;
import model.NUPlannerSystem;
import view.NUPlannerView;

/**
 * Controller to mediate between a GUI view and model.
 */
public class NUPlannerGUIController implements NUController {
  private final NUPlannerView view;

  /**
   * Makes a controller, given the view for a planner system.
   * @param view view for a planner system
   */
  public NUPlannerGUIController(NUPlannerView view) {
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public void runPlanner(NUPlannerSystem model, ScheduleStrat strat) {
    NUPlannerSystem model1 = Objects.requireNonNull(model);
    Features features = new NUFeature(model1, view);
    try {
      this.view.setListener(features);
    }
    catch (IOException e) {
      // show this is a bad bad appendable for listener
    }
    try {
      this.view.display();
    }
    catch (IOException e) {
      // bad bad appendable for display
    }
  }

}
