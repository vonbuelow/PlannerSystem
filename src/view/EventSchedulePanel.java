package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.EventRep;
import model.ReadonlyNUPlannerSystem;

/**
 * This represents the event schedule panel.
 * This panel pops up with the given event it is clicked on.
 */
public class EventSchedulePanel extends JPanel {

  private final JTextField eventNameField;
  private JComboBox<String> startDayComboBox;
  private JComboBox<String> endDayComboBox;
  private final JTextField startTimeField;
  private final JTextField endTimeField;
  private final JTextField locationField;
  private JList<String> availableUsersList;
  private FlowLayout layout;

  /**
   * Create an event schedule panel.
   * @param     selectedUser The selected user for the given schedule panel.
   * @param     event The given event to display.
   * @param     model The model being used to schedule an event with.
   */
  public EventSchedulePanel(String selectedUser, EventRep event, ReadonlyNUPlannerSystem model) {
    this.eventNameField = new JTextField(event.getName());
    this.startDayComboBox = new JComboBox<>(defaultDays());
    this.startDayComboBox.setSelectedItem(event.getTime().getStartDay());
    this.startTimeField = new JTextField(event.getTime().getStartTime());
    this.endDayComboBox = new JComboBox<>(defaultDays());
    this.endDayComboBox.setSelectedItem(event.getTime().getEndDay());
    this.endTimeField = new JTextField(event.getTime().getEndTime());
    this.locationField = new JTextField(event.getLocation().getPlace());
    this.layout = (new FlowLayout(FlowLayout.LEFT));
    eventNamePanel(this);
    locationPanel(this);
    userPanel(this, event.getInvitedUsers(), selectedUser);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setVisible(true);
  }

  /**
   * Adds an event name to a panel.
   * @param eventPanel event panel to be added to
   */
  private void eventNamePanel(EventSchedulePanel eventPanel) {
    JPanel eventNamePanel = new JPanel();
    eventNamePanel.setLayout(layout);
    eventNamePanel.add(new JLabel("Event name:"));
    eventNamePanel.add(eventNameField);
    eventPanel.add(eventNamePanel);
  }

  /**
   * Set up the location panel.
   * @param      eventPanel The event panel to add the components to.
   */
  private void locationPanel(EventSchedulePanel eventPanel) {
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
   * Adds user to given event panel.
   * @param eventPanel event panel to be added to
   * @param strings users available
   */
  private void userPanel(EventSchedulePanel eventPanel, List<String> strings, String selected) {
    JPanel userListPanel = new JPanel();
    userListPanel.setLayout(layout);
    userListPanel.add(new JLabel("Available users:"), BorderLayout.NORTH);
    availableUsersList = new JList<String>(strings.toArray(new String[0]));
    availableUsersList.setSelectedValue(selected, true);
    userListPanel.add(availableUsersList);
    eventPanel.add(userListPanel);
  }

  /**
   * Gives default days to choose from for starting and ending events.
   */
  private String[] defaultDays() {
    return new String[]{"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};
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
