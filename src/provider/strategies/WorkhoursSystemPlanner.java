package provider.strategies;

import java.time.LocalDateTime;
import java.util.List;

/**
 * System Planner type for the scheduling events only on work hours.
 * Starting Monday at 9:00, events are scheduled for all users at the earliest time possible where
 * there is no time conflict for all users from 9:00 to 17:00 on Mondays to Fridays.
 */
public class WorkhoursSystemPlanner extends SystemPlanner implements SystemInterface {

  public WorkhoursSystemPlanner() {
    super();
  }

  @Override
  public void automaticallyScheduleEvent(String name, int duration, String location,
                                         Boolean isOnline, List<String> users) {
    super.automaticallyScheduleEvent(name, duration, location, isOnline, users);
    LocalDateTime startTime = LocalDateTime.of(2023, 1, 2, 0, 0);
    LocalDateTime endTime = LocalDateTime.of(2023, 1, 2, 0, 0).plusMinutes(duration);
    int count = 540;

    if (duration > 480) {
      throw new IllegalArgumentException("Cannot schedule event because the duration of event " +
              "is over 8 hours!");
    }
    bob:
    while (count < 6780) {
      while (count < 1020) { // monday
        Event event = new Event(name, startTime.plusMinutes(count), endTime.plusMinutes(count),
                location, isOnline, users);
        count += 1;
        int remainingDuration = (17 - event.getStartTime().getHour()) * 60;
        if (automaticallyScheduleEventHelper(duration, event, remainingDuration)) {
          break bob;
        }
      }
      count += 960;
      while (count < 2460) { // tuesday
        Event event = new Event(name, startTime.plusMinutes(count), endTime.plusMinutes(count),
                location, isOnline, users);
        count += 1;
        int remainingDuration = (17 - event.getStartTime().getHour()) * 60;
        if (automaticallyScheduleEventHelper(duration, event, remainingDuration)) {
          break bob;
        }
      }
      count += 960;
      while (count < 3900) { // wednesday
        Event event = new Event(name, startTime.plusMinutes(count), endTime.plusMinutes(count),
                location, isOnline, users);
        count += 1;
        int remainingDuration = (17 - event.getStartTime().getHour()) * 60;
        if (automaticallyScheduleEventHelper(duration, event, remainingDuration)) {
          break bob;
        }
      }
      count += 960;
      while (count < 5340) { // thursday
        Event event = new Event(name, startTime.plusMinutes(count), endTime.plusMinutes(count),
                location, isOnline, users);
        count += 1;
        int remainingDuration = (17 - event.getStartTime().getHour()) * 60;
        if (automaticallyScheduleEventHelper(duration, event, remainingDuration)) {
          break bob;
        }
      }
      count += 960;
      while (count < 6780) { // friday
        Event event = new Event(name, startTime.plusMinutes(count), endTime.plusMinutes(count),
                location, isOnline, users);
        count += 1;
        int remainingDuration = (17 - event.getStartTime().getHour()) * 60;
        if (automaticallyScheduleEventHelper(duration, event, remainingDuration)) {
          break bob;
        }
      }
      throw new IllegalArgumentException("Cannot schedule event that fits all users!");
    }
  }

  private boolean automaticallyScheduleEventHelper(int duration, Event event,
                                                   int remainingDuration) {
    if (!isConflictWithAllUsers(event) && duration <= 480 && duration <= remainingDuration) {
      addEventForAllParticipants(event);
      return true;
    }
    return false;
  }
}