package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
  private JComboBox listOfUsers;

  public MainSystemFrame(NUPlannerSystem model) {
    this.model = model;
    createMSFrame(this);
    createMenu();
    this.setJMenuBar(this.menuBar);
  }

  private void createMenu() {
    this.menuBar = new JMenuBar();
    this.menu = new JMenu("File");
    menuBar.add(this.menu);
    menu.add(new JMenuItem("Save all Calendars"));
    menu.add(new JMenuItem("Save this Calendar"));
  }

  private void createMSFrame(MainSystemFrame frame) {
    frame.setTitle("NU Planner System");
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(700, 480);
    buttonLayout(frame);
  }

  private void buttonLayout(MainSystemFrame frame) {
    JPanel buttonPanel = new JPanel(new FlowLayout()); // default is flow layout
    String[] names = {"emma", "noelis", "ur mom"};
    listOfUsers = new JComboBox<String>(names);
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
          System.out.println("Selected directory: "
                  + fileChooser.getSelectedFile().getAbsolutePath());
        }
      }
    });
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
