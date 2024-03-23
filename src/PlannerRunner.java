import controller.Controller;
import model.CentralSystem;
import model.NUPlannerSystem;
import model.ReadonlyNUPlannerSystem;
import view.MainSystemFrame;
import view.NUPlannerTextView;
import view.NUPlannerView;

public class PlannerRunner {
  public static void main(String[] args) {
    NUPlannerSystem model = new CentralSystem();
    NUPlannerView view = new MainSystemFrame(model);
    //Controller controller = new Controller(model, view);
    view.display();
  }
}
