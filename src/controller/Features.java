package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import model.EventRep;

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
   * Add an event to all users schedules in the system.
   * @param     event the event to add to everyone's schedules.
   */
  //void addEventAllScheds(EventRep event);

  /**
   * Add an event to the schedules of those who were invited.
   * @param     invitees those who were invited to the event.
   * @param     event the event to add to the invitees schedules.
   */
  // void addEventInvitee(List<String> invitees, EventRep event);

  // continue to add the rest of the methods inside of the central system class
  // more about the buttons than the model

}
