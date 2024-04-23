package model.eventfields;

/**
 * Days of the week, along with their string representations and number of days they occur
 * after the designated starting day of the week (by default: Sunday).
 */
public enum SatStartDay {
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

  /**
   * Returns the number position of the day of the week relative to the start of the
   * week. Since Sunday is considered the first day of the week, it has a position of 0.
   * Monday is one day after the start day, so it has a value of 1.
   * @return number of days after the starting day for the given day.
   */
  public int orderOfDayInWeek() {
    return this.startDayVal;
  }

  @Override
  public String toString() {
    return this.val + ": ";
  }

  /**
   * Get the actual value associated with a day.
   * @return    The int with is related to the day.
   */
  public int getLocalDateVal() {
    if (this.equals(Day.SUNDAY)) {
      return 7;
    }
    else if (this.equals(Day.MONDAY)) {
      return 1;
    }
    else if (this.equals(Day.TUESDAY)) {
      return 2;
    }
    else if (this.equals(Day.WEDNESDAY)) {
      return 3;
    }
    else if (this.equals(Day.THURSDAY)) {
      return 4;
    }
    else if (this.equals(Day.FRIDAY)) {
      return 5;
    }
    return 6;
  }
  // don't need to override equals since it is an Enum and has a given unique hashcode
}

