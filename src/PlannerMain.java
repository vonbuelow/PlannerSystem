import controller.NUController;
import controller.NUPlannerGUIController;
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

    // COMMAND LINE ARGUMENTS
    // STRATEGY FACTORY
    // PASSING IN THE STRAT TO THE MODEL SOMEHOW ???

    // 1. prompt
    // 2. make
    // 3. pass in to controller
    //
    // calling the
    NUPlannerSystem model = new CentralSystem();
    NUPlannerView view = new MainSystemFrame(model);
    NUController controller = new NUPlannerGUIController(view);
    controller.runPlanner(model);
  }
}
