package view;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
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
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setVisible(true);
    // how to display all users to select?? with the first person being the host
  }

  private void userPanel(EventPanel eventPanel, Set<String> strings) {
    JPanel userListPanel = new JPanel();
    userListPanel.add(new JLabel("Available users"), BorderLayout.NORTH);
    userListPanel.add(new JList<String>(strings.toArray(new String[0])));
    eventPanel.add(userListPanel);
  }

  // 33 lines atm
  private void locationPanel(EventPanel eventPanel) {
    JPanel location = new JPanel();
    location.add(new JLabel("Location:"));
    location.setAlignmentX(RIGHT_ALIGNMENT);
    eventPanel.add(location);

    JPanel locationOptions = new JPanel();
    String[] options = {"Is online", "Is not online"};
    locationOptions.add(new JList<>(options));
    locationOptions.add(new JTextField(15));
    eventPanel.add(locationOptions);

    JPanel startDayPanel = new JPanel();
    startDayPanel.add(new JLabel("Starting Day:"));
    startDayPanel.add(this.days);
    eventPanel.add(startDayPanel);

    JPanel startTimePanel = new JPanel();
    startTimePanel.add(new JLabel("Starting Time:"));
    startTimePanel.add(new JTextField(15));
    eventPanel.add(startTimePanel);

    JPanel endDayPanel = new JPanel();
    endDayPanel.add(new JLabel("Ending Day:"));
    endDayPanel.add(this.days);
    eventPanel.add(startDayPanel);

    JPanel endTimePanel = new JPanel();
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
    JPanel eventNamePanel = new JPanel();
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
}
