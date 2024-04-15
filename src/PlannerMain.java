import controller.NUController;
import controller.NUPlannerGUIController;
import controller.strategy.ScheduleStrat;
import controller.strategy.StratFactory;
import model.CentralSystem;
import model.NUPlannerSystem;
import view.MainSystemFrame;
import view.NUPlannerView;

/**
 * Runs the planner system. Allows uploading schedules and editing user calendars.
 */
public class PlannerMain {

  /**
   * The main method to run the system.
   * @param     args the user input to control the systems actions.
   */
  public static void main(String[] args) {
    NUPlannerSystem model = new CentralSystem();
    NUPlannerView view = new MainSystemFrame(model);
    NUController controller = new NUPlannerGUIController(view);
    StratFactory stratFactory = new StratFactory();
    ScheduleStrat strat;

    if (args.length == 1) {
      strat = getScheduleStrat(args, stratFactory, model);
    }
    else if (args.length == 2) {
      strat = getScheduleStrat(args, stratFactory, model);
      if (args[1].equalsIgnoreCase("provider")) {
        // make the providers view
      }
      else {
        // make our view
      }
    }
    else {
      throw new IllegalArgumentException("Needs exactly 1-2 arguments");
    }
    controller.runPlanner(model, strat);
  }

  /**
   * Returns a strategy to use for scheduling events.
   * @param args command-line arguments to choose a strategy
   * @param stratFactory creator of strategies
   * @param model model of system to attach the strategy to
   * @return an automatic scheduling strategy for the planner system
   */
  private static ScheduleStrat getScheduleStrat(String[] args, StratFactory stratFactory,
                                                NUPlannerSystem model) {
    ScheduleStrat strat;
    if (args[0].equalsIgnoreCase("workhours")) {
      strat = stratFactory.makeStrat("workhours", model);
    }
    else if (args[0].equalsIgnoreCase("anytime")) {
      strat = stratFactory.makeStrat("anytime", model);
    }
    else {
      strat = stratFactory.makeStrat("workhours", model);
    }
    return strat;
  }
}
