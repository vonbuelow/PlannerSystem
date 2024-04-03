package controller;

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
    this.view.setListener(this.features);
    this.view.display();
  }

}
