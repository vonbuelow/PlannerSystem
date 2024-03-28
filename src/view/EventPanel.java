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
  private final JTextField eventNameField;
  private JComboBox<String> startDayComboBox;
  private JComboBox<String> endDayComboBox;
  private final JTextField startTimeField;
  private final JTextField endTimeField;
  private final JTextField locationField;
  private JList<String> availableUsersList;
  private FlowLayout layout;

  /**
   * Creates a panel to view information about an event's name, time, location, etc.
   * @param selectedUser uid of the user selected on the panel
   * @param model system of users
   */
  protected EventPanel(String selectedUser, NUPlannerSystem model) {
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

  /**
   * Adds user to given event panel.
   * @param eventPanel event panel to be added to
   * @param strings users available
   */
  private void userPanel(EventPanel eventPanel, Set<String> strings) {
    JPanel userListPanel = new JPanel();
    userListPanel.setLayout(layout);
    userListPanel.add(new JLabel("Available users:"), BorderLayout.NORTH);
    availableUsersList = new JList<String>(strings.toArray(new String[0]));
    userListPanel.add(availableUsersList);
    eventPanel.add(userListPanel);
  }

  /**
   * Adds location to given event panel.
   * @param eventPanel event panel to be added to
   */
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

  /**
   * Constructor for potential controller use.
   * @param event event to be presented in a panel
   * @param model system of users
   */
  public EventPanel(EventRep event, NUPlannerSystem model) {
    this.eventNameField = new JTextField(event.getName());
    this.locationField = new JTextField(event.getLocation().getPlace());
    this.startTimeField = new JTextField(event.getTime().getStartTime());
    this.endTimeField = new JTextField(event.getTime().getEndTime());
    this.availableUsersList = new JList<>(model.usersInSystem().toArray(new String[0]));
  }

  /**
   * Adds an event name to a panel.
   * @param eventPanel event panel to be added to
   */
  private void eventNamePanel(EventPanel eventPanel) {
    JPanel eventNamePanel = new JPanel();
    eventNamePanel.setLayout(layout);
    eventNamePanel.add(new JLabel("Event name:"));
    eventNamePanel.add(eventNameField);
    eventPanel.add(eventNamePanel);
  }

  /**
   * Gives default days to choose from for starting and ending events.
   */
  private void defaultDays() {
    String[] dayOptions = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};
    this.startDayComboBox = new JComboBox<>(dayOptions);
    this.endDayComboBox = new JComboBox<>(dayOptions);
  }

  /**
   * Gets the text for an event name.
   * @return an event name as a string
   */
  protected String getEventName() {
    return eventNameField.getText();
  }

  /**
   * Gets the text for an event start day.
   * @return event start day as a string
   */
  protected String getStartDay() {
    return (String) startDayComboBox.getSelectedItem();
  }

  /**
   * Gets the text for an event end day.
   * @return event end day as a string
   */
  protected String getEndDay() {
    return (String) endDayComboBox.getSelectedItem();
  }

  /**
   * Gets the text for an event start time.
   * @return event start time as a string
   */
  protected String getStartTime() {
    return startTimeField.getText();
  }

  /**
   * Gets the text for an event end time.
   * @return event end time as a string
   */
  protected String getEndTime() {
    return endTimeField.getText();
  }

  /**
   * Gets the text for an event location.
   * @return event location as a string
   */
  protected String getLoc() {
    return locationField.getText();
  }

  /**
   * Gets the name of a user from available users.
   * @return uid as a string
   */
  protected String getSelectedUser() {
    return availableUsersList.getSelectedValue();
  }
}
