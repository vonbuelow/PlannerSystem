package model;

import java.util.List;

public interface ScheduleRep {
  /**
   * Returns the name of the user that owns this schedule.
   * This does not promote alteration since it is not a reference
   * to the value of the name. *USED MAINLY BY THE WRITER*
   * @return the user id of the owner
   */
  String scheduleOwner();

  /**
   * Returns a list of the events from the current schedule.
   * Giving access to the WRITER to the events listed for the user.
   * Attempting to minimize too many permissions for modification.
   * @return    A clone of the existing list of events.
   */
  List<Event> eventsPlanned();

  /**
   * Adds the given event to the current schedule
   * @param event
   */
  void addEvent(Event event);

  /**
   * Removes the given event from this schedule's user, if it exists in the
   * schedule.
   * @param event event to be removed
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in schedule or does not have
   *     invitees
   */
  void removeEvent(Event event);
}