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

    // restrict to only two arguments
    if(args.length != 2) {
      throw new IllegalArgumentException("Only allowed to input 2 arguments");
    }

    if(args[0].equalsIgnoreCase("provider")) {
      // make the providers view
    }
    else {
      // make our view
    }

    // check second args value?
    if (args[1].equalsIgnoreCase("workhours")) {
      strat = stratFactory.makeStrat("workhours", model);
    }
    else if (args[1].equalsIgnoreCase("anytime")) {
      strat = stratFactory.makeStrat("anytime", model);
    }
    else {
      strat = stratFactory.makeStrat("workhours", model);
    }

    controller.runPlanner(model, strat);
  }
}
