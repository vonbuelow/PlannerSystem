package controller.strategy;

public class StratFactory {

  public ScheduleStrat makeStrat(String strat) {
    if (strat == null) {
      throw new IllegalArgumentException("cannot have a null strat");
    }
    else if (strat.equalsIgnoreCase("anytime")) {
      return new AnyTimeStrat();
    }
    else if (strat.equalsIgnoreCase("workhours")){
      return new WorkHoursStrat();
    }
    else {
      // what would this return???
      return new WorkHoursStrat();
    }
  }
}
