package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.Features;
import model.ReadonlyNUPlannerSystem;

/**
 * Representing a scheduling event frame.
 * This frame pops up when a user wants to schedule an event.
 * The frame only allows the user to either X out or schedule.
 */
public class SchedulingEventFrame extends JFrame {
  private SchedulingEventPanel panel;

  /**
   * The frame of an event with lays out more components within the frame.
   * This includes buttons and an event panel to display event information.
   * @param     name The name of the selected use.
   * @param     model The model this system is using as a READ ONLY VERSION.
   * @param     executer The features interface if the model has to be changed in any way.
   */
  SchedulingEventFrame(String name, ReadonlyNUPlannerSystem model, Features executer) {
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
   * @param     frame the given frame to add the panel to.
   * @param     name The name of the selected user.
   * @param     model the given model to work off of.
   */
  void setPanel(SchedulingEventFrame frame,  String name, ReadonlyNUPlannerSystem model) {
    panel = new SchedulingEventPanel(name, model);
    this.add(panel);
  }

  /**
   * Set up the buttons for this event frame, with just schedule.
   * @param     frame the given frame to add the buttons to.
   * @param     model the given model to work off of.
   */
  void setButton(SchedulingEventFrame frame, ReadonlyNUPlannerSystem model) {
    JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
    JButton modifyEvent = new JButton("Schedule");
    eventButtonListener(modifyEvent, frame);
    buttonPanel.add(modifyEvent);
    buttonPanel.setBackground(new Color(174, 200, 227));
    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }


  /**
   * Listening for either removing or modifying an event.
   * @param     button the given button.
   * @param     eventFrame this event frame to listen out for.
   */
  private void eventButtonListener(JButton button, SchedulingEventFrame eventFrame) {
    button.addActionListener(e -> {
      String eventName = panel.getEventName();
      String location = panel.getLoc();
      String duration = panel.getDuration();
      String selectedUser = panel.getSelectedUser();

      if (eventName.isEmpty() || location.isEmpty() || !duration.equals("0")
              || selectedUser == null) {
        // ERROR BOX
        return;
      }
      // change up the executer to take in the information from the user to schedule
      // and event.
      //this.executer.schedule();
    });
  }
}
