package model;

import java.util.List;

import model.eventfields.Location;
import model.eventfields.Time;

/**
 * Represents an event in a user's schedule.
 */
public interface EventRep {

  /**
   * Returns the time of the current event.
   * @return military time of the event
   */
  Time getTime();

  /**
   * Determines if the given event overlaps with this one.
   * This would mean a time conflict.
   * @param e the event that may overlap with current
   * @return true iff one event ends at the same time as the other starts
   */
  boolean overlapsWith(EventRep e);


  String getName();

  List<String> getInvitedUsers();

  Location getLocation();
}
