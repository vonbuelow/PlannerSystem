package controller;

import controller.strategy.ScheduleStrat;
import model.NUPlannerSystem;

/**
 * Starts the program by connecting all aspects of MVC along with a strategy for
 * scheduling events.
 */
public interface NUController {
  /**
   * Executes running the planner, when exiting the program this method stops.
   * @param     model a non-null planner system method.
   * @throws IllegalArgumentException if the given model is null
   */
  void runPlanner(NUPlannerSystem model, ScheduleStrat strat);

}
