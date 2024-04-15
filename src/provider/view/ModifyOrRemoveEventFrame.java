package provider.view;

//import controller.Features;
import provider.model.EventInterface;
import provider.model.UserInterface;
import provider.controller.Features;
import model.Event;
import provider.model.ReadOnlySystemInterface;
//import model.SystemPlanner;
//import model.User;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Public class ModifyOrRemoveEventFrame that represents the frame that helps to modify or
 * remove event frame. It extends JFrame.
 * This class takes in a model, event and current User to remove or modify an event from the model.
 * This frame is populated when an event is clicked on by the user.
 */
public class ModifyOrRemoveEventFrame extends JFrame implements EventFrameInterface {
  private JTextField eventNameTextField;
  private JTextField locationTextField;
  private JCheckBox isOnlineCheckBox;
  private JComboBox<String> startingDayComboBox;
  private JTextField startingTimeTextField;
  private JComboBox<String> endingDayComboBox;
  private JTextField endingTimeTextField;
  private JList<String> userList;
  private JButton modifyButton;
  private JButton removeButton;
  private EventInterface event;

  /**
   * Constructor for ModifyOrRemoveEventFrame that takes in a model, event, and currentUser.
   * @param model of the system.
   * @param event event that is clicked on.
   * @param currentUser user that is selected by the user.
   */
  public ModifyOrRemoveEventFrame(ReadOnlySystemInterface model, EventInterface event,
                                  String currentUser) {
    this.event = event;

    modifyOrRemoveEventFrameHelper(model, event);
    modifyButton.setActionCommand("Modify event");
    removeButton.setActionCommand("Remove event");

    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Adds an action listener to modify and remove Button so that text fields can be accessed.
   * This allows for an event to be modified or removed using the info from accessed fields.
   * Used in the controller.
   * @param features interface that it takes in and its function.
   */
  public void addFeatures(Features features) {
    modifyButton.addActionListener(e -> features.modifyEvent(eventNameTextField.getText(),
            String.valueOf(startingDayComboBox.getSelectedItem()), startingTimeTextField.getText(),
            String.valueOf(endingDayComboBox.getSelectedItem()), endingTimeTextField.getText(),
            locationTextField.getText(), isOnlineCheckBox.isSelected(),
            userList.getSelectedValuesList(), event));
    modifyButton.addActionListener(e -> this.dispose());

    removeButton.addActionListener(e -> features.removeEvent(event));
    removeButton.addActionListener(e -> this.dispose());
  }

  /**
   * Sets event fields to be modified or removed.
   * Currently combined into one method as per assignment specifications to
   * simply print out the details of the event being modified or removed.
   * @param x whether its modified or removed
   * @param currentUser the user to modify or remove event for
   */
  private void modifyOrRemoveEvent(String x, String currentUser) {
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
    } else {
      System.out.println(x);
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
   * Helper method to display and populate the currently selected event's details.
   * @param model the model of the system
   * @param event the event to be displayed
   */
  private void modifyOrRemoveEventFrameHelper(ReadOnlySystemInterface model,
                                              EventInterface event) {
    eventNameTextField = new JTextField(event.getName());
    locationTextField = new JTextField(event.getLocation());
    isOnlineCheckBox = new JCheckBox("Is online");
    isOnlineCheckBox.setSelected(event.isOnline());

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    startingDayComboBox = new JComboBox<>(days);
    String startDay = event.getStartTime().getDayOfWeek().toString();
    startDay = startDay.charAt(0) + startDay.substring(1).toLowerCase();
    startingDayComboBox.setSelectedItem(startDay);
    endingDayComboBox = new JComboBox<>(days);
    String endDay = event.getEndTime().getDayOfWeek().toString();
    endDay = endDay.charAt(0) + endDay.substring(1).toLowerCase();
    endingDayComboBox.setSelectedItem(endDay);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
    startingTimeTextField = new JTextField(formatter.format(event.getStartTime()));
    endingTimeTextField = new JTextField(formatter.format(event.getEndTime()));

    String[] users = model.getAllUsers().toArray(new String[0]);
    List<String> eventUsers = event.getUsers();
    ArrayList<String> duplicates = new ArrayList<>(eventUsers);
    duplicates.addAll(List.of(users));
    List<String> uniqueUsers = duplicates.stream().distinct().collect(Collectors.toList());
    userList = new JList<>(uniqueUsers.toArray(new String[0]));
    // matching event participants to users in the system
    List<Integer> selectedIndices = new ArrayList<>();
    for (String user : eventUsers) {
      int index = uniqueUsers.indexOf(user);
      if (index != -1) {
        selectedIndices.add(index);
      }
    }
    int[] indicesArray = selectedIndices.stream().mapToInt(i -> i).toArray();
    userList.setSelectedIndices(indicesArray); // pre-selecting users already in the event

    modifyButton = new JButton("Modify event");
    removeButton = new JButton("Remove event");

    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    add(drawLabelAndComp("Event name:", eventNameTextField));
    add(drawLabelAndComp("Location:", locationTextField));
    add(isOnlineCheckBox);
    add(drawLabelAndComp("Starting Day:", startingDayComboBox));
    add(drawLabelAndComp("Starting time:", startingTimeTextField));
    add(drawLabelAndComp("Ending Day:", endingDayComboBox));
    add(drawLabelAndComp("Ending time:", endingTimeTextField));
    add(drawLabelAndComp("Available participants:", new JScrollPane(userList)));
    add(modifyButton);
    add(removeButton);
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
        UserInterface prof = new User("Prof. Lucia");
        UserInterface anon = new User("Student Anon");
        UserInterface chat = new User("Chat");
        UserInterface random = new User("Random Guy");
        ((SystemPlanner) model).addUser(prof);
        ((SystemPlanner) model).addUser(anon);
        ((SystemPlanner) model).addUser(chat);
        ((SystemPlanner) model).addUser(random);
        ((SystemPlanner) model).uploadXML("prof.xml");
        new ModifyOrRemoveEventFrame(model, prof.getSchedule().getEventList().get(0),
                prof.getUsername());
      }
    });
  }
}