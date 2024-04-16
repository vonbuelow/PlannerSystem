package provider.view;

import provider.controller.Features;
import provider.model.ReadOnlySystemInterface;
import provider.model.UserInterface;
//import provider.model.User;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
 * Public class CreateEventFrame represents a create event frame that extends JFrame.
 * This class populates a "create event" frame that the user can use to create an event.
 * It can take in an event Name, location, isOnline, Starting/ending day, starting/ending time,
 * and user list. The user can choose multiple users by holding command and while clicking on the
 * user's names.
 * We pass a currentUser that points to the user that the user selected.
 */
public class CreateEventFrame extends JFrame implements EventFrameInterface {

  private JTextField eventNameTextField;
  private JTextField locationTextField;
  private JCheckBox isOnlineCheckBox;
  private JComboBox<String> startingDayComboBox;
  private JTextField startingTimeTextField;
  private JComboBox<String> endingDayComboBox;
  private JTextField endingTimeTextField;
  private JList<String> userList;
  private JButton createButton;
  private ReadOnlySystemInterface model;
  private String currentUser;

  /**
   * Constructor for createEventFrame that takes in a model and currentUser.
   * It creates the frame.
   * @param model of the system.
   * @param currentUser the current user that is selected by user.
   */
  public CreateEventFrame(ReadOnlySystemInterface model, String currentUser) {
    this.model = model;
    this.currentUser = currentUser;

    createEventFrameHelper();
    createButton.setActionCommand("Create event");

    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Adds an action listener to createButton so that info from the text fields can be accessed.
   * This allows for an event to be created from the accessed fields.
   * Passed to the controller.
   * @param features interface that it takes in and its function.
   */
  public void addFeatures(Features features) {
    createButton.addActionListener(e -> features.createEvent(eventNameTextField.getText(),
            String.valueOf(startingDayComboBox.getSelectedItem()), startingTimeTextField.getText(),
            String.valueOf(endingDayComboBox.getSelectedItem()), endingTimeTextField.getText(),
            locationTextField.getText(), isOnlineCheckBox.isSelected(),
            userList.getSelectedValuesList()));
    createButton.addActionListener(e -> this.dispose()); //change?
  }

  /**
   * createEvent method sets all the fields and gets it to output it into the system.
   */
  private void createEvent() {
    String eventName = eventNameTextField.getText();
    String location = locationTextField.getText();
    boolean isOnline = isOnlineCheckBox.isSelected();
    String startDay = String.valueOf(startingDayComboBox.getSelectedItem());
    String startTime = startingTimeTextField.getText();
    String endDay = String.valueOf(endingDayComboBox.getSelectedItem());
    String endTime = endingTimeTextField.getText();
    List<String> participants = userList.getSelectedValuesList();

    if (eventName.isEmpty() || location.isEmpty() || startTime.isEmpty()
            || endTime.isEmpty() || participants.isEmpty()) {
      System.out.println("Please fill in all the fields.");
      JOptionPane.showMessageDialog(null, "Cannot create event",
              "Message", JOptionPane.INFORMATION_MESSAGE);
    } else {
      System.out.println("Creating Event: ");
      System.out.println("Event name: " + eventName);
      System.out.println("Event location: " + location);
      System.out.println("Event online: " + isOnline);
      System.out.println("Event start day: " + startDay);
      System.out.println("Event start time: " + startTime);
      System.out.println("Event end day: " + endDay);
      System.out.println("Event end time: " + endTime);
      System.out.println("Event host: " + currentUser);
      System.out.println("Event participants: " + participants);
    }
  }

  /**
   * createEventFrameHelper is a helper method for createEventFrame.
   * It creates all the fields, checkboxes, that is in the frame.
   */
  protected void createEventFrameHelper() {
    createButton = new JButton("Create event");
    eventNameTextField = new JTextField();
    locationTextField = new JTextField();
    isOnlineCheckBox = new JCheckBox("Is online");
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    startingDayComboBox = new JComboBox<>(days);
    endingDayComboBox = new JComboBox<>(days);
    startingTimeTextField = new JTextField();
    endingTimeTextField = new JTextField();
    String[] users = model.getAllUsers().toArray(new String[0]);
    userList = new JList<>(users);
    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(drawLabelAndComp("Event name:", eventNameTextField));
    add(drawLabelAndComp("Location:", locationTextField));
    add(isOnlineCheckBox);
    add(drawLabelAndComp("Starting Day:", startingDayComboBox));
    add(drawLabelAndComp("Starting time:", startingTimeTextField));
    add(drawLabelAndComp("Ending Day:", endingDayComboBox));
    add(drawLabelAndComp("Ending time:", endingTimeTextField));
    add(drawLabelAndComp("Available participants:", new JScrollPane(userList)));
    add(createButton);
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
        ReadOnlySystemInterface model = new AnytimeSystemPlanner();
        UserInterface prof = new User("Prof. Lucia");
        UserInterface anon = new User("Student Anon");
        UserInterface chat = new User("Chat");
        UserInterface random = new User("Random Guy");
        ((SystemPlanner) model).addUser(prof);
        ((SystemPlanner) model).addUser(anon);
        ((SystemPlanner) model).addUser(chat);
        ((SystemPlanner) model).addUser(random);
        new CreateEventFrame(model, prof.getUsername());
      }
    });
  }
}