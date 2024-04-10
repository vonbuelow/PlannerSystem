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

public class EventScheduleFrame extends JFrame{
  private EventSchedulePanel panel;
  private Features executer;

  /**
   * Construct an event schedule frame.
   * @param     name
   * @param     model
   * @param     executer
   * @param     event
   */
  EventScheduleFrame(String name, ReadonlyNUPlannerSystem model,
                     Features executer, EventRep event) {
    this.executer = executer;
    this.setTitle("Event Info!");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setPanel(this, name, model, event);
    setButton(this, model);
    this.pack();
    this.setVisible(true);
  }


  public void setPanel(EventScheduleFrame frame,  String name,
                       ReadonlyNUPlannerSystem model, EventRep event) {
    panel = new EventSchedulePanel(name, event, model);
    frame.add(panel);
  }


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
