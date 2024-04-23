package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.eventfields.Location;
import model.eventfields.TimeRep;

/**
 * An event in a user's schedule.
 * Has a name of the event, time, location, and list of those invited.
 */
public class Event implements EventRep {
  protected String name;
  protected TimeRep time;
  protected Location loc;
  protected final List<String> invitees;

  /**
   * An event which has a name, time, location, and those who are invited.
   * @param     name of the event rep. by a string.
   * @param     time of the event rep. by a class (Time).
   * @param     loc of the event rep. by a class (Location).
   * @param     invitees of the event rep. by a list of strings.
   * @throws IllegalArgumentException if the given list of invitees is null or empty
   */
  public Event(String name, TimeRep time, Location loc, List<String> invitees) {
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
    if (o instanceof EventRep) {
      EventRep e = (Event)o;

      return this.name.equals(e.getName()) && this.time.equals(e.getTime())
              && this.loc.equals(e.getLocation()) && this.invitees.equals(e.getInvitedUsers());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this);
  }

  @Override
  public TimeRep getTime() {
    return this.time;
  }

  @Override
  public boolean overlapsWith(EventRep e) {
    if (e == null) {
      throw new IllegalArgumentException("event cannot be null");
    }
    return this.time.overlapsWith(e.getTime());
  }

  @Override
  public String getName() {
    String name = "";
    name += this.name;
    return name;
  }

  @Override
  public List<String> getInvitedUsers() {
    List<String> users = new ArrayList<>();
    users.addAll(this.invitees);
    return users;
  }

  @Override
  public Location getLocation() {
    return this.loc;
  }

  @Override
  public void modifyName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("name cannot be null/empty");
    }
    if (name.equals(this.name)) {
      throw new IllegalStateException("given name cannot be same as the current");
    }
    this.name = name;
  }

  @Override
  public void modifyTime(TimeRep time) {
    if (time == null) {
      throw new IllegalArgumentException("time cannot be null");
    }
    if (time.equals(this.time)) {
      throw new IllegalStateException("new time cannot be same as current");
    }
    this.time = time;
  }

  @Override
  public void modifyLocation(Location loc) {
    if (loc == null) {
      throw new IllegalArgumentException("not a valid location");
    }
    if (loc.equals(this.loc)) {
      throw new IllegalStateException("new location cannot be same as current");
    }
    this.loc = loc;
  }

  @Override
  public void modifyInvitees(List<String> invitees, boolean toAdd) {
    if (invitees == null || invitees.isEmpty()) {
      throw new IllegalArgumentException("invitees cannot be null or empty");
    }
    if (toAdd) {
      this.invitees.addAll(invitees);
    }
    else {
      for (String user: invitees) {
        this.invitees.remove(user);
      }
    }
  }
}

