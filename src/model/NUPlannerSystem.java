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
   * @param     event The added event.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if the event is already in the system
   */
  void addEventToAllSchedules(EventRep event);

  /**
   * NEWWWWW.
   * Add an event to a user schedule.
   * @param event
   */
  void addEventToUserSchedules(String uid, EventRep event);

  /**
   * Adds in a new use to the central/NUPlannerSystem.
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
   * @param uid the user id of the user's schedule
   * @throws IllegalArgumentException if event, eventName, or uid is null
   * @throws IllegalStateException if event: does not exist in system,
   *     or is not in the given user's schedule, if eventName is the same as another
   *     event in given user's schedule, or if uid is empty, not in system, or not
   *     the host of the given event.
   */
  void modifyName(EventRep event, String eventName, String uid);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyTime(EventRep event, Time time, String uid);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyLocation(EventRep event, Location loc, String uid);

  /**
   * Change an existing event that is passed in to pull up.
   * @param     event the new event to change/edit.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd, String uid);

  /**
   * Remove an existing event from the system. If event is removed from host's
   * schedule, remove the event from all invitees' schedules. Otherwise, remove
   * just from the single invitee's schedule.
   * @param     event the event to be removed.
   * @param     uid the user id of the user.
   * @throws IllegalArgumentException if the given event is null
   * @throws IllegalStateException if the given event is not currently in the system
   */
  void removeEvent(EventRep event, String uid);


  void addUser(File file);
}
