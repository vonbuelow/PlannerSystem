package model.eventfields;


/**
 * Time with a start day, start time, end day, and end time of an event.
 */
public class Time {
  Day start;
  Day end;
  String startTime;
  String endTime;


  /**
   * Creates an instance of a military time as a four-digit number between 0000 and 2359.
   * @param start start day of an event time
   * @param startTime starting clock time of the event
   * @param end end day of an event time
   * @param endTime ending clock time of the event
   * @throws IllegalArgumentException when times are not: 4-digit numbers, in military time,
   *     non-null.
   * @throws IllegalStateException when times are equal
   *
   */
  public Time(Day start, String startTime, Day end, String endTime) {
    int lengthST = String.valueOf(startTime).length(); // length of the start time number
    int lengthET = String.valueOf(endTime).length(); // length of the end time number
    if (lengthST != 4 || lengthET != 4) {
      throw new IllegalArgumentException("Invalid time format has to be 4 digits XXXX. "
              + "You entered: " + start + " " + startTime + "\n" + end + " " + endTime);
    }
    if (!militaryTime(startTime)) {
      throw new IllegalArgumentException("Start time is NOT IN MILITARY TIME: " + startTime);
    }
    if (!militaryTime(endTime)) {
      throw new IllegalArgumentException("End time is NOT IN MILITARY TIME: " + endTime);
    }
    if (start == null || end == null) {
      throw new IllegalArgumentException("No days can be null.");
    }
    if (startTime.equals(endTime)) {
      throw new IllegalStateException("start and end times must be different");
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

      return this.start.equals(e.start) && this.startTime == e.startTime
              && this.end.equals(e.end) && this.endTime == e.endTime;
    } 
    return false;
  }

  @Override
  public int hashCode() {
    return 37 * (getNumTime(startTime) + getNumTime(endTime));
  }

  /**
   * Returns whether the given time overlaps with the current.
   * @param time the time whose time is being compared to the current
   * @return
   */
  public boolean overlapsWith(Time time) {
    int thisStartDay = this.start.orderOfDayInWeek();
    int thatStartDay = time.start.orderOfDayInWeek();
    int thisEndDay = this.end.orderOfDayInWeek();
    int thatEndDay = time.end.orderOfDayInWeek();

    return !(thisStartDay > thatStartDay && thisEndDay > thatEndDay
            && getNumTime(this.endTime) >= getNumTime(time.startTime))
            || (thisStartDay > thatStartDay && thisEndDay < thatEndDay)
            || (thisStartDay < thatStartDay && thisEndDay < thatEndDay);
  }
}
