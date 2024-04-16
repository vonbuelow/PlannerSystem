package model.adapters;

import java.util.List;
import java.util.Objects;
import model.ScheduleRep;
import provider.model.EventInterface;
import provider.model.ScheduleInterface;

public class ScheduleAdapter implements ScheduleInterface {
  private final ScheduleRep adaptee;

  public ScheduleAdapter(ScheduleRep adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public List<String> getEventListAllParticipants() {
    return null;
  }

  @Override
  public List<EventInterface> getEventList() {
    return null;
  }

  @Override
  public void addEventList(List<EventInterface> eventList) {

  }

  @Override
  public void addEvent(EventInterface event) {

  }

  @Override
  public void removeEvent(EventInterface event) {

  }
}
