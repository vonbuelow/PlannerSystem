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
  // Invariant: all events in the event list are in at least one user's schedule
  private final Map<String, ScheduleRep> allSchedules; // all users -> their respective schedules
  private final List<EventRep> eventList; // list of all events if it is useful -> maintain

  /**
   * Creates the default central system (empty) for uploading user schedules into.
   */
  public CentralSystem() {
    this.allSchedules = new HashMap<String, ScheduleRep>();
    this.eventList = new ArrayList<EventRep>();
  }

  /**
   * A testing constructor for a system with schedules loaded in.
   * @param allS all schedules for users in the system
   * @param events all unique events in the system
   */
  public CentralSystem(Map<String, ScheduleRep> allS, List<EventRep> events) {
    this.allSchedules = Objects.requireNonNull(allS); // consider deep copies
    this.eventList = Objects.requireNonNull(events);
  }

  @Override
  public void saveSchedule(File folderToSaveTo) {
    if (!folderToSaveTo.exists()) {
      throw new IllegalArgumentException("give a valid folder");
    }
    for (ScheduleRep schedule: this.allSchedules.values()) {
      XMLWriter.writeToFile(schedule, folderToSaveTo.getAbsolutePath());
    }
  }

  @Override
  public void addEventToAllSchedules(EventRep event) {
    eventNullException(event);
    if (this.eventList.contains(event)) {
      throw new IllegalStateException("event already exists in system");
    }
    // will add an event to all schedules when applicable for invitees/host.
    for (ScheduleRep sched : allSchedules.values()) {
      sched.addEvent(event);
    }
    if (!this.eventList.contains(event)) {
      eventList.add(event);
    }
  }

  /**
   * Throws an IllegalArgumentException if the given event is null.
   * @param event the event in question
   * @throws IllegalArgumentException if the given event is null
   */
  private static void eventNullException(EventRep event) {
    if (event == null) {
      throw new IllegalArgumentException("event cannot be null");
    }
  }

  @Override
  public void addNewUser(Map<String, ScheduleRep> newUser) {
    // will add an event to all schedules when applicable for invitees/host.
    // INVARIANT CHECK
    if (newUser == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    if (newUser.isEmpty()) {
      throw new IllegalStateException("new user must be non-empty");
    }

    String userID = newUser.keySet().iterator().next();
    if (this.allSchedules.containsKey(userID)) {
      throw new IllegalStateException("new user must be unique");
    }

    List<EventRep> existingEvents = new ArrayList<EventRep>();
    ScheduleRep existingSched = new Schedule(userID, existingEvents);

    for (EventRep e : this.eventList) {
      if (e.getInvitedUsers().contains(userID)) {
        existingEvents.add(e);
      }
    }

    for (EventRep existing : existingSched.eventsPlanned()) {
      for (EventRep newEvent : newUser.values().iterator().next().eventsPlanned()) {
        if (!existing.overlapsWith(newEvent)) {
          existingEvents.add(newEvent);
        }
      }
    }

    Map<String, ScheduleRep> officialNewUser = new HashMap<String, ScheduleRep>();
    officialNewUser.put(userID, existingSched);

    /*this.allSchedules.putAll();*/
  }

  @Override
  public void modifyName(EventRep event, String eventName, String uid) {
    if (event == null || eventName == null || uid == null) {
      throw new IllegalArgumentException("the given event, name, and uid cannot be null");
    }
    if (!this.eventList.contains(event)
            || !allSchedules.get(uid).eventsPlanned().contains(event)) {
      throw new IllegalStateException("event must be in the system and"
              + " in the given user's schedule");
    }
    //if (uid.isEmpty() || )
    /*if (allSchedules.get(uid).eventsPlanned().an) {

    }*/
  }

  @Override
  public void modifyTime(EventRep event, Time time, String uid) {

  }

  @Override
  public void modifyLocation(EventRep event, Location loc, String uid) {

  }

  @Override
  public void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd, String uid) {

  }

  @Override
  public void removeEvent(EventRep event, String uid) {
    eventNullException(event);
    if (!eventList.contains(event)) {
      throw new IllegalStateException("event must be in system");
    }
    if (uid.equals(event.getInvitedUsers().get(0))) {
      for (ScheduleRep sched : allSchedules.values()) {
        sched.removeEvent(event);
      }
    }
    else {
      allSchedules.get(uid).removeEvent(event);
    }
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
  public Map<String, ScheduleRep> usersSchedules() {
    Map<String, ScheduleRep> copy = new HashMap<>();
    copy.putAll(allSchedules); // removes aliasing
    return copy;
  }
}
