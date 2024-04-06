package controller.strategy;

import java.util.List;

import model.EventRep;
import model.eventfields.Location;

public class WorkHoursStrat implements ScheduleStrat {
  @Override
  public EventRep schedule(String name, Location loc, int duration, List<String> invitees) {
    // schedule an event
    // find the first possible time (starting Sunday at 00:00) that
    // allows all invitees and the host to be present and return an event with that block of time

    // ** maybe instead it takes in the fields, then constructs the event after

    return null;
  }
}
