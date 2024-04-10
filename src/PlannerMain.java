import controller.NUController;
import controller.NUPlannerGUIController;
import controller.strategy.AnyTimeStrat;
import controller.strategy.ScheduleStrat;
import controller.strategy.WorkHoursStrat;
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
    //String stratName = args[0];
    //ScheduleStrat strat;

    /*switch (stratName) {
      case "workday": strat = new WorkHoursStrat(model);
      case "anytime": strat = new AnyTimeStrat(model);
      default: strat = new AnyTimeStrat(model);
    }*/

    controller.runPlanner(model, new WorkHoursStrat(model));
  }
}
