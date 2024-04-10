package view;

import java.awt.*;

import javax.swing.*;

import controller.Features;
import model.EventRep;
import model.ReadonlyNUPlannerSystem;

public class SchedulingEventFrame extends JFrame {
  private SchedulingEventPanel panel;
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
  SchedulingEventFrame(String name, ReadonlyNUPlannerSystem model, Features executer) {
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
  void setPanel(SchedulingEventFrame frame,  String name, ReadonlyNUPlannerSystem model) {
    panel = new SchedulingEventPanel(name, model);
    this.add(panel);
  }

  /**
   *
   * @param frame
   * @param model
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
      //this.executer.schedule();
    });
  }
}
