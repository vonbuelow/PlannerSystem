package model.eventfields;

import java.util.Objects;

/**
 * Time with a start day, start time, end day, and end time of an event.
 */
public class Time {
  private Day start;
  private Day end;
  private String startTime;
  private String endTime;


  /**
   * Creates an instance of a military time as a four-digit number between 0000 and 2359.
   * @param start start day of an event time
   * @param startTime starting clock time of the event
   * @param end end day of an event time
   * @param endTime ending clock time of the event
   * @throws IllegalArgumentException when times are not: 4-digit numbers, in military time,
   *     non-null, and different
   *
   */
  public Time(Day start, String startTime, Day end, String endTime) {
    if (start == null || end == null || startTime == null || endTime == null) {
      throw new IllegalArgumentException("No days or hours can be null");
    }
    int lengthST = startTime.length(); // length of the start time number
    int lengthET = endTime.length(); // length of the end time number
    if (lengthST != 4 || lengthET != 4) {
      throw new IllegalArgumentException("Valid time format has to be 4 digits XXXX. "
              + "You entered: " + start + " " + startTime + "\n" + end + " " + endTime);
    }
    if (!militaryTime(startTime)) {
      throw new IllegalArgumentException("Start time is NOT IN MILITARY TIME: " + startTime);
    }
    if (!militaryTime(endTime)) {
      throw new IllegalArgumentException("End time is NOT IN MILITARY TIME: " + endTime);
    }
    if (startTime.equals(endTime) && start.equals(end)) {
      throw new IllegalArgumentException("start and end times must be different");
    }
    this.start = start;
    this.startTime = startTime;
    this.end = end;
    this.endTime = endTime;
  }

  /**
   * 0-23 hours inclusive.
   * 0-59 minutes inclusive.
   * @param    time valid 4 digit length.
   * @return    if the given time is a valid military time with the above specs.
   */
  private boolean militaryTime(String time) {
    int numTime = getNumTime(time);

    int hours = numTime / 100;
    int minutes = numTime % 100;
    return hours <= 23 && hours >= 0 && minutes >= 0 && minutes <= 59;
  }

  /**
   * Gets the numerical version of a military time.
   * @param time start or end military time of event
   * @return time as a number
   */
  private static int getNumTime(String time) {
    if (time.charAt(0) == '0') {
      time = time.substring(1);
    }
    return Integer.parseInt(time);
  }

  @Override
  public String toString() {
    return "time: " + this.start.toString() + this.startTime + " -> "
            + this.end.toString() + this.endTime;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Time) {
      Time e = (Time)o;

      return this.start.equals(e.start) && this.startTime.equals(e.startTime)
              && this.end.equals(e.end) && this.endTime.equals(e.endTime);
    } 
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this);
  }

  /**
   * Returns whether the given time overlaps with the current.
   * Public for Event's usage of the same logic.
   * @param time the time whose time is being compared to the current
   * @return true iff the current time conflicts with the given time
   * @throws IllegalArgumentException if the given time is null
   */
  public boolean overlapsWith(Time time) {
    if (time == null) {
      throw new IllegalArgumentException("time cannot be null");
    }
    // Convert days to their order in the week
    int thisStartDay = this.start.orderOfDayInWeek();
    int thatStartDay = time.start.orderOfDayInWeek();
    int thisEndDay = this.end.orderOfDayInWeek();
    int thatEndDay = time.end.orderOfDayInWeek();

    // Adjust for week wrapping
    if (thisEndDay < thisStartDay || (thisEndDay == thisStartDay
            && compareTimes(this.startTime, this.endTime) > 0)) {
      thisEndDay += 7;
    }
    if (thatEndDay < thatStartDay || (thatEndDay == thatStartDay
            && compareTimes(time.startTime, time.endTime) > 0)) {
      thatEndDay += 7;
    }

    // times to minutes since start of the week for comparison
    int thisStartTime = thisStartDay * 24 * 60 + timeToMinutes(this.startTime);
    int thisEndTime = thisEndDay * 24 * 60 + timeToMinutes(this.endTime);
    int thatStartTime = thatStartDay * 24 * 60 + timeToMinutes(time.startTime);
    int thatEndTime = thatEndDay * 24 * 60 + timeToMinutes(time.endTime);

    // Check for overlap
    return !(thisEndTime <= thatStartTime || thatEndTime <= thisStartTime);
  }

  /**
   * Converts a military time string to minutes past midnight.
   */
  private int timeToMinutes(String time) {
    int hours = Integer.parseInt(time.substring(0, 2));
    int minutes = Integer.parseInt(time.substring(2));
    return hours * 60 + minutes;
  }

  /**
   * Compares two military times.
   * @return positive if first is after second, negative if first is before second, 0 if equal.
   */
  private int compareTimes(String first, String second) {
    return timeToMinutes(first) - timeToMinutes(second);
  }

  /**
   * Gets the start day of the current time.
   * Public for XML writer observation.
   * @return the time's start day as a Day
   */
  public Day getStartDayDefault() {
    return this.start;
  }

  /**
   * Gets a string abbreviation of the current time's start day.
   * Public for writer and panel observation.
   * @return the time's start day abbreviated
   */
  public String getStartDay() {
    return getDayString(this.start);
  }

  /**
   * Converts the given day into an abbreviated string.
   * @param day the current time's day
   * @return an abbreviation of the given day's name
   */
  private String getDayString(Day day) {
    int dayLength = day.toString().length();
    return day.toString().substring(0, dayLength - 2);
  }

  /**
   * Gets the end day of the current time.
   * Public for time observation in EventAdapter.
   * @return the time's start day as a Day
   */
  public Day getEndDayDefault() {
    return this.end;
  }

  /**
   * Gets a string abbreviation of the current time's end day.
   * Public for XML writer observation.
   * @return the time's end day abbreviated
   */
  public String getEndDay() {
    return getDayString(this.end);
  }

  /**
   * The start hour of the current time.
   * Public for XML writer and panels.
   * @return a copy of the current time's start time
   */
  public String getStartTime() {
    String st = "";
    st += this.startTime;
    return st;
  }

  /**
   * The end hour of the current time.
   * Public for XML writer and panels.
   * @return a copy of the current time's end time
   */
  public String getEndTime() {
    String et = "";
    et += this.endTime;
    return et;
  }
}
