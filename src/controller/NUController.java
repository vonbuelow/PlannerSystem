package controller;

import java.io.IOException;

import model.NUPlannerSystem;

public interface NUController {

  /**
   * Executes running the planner, when exiting the program this method stops.
   * @param     model a non-null planner system method.
   */
  void runPlanner(NUPlannerSystem model);

}
