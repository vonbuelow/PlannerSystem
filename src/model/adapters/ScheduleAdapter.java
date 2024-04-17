package model.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Event;
import model.EventRep;
import model.ScheduleRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.EventInterface;
import provider.model.ScheduleInterface;

public class ScheduleAdapter implements ScheduleInterface {
  private final ScheduleRep adaptee;

  public ScheduleAdapter(ScheduleRep adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public List<String> getEventListAllParticipants() {
    return adaptee.getAllEventsParticipants();
  }

  @Override
  public List<EventInterface> getEventList() {
    List<EventRep> eventsToAdapt = adaptee.eventsPlanned();
    List<EventInterface> adaptedEvents = new ArrayList<>();
    for (EventRep e : eventsToAdapt) {
      adaptedEvents.add(makeProviderEvent(e));
    }
    return adaptedEvents;
  }

  @Override
  public void addEventList(List<EventInterface> eventList) {

  }

  @Override
  public void addEvent(EventInterface event) {
    adaptee.addEvent(makeDefaultEvent(event));
  }

  @Override
  public void removeEvent(EventInterface event) {
    adaptee.removeEvent(makeDefaultEvent(event));
  }

  private EventRep makeDefaultEvent(EventInterface event) {
    List<String> invitees
    return new Event(event.getName(),
            new Time(getDayFromVal(event.getStartTime().getDayOfWeek().getValue()),
                    event.getStartTime().toString(),
                    getDayFromVal(event.getEndTime().getDayOfWeek().getValue()),
                    event.getEndTime().toString()),
            new Location(event.isOnline(), event.getLocation()),
            event.getHost().concat(event.getUsers());
  }

  /**
   * Get a given day based on the String entered.
   * @param     day The string rep. of a day
   * @return    The respective day enum to represent a day.
   */
  private Day getDayFromVal(int value) {
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

  private EventInterface makeProviderEvent(EventRep e) {
    return new EventAdapter(e); //?????
  }
}
