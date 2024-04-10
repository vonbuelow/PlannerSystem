package controller.strategy;

import java.util.List;

import model.Event;
import model.EventRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

public class WorkHoursStrat implements ScheduleStrat {
  @Override
  public EventRep schedule(String name, Location loc, int duration, List<String> invitees) {
    // schedule an event
    // find the first possible time (starting Sunday at 00:00) that
    // allows all invitees and the host to be present and return an event with that block of time

    // ** maybe instead it takes in the fields, then constructs the event after

    // duration should be less than 24 * 60 * 7
    for (int workday = Day.MONDAY.orderOfDayInWeek();
         workday < Day.FRIDAY.orderOfDayInWeek(); workday++) {
      for (int hr = 0; hr < 23; hr++) {
        for (int min = 0; min < 59; min++) {
          // hypothetical time of the event to be created
          Time hypTime = this.setHrAndMin(workday, hr, min, duration);
          // hypothetical event to be created
          EventRep hypEvent = new Event(name, hypTime, loc, invitees);
          // check if event can even be added without time conflict in any user schedule
        }
      }
    }
    return null;
  }

  /**
   * Returns a new time that has the given hour and min converted to a String.
   * @param workday day of the week that the event starts on
   * @param hr hour of an event start time
   * @param min minute of an event end time
   * @param duration amount of time between the start and end time in minutes
   * @return a Time with the workday as the start day, end day depending on duration
   *     and the starting time as the given hour and minute
   */
  private Time setHrAndMin(int workday, int hr, int min, int duration) {
    String convertedHr = convertTimeNum(hr);
    String convertedMin = convertTimeNum(min);

    int durationHrs = duration / 60;
    int durationMins = duration % 60;

    /*if ((hr + durationHrs) / 24 >= 1) {

    }*/

    return null;
  }

  private String convertTimeNum(int t) {
    if (t < 10) {
      return "0" + t;
    }
    return String.valueOf(t);
  }
  
}
