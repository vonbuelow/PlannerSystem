package model.adapters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import model.EventRep;
import provider.model.EventInterface;

public class EventAdapter implements EventInterface {
  private final EventRep adaptee;

  public EventAdapter(EventRep adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public String getName() {
    return adaptee.getName();
  }

  @Override
  public void setName(String name) {
    adaptee.modifyName(name);
  }

  @Override
  public LocalDateTime getStartTime() {
    return null; //adaptee.getTime().getStartTime()
  }

  @Override
  public void setStartTime(LocalDateTime startTime) {
    //adaptee.modifyTime(new Time(endTime.getDayOfWeek()...));
  }

  @Override
  public LocalDateTime getEndTime() {
    return null; //adaptee.getTime().getEndTime()
  }

  @Override
  public void setEndTime(LocalDateTime endTime) {
    //adaptee.modifyTime(new Time(endTime.getDayOfWeek()...));
  }

  @Override
  public String getLocation() {
    return adaptee.getLocation().toString();
  }

  @Override
  public void setLocation(String location) {

  }

  @Override
  public boolean isOnline() {
    return false;
  }

  @Override
  public void setIsOnline(boolean isOnline) {

  }

  @Override
  public String getHost() {
    return null;
  }

  @Override
  public List<String> getUsers() {
    return null;
  }

  @Override
  public void setUsers(List<String> users) {

  }
}
