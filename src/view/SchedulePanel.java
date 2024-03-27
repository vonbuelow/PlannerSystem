package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import javax.swing.JPanel;
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

    for (EventRep event : events) {
      int dayStartIndex = dayIndex(event.getTime().getStartDay());
      int dayEndIndex = dayIndex(event.getTime().getEndDay());
      int[] startTime = parseTime(event.getTime().getStartTime());
      int[] endTime = parseTime(event.getTime().getEndTime());

      int startYPosition = startTime[0] * rowSpacing + (startTime[1] * rowSpacing / 60);
      int endYPosition = endTime[0] * rowSpacing + (endTime[1] * rowSpacing / 60);

      // Determine the last day to draw the event. It should not go past Saturday.
      int lastDayToDraw = (dayEndIndex >= dayStartIndex) ? dayEndIndex : 6;

      for (int currentDay = dayStartIndex; currentDay <= lastDayToDraw; currentDay++) {
        int x = colSpacing * currentDay;
        int y = (currentDay == dayStartIndex) ? startYPosition : 0;
        int height = (currentDay == dayEndIndex) ? endYPosition - y : getHeight() - y;

        if (currentDay == dayEndIndex && currentDay != dayStartIndex) {
          y = 0;
          height = endYPosition;
        }

        g.setColor(new Color(89, 70, 211, 255));
        g.fillRect(x, y, colSpacing, height);

        // If the event spans over to the next week, stop drawing at Saturday
        if (currentDay == 6) {
          break;
        }
      }
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
  public void updateView() {
    //this.paintComponent(new );
  }

  protected void showSchedule(String selectedUser) {
    this.selectedUser = selectedUser;
    repaint();
  }

  private int[] parseTime(String time) {
    int hour = Integer.parseInt(time.substring(0, 2));
    int minute = Integer.parseInt(time.substring(2, 4));
    return new int[]{hour, minute};
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
