package controller.strategy;

import model.NUPlannerSystem;

public class StratFactory {

  public ScheduleStrat makeStrat(String strat, NUPlannerSystem model) {
    if (strat == null) {
      throw new IllegalArgumentException("cannot have a null strat");
    }
    else if (strat.equalsIgnoreCase("anytime")) {
      return new AnyTimeStrat(model);
    }
    else if (strat.equalsIgnoreCase("workhours")){
      return new WorkHoursStrat(model);
    }
    else {
      throw new IllegalArgumentException("not a valid strat");
    }
  }
}
