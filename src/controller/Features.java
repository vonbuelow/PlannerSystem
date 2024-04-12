package controller;

import java.io.File;
import java.util.List;

import controller.strategy.ScheduleStrat;
import model.EventRep;
import model.eventfields.Location;

/**
 * Represents the public methods for features needed from a planner system, such as
 * adding a user or modifying events.
 */
public interface Features {

  /**
   * Adding in a new user schedule to the model.
   * The model being taken in from the constructor.
   * @param     file the file to add in to the system.
   */
  void addUser(File file);

  /**
   * Save all the users in the system to a given Directory.
   * @param     dir the directory to save all XML's to
   */
  void saveUsers(File dir);

  /**
   * Handle clicking on an event if there is one there and display the event frame.
   * @param     hour The hour the event is taking place
   * @param     day The day the event is taking place
   * @param     selectedUser The selected user of the event.
   */
  void handleClick(double hour, int day, String selectedUser);

  /**
   * Modify a given event depending on what has changed.
   * @param      event an event that has changed.
   */
  void modify(EventRep event);

  /**
   * Remove a given event, act depending on who is the host.
   * @param     event The event to remove.
   * @param     user The user that the event was removed based on.
   */
  void remove(EventRep event, String user);

  /**
   * Create the given event for all users invited.
   * @param      event The given event to create.
   */
  void create(EventRep event);

  /**
   * Schedule an event using the given strategy which was made at runtime.
   * @param     name The name of the event
   * @param     loc The location of the event
   * @param     duration The duration of the event
   * @param     invitees The invitees to the event
   * @param     strat The strategy to schedule events with
   */
  void schedule(String name, Location loc, int duration,
                List<String> invitees, ScheduleStrat strat);

}
