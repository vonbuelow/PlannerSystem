package view;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import model.EventRep;
import model.NUPlannerSystem;

/**
 * Describes capabilities of schedule panel.
 */
public class SchedulePanel extends JPanel {
  private NUPlannerSystem model;
  private String selectedUser;

  /**
   * put the content here of the actual schedule, called from the main system frame.
   */
  SchedulePanel(NUPlannerSystem model) {
    this.model = model;
    this.selectedUser = "";
    this.setLayout(new BorderLayout());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawEvents(g);
    drawLines(g);
  }

  private void drawEvents(Graphics g) {
    if (selectedUser.equals("")) {
      return;
    }

    List<EventRep> events = model.getUserEvents(selectedUser);
    int colSpacing = getWidth() / 7;
    int rowSpacing = getHeight() / 24;
    System.out.println(events.toString() + "drawimg these rn");
    for (EventRep event : events) {
      int dayIndex = dayIndex(event.getTime().getStartDay());
      int startTimeIndex = Integer.parseInt(event.getTime().getStartTime());
      int endTimeIndex = Integer.parseInt(event.getTime().getEndTime());

      int x = colSpacing * dayIndex;
      int y = rowSpacing * startTimeIndex;
      int height = rowSpacing * (endTimeIndex - startTimeIndex);

      g.setColor(Color.RED); // Set the event color
      g.fillRect(x, y, colSpacing, height); // Draw the event as a filled rectangle
    }
  }

  private void drawLines(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    int lineSpacing = height / 24; // Calculate spacing for 24 lines
    int colSpacing = width / 7;

    g.setColor(Color.BLACK); // Set the line color

    for (int i = 0; i < 24; i++) {
      int y = i * lineSpacing;
      g.drawLine(0, y, width, y); // Draw each line across the panel width
    }

    for (int j = 0; j < 7; j++) {
      int x = j * colSpacing;
      g.drawLine(x, 0, x, height);
    }
  }

 /* private void daysOfTheWeek(SchedulePanel schedulePanel, NUPlannerSystem model) {
    JPanel week = new JPanel();
    week.setLayout(new GridLayout(1, 7));
    for (int i = 0; i < 7; i++) {
      JPanel day = new JPanel();
      day.setBorder(new LineBorder(new Color(7, 36, 60), 2, false));
      week.add(day);
      // add a listener here??????????
    }
    schedulePanel.add(week);
  }*/

  public void updateView() {

  }

  protected void showSchedule(String selectedUser) {
    this.selectedUser = selectedUser;
    repaint();
  }

  private int dayIndex(String startDay) {
    switch (startDay) {
      case "Sunday":
        return 0;
      case "Monday":
        return 1;
      case "Tuesday":
        return 2;
      case "Wednesday":
        return 3;
      case "Thursday":
        return 4;
      case "Friday":
        return 5;
      case "Saturday":
        return 6;
      default:
        break;
    }
    return -1;
  }
}
