package controller.strategy;

import model.NUPlannerSystem;

/**
 * The factory used for making strategies based on name.
 */
public class StratFactory {

  /**
   * Making the given strategy and passing in the model for the strategy to use.
   * @param      strat The given strategy.
   * @param      model The model the system is using.
   * @return The strategy the user wants to use.
   */
  public ScheduleStrat makeStrat(String strat, NUPlannerSystem model) {
    if (strat == null) {
      throw new IllegalArgumentException("cannot have a null strat");
    }
    else if (strat.equalsIgnoreCase("anytime")) {
      return new AnyTimeStrat(model);
    }
    else if (strat.equalsIgnoreCase("workhours")) {
      return new WorkHoursStrat(model);
    }
    else {
      throw new IllegalArgumentException("not a valid strat");
    }
  }
}
