package model;

import java.util.List;
import java.util.Objects;

import model.eventfields.Day;

/**
 * A schedule of a user.
 * This contains the users name and the events in their schedule.
 */
public class Schedule {
  String name;
  List<Event> events;

  Schedule(String name, List<Event> events) {
    this.name = name;
    this.events = Objects.requireNonNull(events); // can be empty or have elements inside it
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
    
  }

  // filter events by days of the week



  // remove an event, based on if this user is the first in the list of the invitess
  // then it is removed just from their schedule, where to implement this though
}
