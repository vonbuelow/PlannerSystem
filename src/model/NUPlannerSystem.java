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
public interface NUPlannerSystem extends ReadonlyNUPlannerSystem {

  /**
   * Saves the schedule of the user with the given name to an XML file.
   * @param fileToSave place of where the users' schedule should be saved to
   * @throws IllegalArgumentException if the given folder does not exist
   */
  void saveSchedule(File fileToSave);

  /**
   * Add a new event to the central system.
   * Affecting the user's schedules who are invited.
   * NEW: Adding in a try-catch to allow for some but not all to be invited if no overlap.
   * @param     event The added event.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if the event is already in the system
   */
  void addEventToAllSchedules(EventRep event);

  /**
   * Add an event to a single user's schedule.
   * @param event event to be added
   * @throws IllegalArgumentException if event or uid is null, or uid is empty
   * @throws IllegalStateException if given user cannot be found in the system
   */
  void addEventToInviteeSchedule(String uid, EventRep event);

  /**
   * Adds in a new user to the central/NUPlannerSystem.
   * @param newUser user to be added to the system
   * @throws IllegalArgumentException if map is null
   * @throws IllegalStateException if new user already exists in system
   */
  void addNewUser(Map<String, ScheduleRep> newUser);

  /**
   * Modifies the name of the given event in the given user's schedule.
   * Changes it to the given event name.
   * @param event the existing event to modify
   * @param eventName new name to give the event
   * @throws IllegalArgumentException if event or eventName is null
   * @throws IllegalStateException if event: does not exist in system, if eventName is the same as another
   *     event in given user's schedule, or if uid is empty, not in system, or not
   *     the host of the given event.
   */
  void modifyName(EventRep event, String eventName);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event or time is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyTime(EventRep event, Time time);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event or location is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyLocation(EventRep event, Location loc);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event, invitees, or uid is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd, String uid);

  /**
   * Remove an existing event from the system. If event is removed from host's
   * schedule, remove the event from all invitees' schedules. Otherwise, remove
   * just from the single invitee's schedule.
   * Event should be updated from all users' schedules.
   * @param     event the event to be removed.
   * @param     uid the user id of the user.
   * @throws IllegalArgumentException if the given event or uid is null or empty
   * @throws IllegalStateException if the given event is not currently in the system
   *     or the given uid is not invited to the given event
   */
  void removeEvent(EventRep event, String uid);


  void addUser(File file);
}
