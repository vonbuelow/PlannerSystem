package model.eventfields;

/**
 * interface to represent a day.
 */
public interface DayRep {

  /**
   * Returns the number position of the day of the week relative to the start of the
   * week. Since Sunday is considered the first day of the week, it has a position of 0.
   * Monday is one day after the start day, so it has a value of 1.
   * @return number of days after the starting day for the given day.
   */
  public int orderOfDayInWeek();

  //public int getLocalDateVal();
}
