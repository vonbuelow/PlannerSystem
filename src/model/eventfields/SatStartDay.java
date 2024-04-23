package model.eventfields;

/**
 * Days of the week, along with their string representations and number of days they occur
 * after the designated starting day of the week (by default: Sunday).
 */
public enum SatStartDay implements DayRep {
  // consider adding a method to mutate the starting day of the week.

  SATURDAY("Saturday", 0),
  SUNDAY("Sunday", 1),
  MONDAY("Monday", 2),
  TUESDAY("Tuesday", 3),
  WEDNESDAY("Wednesday", 4),
  THURSDAY("Thursday", 5),
  FRIDAY("Friday", 6);

  private final String val;
  private int startDayVal; // up for change if start day of the week is changed from Sunday

  /**
   * Creates a representation of a day of the week with a value representing its name
   * and another representing the number of days it occurs after the start day (Sunday).
   * @param val name of the day
   * @param startDayVal number of days after the start day of the week that it is
   */
  SatStartDay(String val, int startDayVal) {
    this.val = val;
    this.startDayVal = startDayVal;
  }

  @Override
  public int orderOfDayInWeek() {
    return this.startDayVal;
  }

  @Override
  public String toString() {
    return this.val + ": ";
  }
}

