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

  public Event(String name, Time time, Location loc, List<String> invitees) {
    this.name = name;
    this.time = time;
    this.loc = loc;
    this.invitees = invitees;
  }


  // add in modification methods
  // set name
  // set time
  // set location
  // set invitees // ->> invariant would not allowing the host to be changed

  @Override
  public boolean equals(Object o) {
    if (o instanceof Event) {
      Event e = (Event)o;

      return this.name.equals(e.name) && this.time.equals(e.time)
              && this.loc.equals(e.loc) && this.invitees.equals(e.invitees);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return 37 * name.length() * invitees.size() * invitees.size();
  }

}

