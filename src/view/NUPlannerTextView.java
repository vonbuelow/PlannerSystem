package view;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import controller.NUFeature;
import model.ReadonlyNUPlannerSystem;
import model.ScheduleRep;

/**
 * A textual view of the NUPlannerSystem for testing purposes and correctness.
 * The view represented by overriding the toString method.
 */
public class NUPlannerTextView implements NUPlannerView {
  private final ReadonlyNUPlannerSystem system;

  /**
   * Takes in a read-only version of the planner system to create a view.
   * @param system read-only planner system
   */
  public NUPlannerTextView(ReadonlyNUPlannerSystem system) {
    this.system = Objects.requireNonNull(system);
  }

  @Override
  public String toString() {
    Appendable view = new StringBuilder();
    Map<String, ScheduleRep> schedules = this.system.usersSchedules();
    for (Map.Entry<String, ScheduleRep> entry : schedules.entrySet()) {
      try {
        view.append(entry.getValue().toString()).append("\n");
      }
      catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
    return view.toString().stripTrailing();
  }

  @Override
  public void setListener(NUFeature executer) {
    // do nothing because this is the console view
  }

  @Override
  public void display() {
    // do nothing because this is the console view
  }
}
