package controller.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.eventfields.Day;
import model.eventfields.DayRep;
import model.eventfields.Location;
import model.eventfields.Time;
import model.eventfields.TimeRep;

/**
 * A strategy to be used with an event that can be placed at anytime.
 */
public class AnyTimeStrat implements ScheduleStrat {
  private final NUPlannerSystem model;

  /**
   * Creates an "anytime" strategy to use for scheduling events for the given system.
   * @param model planner system to schedule events for
   */
  public AnyTimeStrat(NUPlannerSystem model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public EventRep schedule(String name, Location loc, int duration, List<String> invitees) {
    if (duration >= 10080 || duration < 1) {
      throw new IllegalArgumentException(
              "duration must be at least 1 min but can't be 10080 or more");
    }

    List<DayRep> daysOfTheWeek = new ArrayList<>(
            Arrays.asList(Day.SUNDAY, Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY,
                    Day.THURSDAY, Day.FRIDAY, Day.SATURDAY));

    for (DayRep day : daysOfTheWeek) {
      for (int hr = 0; hr < 23; hr++) {
        for (int min = 0; min < 59; min++) {
          // hypothetical time of the event to be created
          TimeRep hypTime = this.setTime(day, hr, min, duration);
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
  private TimeRep setTime(DayRep startDay, int hr, int min, int duration) {
    String convertedHr = convertTimeNum(hr);
    String convertedMin = convertTimeNum(min);
    String startTime = convertedHr + convertedMin;

    int hrsToAdd = (min + duration) / 60;
    int endHrBeforeMod = hr + hrsToAdd;
    int numDays = endHrBeforeMod / 24;
    int endHr = endHrBeforeMod % 24;
    int endMin = (min + duration) % 60;


    String endHrStr = convertTimeNum(endHr);
    String endMinStr = convertTimeNum(endMin);
    int endDayVal = (startDay.orderOfDayInWeek() + numDays) % 7;
    DayRep endDay = null;

    if (endDayVal == 0) {
      endDay = Day.SUNDAY;
    }
    else if (endDayVal == 1) {
      endDay = Day.MONDAY;
    }
    else if (endDayVal == 2) {
      endDay = Day.TUESDAY;
    }
    else if (endDayVal == 3) {
      endDay = Day.WEDNESDAY;
    }
    else if (endDayVal == 4) {
      endDay = Day.THURSDAY;
    }
    else if (endDayVal == 5) {
      endDay = Day.FRIDAY;
    }
    else if (endDayVal == 6) {
      endDay = Day.SATURDAY;
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
