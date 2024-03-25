package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import model.NUPlannerSystem;

/**
 * Describes capabilities of schedule panel.
 */
public class SchedulePanel extends JPanel {
  private NUPlannerSystem model;

  /**
   * put the content here of the actual schedule, called from the main system frame.
   */
  SchedulePanel(NUPlannerSystem model) {
    this.model = model;
    this.setLayout(new BorderLayout());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawLines(g);
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

  private void daysOfTheWeek(SchedulePanel schedulePanel, NUPlannerSystem model) {
    JPanel week = new JPanel();
    week.setLayout(new GridLayout(1, 7));
    for (int i = 0; i < 7; i++) {
      JPanel day = new JPanel();
      day.setBorder(new LineBorder(new Color(7, 36, 60), 2, false));
      week.add(day);
      // add a listener here??????????
    }
    schedulePanel.add(week);
  }

  public void updateView() {

  }
}
