import controller.NUController;
import controller.NUPlannerGUIController;
import controller.strategy.ScheduleStrat;
import controller.strategy.StratFactory;
import model.ModelFactory;
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
    ModelFactory factory = new ModelFactory();
    NUPlannerSystem model = factory.makeModel("sunday");
    StratFactory stratFactory = new StratFactory();
    ScheduleStrat strat = stratFactory.makeStrat("workhours", model);

    // just get the requested strategy
    if (args.length >= 1) {
      if (args[0].equalsIgnoreCase("workhours")) {
        strat = stratFactory.makeStrat("workhours", model);
      }
      else if (args[0].equalsIgnoreCase("anytime")) {
        strat = stratFactory.makeStrat("anytime", model);
      }
    }
    if (args.length == 2) {
      // if the second argument is provider
      if (args[1].equalsIgnoreCase("saturday")) {
        model = new ModelFactory().makeModel("saturday");
      }
    }
    else {
      throw new IllegalArgumentException("Needs exactly 1-2 arguments");
    }
    NUPlannerView view = new MainSystemFrame(model);
    NUController controller = new NUPlannerGUIController(view);
    controller.runPlanner(model, strat);
  }
}
