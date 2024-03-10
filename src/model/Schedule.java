package model;

import java.util.List;

/**
 * A schedule of a user.
 * This contains the users name and the events in their schedule.
 */
public class Schedule {
  String name;
  List<Event> events;

  Schedule(String name, List<Event> events) {
    this.name = name;
    this.events = events; // can be empty or have elements inside it
  }

}
