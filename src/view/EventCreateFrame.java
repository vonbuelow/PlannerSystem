package view;

import java.awt.*;

import javax.swing.*;

import controller.Features;
import model.ReadonlyNUPlannerSystem;

/**
 * Represents the create event frame.
 * This event has a single button to create.
 */
public class EventCreateFrame extends JFrame {
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
  EventCreateFrame(String name, ReadonlyNUPlannerSystem model, Features executer) {
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
   * @param     frame the given frame to add components to
   * @param     name The name of the selected user
   * @param     model
   */
  void setPanel(EventCreateFrame frame,  String name, ReadonlyNUPlannerSystem model) {
    panel = new EventPanel(name, model);
    this.add(panel);
  }

  /**
   * Set up the buttons for the event create frame.
   * @param     frame the given frame to add components to
   * @param     model the given model of the system.
   */
  void setButton(EventCreateFrame frame, ReadonlyNUPlannerSystem model) {
    JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
    JButton create = new JButton("Create");
    eventButtonListener(create, true, frame);
    buttonPanel.add(create);
    buttonPanel.setBackground(new Color(174, 200, 227));
    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }


  /**
   * Listening for either removing or modifying an event.
   * @param     button the given button.
   * @param     modify if the boolean is modifying the event (true) or removing
   * @param     eventFrame this event frame to listen out for.
   */
  private void eventButtonListener(JButton button, boolean modify, EventCreateFrame eventFrame) {
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
        return;
      }

      //this.executer.create();

    });
  }
}
