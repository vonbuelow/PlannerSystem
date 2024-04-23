package model;

import java.util.List;

import model.eventfields.Location;
import model.eventfields.TimeRep;

public class SatStartEvent extends Event {

  /**
   * An event which has a name, time, location, and those who are invited.
   * @param     name of the event rep. by a string.
   * @param     time of the event rep. by a class (Time).
   * @param     loc of the event rep. by a class (Location).
   * @param     invitees of the event rep. by a list of strings.
   * @throws IllegalArgumentException if the given list of invitees is null or empty
   */
  public SatStartEvent(String name, TimeRep time, Location loc, List<String> invitees) {
    super(name, time, loc, invitees);
  }

}
