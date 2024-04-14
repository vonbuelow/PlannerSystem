package provider.view;

import controller.Features;
import provider.strategies.AnytimeSystemPlanner;
import model.Event;
import provider.model.ReadOnlySystemInterface;
//import model.SystemPlanner;
//import model.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main system frame for the planner system.
 * Displays the schedule of events for a selected user in graphical view.
 * Allows the client to select, modify and remove events for a selected user's schedule.
 * Additionally, allows importing and saving of user schedules.
 */
public class MainSystemFrame extends JFrame implements MainSystemFrameInterface {

  private JPanel schedulePanel;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userComboBox;
  private JMenuItem addCalendarMenuItem;
  private JMenuItem saveCalendarsMenuItem;
  private ReadOnlySystemInterface model;
  private String currentUser;

  /**
   * Constructor for the main system frame.
   * Operates on a read only version of the model to prevent unwanted mutation.
   *
   * @param model read only model
   */
  public MainSystemFrame(ReadOnlySystemInterface model) {
    this.model = model;
    initializeMenu();
    initializeSchedulePanel();
    initializeButtons();
    setupFrameLayout();
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public Component getParentComponent() {
    return this;
  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.schedulePanel.repaint();
  }

  @Override
  public void refreshComboBox() {
    String[] modelUsers = model.getAllUsers().toArray(new String[0]);
    ArrayList<String> currentUsers = new ArrayList<>();

    int size = userComboBox.getItemCount();
    for (int i = 0; i < size; i++) {
      String item = userComboBox.getItemAt(i);
      currentUsers.add(item);
    }

    ArrayList<String> duplicates = new ArrayList<>(currentUsers);
    duplicates.addAll(List.of(modelUsers));
    List<String> uniqueUsers = duplicates.stream().distinct().collect(Collectors.toList());

    userComboBox.removeAllItems();
    for (String user : uniqueUsers) {
      userComboBox.addItem(user);
    }
  }

  /**
   * Sets the current user.
   * @param user the user to set it to
   */
  @Override
  public void setCurrentUser(String user) {
    this.currentUser = user;
  }

  /**
   * Retrieves the current user.
   * @return the currently selected user
   */
  @Override
  public String getCurrentUser() {
    return this.currentUser;
  }

  /**
   * Displays an error message to the client in GUI format.
   * Used to display exceptions when they occur.
   * @param message the error message to be displayed
   */
  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Adds an action listener to all the Buttons in main system frame to call upon the controller.
   * This allows for the functions in features to do what it is supposed to do to alter the view
   * or model when clicked.
   * Used in the controller.
   * @param features interface that it takes in and its function.
   */
  @Override
  public void addFeatures(Features features) {
    addCalendarMenuItem.addActionListener(e -> features.addCalendar());
    saveCalendarsMenuItem.addActionListener(e -> features.saveCalendars());
    createEventButton.addActionListener(e -> features.createEventFrame());
    scheduleEventButton.addActionListener(e -> features.scheduleEventFrame());
    userComboBox.addActionListener(e ->
            features.selectUser(String.valueOf(userComboBox.getSelectedItem())));
    addClickListener(features);
  }

  /**
   * Initialises the menu at the top for loading and saving schedules.
   * Clicking on buttons displays frames for loading and saving.
   */
  private void initializeMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    addCalendarMenuItem = new JMenuItem("Add calendar");
    addCalendarMenuItem.setActionCommand("Add calendar");

    saveCalendarsMenuItem = new JMenuItem("Save calendars");
    saveCalendarsMenuItem.setActionCommand("Save calendars");

    fileMenu.add(addCalendarMenuItem);
    fileMenu.add(saveCalendarsMenuItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);
  }

  /**
   * Initialises the schedule panel which displays the week and events occurring.
   */
  private void initializeSchedulePanel() {
    schedulePanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEvents(g);
        drawScheduleGrid(g);
      }
    };

    schedulePanel.setPreferredSize(new Dimension(800, 600));
  }

  /**
   * Adds a mouse listener to the schedule panel so that clicking on an event in the schedule
   * panel does something.
   * @param features interface that it takes in and its function.
   */
  private void addClickListener(Features features) {
    schedulePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        handleEventClick(e, features);
      }
    });
  }

  /**
   * Handles click events for the system.
   * Determines if an event is clicked on and displays said event's details for modification.
   * Calls on features interface when an event is clicked on.
   * @param e mouse event
   */
  private void handleEventClick(MouseEvent e, Features features) {
    double x = e.getX();
    double y = e.getY();

    System.out.println("Clicked on: " + "(X: " + x + ", Y: " + y + ")");

    List<Event> currentUserEvents = model.getUserEventList(model.getUser(currentUser));
    if (currentUserEvents != null && !currentUserEvents.isEmpty()) {
      for (Event event : currentUserEvents) {
        double dayWidth = (double) schedulePanel.getWidth() / 7;
        double hourHeight = (double) schedulePanel.getHeight() / 24;
        double minuteHeight = hourHeight / 60;

        LocalDateTime startTime = event.getStartTime();
        LocalDateTime endTime = event.getEndTime();

        double startX = startTime.getDayOfWeek().getValue() * dayWidth;
        if (startTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) { // Account for Sunday being day 1
          startX = 0;
        }
        double endX = endTime.getDayOfWeek().getValue() * dayWidth;
        double startY = (startTime.getHour() * hourHeight) +
                (startTime.getMinute() * minuteHeight);
        double endY = (endTime.getHour() * hourHeight) + (endTime.getMinute() * minuteHeight);

        if (x >= startX && x <= startX + dayWidth && y >= startY && y <= endY) {
          features.modifyOrRemoveEventFrame(event);
          break;
        } else if (startTime.getDayOfWeek() != endTime.getDayOfWeek()) {
          if (x >= startX && x <= startX + dayWidth
                  && y >= startY && y <= schedulePanel.getHeight()) {
            features.modifyOrRemoveEventFrame(event);
            break;
          } else if (x >= endX && x <= endX + dayWidth && y >= 0 && y <= endY) {
            features.modifyOrRemoveEventFrame(event);
            break;
          } else {
            for (int i = startTime.getDayOfWeek().getValue() + 1;
                 i < endTime.getDayOfWeek().getValue(); i++) {
              if (x >= i * dayWidth && x <= (i * dayWidth) + dayWidth) {
                features.modifyOrRemoveEventFrame(event);
                break;
              }
            }
          }
        }
      }
    }
  }

  /**
   * Initialises event creation/scheduling buttons and user selection box at the bottom.
   * Clicking on buttons displays event creation frame.
   * Clicking on drop down box allows client to select a user to display their schedule.
   */
  private void initializeButtons() {
    createEventButton = new JButton("Create event");
    createEventButton.setActionCommand("Create event");

    scheduleEventButton = new JButton("Schedule event");
    scheduleEventButton.setActionCommand("Schedule event");

    userComboBox = new JComboBox<>();
    String[] users = model.getAllUsers().toArray(new String[0]);
    userComboBox = new JComboBox<>(users);
    currentUser = String.valueOf(userComboBox.getSelectedItem());
    userComboBox.setActionCommand("Select user");
  }

  /**
   * Sets up layout for event creation/scheduling buttons and user selection box at the bottom.
   */
  private void setupFrameLayout() {
    setLayout(new BorderLayout());
    add(schedulePanel, BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(userComboBox);
    buttonPanel.add(createEventButton);
    buttonPanel.add(scheduleEventButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Draws the schedule grid onto the main frame.
   * Displays the week partitioned by day and hours.
   *
   * @param g graphics
   */
  private void drawScheduleGrid(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);

    int panelHeight = schedulePanel.getHeight();
    int panelWidth = schedulePanel.getWidth();
    int hourHeight = panelHeight / 24;
    int dayWidth = panelWidth / 7;

    // Draw day names
    String[] daysOfWeek = {"Sunday", "Monday", "Tuesday",
                           "Wednesday", "Thursday", "Friday", "Saturday"};
    for (int i = 0; i < daysOfWeek.length; i++) {
      int x = i * dayWidth + (dayWidth - g2d.getFontMetrics().stringWidth(daysOfWeek[i])) / 2;
      int y = (int) (hourHeight * 0.75);
      g2d.drawString(daysOfWeek[i], x, y);
    }

    // Draw hour lines
    for (int i = 0; i <= 24; i++) {
      if (i % 4 == 0) {
        g2d.setStroke(new BasicStroke(4)); // Every 4th line/hour is made thicker
      } else {
        g2d.setStroke(new BasicStroke(1));
      }
      g2d.drawLine(0, i * hourHeight, panelWidth, i * hourHeight);
    }
    // Draw day lines
    for (int i = 0; i <= 7; i++) {
      g2d.setStroke(new BasicStroke(1));
      g2d.drawLine(i * dayWidth, 0, i * dayWidth, panelHeight);
    }
  }

  /**
   * Draws the currently selected user's events onto the schedule.
   * Events are displayed as red boxes corresponding to their start and end times.
   *
   * @param g graphics
   */
  private void drawEvents(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    List<Event> currentUserEvents = model.getUserEventList(model.getUser(currentUser));
    if (currentUserEvents != null && !currentUserEvents.isEmpty()) {
      for (Event event : currentUserEvents) {
        LocalDateTime startTime = event.getStartTime();
        LocalDateTime endTime = event.getEndTime();

        double dayWidth = (double) schedulePanel.getWidth() / 7;
        double hourHeight = (double) schedulePanel.getHeight() / 24;
        double minuteHeight = hourHeight / 60;

        // Calculate start and end positions for the event
        double startX = startTime.getDayOfWeek().getValue() * dayWidth;
        if (startTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) { // Account for Sunday being day 1
          startX = 0;
        }
        double endX = endTime.getDayOfWeek().getValue() * dayWidth;
        double startY = (startTime.getHour() * hourHeight) +
                (startTime.getMinute() * minuteHeight);
        double endY = (endTime.getHour() * hourHeight) + (endTime.getMinute() * minuteHeight);

        // Handle events that span multiple days
        if (startTime.getDayOfWeek() != endTime.getDayOfWeek()) {
          // Draw first day
          g2d.setColor(Color.RED);
          g2d.fillRect((int) startX, (int) startY, (int) dayWidth,
                  (int) (schedulePanel.getHeight() - startY));

          if (startTime.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())
                  == endTime.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())) {
            for (int i = startTime.getDayOfWeek().getValue() + 1;
                 i < endTime.getDayOfWeek().getValue(); i++) {
              g2d.fillRect((int) (i * dayWidth), 0, (int) dayWidth, (int) (24 * hourHeight));
            }
            g2d.fillRect((int) endX, 0, (int) dayWidth, (int) endY);
          } else { // Handle events that end on the next week
            for (int i = startTime.getDayOfWeek().getValue() + 1; i < 7; i++) {
              g2d.fillRect((int) (i * dayWidth), 0, (int) dayWidth, (int) (24 * hourHeight));
            }
          }
          // printEventCoordinates(event, startX, dayWidth, startY, endX, endY);
        } else { // Draw for single day events
          g2d.setColor(Color.RED);
          g2d.fillRect((int) startX, (int) startY, (int) dayWidth, (int) (endY - startY));
        }
      }
    }
  }

  /**
   * Helper method for displaying an event's coordinates that are drawn.
   * Simply for testing/debugging purposes.
   *
   * @param event    the event
   * @param startX   the start x coordinate
   * @param dayWidth the width of a day in the schedule
   * @param startY   the start y coordinate
   * @param endX     the end x coordinate
   * @param endY     the end y coordinate
   */
  private void printEventCoordinates(Event event, double startX, double dayWidth, double startY,
                                     double endX, double endY) {
    System.out.println("Start X bounds for " + event.getName() + ": "
            + "(X: " + startX + ", X2: " + (startX + dayWidth) + ")");
    System.out.println("Start Y bounds for " + event.getName() + ": "
            + "(Y: " + startY + ", Y2: " + schedulePanel.getHeight() + ")");
    System.out.println("End X bounds for " + event.getName() + ": "
            + "(X: " + endX + ", X2: " + (endX + dayWidth) + ")");
    System.out.println("End Y bounds for " + event.getName() + ": "
            + "(Y: " + 0 + ", Y2: " + endY + ")");
  }

  /**
   * Main method to run the main view.
   *
   * @param args arguments
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
        ((SystemPlanner) model).uploadXML("prof.xml");
        new MainSystemFrame(model).render();
      }
    });
  }
}
