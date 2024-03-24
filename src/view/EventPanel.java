package view;

import java.awt.*;
import java.util.Set;

import javax.swing.*;

import model.EventRep;
import model.NUPlannerSystem;

/**
 * Describes capabilities of event frame's panel.
 */
public class EventPanel extends JPanel {

  private String name, location, start, end;
  private JMenu isOnline, days;
  private JList<String> availableUsers;

  EventPanel(String selectedUser, NUPlannerSystem model) {
    defaultDays();
    eventNamePanel(this);
    locationPanel(this);
    userPanel(this, model.usersInSystem());
    this.setVisible(true);
    // how to display all users to select?? with the first person being the host
  }

  private void userPanel(EventPanel eventPanel, Set<String> strings) {
    JPanel userListPanel = new JPanel(new BorderLayout());
    userListPanel.add(new JLabel("Available users"), BorderLayout.NORTH);
    userListPanel.add(new JList<String>(strings.toArray(new String[0])));
    eventPanel.add(userListPanel);
  }

  private void locationPanel(EventPanel eventPanel) {
    JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    locationPanel.add(new JLabel("Location:"));
    locationPanel.add(new JTextField(15)); //??
    eventPanel.add(locationPanel);

    JPanel onlinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    onlinePanel.add(defaultOnline());
    onlinePanel.add(new JTextField(15));
    eventPanel.add(onlinePanel);

    JPanel startDayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    startDayPanel.add(new JLabel("Starting Day:"));
    startDayPanel.add(this.days);
    eventPanel.add(startDayPanel);

    JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    startTimePanel.add(new JLabel("Starting Time:"));
    startTimePanel.add(new JTextField(15));
    eventPanel.add(startTimePanel);

    JPanel endDayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    endDayPanel.add(new JLabel("Ending Day:"));
    endDayPanel.add(this.days);
    eventPanel.add(startDayPanel);

    JPanel endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    endTimePanel.add(new JLabel("Ending Time:"));
    startDayPanel.add(new JTextField(15));
    eventPanel.add(startDayPanel);
  }


  EventPanel(EventRep event, NUPlannerSystem model) {
    this.name = event.getName();
    this.location = event.getLocation().getPlace();
    this.start = event.getTime().getStartTime();
    this.end = event.getTime().getEndTime();
    this.availableUsers = new JList<>(model.usersInSystem().toArray(new String[0]));
  }

  private void eventNamePanel(EventPanel eventPanel) {
    JPanel eventNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    eventNamePanel.add(new JLabel("Event name:"));
    eventNamePanel.add(new JTextField(15));
    eventPanel.add(eventNamePanel);
  }
  private void defaultDays() {
    this.days = new JMenu();
    this.days.add(new JMenuItem("Sunday"));
    this.days.add(new JMenuItem("Monday"));
    this.days.add(new JMenuItem("Tuesday"));
    this.days.add(new JMenuItem("Wednesday"));
    this.days.add(new JMenuItem("Thursday"));
    this.days.add(new JMenuItem("Friday"));
    this.days.add(new JMenuItem("Saturday"));
  }

  private JMenu defaultOnline() {
    isOnline = new JMenu();
    isOnline.add(new JMenuItem("false"));
    isOnline.add(new JMenuItem("false"));
    return isOnline;
  }
}
