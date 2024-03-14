package model;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

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
  private final Set<String> allUsers; // all users
  private final List<Event> eventList; // list of all events

  // default
  CentralSystem() {
    this.allSchedules = new HashMap<String, Schedule>();
    this.allUsers = this.allSchedules.keySet();
    this.eventList = new ArrayList<>();
  }

  // testing constructor
  CentralSystem(Map<String, Schedule> allS, List<Event> events) {
    this.allSchedules = allS;
    this.allUsers = this.allSchedules.keySet();
    this.eventList = events;
  }

  @Override
  public void saveSchedule(String name) {
    XMLWriter.writeToFile(this.allSchedules.get(name));
  }

  @Override
  public void addSchedule(Event event) {
    // will add an event to all schedules when applicable for invitees/host.
  }

  @Override
  public void addSchedule(Map<String, Schedule> newUser) {
    // will add an event to all schedules when applicable for invitees/host.
    // INVARIANT CHECK
    this.allSchedules.putAll(newUser);
  }

  // new user/schedule -> existing event invitees.
  // if contained && doesnt conflict with currech sched. add event.

  @Override
  public void modify(Event event) {
    // changes some aspect of an existing event to be updated.
    // and updates all who are invited to the event as well.
  }

  @Override
  public void remove(Event event) {
    // removes an event from the host and all invitees schedules.
  }


  @Override
  public void addUser(File file) {
    try {
      XMLReader reader = new XMLReader(file);
      addSchedule(reader.read());
      // INVARIANT CHECKING EVENT OVERLAP
      // IF A USER SHOULD BE ADDED TO A NEW EVENT THAT HAS BEEN LOADED IN
      // EVERY USER IS UNIQUE
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
    catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    catch (Exception e) {
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
