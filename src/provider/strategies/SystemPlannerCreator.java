package provider.strategies;

/**
 * System Planner creator class that allows the three planner types to be used.
 */
public class SystemPlannerCreator {

  /**
   * Returns an instance of a subclasss of SystemInterface depending on the value of the parameter.
   */
  public enum PlannerType {
    ANYTIME, WORKHOURS, LENIENT;
  }

  /**
   * Create planner method that uses switch to check for input of planner type.
   * @param type planner made.
   * @return a system planner
   */
  public static SystemInterface createPlanner(PlannerType type) {
    switch (type) {
      case ANYTIME:
        return new AnytimeSystemPlanner();
      case WORKHOURS:
        return new WorkhoursSystemPlanner();
      case LENIENT:
        return new LenientSystemPlanner();
      default:
        throw new IllegalArgumentException("Invalid Command. Try Again!");
    }
  }
}

