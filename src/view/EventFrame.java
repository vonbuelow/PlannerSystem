package view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Features;
import controller.NUFeature;
import model.ReadonlyNUPlannerSystem;

/**
 * Describes capabilities of event frame.
 */
public class EventFrame extends JFrame{
  private EventPanel panel;
  private Features executer;
  private String name;
  private ReadonlyNUPlannerSystem model;



  /**
   * The frame of an event with lays out more components within the frame.
   * This includes buttons and an event panel to display event information.
   * @param     name The name of the selected use.
   * @param     model The model this system is using as a READ ONLY VERSION.
   * @param     executer The features interface if the model has to be changed in any way.
   */
  EventFrame(String name, ReadonlyNUPlannerSystem model, Features executer) {
    this.name = name;
    this.model = model;
    this.executer = executer;
    this.setTitle("Event Info!");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setPanel(this, name, model);
    setButton(this, model);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Create the given panel and its respective parameters to this frame.
   * @param     frame
   * @param     name
   * @param     model
   */
  void setPanel(EventFrame frame,  String name, ReadonlyNUPlannerSystem model) {
    panel = new EventPanel(name, model);
    this.add(panel);
  }

  /**
   *
   * @param frame
   * @param model
   */
  void setButton(EventFrame frame, ReadonlyNUPlannerSystem model) {
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
      }
      else {
        System.out.println("Remove event: " + eventName + ", "
                + location + ", " + startDay + ", " + startTime
                + ", " + endDay + ", " + endTime
                + ", From user: " + selectedUser);
        eventFrame.dispose();
      }
    });
  }

}