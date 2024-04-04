package view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.NUFeature;
import model.ReadonlyNUPlannerSystem;

/**
 * Describes capabilities of event frame.
 */
public class EventFrame extends JFrame {
  private EventPanel panel;
  private NUFeature executer;

  EventFrame(String name, ReadonlyNUPlannerSystem model, NUFeature executer) {
    this.executer = executer;
    this.setTitle("Event Info!");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    panel = new EventPanel(name, model);
    this.add(panel);
    buttonLayout(this, model);
    this.pack();
    this.setVisible(true);
  }

  private void buttonLayout(EventFrame eventFrame, ReadonlyNUPlannerSystem model) {
    JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
    JButton modifyEvent = new JButton("Modify Event");
    eventButtonListener(modifyEvent, true, eventFrame);
    buttonPanel.add(modifyEvent);
    //createButton.addActionListener();
    JButton removeEvent = new JButton("Remove Event");
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
        // ERROR BOX ASF
        return;
      }

      if (modify) {
        // MODIFY EVENT: CREATE EVENT INSIDE OF THE
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