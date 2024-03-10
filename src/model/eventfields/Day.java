package model.eventfields;

/**
 * Days of the week.
 */
public enum Day {

  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday"),
  SUNDAY("Sunday");

  private final String val;

  Day(String val) {
    this.val = val;
  }

  @Override
  public String toString() {
    return this.val;
  }
  // don't need to override equals since it is an Enum and has a given unique hashcode
}
