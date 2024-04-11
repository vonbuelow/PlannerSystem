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

    if (args[0].equalsIgnoreCase("workday")) {
      strat = stratFactory.makeStrat("workday", model);
    }
    else if (args[0].equalsIgnoreCase("anytime")) {
      strat = stratFactory.makeStrat("anytime", model);
    }
    else {
      strat = stratFactory.makeStrat("workday", model);
    }

    controller.runPlanner(model, strat);
  }
}
