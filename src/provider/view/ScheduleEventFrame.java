package provider.view;

import controller.Features;
import provider.strategies.AnytimeSystemPlanner;
import provider.model.ReadOnlySystemInterface;
//import model.SystemPlanner;
//import model.User;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.util.List;

/**
 * Public class ScheduleEventFrame represents a schedule event frame that extends JFrame.
 * This class populates a "schedule event" frame that the user can use to schedule an event.
 * It can take in an event Name, location, isOnline, duration,and user list. The user can
 * choose multiple users by holding command and while clicking on the user's names.
 * We pass a currentUser that points to the user that the user selected.
 */
public class ScheduleEventFrame extends JFrame implements EventFrameInterface {

  private JTextField eventNameTextField;
  private JTextField locationTextField;
  private JCheckBox isOnlineCheckBox;
  private JTextField durationTextField;
  private JList<String> userList;
  private JButton scheduleButton;
  private ReadOnlySystemInterface model;
  private String currentUser;

  /**
   * Constructor for createEventFrame that takes in a model and currentUser.
   * It creates the frame.
   * @param model of the system.
   * @param currentUser the current user that is selected by user.
   */
  public ScheduleEventFrame(ReadOnlySystemInterface model, String currentUser) {
    this.model = model;
    this.currentUser = currentUser;

    scheduleEventFrameHelper();
    scheduleButton.setActionCommand("Schedule event");

    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Adds an action listener to scheduleButton so that info from the text fields can be accessed
   * and used to schedule an event in the model.
   * This allows for an event to be scheduled from the accessed fields.
   * Used in the controller.
   * @param features interface that it takes in and its function.
   */
  public void addFeatures(Features features) {
    scheduleButton.addActionListener(e -> features.scheduleEvent(eventNameTextField.getText(),
            locationTextField.getText(), isOnlineCheckBox.isSelected(),
            durationTextField.getText(), userList.getSelectedValuesList()));
    scheduleButton.addActionListener(e -> this.dispose()); //change?
  }

  /**
   * createEvent method sets all the fields and gets it to output it into the system.
   */
  private void scheduleEvent() {
    String eventName = eventNameTextField.getText();
    String location = locationTextField.getText();
    boolean isOnline = isOnlineCheckBox.isSelected();
    String duration = durationTextField.getText();
    List<String> participants = userList.getSelectedValuesList();

    if (eventName.isEmpty() || location.isEmpty() || duration.isEmpty() || participants.isEmpty()) {
      // System.out.println("Please fill in all the fields.");
      JOptionPane.showMessageDialog(null, "Cannot create event",
              "Message", JOptionPane.INFORMATION_MESSAGE);
    } else {
      System.out.println("Creating Event: ");
      System.out.println("Event name: " + eventName);
      System.out.println("Event location: " + location);
      System.out.println("Event online: " + isOnline);
      System.out.println("Event duration: " + duration);
      System.out.println("Event host: " + currentUser);
      System.out.println("Event participants: " + participants);
    }
  }

  /**
   * createEventFrameHelper is a helper method for createEventFrame.
   * It creates all the fields, checkboxes, that is in the frame.
   */
  protected void scheduleEventFrameHelper() {
    scheduleButton = new JButton("Schedule event");
    eventNameTextField = new JTextField();
    locationTextField = new JTextField();
    isOnlineCheckBox = new JCheckBox("Is online");
    durationTextField = new JTextField();
    String[] users = model.getAllUsers().toArray(new String[0]);
    userList = new JList<>(users);
    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(drawLabelAndComp("Event name:", eventNameTextField));
    add(drawLabelAndComp("Location:", locationTextField));
    add(isOnlineCheckBox);
    add(drawLabelAndComp("Duration in minutes:", durationTextField));
    add(drawLabelAndComp("Available participants:", new JScrollPane(userList)));
    add(scheduleButton);
  }

  /**
   * drawLabelAndComp draws the label and component for the JPanel.
   * @param labelString the string that is the label.
   * @param components the component added to the panel.
   * @return a JPanel.
   */
  private JPanel drawLabelAndComp(String labelString, JComponent components) {
    JLabel label = new JLabel(labelString);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
    panel.add(label);
    panel.add(components);
    return panel;
  }

  /**
   * Main method that actually helps run populating the CreateEventFrame.
   * @param args arguments inputted to run the main method.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        // just for testing, remove later
        ReadOnlySystemInterface model = new AnytimeSystemPlanner();
        User prof = new User("Prof. Lucia");
        User anon = new User("Student Anon");
        User chat = new User("Chat");
        User random = new User("Random Guy");
        ((SystemPlanner) model).addUser(prof);
        ((SystemPlanner) model).addUser(anon);
        ((SystemPlanner) model).addUser(chat);
        ((SystemPlanner) model).addUser(random);
        new ScheduleEventFrame(model, prof.getUsername());
      }
    });
  }
}
