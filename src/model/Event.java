package model;

import java.util.List;

import model.eventfields.Location;
import model.eventfields.Time;

/**
 * An event in a user's schedule.
 * Has a name of the event, time, location, and list of those invited.
 */
public class Event {
  private String name;
  private Time time;
  private Location loc;
  private List<String> invitees;

  Event(String name, Time time, Location loc, List<String> invitees) {
    this.name = name;
    this.time = time;
    this.loc = loc;
    this.invitees = invitees;
  }



}

