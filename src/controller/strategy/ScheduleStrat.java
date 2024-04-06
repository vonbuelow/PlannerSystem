package controller.strategy;

import java.util.List;

import model.EventRep;
import model.eventfields.Location;

public interface ScheduleStrat {
  EventRep schedule(String name, Location loc, int duration, List<String> invitees);
}
