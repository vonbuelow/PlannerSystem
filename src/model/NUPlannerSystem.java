package model;

import java.io.File;
import java.util.Map;

/**
 * The interface of a NUPlannerSystem.
 * Will have behavior of adding, modifying, and removing events in a system.
 */
public interface NUPlannerSystem {

  /**
   * Add a new event to the central system.
   * Affecting the user's schedules who are invited.
   * @param     event The added event.
   */
  void add(Event event);

  void add(Map<String, Schedule> newUser);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   */
  void modify(Event event);

  /**
   * Remove an existing event from the system.
   * @param     event the event to be removed.
   */
  void remove(Event event);

  /**
   * A copy of all users in a system and their schedules for toString purposes.
   * @return
   */
  Map<String, Schedule> usersSchedules();

  void upload(File file);
}
