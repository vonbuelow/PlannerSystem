package model.adapters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import model.EventRep;
import model.eventfields.Location;
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
    return null;
    //return adaptee.getTime().getStartTime();
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
    /*Location locAsDefault = new Location()
    adaptee.modifyLocation(locAsDefault);*/
  }

  @Override
  public boolean isOnline() {
    String isOnTheLine = adaptee.getLocation().isOnline();
    return isOnTheLine.equals("true");
  }

  @Override
  public void setIsOnline(boolean isOnline) {
    Location defaultLoc = adaptee.getLocation();
    adaptee.modifyLocation(new Location(isOnline, defaultLoc.getPlace()));
  }

  @Override
  public String getHost() {
    return adaptee.getInvitedUsers().get(0);
  }

  @Override
  public List<String> getUsers() {
    List<String> allInvitees = adaptee.getInvitedUsers();
    allInvitees.remove(0);
    return allInvitees;
  }

  @Override
  public void setUsers(List<String> users) {
    /*List<String> usersToRemove = adaptee.getInvitedUsers()
            .stream().filter(u -> !users.contains(u));*/
    // adaptee.modifyInvitees(usersToRemove, false);
    //adaptee.modifyInvitees(users, true);
  }
}
