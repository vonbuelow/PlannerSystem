package model.adapters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.EventRep;
import model.eventfields.Location;
import provider.model.EventInterface;

public class EventAdapter extends AbstractAdapter implements EventInterface {
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
    return LocalDateTime.of(2023, 1,
            adaptee.getTime().getStartDayDefault().getLocalDateVal(),
            Integer.parseInt(adaptee.getTime().getStartTime().substring(0,2)),
            Integer.parseInt(adaptee.getTime().getStartTime().substring(2,4)));
  }

  @Override
  public void setStartTime(LocalDateTime startTime) {
    adaptee.modifyTime(makeDefaultTime(startTime, getEndTime()));
  }

  @Override
  public LocalDateTime getEndTime() {
    return LocalDateTime.of(2023, 1,
            adaptee.getTime().getEndDayDefault().getLocalDateVal(),
            Integer.parseInt(adaptee.getTime().getEndTime().substring(0,2)),
            Integer.parseInt(adaptee.getTime().getEndTime().substring(2,4)));
  }

  @Override
  public void setEndTime(LocalDateTime endTime) {
    adaptee.modifyTime(makeDefaultTime(getStartTime(), endTime));
  }

  @Override
  public String getLocation() {
    return adaptee.getLocation().toString();
  }

  @Override
  public void setLocation(String location) {
    Location locAsDefault = adaptee.getLocation();
    adaptee.modifyLocation(
            new Location(Boolean.parseBoolean(locAsDefault.isOnline()), location));
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
    List<String> addUsers = new ArrayList<>();
    List<String> remUsers = new ArrayList<>();
    for (String person : this.getUsers()) {
      if (!users.contains(person)) {
        remUsers.add(person);
      }
    }
    for (String usr : users) {
      if (!this.getUsers().contains(usr)) {
        addUsers.add(usr);
      }
    }
    this.adaptee.modifyInvitees(remUsers, false);
    this.adaptee.modifyInvitees(addUsers, true);
  }
}
