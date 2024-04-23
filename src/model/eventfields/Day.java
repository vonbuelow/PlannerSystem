package model.eventfields;

/**
 * Days of the week, along with their string representations and number of days they occur
 * after the designated starting day of the week (by default: Sunday).
 */
public enum Day implements DayRep {
  // consider adding a method to mutate the starting day of the week.
  SUNDAY("Sunday", 0),
  MONDAY("Monday", 1),
  TUESDAY("Tuesday", 2),
  WEDNESDAY("Wednesday", 3),
  THURSDAY("Thursday", 4),
  FRIDAY("Friday", 5),
  SATURDAY("Saturday", 6);

  private final String val;
  private int startDayVal; // up for change if start day of the week is changed from Sunday

  /**
   * Creates a representation of a day of the week with a value representing its name
   * and another representing the number of days it occurs after the start day (Sunday).
   * @param val name of the day
   * @param startDayVal number of days after the start day of the week that it is
   */
  Day(String val, int startDayVal) {
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

  /*@Override
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
  }*/
}
