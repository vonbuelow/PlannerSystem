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
  List<EventRep> events;

  public Schedule(String name, List<EventRep> events) {
    this.name = name;
    this.events = Objects.requireNonNull(events); // can be empty or have elements inside it
  }

  @Override
  public String scheduleOwner() {
    String uid = "";
    uid += this.name;
    return uid;
  }

  @Override
  public List<EventRep> eventsPlanned() {
    List<EventRep> ret = new ArrayList<>();
    ret.addAll(this.events);
    return ret;
  }

  @Override
  public String toString() {
    String userSchedule = "";
    userSchedule.concat("User: " + this.name + "\n");
    userSchedule.concat("Sunday: " + eventsOfDay(Day.SUNDAY) + "\n");
    userSchedule.concat("Monday: " + eventsOfDay(Day.MONDAY) + "\n");
    userSchedule.concat("Tuesday: " + eventsOfDay(Day.TUESDAY) + "\n");
    userSchedule.concat("Wednesday: " + eventsOfDay(Day.WEDNESDAY) + "\n");
    userSchedule.concat("Thursday: " + eventsOfDay(Day.THURSDAY) + "\n");
    userSchedule.concat("Friday: " + eventsOfDay(Day.FRIDAY) + "\n");
    userSchedule.concat("Saturday: " + eventsOfDay(Day.SATURDAY));

    return userSchedule;
  }

  /**
   * Returns a string of the events of the given day.
   * @param day day of the week
   * @return string representation of events based on the day of the week.
   */
  private String eventsOfDay(Day day) {
    String ret = "";
    List<EventRep> eventsOfGivenDay = new ArrayList<EventRep>();

    for (EventRep e : this.events) {
      if (e.getTime().getStartDayDefault().equals(day)) {
        eventsOfGivenDay.add(e);
      }
    }

    for (EventRep ev : eventsOfGivenDay) {
      ret += ev.toString();
    }

    return ret;
  }

  @Override
  public void addEvent(EventRep event) {
    eventNullException(event);
    if (!this.events.contains(event)
            && !overlapWith(event) && event.getInvitedUsers().contains(this.name)) {
      this.events.add(event);
    }
    else {
      throw new IllegalStateException(
              "event exists already, conflicts with another, or owner isn't invited");
    }
  }

  @Override
  public boolean overlapWith(EventRep event) {
    return this.events.stream().anyMatch(f -> f.overlapsWith(event));
  }

  @Override
  public void removeEvent(EventRep event) {
    eventNullException(event);
    if (!this.events.contains(event)) {
      throw new IllegalStateException("event must be in schedule");
    }
    this.events.remove(event);
  }

  /**
   * Throws an IllegalArgumentException if the given event is null.
   * @param event event in question
   * @throws IllegalArgumentException if given event is null
   */
  private static void eventNullException(EventRep event) {
    if (event == null) {
      throw new IllegalArgumentException("event cannot be null");
    }
  }

}
