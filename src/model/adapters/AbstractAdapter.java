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

public class AbstractAdapter {

  protected EventRep makeDefaultEvent(EventInterface event) {
    List<String> invitees = new ArrayList<>(Arrays.asList(event.getHost()));
    invitees.addAll(event.getUsers());

    return new Event(event.getName(),
            makeDefaultTime(event.getStartTime(), event.getEndTime()),
            new Location(event.isOnline(), event.getLocation()),
            invitees);
  }

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

  protected EventInterface makeProviderEvent(EventRep e) {
    return new EventAdapter(e);
  }
}
