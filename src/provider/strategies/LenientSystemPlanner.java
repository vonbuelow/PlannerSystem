package provider.strategies;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * System Planner type for the scheduling events leniently.
 * Hosts and at least one other user has to have no conflict for an event to be scheduled.
 */
public class LenientSystemPlanner extends SystemPlanner implements SystemInterface {

  public LenientSystemPlanner() {
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
      throw new IllegalArgumentException("Cannot schedule event because duration over 8 hours!");
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

  /**
   * Helper method for automatically schedule event.
   * @param duration of event
   * @param event the event that is made for checking if it can be added to user's schedule
   * @param remainingDuration the remaining duration during that day to check if it can be added
   *                          to user's schedule on that day.
   * @return true to break the loop if event is added to all users. returns false to continue the
   *         loop and check out different event times.
   */
  private boolean automaticallyScheduleEventHelper(int duration, Event event,
                                                   int remainingDuration) {
    if (!isConflictWithAllUsers(event) && duration <= 480 && duration <= remainingDuration) {
      addEventForAllParticipants(event);
      return true;
    } else if (isConflictWithAllUsers(event) && duration <= 480 && duration <= remainingDuration) {
      List<User> users = getUsersWithNoConflict(event);
      for (User user : users) {
        addEvent(user, event);
      }
      if (!users.isEmpty()) {
        return true;
      }
    } else {
      return false;
    }
    return false;
  }

  /**
   * Gets a list of users with no conflict. Host is the first part of the list.
   * followed by the rest of the participants.
   * @param event event that is being checked
   * @return a list of users.
   */
  private List<User> getUsersWithNoConflict(Event event) {
    String host = event.getHost();
    User hostUser = getUser(host);
    List<String> participants = event.getUsers();
    List<User> userListWithNoConflict = new ArrayList<>();

    if (!isConflict(event, hostUser)) {
      userListWithNoConflict.add(hostUser);
      for (int i = 1; i < participants.size(); i++) {
        List<User> userList = new ArrayList<>();
        User user = getUser(participants.get(i));
        userList.add(user);
        for (int j = 0; j < userList.size(); j++) {
          if (!isConflict(event, userList.get(j))) {
            userListWithNoConflict.add(userList.get(j));
          }
        }
      }
    }
    if (userListWithNoConflict.size() > 1) {
      return userListWithNoConflict;
    } else {
      return new ArrayList<>();
    }
  }
}