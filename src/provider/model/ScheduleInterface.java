package model;

import java.util.List;

import provider.model.EventInterface;

/**
 * Class representing a schedule.
 * Holds all the events that a user is invited to.
 * Events in a schedule can be added, modified and removed.
 */
public interface ScheduleInterface {

  /**
   * Retrieves all the participants from all events in the schedule.
   * @return a list of all the participants
   */
  List<String> getEventListAllParticipants();

  /**
   * Retrieves the list of events from the schedule.
   * @return a list of all events
   */
  List<EventInterface> getEventList();

  /**
   * Adds a list of events to a schedule.
   * @param eventList the list of events to be added
   */
  void addEventList(List<EventInterface> eventList);

  /**
   * Adds a single event to a schedule.
   * @param event the event to be added
   */
  void addEvent(EventInterface event);

  /**
   * Removes a single event from a schedule.
   * @param event the event to be removed
   */
  void removeEvent(EventInterface event);
}
