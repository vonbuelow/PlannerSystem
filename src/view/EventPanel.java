package view;


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import model.EventRep;
import model.NUPlannerSystem;

/**
 * Describes capabilities of event frame's panel.
 */
public class EventPanel extends JPanel {

  private JTextField eventNameField;
  private JComboBox<String> startDayComboBox;
  private JComboBox<String> endDayComboBox;
  private JTextField startTimeField;
  private JTextField endTimeField;
  private JTextField locationField;
  private JList<String> availableUsersList;
  private JMenu isOnline;
  private JComboBox<String> startDay, endDay;
  private FlowLayout layout;

  EventPanel(String selectedUser, NUPlannerSystem model) {
    eventNameField = new JTextField(15);
    startTimeField = new JTextField(15);
    endTimeField = new JTextField(15);
    locationField = new JTextField(15);
    defaultDays();
    this.layout = (new FlowLayout(FlowLayout.LEFT));
    eventNamePanel(this);
    locationPanel(this);
    userPanel(this, model.usersInSystem());
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setVisible(true);
    // how to display all users to select?? with the first person being the host
  }

  private void userPanel(EventPanel eventPanel, Set<String> strings) {
    JPanel userListPanel = new JPanel();
    userListPanel.setLayout(layout);
    userListPanel.add(new JLabel("Available users:"), BorderLayout.NORTH);
    availableUsersList = new JList<String>(strings.toArray(new String[0]));
    userListPanel.add(availableUsersList);
    eventPanel.add(userListPanel);
  }

  // 33 lines atm
  private void locationPanel(EventPanel eventPanel) {
    defaultDays();

    JPanel location = new JPanel();
    location.setLayout(layout);
    location.add(new JLabel("Location:"));
    eventPanel.add(location);

    JPanel locationOptions = new JPanel();
    locationOptions.setLayout(layout);
    String[] options = {"Is online", "Is not online"};
    locationOptions.add(new JComboBox<>(options));
    locationOptions.add(locationField);
    eventPanel.add(locationOptions);

    JPanel startDayPanel = new JPanel();
    startDayPanel.setLayout(layout);
    startDayPanel.add(new JLabel("Starting Day:"));
    startDayPanel.add(startDayComboBox);
    eventPanel.add(startDayPanel);

    JPanel startTimePanel = new JPanel();
    startTimePanel.setLayout(layout);
    startTimePanel.add(new JLabel("Starting Time:"));
    startTimePanel.add(startTimeField);
    eventPanel.add(startTimePanel);

    JPanel endDayPanel = new JPanel();
    endDayPanel.setLayout(layout);
    endDayPanel.add(new JLabel("Ending Day:"));
    endDayPanel.add(endDayComboBox);
    eventPanel.add(endDayPanel);

    JPanel endTimePanel = new JPanel();
    endTimePanel.setLayout(layout);
    endTimePanel.add(new JLabel("Ending Time:"));
    startDayPanel.add(endTimeField);
    eventPanel.add(startDayPanel);
  }


  EventPanel(EventRep event, NUPlannerSystem model) {
    this.eventNameField = new JTextField(event.getName());
    this.locationField = new JTextField(event.getLocation().getPlace());
    this.startTimeField = new JTextField(event.getTime().getStartTime());
    this.endTimeField = new JTextField(event.getTime().getEndTime());
    this.availableUsersList = new JList<>(model.usersInSystem().toArray(new String[0]));
  }

  private void eventNamePanel(EventPanel eventPanel) {
    JPanel eventNamePanel = new JPanel();
    eventNamePanel.setLayout(layout);
    eventNamePanel.add(new JLabel("Event name:"));
    eventNamePanel.add(eventNameField);
    eventPanel.add(eventNamePanel);
  }

  private void defaultDays() {
    String[] dayOptions = {"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"};
    this.startDayComboBox = new JComboBox<>(dayOptions);
    this.endDayComboBox = new JComboBox<>(dayOptions);
  }

  public String getEventName() {
    return eventNameField.getText();
  }

  public String getStartDay() {
    return (String) startDayComboBox.getSelectedItem();
  }

  public String getEndDay() {
    return (String) endDayComboBox.getSelectedItem();
  }

  public String getStartTime() {
    return startTimeField.getText();
  }

  public String getEndTime() {
    return endTimeField.getText();
  }

  public String getLoc() {
    return locationField.getText();
  }

  public String getSelectedUser() {
    return availableUsersList.getSelectedValue();
  }
}
