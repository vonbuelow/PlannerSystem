package view;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import model.NUPlannerSystem;
import model.Schedule;

public class NUPlannerTextView implements NUPlannerView {
  private final NUPlannerSystem system;
  // consider generality with method calls on specific systems

  public NUPlannerTextView(NUPlannerSystem system) {
    this.system = Objects.requireNonNull(system);
  }

  @Override
  public String toString() {
    Appendable view = new StringBuilder();
    Map<String, Schedule> schedules = this.system.usersSchedules();
    for(Map.Entry<String, Schedule> schedule : schedules.entrySet()) {
      try {
        view.append(schedule.getValue().toString()).append("\n");
      }
      catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
    return view.toString().stripTrailing();
  }
}
