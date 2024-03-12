package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// INVARIANT CONSIDERATIONS

/**
 * Representing the central system of a NUPlanner.
 * Contains a map of all schedules in the system.
 * Has a set of all users contained in the system.
 * A list of events in the system.
 */
public class CentralSystem implements NUPlannerSystem {
  private Map<String, Schedule> allSchedules; // all users -> their respective schedules
  private Set<String> allUsers; // all users
  private List<Event> eventList; // list of all events

  // default
  CentralSystem() {
    this.allSchedules = new HashMap<String, Schedule>();
    this.allUsers = this.allSchedules.keySet();
    this.eventList = new ArrayList<>();
  }

  // testing constructor
  CentralSystem(Map<String, Schedule> allS, List<Event> events) {
    this.allSchedules = allS;
    this.allUsers = this.allSchedules.keySet();
    this.eventList = events;
  }

  @Override
  public void add(Event event) {
    // will add an event to all schedules when applicable for invitees/host.
  }

  // new user/schedule -> existing event invitees.
  // if contained && doesnt conflict with currech sched. add event.

  @Override
  public void modify(Event event) {
    // changes some aspect of an existing event to be updated.
    // and updates all who are invited to the event as well.
  }

  @Override
  public void remove(Event event) {
    // removes an event from the host and all invitees schedules.
  }

  public Map<String, Schedule> usersSchedules() {
    Map<String, Schedule> copy = new HashMap<>();
    copy.putAll(allSchedules); // removes aliasing
    return copy;
  }
}
