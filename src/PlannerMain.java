import controller.NUController;
import controller.NUPlannerGUIController;
import controller.strategy.ScheduleStrat;
import controller.strategy.StratFactory;
import model.CentralSystem;
import model.NUPlannerSystem;
import model.adapters.ModelAdapter;
import provider.view.MainSystemFrameInterface;
import view.MainSystemFrame;
import view.NUPlannerView;
import view.adapter.ViewAdapter;

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
    StratFactory stratFactory = new StratFactory();
    ScheduleStrat strat = stratFactory.makeStrat("workhours", model);

    // just get the requested strategy
    if (args.length <= 1) {
      if (args[0].equalsIgnoreCase("workhours")) {
        strat = stratFactory.makeStrat("workhours", model);
      }
      else if (args[0].equalsIgnoreCase("anytime")) {
        strat = stratFactory.makeStrat("anytime", model);
      }
    }
    // otherwise see if they want the provider view
    if (args.length == 2) {
      // if the second argument is provider
      if (args[1].equalsIgnoreCase("provider")) {
        MainSystemFrameInterface msf = new provider.view.MainSystemFrame(new ModelAdapter(model));
        view = new ViewAdapter(msf, new ModelAdapter(model));
      }
    }
    // if the argument is more than 1 or 2 then NO
    else {
      throw new IllegalArgumentException("Needs exactly 1-2 arguments");
    }

    NUController controller = new NUPlannerGUIController(view);
    controller.runPlanner(model, strat);
  }
}
