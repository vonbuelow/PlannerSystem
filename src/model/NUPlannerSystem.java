package model;

import java.io.File;
import java.util.List;
import java.util.Map;

import model.eventfields.Location;
import model.eventfields.Time;

/**
 * The interface of a NUPlannerSystem.
 * Will have behavior of adding, modifying, and removing events in a system.
 */
public interface NUPlannerSystem {
  /**
   * Saves the schedule of the user with the given name to an XML file.
   * @param name name of the user whose schedule is to be saved
   * @throws IllegalArgumentException if the given name is empty or null
   * @throws IllegalStateException if the given uid (name) cannot be found in the system
   */
  void saveSchedule(String name);

  /**
   * Add a new event to the central system.
   * Affecting the user's schedules who are invited.
   * @param     event The added event.
   * @throws IllegalArgumentException if event is null or
   */
  void addEventToAllSchedules(Event event);

  void addNewUser(Map<String, Schedule> newUser);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modify(Event event);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyName(Event event, String name);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyTime(Event event, Time time);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyLocation(Event event, Location loc);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyInvitees(Event event, List<String> invitees, boolean toAdd);

  /**
   * Remove an existing event from the system. If event is removed from host's
   * schedule, remove the event from all invitees' schedules. Otherwise, remove
   * just from the invitees' schedule.
   * @param     event the event to be removed.
   */
  void removeEvent(Event event);

  /**
   * A copy of all users in a system and their schedules for toString purposes.
   * @return
   */
  Map<String, Schedule> usersSchedules();

  void addUser(File file);
}
