package controller.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

public class WorkHoursStrat implements ScheduleStrat {
  private final NUPlannerSystem model;

  public WorkHoursStrat(NUPlannerSystem model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public EventRep schedule(String name, Location loc, int duration, List<String> invitees) {
    if (duration > 480 || duration < 1) {
      throw new IllegalArgumentException(
              "duration must be at least 1 min but can't be more than 480");
    }

    List<Day> workdays = new ArrayList<>(
            Arrays.asList(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY,
                    Day.THURSDAY, Day.FRIDAY));

    for (Day workday : workdays) {
      for (int hr = 9; hr < 17; hr++) {
        for (int min = 0; min < 59; min++) {
          // hypothetical time of the event to be created
          try {
            Time hypTime = this.setTime(workday, hr, min, duration);
            EventRep hypEvent = new Event(name, hypTime, loc, invitees);
            if (!this.model.doesOverlap(hypEvent)) { // if no invitees have overlapping
              return hypEvent;
            }
          } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
          }
        }
      }
    }

    throw new IllegalStateException("could not schedule workday event for all invitees");
  }

  /**
   * Returns a new time that has the given hour and min converted to a String.
   * @param workday day of a workday event (start and end)
   * @param hr hour of an event start time
   * @param min minute of an event end time
   * @param duration amount of time between the start and end time in minutes
   * @return a Time with the workday as the start day, end day depending on duration
   *     and the starting time as the given hour and minute
   */
  private Time setTime(Day workday, int hr, int min, int duration) {
    String convertedHr = convertTimeNum(hr);
    String convertedMin = convertTimeNum(min);
    String startTime = convertedHr + convertedMin;

    int hrsToAdd = (min + duration) / 60;
    int endHr = hr + hrsToAdd;
    int endMin = (min + duration) % 60;

    if (endHr > 17 || (endHr == 17 && endMin > 0)) {
      throw new IllegalStateException("time could not be created");
    }

    String endHrStr = convertTimeNum(endHr);
    String endMinStr = convertTimeNum(endMin);
    String endTime = endHrStr + endMinStr;

    return new Time(workday, startTime, workday, endTime);
  }

  /**
   * Converts the given time parameter to a String representing hour or minutes.
   * @param t time number (hrs or mins) to be converted
   * @return the given time number as a String
   */
  private String convertTimeNum(int t) {
    if (t < 10) {
      return "0" + t;
    }
    return String.valueOf(t);
  }
  
}
