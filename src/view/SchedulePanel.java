package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JPanel;

import controller.Features;
import model.EventRep;
import model.ReadonlyNUPlannerSystem;

/**
 * Describes capabilities of schedule panel.
 */
public class SchedulePanel extends JPanel {
  private final ReadonlyNUPlannerSystem model;
  private String selectedUser; // is changed
  private boolean hasToggle;

  /**
   * put the content here of the actual schedule, called from the main system frame.
   * @param     model the model of the given schedule system.
   */
  SchedulePanel(ReadonlyNUPlannerSystem model) {
    this.hasToggle = false;
    this.model = model;
    this.selectedUser = "";
    this.setLayout(new BorderLayout());
  }

  /**
   * Add in a click listener and allowing the features to be called back on.
   * @param     features The way to start the command call backs.
   */
  public void addClickListener(Features features) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        SchedulePanel panel = SchedulePanel.this;
        double hour = ((double) e.getY() / ((double) panel.getHeight() / 24)) + 1.0; // + 1????
        int day = (e.getX() / (panel.getWidth() / 7)); // + 1???
        features.handleClick(hour, day, selectedUser);
      }

      @Override
      public void mousePressed(MouseEvent e) {
        //press
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //release
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //entered
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //exited
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (!hasToggle) {
      drawEvents(g); // interface(hostColorDecorator) -> decorators??
    }
    else {
      drawHostEvents(g);
    }
    drawLines(g);
  }

  private void drawHostEvents(Graphics g) {
    if (selectedUser.equals("")) {
      return;
    }

    System.out.println("correct method to draw host events");
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
        if (selectedUser.equals(removeQuotes(event.getInvitedUsers().get(0)))) {
          g.setColor(new Color(173, 164, 232, 255));
          g.fillRect(x, y, colSpacing, height);
        }
        else {
          g.setColor(new Color(89, 70, 211, 255));
          g.fillRect(x, y, colSpacing, height);
        }

        // If the event spans over to the next week, stop drawing at Saturday
        if (currentDay == 6) {
          break;
        }
      }
    }
  }

  public static String removeQuotes(String s) {
    // Replace single and double quotes with nothing
    s = s.replace("'", "");
    s = s.replace("\"", ""); // Note the escape character for double quotes
    return s;
  }

  /**
   * Drawing of the events as purple rectangles.
   * @param     g The graphic being passed on from paintComponent.
   */
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

  /**
   * Draw the lines of the schedule.
   * @param     g a graphic which comes from paint component.
   */
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

  /**
   * Show the schedule of the given user.
   * This is done by repainting and updating which user should be viewed.
   * Less about the user and more the view.
   * @param     selectedUser The user whose schedule should be shown.
   */
  protected void showSchedule(String selectedUser) {
    this.selectedUser = selectedUser;
    repaint();
  }

  /**
   * Given the time go in and get the hours and minutes.
   * @param     time The time being parsed.
   * @return    An array of both hours first and then minutes.
   */
  private int[] parseTime(String time) {
    int hour = Integer.parseInt(time.substring(0, 2));
    int minute = Integer.parseInt(time.substring(2, 4));
    return new int[]{hour, minute};
  }

  /**
   * Get the given day starting index.
   * @param      startDay The day we are trying to index.
   * @return The index to match the string of the day
   */
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

  public void paintWithHost(boolean hasToggled) {
    hasToggle = hasToggled;
    repaint();
  }
}
