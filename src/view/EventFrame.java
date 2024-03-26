package view;

import java.awt.*;

import javax.swing.*;
import model.NUPlannerSystem;

/**
 * Describes capabilities of event frame.
 */
public class EventFrame extends JFrame {

  private JButton modifyEvent, removeEvent;
  EventPanel panel;

  EventFrame(String name, NUPlannerSystem model) {
    this.setTitle("Event Info!");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    panel = new EventPanel(name, model);
    this.add(panel);
    buttonLayout(this, model);
    this.pack();
    this.setVisible(true);
  }

  private void buttonLayout(EventFrame eventFrame, NUPlannerSystem model) {
      JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
      modifyEvent = new JButton("Modify Event");
      eventButtonListener(modifyEvent, true, eventFrame);
      buttonPanel.add(modifyEvent);
      //createButton.addActionListener();
      removeEvent = new JButton("Remove Event");
      eventButtonListener(removeEvent, false, eventFrame);
      buttonPanel.add(removeEvent);
      buttonPanel.setBackground(new Color(174, 200, 227));
      eventFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Listening for either removing or modifying an event.
   * @param     button the given button.
   * @param     modify if the boolean is modifying the event (true) or removing
   * @param     eventFrame this event frame to listen out for.
   */
  private void eventButtonListener(JButton button, boolean modify, EventFrame eventFrame) {
    button.addActionListener(e -> {
      String eventName = panel.getEventName();
      String location = panel.getLoc();
      String startDay = panel.getStartDay();
      String startTime = panel.getStartTime();
      String endDay = panel.getEndDay();
      String endTime = panel.getEndTime();
      String selectedUser = panel.getSelectedUser();

      if (eventName.isEmpty() || location.isEmpty() || startDay == null
              || startTime.isEmpty() || endDay == null || endTime.isEmpty()
              || selectedUser == null) {
        System.out.println("Error: One or more fields are empty.");
        return;
      }

      if (modify) {
        System.out.println("Modify event: " + eventName + ", "
                + location + ", " + startDay + ", " + startTime
                + ", " + endDay + ", " + endTime + ", Host: "
                + selectedUser);
        eventFrame.dispose();
      } else {
        System.out.println("Remove event: " + eventName + ", "
                + location + ", " + startDay + ", " + startTime
                + ", " + endDay + ", " + endTime
                + ", From user: " + selectedUser);
        eventFrame.dispose();
      }
    });
  }
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
