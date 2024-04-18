package model.adapters;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.Schedule;
import model.ScheduleRep;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.model.EventInterface;
import provider.model.SystemInterface;
import provider.model.UserInterface;

/**
 * Represents a provider type system that is adapted to by a default type of system.
 * Creating users, automatic scheduling and checking for schedule conflicts are
 * unsupported operations due to reasons specified by their exception messages.
 */
public class ModelAdapter extends AbstractAdapter implements SystemInterface {
  private final NUPlannerSystem adaptee;

  /**
   * Initializes the model type that will adapt to the provider's model specifications.
   * @param adaptee default model type
   */
  public ModelAdapter(NUPlannerSystem adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  @Override
  public UserInterface getUser(String username) {
    return new ScheduleAdapter(new Schedule(username, adaptee.getUserEvents(username)));
  }

  @Override
  public List<String> getAllUsers() {
    return new ArrayList<>(this.adaptee.usersInSystem());
  }

  @Override
  public boolean isConflictWithAllUsers(EventInterface event) {
    throw new UnsupportedOperationException("cannot check all users have conflict, "
    + "only at least one");
  }

  @Override
  public List<EventInterface> getUserEventList(UserInterface user) {
    List<EventInterface> ret = new ArrayList<>();

    for (EventRep eventRep: this.adaptee.getUserEvents(user.getUsername())) {
      ret.add(makeProviderEvent(eventRep));
    }

    return ret;
  }

  @Override
  public List<EventInterface> seeEvent(UserInterface user, LocalDateTime time) {
    // check the ones that overlap with that time
    Time realTime = makeDefaultTime(time, time.plusMinutes(1));
    List<EventInterface> ret = new ArrayList<>();

    for (EventInterface event: user.getSchedule().getEventList()) {
      if(ret.size() != 1) {
        if (makeDefaultEvent(event).getTime().overlapsWith(realTime)) {
          ret.add(event);
        }
      }
      else {
        return ret;
      }
    }
    throw new IllegalStateException("cannot find event at this given time");
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
    throw new UnsupportedOperationException("not implemented in our representation of "
    + "a user or a schedule. As in we don't track users that aren't in the system officially.");
  }

  @Override
  public void addUser(UserInterface user) throws IOException {
    Map<String, ScheduleRep> add = new HashMap<>();
    List<EventRep> events = new ArrayList<>();
    for (EventInterface evnt: user.getSchedule().getEventList()) {
      events.add(makeDefaultEvent(evnt));
    }
    ScheduleRep sched = new Schedule(user.getUsername(), events);
    add.put(user.getUsername(), sched);
    adaptee.addNewUser(add);
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
                          EventInterface newEvent) throws IOException {
    List<String> addUsers = new ArrayList<>();
    List<String> remUsers = new ArrayList<>();
    if (!oldEvent.getUsers().equals(newEvent.getUsers())) {

      // for each user in old event, if new event lou does not contain the given user -> remove
      // for each user in new event, if old event lou does not contain the given user -> add
      for (String person : oldEvent.getUsers()) {
        if (!newEvent.getUsers().contains(person)) {
          remUsers.add(person);
        }
      }
      for (String usr : newEvent.getUsers()) {
        if (!oldEvent.getUsers().contains(usr)) {
          addUsers.add(usr);
        }
      }
      this.adaptee.modifyInvitees(makeDefaultEvent(oldEvent), remUsers, false);
      this.adaptee.modifyInvitees(makeDefaultEvent(oldEvent), addUsers, true);
    }
    else if (!oldEvent.getName().equals(newEvent.getName())) {
      this.adaptee.modifyName(makeDefaultEvent(oldEvent), newEvent.getName());
    }
    else if (!oldEvent.getLocation().equals(newEvent.getLocation()) ||
    !((Boolean.valueOf(oldEvent.isOnline())).equals(newEvent.isOnline()))) {
      this.adaptee.modifyLocation(makeDefaultEvent(oldEvent), new Location(newEvent.isOnline(),
              newEvent.getLocation()));
    }
    else if (!oldEvent.getStartTime().equals(newEvent.getStartTime()) ||
    !oldEvent.getEndTime().equals(newEvent.getEndTime())) {
      this.adaptee.modifyTime(makeDefaultEvent(oldEvent), makeDefaultTime(newEvent.getStartTime(),
              newEvent.getEndTime()));
    }
  }

  @Override
  public void removeEvent(UserInterface user, EventInterface event) throws IOException {
    this.adaptee.removeEvent(makeDefaultEvent(event), user.getUsername());
  }

  @Override
  public void automaticallyScheduleEvent(String name, int duration, String location,
                                         Boolean isOnline, List<String> users) {
    throw new UnsupportedOperationException("We did not schedule through the model but "
    + "through our strategy implementation.");
  }
}
