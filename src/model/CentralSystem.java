package model;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

import model.eventfields.Location;
import model.eventfields.Time;
import xmlfunc.XMLReader;
import xmlfunc.XMLWriter;

// INVARIANT CONSIDERATIONS

/**
 * Representing the central system of a NUPlanner.
 * Contains a map of all schedules in the system.
 * Has a set of all users contained in the system.
 * A list of events in the system.
 */
public class CentralSystem implements NUPlannerSystem {
  private final Map<String, Schedule> allSchedules; // all users -> their respective schedules
  private final List<Event> eventList; // list of all events

  /**
   * Creates the default central system (empty) for uploading user schedules into.
   */
  public CentralSystem() {
    this.allSchedules = new HashMap<String, Schedule>();
    this.eventList = new ArrayList<Event>();
  }

  /**
   * A testing constructor for a system with schedules loaded in.
   * @param allS all schedules for users in the system
   * @param events all unique events in the system
   */
  public CentralSystem(Map<String, Schedule> allS, List<Event> events) {
    this.allSchedules = Objects.requireNonNull(allS); // consider deep copies
    this.eventList = Objects.requireNonNull(events);
  }

  @Override
  public void saveSchedule(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("name must exist and cannot be empty");
    }
    if (!allSchedules.containsKey(name)) {
      throw new IllegalStateException("user not found");
    }
    XMLWriter.writeToFile(this.allSchedules.get(name));
  }

  @Override
  public void addEventToAllSchedules(Event event) {
    // will add an event to all schedules when applicable for invitees/host.
    for (Schedule sched : allSchedules.values()) {
      sched.addEvent(event);
    }
    if (!this.eventList.contains(event)) {
      eventList.add(event);
    }
  }

  @Override
  public void addNewUser(Map<String, Schedule> newUser) {
    // will add an event to all schedules when applicable for invitees/host.
    // INVARIANT CHECK
    this.allSchedules.putAll(newUser);
  }

  @Override
  public void modifyName(Event event, String eventName, String uid) {

  }

  @Override
  public void modifyTime(Event event, Time time, String uid) {

  }

  @Override
  public void modifyLocation(Event event, Location loc, String uid) {

  }

  @Override
  public void modifyInvitees(Event event, List<String> invitees, boolean toAdd, String uid) {

  }

  @Override
  public void removeEvent(Event event, String uid) {

  }


  // new user/schedule -> existing event invitees.
  // if contained && does not conflict with current sched. add event.


  @Override
  public void addUser(File file) {
    try {
      XMLReader reader = new XMLReader(file);
      addNewUser(reader.readXML());
      // INVARIANT CHECKING EVENT OVERLAP
      // IF A USER SHOULD BE ADDED TO A NEW EVENT THAT HAS BEEN LOADED IN
      // EVERY USER IS UNIQUE
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * The users schedule for toString text display implementation.
   * @return    a copy not direct reference to the map storing current user->schedules.
   */
  public Map<String, Schedule> usersSchedules() {
    Map<String, Schedule> copy = new HashMap<>();
    copy.putAll(allSchedules); // removes aliasing
    return copy;
  }
}
