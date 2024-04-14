package provider.model;

import java.time.DayOfWeek;
import java.util.List;

/**
 * A model representing the Planner System.
 * Features the functionalities required like uploading and saving a user's
 * schedule, creating and adding users to the system, event modification, etc.
 */
public interface SystemInterface extends ReadOnlySystemInterface {

  /**
   * Upload an XML file representing a user's schedule.
   * @param fileName the file to be read
   */
  void uploadXML(String fileName);

  /**
   * Save a user's schedule to an XML file.
   * @param fileName the file name to be saved
   * @param user the user to save the file for
   */
  void saveUserSchedule(String fileName, User user);

  /**
   * Create a user and add them to the system.
   * @param username name of the user
   */
  void createUser(String username);

  /**
   * Add an existing user to the system.
   * @param user the user to be added
   */
  void addUser(User user);

  /**
   * Add an event to a given user's schedule.
   * @param user the user to add the event for
   * @param event the event to be added
   */
  void addEvent(User user, Event event);

  /**
   * Creates an event.
   * @param name the name of the event
   * @param startDayEnum the start day of the event
   * @param startTimeString the start time of the event
   * @param endDayEnum the end day of the event
   * @param endTimeString the end time of the event
   * @param location the location of the event
   * @param isOnline is event online
   * @param users list of users invited to event
   */
  void createEvent(String name, DayOfWeek startDayEnum, String startTimeString,
                   DayOfWeek endDayEnum, String endTimeString,
                   String location, Boolean isOnline, List<String> users);

  /**
   * Modifies an event for a user with a new event.
   * @param user the user to modify the event for
   * @param oldEvent the old event to be modified
   * @param newEvent the new version of the event
   */
  void modifyEvent(User user, Event oldEvent, Event newEvent);

  /**
   * Removes an event from a given user's schedule.
   * @param user the user to remove the event for
   * @param event the event to be removed
   */
  void removeEvent(User user, Event event);

  /**
   * Automatically schedule an event for a user.
   * Will be implemented in a future assignment.
   */
  void automaticallyScheduleEvent(String name, int duration, String location,
                                  Boolean isOnline, List<String> users);

}
