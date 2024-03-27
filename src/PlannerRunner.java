import model.CentralSystem;
import model.NUPlannerSystem;
import view.MainSystemFrame;
import view.NUPlannerView;

/**
 * The main system to run a planner system with a given model and view.
 */
public class PlannerRunner {

  /**
   * The main method to run the model and display the GUI.
   * @param     args the arguments a user passes into the controller.
   */
  public static void main(String[] args) {
    NUPlannerSystem model = new CentralSystem();
    NUPlannerView view = new MainSystemFrame(model);
    //Controller controller = new Controller(model, view);
    view.display();
  }
}
