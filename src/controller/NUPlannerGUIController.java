package controller;

import java.io.IOException;
import java.util.Objects;

import model.NUPlannerSystem;
import view.NUPlannerView;

public class NUPlannerGUIController implements NUController {
  private final NUPlannerView view;
  private NUPlannerSystem model;
  private Features features;

  public NUPlannerGUIController(NUPlannerView view) {
    this.view = view;
  }

  public void runPlanner(NUPlannerSystem model) {
    this.model = Objects.requireNonNull(model);
    this.features = new NUFeature(model, view);
    try {
      this.view.setListener(this.features);
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
