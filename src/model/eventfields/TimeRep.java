package model.eventfields;

/**
 * An interface representation of time.
 * Allow for modularity in developing new time representations.
 */
public interface TimeRep {

  /**
   * Returns whether the given time overlaps with the current.
   * Public for Event's usage of the same logic.
   * @param time the time whose time is being compared to the current
   * @return true iff the current time conflicts with the given time
   * @throws IllegalArgumentException if the given time is null
   */
  public boolean overlapsWith(Time time);

  /**
   * Gets the start day of the current time.
   * Public for XML writer observation.
   * @return the time's start day as a Day
   */
  public Day getStartDayDefault();

  /**
   * Gets a string abbreviation of the current time's start day.
   * Public for writer and panel observation.
   * @return the time's start day abbreviated
   */
  public String getStartDay();

  /**
   * Gets the end day of the current time.
   * Public for time observation in EventAdapter.
   * @return the time's start day as a Day
   */
  public Day getEndDayDefault();

  /**
   * Gets a string abbreviation of the current time's end day.
   * Public for XML writer observation.
   * @return the time's end day abbreviated
   */
  public String getEndDay();

  /**
   * The start hour of the current time.
   * Public for XML writer and panels.
   * @return a copy of the current time's start time
   */
  public String getStartTime();

  /**
   * The end hour of the current time.
   * Public for XML writer and panels.
   * @return a copy of the current time's end time
   */
  public String getEndTime();
}
