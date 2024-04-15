package provider.controller;

import java.util.List;

import provider.model.EventInterface;

/**
 * Interface Features that represents all the functionalities that the user can use to affect view.
 * All void methods that when used change the view and connect the model to the view.
 */
public interface Features {

  /**
   * Loads in a file into the system that represents a schedule(s) and refreshes the
   * view to the new information added.
   */
  void addCalendar();

  /**
   * Saves a file representing the current user's schedule in a chosen directory.
   */
  void saveCalendars();

  /**
   * Allows client to select a user's schedule to view.
   * @param selectedUser the user that is selected to view the schedule
   */
  void selectUser(String selectedUser);

  /**
   * To populate the create event frame and display it in the view.
   */
  void createEventFrame();

  /**
   * To create the event into the model.
   * @param name of the event that is created.
   * @param startDay of the event that is created.
   * @param startTime of the event that is created.
   * @param endDay of the event that is created.
   * @param endTime of the event that is created.
   * @param location of the event that is created.
   * @param isOnline of the event that is created.
   * @param participants of the event that is created.
   */
  void createEvent(String name, String startDay, String startTime, String endDay, String endTime,
                   String location, boolean isOnline, List<String> participants);

  /**
   * To populate the schedule event frame and display it in the view.
   */
  void scheduleEventFrame();

  /**
   * To schedule an event that is either anytime, workhours, or lenient.
   * Takes in duration and finds a slot that is available to its users.
   * @param name of event that is scheduled.
   * @param location of event that is scheduled.
   * @param isOnline of event that is scheduled.
   * @param duration of event that is scheduled.
   * @param participants of event that is scheduled.
   */
  void scheduleEvent(String name, String location, boolean isOnline, String duration,
                     List<String> participants);

  /**
   * To populate the modify and remove event frame and display it in the view.
   * @param event that is getting modified or removed
   */
  void modifyOrRemoveEventFrame(EventInterface event);

  /**
   * To modify an event in the model.
   * @param name of the event that will be modified to.
   * @param startDayString of the event that will be modified to.
   * @param startTimeString of the event that will be modified to.
   * @param endDayString of the event that will be modified to.
   * @param endTimeString of the event that will be modified to.
   * @param location of the event that will be modified to.
   * @param isOnline of the event that will be modified to.
   * @param participants of the event that will be modified to.
   * @param oldEvent of the event that will be modified to.
   */
  void modifyEvent(String name, String startDayString, String startTimeString,
                   String endDayString, String endTimeString, String location,
                   boolean isOnline, List<String> participants, EventInterface oldEvent);

  /**
   * To remove an event on the view.
   * @param event that is going to be removed.
   */
  void removeEvent(EventInterface event);
}