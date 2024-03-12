package model.eventfields;

/**
 * Days of the week.
 */
public enum Day {

  // consider adding a method to mutate the starting day of the week.
  SUNDAY("Sunday", 0),
  MONDAY("Monday", 1),
  TUESDAY("Tuesday", 2),
  WEDNESDAY("Wednesday", 3),
  THURSDAY("Thursday", 4),
  FRIDAY("Friday", 5),
  SATURDAY("Saturday", 6);

  private final String val;
  private int startDayVal;

  Day(String val, int startDayVal) {
    this.val = val;
    this.startDayVal = startDayVal;
  }

  @Override
  public String toString() {
    return this.val + ": ";
  }
  // don't need to override equals since it is an Enum and has a given unique hashcode
}
