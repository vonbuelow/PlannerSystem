package controller;

import java.io.IOException;

import model.NUPlannerSystem;
import view.NUPlannerView;

public class NUPlannerGUIController implements NUController {

  private NUPlannerView view;
  private NUPlannerSystem model;
  private NUFeature features;

  public NUPlannerGUIController(NUPlannerView view) {
    this.view = view;
  }

  public void runPlanner(NUPlannerSystem model) {
    this.model = model;
    this.features = new NUFeature(model);
    try {
      this.view.setListener(this.features);
    } catch (IOException e) {
      // show this is a bad bad appendable for listener
    }
    try {
      this.view.display();
    } catch (IOException e) {
      // bad bad appendable for display
    }
  }

}
