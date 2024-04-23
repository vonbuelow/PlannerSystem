package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import controller.Features;
import model.EventRep;
import model.ReadonlyNUPlannerSystem;

/**
 * Describes capabilities of main system frame, therefore whole view.
 */
public class MainSystemFrame extends JFrame implements NUPlannerView {
  private final ReadonlyNUPlannerSystem model;
  private JMenuBar menuBar;
  private final SchedulePanel content;
  private JComboBox<String> listOfUsers;
  private Features executer;
  private boolean hasToggled;

  /**
   * This represents a main system frame of a ReadOnlyNUPlannerSystem system as a GUI view.
   * @param     model the model of the planner system being run on.
   */
  public MainSystemFrame(ReadonlyNUPlannerSystem model) {
    super();
    this.hasToggled = false;
    this.model = model;
    createMSFrame(this);
    createMenu();
    this.setJMenuBar(this.menuBar);
    this.content = new SchedulePanel(model);
    this.add(this.content);
    this.setPreferredSize(new Dimension(700, 480));
    this.pack();
  }

  /**
   * Creates the menu of the frame.
   */
  private void createMenu() {
    this.menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    this.menuBar.add(menu);
    JMenuItem saveAllItem = new JMenuItem("Save all Calendars");
    saveAllListener(saveAllItem);
    menu.add(saveAllItem);
  }

  /**
   * Save all listener to then go and save all the users schedules.
   * @param     saveAllItem The menu item which represents how to save all schedules.
   */
  private void saveAllListener(JMenuItem saveAllItem) {
    saveAllItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(saveAllItem) == JFileChooser.APPROVE_OPTION) {
          File selectedDirectory = chooser.getSelectedFile();
          System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());
        }
      }
    });
  }

  /**
   * Setting up the default frame.
   * @param     frame The frame to set up.
   */
  private void createMSFrame(MainSystemFrame frame) {
    frame.setTitle("NU Planner System");
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(700, 480);
    buttonLayout(frame);
  }

  /**
   * Set up the button layout of the frame.
   * @param     frame The given frame to update.
   */
  private void buttonLayout(MainSystemFrame frame) {
    JPanel buttonPanel = new JPanel(new FlowLayout());
    // the list of users displayed at the bottom of the screen
    String[] names = this.model.usersInSystem().toArray(new String[0]);
    listOfUsers = new JComboBox<String>(names);
    usersListener(this.listOfUsers, frame);
    buttonPanel.add(listOfUsers);

    // the create button
    JButton createButton = new JButton("Create Event");
    eventButtonListener(createButton, false, frame);
    buttonPanel.add(createButton);
    //createButton.addActionListener();

    // the schedule button
    JButton scheduleButton = new JButton("Schedule Event");
    eventButtonListener(scheduleButton, true, frame);
    buttonPanel.add(scheduleButton);

    // add schedule button
    JButton addSchedule = new JButton("Add Schedule");
    fileButtonListener(addSchedule, frame);
    buttonPanel.add(addSchedule);

    // the host color toggle button
    JButton hostButton = new JButton("Toggle host color");
    hostButtonListener(hostButton, frame);
    buttonPanel.add(hostButton);

    buttonPanel.setBackground(new Color(174, 200, 227));
    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * The display on the screen varies if the toggle is off.
   * Toggle either showing the host vs. non-host events or all events as one uniform color.
   * @param     hostButton the host button
   * @param     frame the frame to listen to.
   */
  private void hostButtonListener(JButton hostButton, MainSystemFrame frame) {
    hostButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        hasToggled = !hasToggled;
        content.paintWithHost(hasToggled);
        frame.repaint();
        // toggle color button + field to keep track if a button has been pressed previously
        // field setting
        // draw the events how they should be displayed

        // decorator????? here
        // features -> component inside for schedule panel??

      }
    });
  }

  /**
   * The given user to display on the system.
   * @param     listOfUsers the list of users in the system.
   * @param     frame the frame to update.
   */
  private void usersListener(JComboBox<String> listOfUsers, MainSystemFrame frame) {
    listOfUsers.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Update selectedUser with the currently selected item
        String selectedUser = String.valueOf(listOfUsers.getSelectedItem());
        content.showSchedule(selectedUser);
        frame.repaint();
      }
    });

  }

  /**
   * Represents the button listener for schedule and create.
   * @param     createButton the given button either create or schedule.
   * @param     isSchedule determine which button was hit.
   * @param     frame The given frame to listen to.
   */
  private void eventButtonListener(JButton createButton,
                                   boolean isSchedule, MainSystemFrame frame) {
    createButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (isSchedule) {
          JDialog event = new JDialog(new SchedulingEventFrame("schedule", model, executer));
        }
        else {
          JDialog event = new JDialog(new EventCreateFrame("create", model, executer));
        }
      }
    });
  }

  /**
   * The file system which should be observed.
   * @param     button The given button to listen to.
   * @param     frame The given frame to open a frame on.
   */
  private void fileButtonListener(JButton button, MainSystemFrame frame) {
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = fileChooser.showOpenDialog(frame);

        // Check if a directory was selected
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          executer.addUser(fileChooser.getSelectedFile());
          String[] names = model.usersInSystem().toArray(new String[0]);
          updateListOfUsers();
          //content.updateView(); ?????
        }
      }
    });
  }

  /**
   * Updating the list of users on the frame.
   */
  private void updateListOfUsers() {
    String[] names = model.usersInSystem().toArray(new String[0]);
    listOfUsers.setModel(new DefaultComboBoxModel<>(names));
  }

  @Override
  public void setListener(Features executer) {
    this.executer = executer;
    this.content.addClickListener(this.executer);
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void openEventFrame(EventRep event, String selectedUser) {
    JDialog eventFrame = new JDialog(new EventScheduleFrame(selectedUser, model, executer, event));
  }
}
