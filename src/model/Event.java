package model;

import java.util.List;
import java.util.Objects;

import model.eventfields.Location;
import model.eventfields.Time;

/**
 * An event in a user's schedule.
 * Has a name of the event, time, location, and list of those invited.
 */
public class Event implements EventRep {
  private String name;
  private Time time;
  private Location loc;
  private List<String> invitees;

  /**
   * An event which has a name, time, location, and those who are invited.
   * @param     name of the event rep. by a string.
   * @param     time of the event rep. by a class (Time).
   * @param     loc of the event rep. by a class (Location).
   * @param     invitees of the event rep. by a list of strings.
   * @throws IllegalArgumentException if the given list of invitees is null or empty
   */
  public Event(String name, Time time, Location loc, List<String> invitees) {
    this.name = Objects.requireNonNull(name);
    this.time = Objects.requireNonNull(time);
    this.loc = Objects.requireNonNull(loc);
    if (invitees == null || invitees.isEmpty()) { // Invariant: there is always a host for an event
      throw new IllegalArgumentException(
              "an event cannot be null and must have at least one invitee");
    }
    this.invitees = invitees;
  }



  // add in modification methods
  // set name
  // set time
  // set location
  // set invitees // ->> invariant would be not allowing the host to be changed

  @Override
  public String toString() {
    String inviteesList = "";

    for (String i : this.invitees) {
      inviteesList += i + "\n";
    }

    return "name: " + this.name
            + "\n" + this.time.toString()
            + "\n" + this.loc.toString()
            + "\ninvitees: " + inviteesList.stripTrailing();
  }

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

  @Override
  public Time getTime() {
    return this.time;
  }

  @Override
  public boolean overlapsWith(EventRep e) {
    return this.time.overlapsWith(e.getTime());
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<String> getInvitedUsers() {
    return this.invitees;
  }

  @Override
  public Location getLocation() {
    return this.loc;
  }
}

