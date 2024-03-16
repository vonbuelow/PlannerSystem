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
    return Objects.hashCode(this);
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

  @Override
  public void modifyName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (name.isEmpty() || name.equals(this.name)) {
      throw new IllegalStateException("given name cannot be empty or same as the current");
    }
    this.name = name;
  }

  @Override
  public void modifyTime(Time time) {
    if (time == null) {
      throw new IllegalArgumentException("time cannot be null");
    }
    this.time = time;
  }

  @Override
  public void modifyLocation(Location loc) {
    if (loc == null) {
      throw new IllegalArgumentException("not a valid location");
    }
    this.loc = loc;
  }

  @Override
  public void modifyInvitees(List<String> invitees, boolean toAdd) {
    // modify events
  }
}

