package model.eventfields;

/**
 * Time with a start day, start time, end day, and end time of an event.
 */
public class SatStartTime extends Time {

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
  public SatStartTime(DayRep start, String startTime, DayRep end, String endTime) {
    super(start, startTime, end, endTime);
  }
}

