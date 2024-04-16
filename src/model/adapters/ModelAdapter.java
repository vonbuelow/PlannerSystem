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
import provider.model.EventInterface;
import provider.model.SystemInterface;
import provider.model.UserInterface;

public class ModelAdapter implements SystemInterface {
  private final NUPlannerSystem adaptee;

  public ModelAdapter(NUPlannerSystem adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public UserInterface getUser(String username) {
    return null;
  }

  @Override
  public List<String> getAllUsers() {
    return null;
  }

  @Override
  public boolean isConflictWithAllUsers(EventInterface event) {
    return false;
  }

  @Override
  public List<EventInterface> getUserEventList(UserInterface user) {
    return null;
  }

  @Override
  public List<EventInterface> seeEvent(UserInterface user, LocalDateTime time) {
    return null;
  }

  @Override
  public void uploadXML(String fileName) {

  }

  @Override
  public void saveUserSchedule(String fileName, UserInterface user) {

  }

  @Override
  public void createUser(String username) {

  }

  @Override
  public void addUser(UserInterface user) {

  }

  @Override
  public void addEvent(UserInterface user, EventInterface event) {

  }

  @Override
  public void createEvent(String name, DayOfWeek startDayEnum, String startTimeString, DayOfWeek endDayEnum, String endTimeString, String location, Boolean isOnline, List<String> users) {

  }

  @Override
  public void modifyEvent(UserInterface user, EventInterface oldEvent, EventInterface newEvent) {

  }

  @Override
  public void removeEvent(UserInterface user, EventInterface event) {

  }

  @Override
  public void automaticallyScheduleEvent(String name, int duration, String location, Boolean isOnline, List<String> users) {

  }
}
