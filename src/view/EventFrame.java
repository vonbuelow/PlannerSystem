package view;

import javax.swing.*;

import model.Event;
import model.EventRep;
import model.NUPlannerSystem;

/**
 * Describes capabilities of event frame.
 */
public class EventFrame extends JFrame {
  private String name, location, start, end;
  private JMenu isOnline, days;
  private JList<String> availableUsers;

  EventFrame(String selectedUser) {
    this.name = "";
    this.location = "";
    this.start = "";
    this.end = "";
    defaultOnline(this.isOnline);
    defaultDays(this.days);
    this.availableUsers = new JList<>(new String[]{selectedUser}); // this wouldn't be possible right?? because we need
                                                                   // at least one person to be the host.
  }

  private void defaultDays(JMenu days) {
  }

  private void defaultOnline(JMenu isOnline) {
  }


  EventFrame(EventRep event, NUPlannerSystem model) {
    this.name = event.getName();
    this.location = event.getLocation().getPlace();
    this.start = event.getTime().getStartTime();
    this.end = event.getTime().getEndTime();
    this.availableUsers = new JList<>(model.usersInSystem().toArray(new String[0]));
  }

  /*
  needs to show all relevant info of an event
  note: starting and ending time take in any string
  - the bottom most non-button component uses a JList
  - buttons to create, modify, or remove event
   */

  /**
   * Pseudocode:
   * pops up when?
   * - create/implement event
   * - click on existing event
   *   - pre-filled in w/ info
   * wishlist:
   * - close frame w/o ending program
   * - on close, "transfer" (to model) info when either adding or modifying
   *   - (save/send info somewhere?)
   *
   * Visual:
   * - elements are stacked above each other
   * ---------------------------------------------------------------
   * text                                   | menu
   *   event name                           |   is online/in person
   *   location                             |   start/end day
   *   time: start and end                  ------------------------
   * -------------------------------------- | JList
   *                                        |   available users
   *
   * - schedule event = all users in system
   * - create event = only invite some users
   *
   * - once create is clicked, event frame prints out "create event" then lists info
   * - remove event -> prints out remove event then info of event
   * - selected user is the remover (user is stored as field in controller)
   * - host is highlighted upon opening the event frame
   */

}
