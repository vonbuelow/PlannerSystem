package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.eventfields.Day;

/**
 * A schedule of a user.
 * This contains the users name and the events in their schedule.
 */
public class Schedule implements ScheduleRep {
  String name;
  List<Event> events;

  public Schedule(String name, List<Event> events) {
    this.name = name;
    this.events = Objects.requireNonNull(events); // can be empty or have elements inside it
  }

  @Override
  public String scheduleOwner() {
    return this.name;
  }

  /**
   * Giving access to the WRITER to the events listed for the user.
   * Attempting to minimize too many permissions for modification.
   * @return    A clone of the existing list of events.
   */
  public List<Event> eventsPlanned() {
    List<Event> ret = new ArrayList<>();
    ret.addAll(this.events);
    return ret;
  }



  @Override
  public String toString() {
    String userSchedule = "";
    userSchedule.concat("User: " + this.name + "\n");
    userSchedule.concat("Sunday: " + eventOfDay(Day.SUNDAY));
    // additional days of the week
    return userSchedule;
  }

  private String eventOfDay(Day day) {
    return "";
  }

  public void addEvent(Event event) {
    if (!this.events.contains(event)
            && this.events.stream().noneMatch(f -> f.overlapsWith(event))) {
      this.events.add(event);
    }
    throw new IllegalStateException("event exists already or conflicts with another");
  }

  @Override
  public void removeEvent(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("event cannot be null");
    }
    if (!this.events.contains(event)) {
      throw new IllegalStateException("event must be in schedule");
    }
    if (event.getInvitedUsers().isEmpty()) {
      throw new IllegalStateException("event must have invitees to remove");
    }
    if (this.name.equals(event.getInvitedUsers().get(0))) {

    }
  }

  // filter events by days of the week



  // remove an event, based on if this user is the first in the list of the invitess
  // then it is removed just from their schedule, where to implement this though
}
