package model;

import java.util.List;

public class Schedule {
  String name;
  List<Event> events;

  Schedule(String name, List<Event> events) {
    this.name = name;
    this.events = events; // can be empty or have elements inside it
  }

}
