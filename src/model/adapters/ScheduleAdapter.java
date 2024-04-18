package model.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.EventRep;
import model.ScheduleRep;
import provider.model.EventInterface;
import provider.model.ScheduleInterface;
import provider.model.UserInterface;

/**
 * Represents a provider type schedule that is adapted to by a default type of schedule.
 */
public class ScheduleAdapter extends AbstractAdapter implements ScheduleInterface, UserInterface {
  private final ScheduleRep adaptee;

  /**
   * Initializes the schedule type that will adapt to the provider's schedule specifications.
   * @param adaptee default schedule type
   */
  public ScheduleAdapter(ScheduleRep adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public List<String> getEventListAllParticipants() {
    List<String> allParticipants = new ArrayList<>();
    for (EventRep e : adaptee.eventsPlanned()) {
      for (String participant : e.getInvitedUsers()) {
        if (!allParticipants.contains(participant)) {
          allParticipants.add(participant);
        }
      }
    }
    return allParticipants;
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
    for (EventInterface ev : eventList) {
      this.addEvent(ev);
    }
  }

  @Override
  public void addEvent(EventInterface event) {
    adaptee.addEvent(makeDefaultEvent(event));
  }

  @Override
  public void removeEvent(EventInterface event) {
    adaptee.removeEvent(makeDefaultEvent(event));
  }

  @Override
  public String getUsername() {
    return adaptee.scheduleOwner();
  }

  @Override
  public ScheduleInterface getSchedule() {
    return this;
  }
}
