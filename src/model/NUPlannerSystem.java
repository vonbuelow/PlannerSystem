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
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if the event is already in the system
   */
  void addEventToAllSchedules(Event event);

  /**
   *
   * @param newUser
   */
  void addNewUser(Map<String, Schedule> newUser);

  /**
   * Modifies the name of the given event in the given user's schedule.
   * Changes it to the given event name.
   * @param event the existing event to modify
   * @param eventName new name to give the event
   * @param uid the user id of the user's schedule
   * @throws IllegalArgumentException if event, eventName, or uid is null
   * @throws IllegalStateException if event: does not exist in system,
   *     or is not in the given user's schedule, if eventName is the same as another
   *     event in given user's schedule, or if uid is empty, not in system, or not
   *     the host of the given event.
   */
  void modifyName(Event event, String eventName, String uid);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyTime(Event event, Time time, String uid);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyLocation(Event event, Location loc, String uid);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyInvitees(Event event, List<String> invitees, boolean toAdd, String uid);

  /**
   * Remove an existing event from the system. If event is removed from host's
   * schedule, remove the event from all invitees' schedules. Otherwise, remove
   * just from the invitees' schedule.
   * @param     event the event to be removed.
   */
  void removeEvent(Event event, String uid);

  /**
   * A copy of all users in a system and their schedules for toString purposes.
   * @return
   */
  Map<String, Schedule> usersSchedules();

  void addUser(File file);
}
