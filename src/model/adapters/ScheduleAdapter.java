package model.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Event;
import model.EventRep;
import model.ScheduleRep;
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
    return new Event(event.getName(),
            new Time(event.getStartTime(), event.getEndTime())
            event.getLocation(),
            event.getUsers().add(0, event.getHost()));
  }

  private EventInterface makeProviderEvent(EventRep e) {
    return new EventAdapter(e); //?????
  }
}
