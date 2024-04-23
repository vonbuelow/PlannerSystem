package model;

import java.util.List;

import model.eventfields.Location;
import model.eventfields.Time;
import model.eventfields.TimeRep;

/**
 * Represents an event in a user's schedule.
 */
public interface EventRep {

  /**
   * Returns the time of the current event.
   * @return military time of the event
   */
  TimeRep getTime();

  /**
   * Determines if the given event overlaps with this one.
   * This would mean a time conflict.
   * @param e the event that may overlap with current
   * @return true iff one event ends at the same time as the other starts
   * @throws IllegalArgumentException if event is null
   */
  boolean overlapsWith(EventRep e);

  /**
   * Gets the name of the current event. *USED FOR WRITER*
   * @return event name
   */
  String getName();

  /**
   * Returns a list of invitees for this event. *USED IN SCHEDULE*
   * @return list of event's invitees' names
   */
  List<String> getInvitedUsers();

  /**
   * Returns the location of this event. *USED FOR WRITER*
   * @return location of the event
   */
  Location getLocation();

  /**
   * Modifies the name of the current event to the given.
   * @param name new name of the event
   * @throws IllegalArgumentException if the name is null/empty
   * @throws IllegalStateException if the name is the same as the event name
   */
  void modifyName(String name);

  /**
   * Modifies the time of the current event to the given.
   * @param time new time of the event
   * @throws IllegalArgumentException if the time is null
   * @throws IllegalStateException if the time is the same as the event's current time
   */
  void modifyTime(TimeRep time);

  /**
   * Modifies the location of the current event to the given.
   * @param loc new location of the event
   * @throws IllegalArgumentException if the location is null
   * @throws IllegalStateException if the location is the same as the
   *     event's current location
   */
  void modifyLocation(Location loc);

  /**
   * Modifies the invitees of the current event to the given.
   * @param invitees list of invitees to event to modify
   * @param toAdd whether the given invitees are to be added or removed from event
   *              (if to add, true, false otherwise)
   * @throws IllegalArgumentException if the list of invitees is null/empty
   */
  void modifyInvitees(List<String> invitees, boolean toAdd);
}
