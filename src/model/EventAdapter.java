package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import model.EventRep;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.EventInterface;

public class EventAdapter implements EventRep {
  private final EventInterface adaptee;

  public EventAdapter(EventInterface adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public Time getTime() {
    LocalDateTime start = adaptee.getStartTime();
    LocalDateTime end = adaptee.getEndTime();

    return ;
  }

  @Override
  public boolean overlapsWith(EventRep e) {
    return this.getTime().overlapsWith(e.getTime());
  }

  @Override
  public String getName() {
    return adaptee.getName();
  }

  @Override
  public List<String> getInvitedUsers() {
    return null;
  }

  @Override
  public Location getLocation() {
    return null;
  }

  @Override
  public void modifyName(String name) {

  }

  @Override
  public void modifyTime(Time time) {

  }

  @Override
  public void modifyLocation(Location loc) {

  }

  @Override
  public void modifyInvitees(List<String> invitees, boolean toAdd) {

  }
}
