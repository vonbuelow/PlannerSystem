package model.adapters;

import java.util.List;
import java.util.Objects;

import model.EventRep;
import model.ScheduleInterface;
import model.ScheduleRep;

public class ScheduleAdapter implements ScheduleRep {
  private final ScheduleInterface adaptee;

  public ScheduleAdapter(ScheduleInterface adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public String scheduleOwner() {
    return ;
  }

  @Override
  public List<EventRep> eventsPlanned() {
    return adaptee.getEventList();
  }

  @Override
  public void addEvent(EventRep event) {

  }

  @Override
  public void removeEvent(EventRep event) {

  }

  @Override
  public boolean overlapWith(EventRep event) {
    return false;
  }
}
