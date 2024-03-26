package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The observations of the NUPlanner system.
 */
public interface ReadonlyNUPlannerSystem {

  /**
   * A copy of all users in a system and their schedules for toString purposes.
   * @return    a map of all the users to their schedules.
   */
  Map<String, ScheduleRep> usersSchedules();

  /**
   * What users (or user names) are in the planner.
   * A set (*can change to array.list - method chaining*) of users as String.
   * @return     a set of all users in a given system.
   */
  Set<String> usersInSystem();

  /**
   * NEW.
   * Does a given event overlap with anyone who is invited, in their existing schedule.
   * @param      event the event which gets passed along all schedules.
   * @return     Whether the event passed in over laps.
   */
  boolean doesOverlap(EventRep event);

  /**
   * NEW.
   * All the events a given user has in their schedule.
   * Is a copy of user list of events.
   * @param      uid the user id to find their events.
   * @return     the list of events this user is attending.
   */
  List<EventRep> getUserEvents(String uid);

  /**
   * NEW.
   * All the events in the current system.
   * Is a copy of system list of events.
   * @return the list of events in the system
   */
  List<EventRep> getSystemEvents();
}
