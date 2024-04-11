package controller.strategy;

import java.util.List;

import model.EventRep;
import model.eventfields.Location;

/**
 * The interface for strategies.
 * If additional strategies want to be implemented.
 */
public interface ScheduleStrat {

  /**
   * Schedule an even given the information.
   * @param     name The name of the event
   * @param     loc the location of event
   * @param     duration the duration of the event
   * @param     invitees the invitees who should come to the event.
   * @return The event that works based on the strategy.
   */
  EventRep schedule(String name, Location loc, int duration, List<String> invitees);

}
