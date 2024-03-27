package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.*;
import controller.Controller;
import model.NUPlannerSystem;

/**
 * Describes capabilities of main system frame, therefore whole view.
 */
public class MainSystemFrame extends JFrame implements NUPlannerView {
  NUPlannerSystem model;
  private JButton createButton, scheduleButton, addSchedule;
  private JMenu menu;
  private JMenuBar menuBar;
  private JMenuItem saveAllItem;
  private SchedulePanel content;
  private JComboBox<String> listOfUsers;
  private String selectedUser;

  public MainSystemFrame(NUPlannerSystem model) {
    this.model = model;
    createMSFrame(this);
    createMenu();
    this.setJMenuBar(this.menuBar);
    this.content = new SchedulePanel(model);
    this.add(this.content);
    this.setMinimumSize(new Dimension(700, 480));
    this.pack();
  }

  private void createMenu() {
    this.menuBar = new JMenuBar();
    this.menu = new JMenu("File");
    this.menuBar.add(this.menu);
    this.saveAllItem = new JMenuItem("Save all Calendars");
    saveAllListener(this.saveAllItem);
    menu.add(saveAllItem);
  }

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

  private void createMSFrame(MainSystemFrame frame) {
    frame.setTitle("NU Planner System");
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(700, 480);
    buttonLayout(frame);
  }

  private void buttonLayout(MainSystemFrame frame) {
    JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
    String[] names = this.model.usersInSystem().toArray(new String[0]);
    listOfUsers = new JComboBox<String>(names);
    usersListener(this.listOfUsers, frame);
    buttonPanel.add(listOfUsers);
    createButton = new JButton("Create Event");
    eventButtonListener(createButton, false, frame);
    buttonPanel.add(createButton);
    //createButton.addActionListener();
    scheduleButton = new JButton("Schedule Event");
    eventButtonListener(scheduleButton, true, frame);
    buttonPanel.add(scheduleButton);
    addSchedule = new JButton("Add Schedule");
    fileButtonListener(addSchedule, frame);
    buttonPanel.add(addSchedule);
    buttonPanel.setBackground(new Color(174, 200, 227));
    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  private void usersListener(JComboBox<String> listOfUsers, MainSystemFrame frame) {
    listOfUsers.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Update selectedUser with the currently selected item
        selectedUser = String.valueOf(listOfUsers.getSelectedItem());
        content.showSchedule(selectedUser);
        System.out.println("Selected User: " + selectedUser);
        //content.repaint();
        frame.repaint();
      }
    });

  }

  private void eventButtonListener(JButton createButton,
                                   boolean isSchedule, MainSystemFrame frame) {
    createButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click") && isSchedule) {
          JDialog event = new JDialog(new EventFrame("Emma", model));
        }
        else {
          JDialog event = new JDialog(new EventFrame("ur mom", model));
        }
      }
    });
  }

  private void fileButtonListener(JButton button, MainSystemFrame frame) {
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = fileChooser.showOpenDialog(frame);

        // Check if a directory was selected
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          model.addUser(fileChooser.getSelectedFile());
          String[] names = model.usersInSystem().toArray(new String[0]);
          updateListOfUsers();
          content.updateView();
          System.out.println(model.usersInSystem().toString());
          //System.out.println(model.getUserEvents("Gordisimo"));
          System.out.println("Selected file: "
                  + fileChooser.getSelectedFile().getAbsolutePath());
        }
      }
    });
  }

  private void updateListOfUsers() {
    String[] names = model.usersInSystem().toArray(new String[0]);
    listOfUsers.setModel(new DefaultComboBoxModel<>(names));
  }

  @Override
  public void setListener(Controller controller) {

  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  /*
  view should look as close to assignment as possible
  note: view has file menu which uses JMenu and JMenuItem which contains two items:
  - one to load XML file
  - other save every loaded schedule
  - can be done w/ buttons instead

  - days are viewed left to right (sun->sat)
  - bold lines drawn every 4 hours
   */


  /**
   * Main system / schedule frame closing actually quits the program
   * - #6200ee -> color for event highlighting (indigo)
   */
}
