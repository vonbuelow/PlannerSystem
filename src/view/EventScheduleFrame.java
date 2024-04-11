package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Features;
import model.EventRep;
import model.ReadonlyNUPlannerSystem;

/**
 * This represents the event schedule frame which pops up when clicking on an event.
 * The frame has two buttons to either remove or modify the event.
 */
public class EventScheduleFrame extends JFrame {
  private EventSchedulePanel panel;

  /**
   * Construct an event schedule frame.
   * @param     name the name of the selected user.
   * @param     model the given model.
   * @param     executer the features interface.
   * @param     event the given event to show.
   */
  EventScheduleFrame(String name, ReadonlyNUPlannerSystem model,
                     Features executer, EventRep event) {
    this.setTitle("Event Info!");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setPanel(this, name, model, event);
    setButton(this, model);
    this.pack();
    this.setVisible(true);
  }


  /**
   * Create the given panel and its respective parameters to this frame.
   * @param     frame the given frame to add the panel to.
   * @param     name The name of the selected user.
   * @param     model the given model to work off of.
   * @param     event the given event to set the panel to.
   */
  public void setPanel(EventScheduleFrame frame,  String name,
                       ReadonlyNUPlannerSystem model, EventRep event) {
    panel = new EventSchedulePanel(name, event, model);
    frame.add(panel);
  }


  /**
   * Set up the buttons for this event frame, with just schedule.
   * @param     frame the given frame to add the buttons to.
   * @param     model the given model to work off of.
   */
  public void setButton(EventScheduleFrame frame, ReadonlyNUPlannerSystem model) {
    JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
    JButton modifyEvent = new JButton("Modify Event");
    eventButtonListener(modifyEvent, true, frame);
    buttonPanel.add(modifyEvent);
    JButton removeEvent = new JButton("Remove Event");
    eventButtonListener(removeEvent, false, frame);
    buttonPanel.add(removeEvent);
    buttonPanel.setBackground(new Color(174, 200, 227));
    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Listening for either removing or modifying an event.
   * @param     button the given button.
   * @param     modify if the event should be modified
   * @param     eventFrame this event frame to listen out for.
   */
  private void eventButtonListener(JButton button, boolean modify, EventScheduleFrame eventFrame) {
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
        // ERROR BOX ASF
        return;
      }
      //makeEvent()
      //EventRep event = new Event(eventName, );

      if (modify) {
        System.out.print("modify");
        //this.executer.modify(event);
        // MODIFY EVENT: CREATE EVENT INSIDE OF THE
        eventFrame.dispose();
      }
      else {
        System.out.print("remove");
        //this.executer.remove(event);
        eventFrame.dispose();
      }
    });
  }
}
