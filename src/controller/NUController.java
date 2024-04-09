package controller;

import model.NUPlannerSystem;

public interface NUController {

  /**
   * Executes running the planner, when exiting the program this method stops.
   * @param     model a non-null planner system method.
   * @throws IllegalArgumentException if the given model is null
   */
  void runPlanner(NUPlannerSystem model);

}
