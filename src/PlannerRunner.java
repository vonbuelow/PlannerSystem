import model.CentralSystem;
import model.ReadonlyNUPlannerSystem;
import view.NUPlannerTextView;
import view.NUPlannerView;

public class PlannerRunner {
  public static void main(String[] args) {
    ReadonlyNUPlannerSystem model = new CentralSystem();
    NUPlannerView view = new NUPlannerTextView(model);
    //view.setVisible(true);
  }
}
