package model;

import java.util.List;
import java.util.Objects;

import model.eventfields.Location;
import model.eventfields.SatStartTime;
import model.eventfields.Time;
import model.eventfields.TimeRep;

public class SatStartEvent implements EventRep {


  private final String name;
  private final TimeRep time;
  private final Location loc;
  private final List<String> invitees;

  /**
   * An event which has a name, time, location, and those who are invited.
   * @param     name of the event rep. by a string.
   * @param     time of the event rep. by a class (Time).
   * @param     loc of the event rep. by a class (Location).
   * @param     invitees of the event rep. by a list of strings.
   * @throws IllegalArgumentException if the given list of invitees is null or empty
   */
  public SatStartEvent(String name, TimeRep time, Location loc, List<String> invitees) {
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
  public TimeRep getTime() {
    return null;
  }

  @Override
  public boolean overlapsWith(EventRep e) {
    return false;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public List<String> getInvitedUsers() {
    return null;
  }

  @Override
  public Location getLocation() {
    return null;
  }

  @Override
  public void modifyName(String name) {

  }

  @Override
  public void modifyTime(TimeRep time) {

  }

  @Override
  public void modifyLocation(Location loc) {

  }

  @Override
  public void modifyInvitees(List<String> invitees, boolean toAdd) {

  }
}
