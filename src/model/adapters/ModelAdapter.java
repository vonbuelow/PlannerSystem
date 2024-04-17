package model.adapters;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.EventInterface;
import provider.model.SystemInterface;
import provider.model.UserInterface;

public class ModelAdapter extends AbstractAdapter implements SystemInterface {
  private final NUPlannerSystem adaptee;

  public ModelAdapter(NUPlannerSystem adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
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
    return false;//adaptee.doesOverlap(makeDefaultEvent(event));
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
  public void uploadXML(String fileName) throws IOException {
    adaptee.addUser(new File(fileName));
  }

  @Override
  public void saveUserSchedule(String fileName, UserInterface user) throws IOException {
    adaptee.saveSchedule(new File(fileName));
  }

  @Override
  public void createUser(String username) {
    //adaptee.addNewUser(new HashMap<String, ScheduleRep>().put(username, new Schedule()));
  }

  @Override
  public void addUser(UserInterface user) {
    //
  }

  @Override
  public void addEvent(UserInterface user, EventInterface event) throws IOException {
    adaptee.addEventToInviteeSchedule(user.getUsername(), makeDefaultEvent(event));
  }

  @Override
  public void createEvent(String name, DayOfWeek startDayEnum, String startTimeString,
                          DayOfWeek endDayEnum, String endTimeString, String location,
                          Boolean isOnline, List<String> users) throws IOException {
    adaptee.addEventToAllSchedules(
            new Event(name,
                    new Time(getDayFromVal(startDayEnum.getValue()),
                            startTimeString,
                            getDayFromVal(endDayEnum.getValue()), endTimeString),
                    new Location(isOnline, location),
                    users));
  }

  @Override
  public void modifyEvent(UserInterface user, EventInterface oldEvent,
                          EventInterface newEvent) {
    if (!oldEvent.)
    adaptee.
  }

  @Override
  public void removeEvent(UserInterface user, EventInterface event) {

  }

  @Override
  public void automaticallyScheduleEvent(String name, int duration, String location,
                                         Boolean isOnline, List<String> users) {

  }
}
