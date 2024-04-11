package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.ReadonlyNUPlannerSystem;

/**
 * This represents the event panel which shows up when scheduling.
 */
public class SchedulingEventPanel extends JPanel {
  private final JTextField eventNameField;
  private final JTextField duration;
  private final JTextField locationField;
  private JList<String> availableUsersList;
  private FlowLayout layout;

  /**
   * Represents the event panel which pops up when attempting to schedule an event.
   * This panel only contains some of the information the other event panels show.
   * The reason for not abstracting it is due to how we implemented the setting up the GUI.
   * @param     selectedUser The selected user for the given event panel.
   * @param     model The model being used to schedule an event with.
   */
  public SchedulingEventPanel(String selectedUser, ReadonlyNUPlannerSystem model) {
    eventNameField = new JTextField(15);
    this.duration = new JTextField(15);
    locationField = new JTextField(15);
    this.layout = (new FlowLayout(FlowLayout.LEFT));
    eventNamePanel(this);
    locationPanel(this);
    userPanel(this, model.usersInSystem(), selectedUser);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setVisible(true);
  }

  /**
   * Adds an event name to a panel.
   * @param eventPanel event panel to be added to
   */
  private void eventNamePanel(SchedulingEventPanel eventPanel) {
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
  private void locationPanel(SchedulingEventPanel eventPanel) {
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

    JPanel duration = new JPanel();
    duration.setLayout(layout);
    duration.add(new JLabel("Duration: "));
    duration.add(this.duration);
    eventPanel.add(duration);
  }

  /**
   * Adds user to given event panel.
   * @param eventPanel event panel to be added to
   * @param strings users available
   */
  private void userPanel(SchedulingEventPanel eventPanel, Set<String> strings, String selected) {
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

  /**
   * Get the duration of the event the client is trying to schedule.
   * @return    A string which represents the duration of the event.
   */
  protected String getDuration() {
    return duration.getText();
  }
}
