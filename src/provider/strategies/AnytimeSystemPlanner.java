package provider.strategies;

import java.time.LocalDateTime;
import java.util.List;

/**
 * System Planner type for the scheduling events anytime.
 * Starting sunday at 00:00, events are scheduled for all users at the earliest time possible where
 * there is no time conflict for all users.
 */
public class AnytimeSystemPlanner extends SystemPlanner implements SystemInterface {
  public AnytimeSystemPlanner() {
    super();
  }

  @Override
  public void automaticallyScheduleEvent(String name, int duration, String location,
                                         Boolean isOnline, List<String> users) {
    super.automaticallyScheduleEvent(name, duration, location, isOnline, users);

    LocalDateTime startTime = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime endTime = LocalDateTime.of(2023, 1, 1, 0, 0).plusMinutes(duration);
    int count = 0;

    while (count < 10080) {
      Event event = new Event(name, startTime.plusMinutes(count), endTime.plusMinutes(count),
              location, isOnline, users);
      count += 1;
      if (!isConflictWithAllUsers(event)) {
        try {
          addEventForAllParticipants(event);
        } catch (Exception e) {
          // your mom
        }
        break;
      }
    }
  }
}
