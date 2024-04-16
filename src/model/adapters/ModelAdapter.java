package model.adapters;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.EventRep;
import model.NUPlannerSystem;
import model.ScheduleRep;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.SystemInterface;

public class ModelAdapter implements NUPlannerSystem {
  private final SystemInterface adaptee;

  public ModelAdapter(SystemInterface adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public void saveSchedule(File fileToSave) throws IOException {
    adaptee.saveUserSchedule(fileToSave.getAbsolutePath(), );
  }

  @Override
  public void addEventToAllSchedules(EventRep event) throws IOException {

  }

  @Override
  public void addEventToInviteeSchedule(String uid, EventRep event) throws IOException {

  }

  @Override
  public void addNewUser(Map<String, ScheduleRep> newUser) throws IOException {

  }

  @Override
  public void modifyName(EventRep event, String eventName) throws IOException {

  }

  @Override
  public void modifyTime(EventRep event, Time time) throws IOException {

  }

  @Override
  public void modifyLocation(EventRep event, Location loc) throws IOException {

  }

  @Override
  public void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd) throws IOException {

  }

  @Override
  public void removeEvent(EventRep event, String uid) throws IOException {

  }

  @Override
  public void addUser(File file) throws IOException {

  }

  @Override
  public Map<String, ScheduleRep> usersSchedules() {
    return null;
  }

  @Override
  public Set<String> usersInSystem() {
    return null;
  }

  @Override
  public boolean doesOverlap(EventRep event) {
    return false;
  }

  @Override
  public List<EventRep> getUserEvents(String uid) {
    return null;
  }

  @Override
  public List<EventRep> getSystemEvents() {
    return null;
  }
}
