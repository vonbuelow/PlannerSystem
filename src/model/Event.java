package model;

import model.eventFields.Location;
import model.eventFields.Time;

public class Event {
  String name;
  Time time;
  Location loc;
  String uid;

  // i think User should be a string since it is a id
  Event(String name, Time time, Location loc, String uid) {
    this.name = name;
    this.time = time;
    this.loc = loc;
    this.uid = uid;
  }

}

