package controller.strategy;

import java.util.List;

import model.Event;
import model.EventRep;
import model.eventfields.Location;

public class LenientStrat implements ScheduleStrat {
  @Override
  public EventRep schedule(String name, Location loc, int duration, List<String> invitees) {
    /*
    To that end, create a new strategy that creates an event in the first time block where
    the host and at least one other invitee can add that event to their schedule. If there is
    more than one non-host invitee that can add the event to their schedule, then they must
    also be invited. The strategy must find a block between Monday and Friday (inclusive)
    from 0900 to 1700 (inclusive).
     */
    // tracking the time to the user the host overlaps with and if there are any that match with
    // the users as well
    return null;
  }
}
