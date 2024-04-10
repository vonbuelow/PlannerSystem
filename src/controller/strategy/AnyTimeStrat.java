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

public class AnyTimeStrat implements ScheduleStrat {
  private final NUPlannerSystem model;

  public AnyTimeStrat(NUPlannerSystem model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public EventRep schedule(String name, Location loc, int duration, List<String> invitees) {
    if (duration > 10080 || duration < 1) {
      throw new IllegalArgumentException(
              "duration must be at least 1 min but not more than 10080");
    }

    List<Day> daysOfTheWeek = new ArrayList<>(
            Arrays.asList(Day.SUNDAY, Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY,
                    Day.THURSDAY, Day.FRIDAY, Day.SATURDAY));

    for (Day day : daysOfTheWeek) {
      for (int hr = 0; hr < 23; hr++) {
        for (int min = 0; min < 59; min++) {
          // hypothetical time of the event to be created
          Time hypTime = this.setTime(day, hr, min, duration);
          EventRep hypEvent = new Event(name, hypTime, loc, invitees);
          if (!this.model.doesOverlap(hypEvent)) { // if no invitees have overlapping
            return hypEvent;
          }
        }
      }
    }
    throw new IllegalStateException("not all invitees can schedule an event");
  }

  /**
   * Returns a new time that has the given hour and min converted to a String.
   * @param startDay day of the week that an event starts on
   * @param hr hour of an event start time
   * @param min minute of an event end time
   * @param duration amount of time between the start and end time in minutes
   * @return a Time with the workday as the start day, end day depending on duration
   *     and the starting time as the given hour and minute
   */
  private Time setTime(Day startDay, int hr, int min, int duration) {
    String convertedHr = convertTimeNum(hr);
    String convertedMin = convertTimeNum(min);
    String startTime = convertedHr + convertedMin;

    int hrsToAdd = (min + duration) / 60;
    int endHr = hr + hrsToAdd;
    int endMin = (min + duration) % 60;
    int numDays = hrsToAdd / 24;

    String endHrStr = convertTimeNum(endHr);
    String endMinStr = convertTimeNum(endMin);
    int endDayVal = (startDay.orderOfDayInWeek() + numDays) % 7;
    Day endDay;

    switch (endDayVal) {
      case 0: endDay = Day.SUNDAY;
      case 1: endDay = Day.MONDAY;
      case 2: endDay = Day.TUESDAY;
      case 3: endDay = Day.WEDNESDAY;
      case 4: endDay = Day.THURSDAY;
      case 5: endDay = Day.FRIDAY;
      default: endDay = Day.SATURDAY;
    }

    String endTime = endHrStr + endMinStr;

    return new Time(startDay, startTime, endDay, endTime);
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
