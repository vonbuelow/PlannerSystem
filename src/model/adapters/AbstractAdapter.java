package model.adapters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Event;
import model.EventRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.EventInterface;

/**
 * Contains methods for model and supporting class adapters to convert interface types
 * to appropriate type (provider -> default or default -> provider).
 */
public class AbstractAdapter {
  /**
   * Creates an event of the default type based on the given event of the provider's type.
   * @param event event that has provider type
   * @return an EventRep that represents the same event as the given
   */
  protected EventRep makeDefaultEvent(EventInterface event) {
    List<String> invitees = new ArrayList<>(Arrays.asList(event.getHost()));
    invitees.addAll(event.getUsers());

    return new Event(event.getName(),
            makeDefaultTime(event.getStartTime(), event.getEndTime()),
            new Location(event.isOnline(), event.getLocation()),
            invitees);
  }

  /**
   * Make a default event with the given things.
   * @param     name the name
   * @param     startDayString the start day as a string
   * @param     startTimeString the start time as a string
   * @param     endDayString the end day as a string
   * @param     endTimeString the end time as string
   * @param     location the location as string
   * @param     isOnline the boolean is it is online
   * @param     participants the participants
   * @return    an event representation
   */
  protected EventRep makeDefaultEvent(String name, String startDayString, String startTimeString,
                                      String endDayString, String endTimeString, String location,
                                      boolean isOnline, List<String> participants) {
    return new Event(name, new Time(getDayFromStr(startDayString), startTimeString,
            getDayFromStr(endDayString), endTimeString), new Location(isOnline, location),
            participants);
  }

  /**
   * Creates a time of the default type based on the given start and end times of the
   * provider's type.
   * @param start start time (of a potential event)
   * @param end end time (of a potential event)
   * @return a Time that represents the same time as the given
   */
  protected Time makeDefaultTime(LocalDateTime start, LocalDateTime end) {
    return new Time(getDayFromVal(start.getDayOfWeek().getValue()),
            start.toString(),
            getDayFromVal(end.getDayOfWeek().getValue()),
            end.toString());
  }

  /**
   * Get a given day based on the given number value.
   * VALUES ARE BASED ON LOCALDATETIME.
   * @param     value The number value of the day of the week, starting on Monday
   * @return    The respective day enum to represent a day.
   */
  protected Day getDayFromVal(int value) {
    if (value == 7) {
      return Day.SUNDAY;
    }
    else if (value == 1) {
      return Day.MONDAY;
    }
    else if (value == 2) {
      return Day.TUESDAY;
    }
    else if (value == 3) {
      return Day.WEDNESDAY;
    }
    else if (value == 4) {
      return Day.THURSDAY;
    }
    else if (value == 5) {
      return Day.FRIDAY;
    }
    else if (value == 6) {
      return Day.SATURDAY;
    }
    else {
      throw new IllegalArgumentException("not a valid day value");
    }
  }

  /**
   * Get a given day based on the given string value.
   * VALUES ARE BASED ON LOCALDATETIME.
   * @param     str The string value of the day of the week
   * @return    The respective day enum to represent a day.
   */
  protected Day getDayFromStr(String str) {
    if (str.equalsIgnoreCase("sunday")) {
      return Day.SUNDAY;
    }
    else if (str.equalsIgnoreCase("monday")) {
      return Day.MONDAY;
    }
    else if (str.equalsIgnoreCase("tuesday")) {
      return Day.TUESDAY;
    }
    else if (str.equalsIgnoreCase("wednesday")) {
      return Day.WEDNESDAY;
    }
    else if (str.equalsIgnoreCase("thursday")) {
      return Day.THURSDAY;
    }
    else if (str.equalsIgnoreCase("friday")) {
      return Day.FRIDAY;
    }
    else if (str.equalsIgnoreCase("saturday")) {
      return Day.SATURDAY;
    }
    else {
      throw new IllegalArgumentException("not a valid day value");
    }
  }

  /**
   * Creates an event that adapts to the provider's event type.
   * @param e default type of event to be adapted
   * @return an EventInterface type of event
   */
  protected EventInterface makeProviderEvent(EventRep e) {
    return new EventAdapter(e);
  }
}
