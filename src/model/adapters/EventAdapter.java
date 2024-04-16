package model.adapters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import model.EventRep;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.EventInterface;

public class EventAdapter implements EventInterface {
  private final EventRep adaptee;

  public EventAdapter(EventRep adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void setName(String name) {

  }

  @Override
  public LocalDateTime getStartTime() {
    return null;
  }

  @Override
  public void setStartTime(LocalDateTime startTime) {

  }

  @Override
  public LocalDateTime getEndTime() {
    return null;
  }

  @Override
  public void setEndTime(LocalDateTime endTime) {

  }

  @Override
  public String getLocation() {
    return null;
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
