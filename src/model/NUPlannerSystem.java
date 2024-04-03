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
   * Saves the schedule of the users to an XML file to a given directory.
   * @param fileToSave place of where the users' schedule should be saved to
   * @throws IllegalArgumentException if the given file is null or doesn't exist
   */
  void saveSchedule(File fileToSave);

  /**
   * Add a new event to the central system.
   * Affecting the user's schedules who are invited.
   * NEW: Adding in a try-catch to not add if any invitees have time conflict.
   * @param event The added event.
   * @throws IllegalArgumentException if event is null
   * @throws IllegalStateException if the event is already in the system
   */
  void addEventToAllSchedules(EventRep event);

  /**
   * Add an event to a single user's schedule.
   * @param uid user id of the invitee to change the schedule of
   * @param event event to be added
   * @throws IllegalArgumentException if event or uid is null, or uid is empty
   * @throws IllegalStateException if given user cannot be found in the system
   */
  void addEventToInviteeSchedule(String uid, EventRep event);

  /**
   * Adds in a new user to the central/NUPlannerSystem.
   * Gives a mapping of their uid to their schedule.
   * All new events conflicting with already existing ones they're
   * invited to are not added to their schedule.
   * @param newUser user to be added to the system
   * @throws IllegalArgumentException if map is null/empty
   * @throws IllegalStateException if new user already exists in system
   */
  void addNewUser(Map<String, ScheduleRep> newUser);

  /**
   * Modifies the name of the given system event.
   * Changes it to the given event name.
   * @param event the existing event to modify
   * @param eventName new name to give the event
   * @throws IllegalArgumentException if event or eventName is null/empty
   * @throws IllegalStateException if event does not exist in system
   */
  void modifyName(EventRep event, String eventName);

  /**
   * Modifies the time of the given system event.
   * @param event the new event to change/edit.
   * @param time new time to set the event to
   * @throws IllegalArgumentException if event or time is null
   * @throws IllegalStateException if event does not exist in system, or time overlaps
   *     with another event in any invitee's schedule
   */
  void modifyTime(EventRep event, Time time);

  /**
   * Modifies the location of the given system event.
   * @param event the new event to change/edit.
   * @param loc new location of the event
   * @throws IllegalArgumentException if event or location is null
   * @throws IllegalStateException if event does not exist in system, if given location
   *     is the same as the current
   */
  void modifyLocation(EventRep event, Location loc);

  /**
   * Modifies the invitees of the given system event.
   * @param     event the new event to change/edit.
   * @param invitees users to be added or removed to/from the event
   * @param toAdd true: invitees are added, false: invitees are removed
   * @throws IllegalArgumentException if event or invitees is null/empty, trying to remove
   *     the host, or there are duplicated user ids in invitees
   * @throws IllegalStateException if event does not exist in system or the number of
   *     invitees to remove exceeds or is the number of current invitees
   */
  void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd);

  /**
   * Remove an existing event from the system. If event is removed from host's
   * schedule, remove the event from all invitees' schedules. Otherwise, remove
   * just from the single invitee's schedule.
   * Event should be updated from all users' schedules.
   * @param     event the event to be removed
   * @param     uid the id of the user
   * @throws IllegalArgumentException if the given event or uid is null or empty
   * @throws IllegalStateException if the given event is not currently in the system
   *     or the given uid is not invited to the given event
   */
  void removeEvent(EventRep event, String uid);

  /**
   * Allows client to give a user schedule as an XML file to the system.
   * XML file gets interpreted into a ScheduleRep.
   * @param file user schedule as xml file
   * @throws IllegalArgumentException if given file is null
   */
  void addUser(File file);
}
