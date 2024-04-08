package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.eventfields.Location;
import model.eventfields.Time;
import xmlfunc.XMLReader;
import xmlfunc.XMLWriter;

/**
 * Representing the central system of a NUPlanner.
 * Contains a map of all schedules in the system.
 * Has a set of all users contained in the system.
 * A list of events in the system.
 */
public class CentralSystem implements NUPlannerSystem {
  // Invariant: all events in the event list are in at least one user's schedule
  // i.e. the size of each event's invitees list is at least one.
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
   * NEW: new constructor keeping track of a list of schedules.
   */
  public CentralSystem(List<Schedule> schedules) {
    this.allSchedules = new HashMap<>();
    this.eventList = new ArrayList<EventRep>();

    // valid schedules is handled by addNewUser method.
    for (Schedule schedule: schedules) {
      Map<String, ScheduleRep> user = new HashMap<>();
      user.put(schedule.scheduleOwner(), schedule);
      addNewUser(user);
    }
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
    if (folderToSaveTo == null) {
      throw new IllegalArgumentException("folder cannot be null");
    }
    if (!folderToSaveTo.exists()) {
      throw new IllegalArgumentException("give a valid folder");
    }
    for (ScheduleRep schedule: this.allSchedules.values()) {
      XMLWriter.writeToFile(schedule, folderToSaveTo);
    }
  }

  @Override
  public void addEventToAllSchedules(EventRep event) {
    eventNullException(event);
    eventAlreadyExistsException(event);
    boolean added = false;

    for (ScheduleRep sched : allSchedules.values()) {
      try {
        sched.addEvent(event);
        added = true;
      } catch (IllegalStateException ex) {
        throw new IllegalStateException(ex);
      }
    }
    if (added) {
      eventList.add(event);
    }
  }

  /**
   * Throws an exception if the given event is already in the current system's
   * list of events.
   * @param event event in question
   * @throws IllegalStateException if the given event is already in the system
   */
  private void eventAlreadyExistsException(EventRep event) {
    if (this.eventList.contains(event)) {
      throw new IllegalStateException("event already exists in system");
    }
  }

  @Override
  public void addEventToInviteeSchedule(String uid, EventRep event) {
    if (uid == null || uid.isEmpty()) {
      throw new IllegalArgumentException("uid cannot be null or empty");
    }
    eventNullException(event);
    if (!this.allSchedules.containsKey(uid)) {
      throw new IllegalStateException("uid is not in system");
    }

    // will add an event to all schedules when applicable for invitees/host.
    try {
      this.allSchedules.get(uid).addEvent(event);
    }
    catch (IllegalStateException e) {
      // NEW**
      throw new IllegalStateException(e);
    }

    if (!eventList.contains(event)) {
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
    if (newUser == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
    if (newUser.isEmpty()) {
      throw new IllegalArgumentException("new user must be non-empty");
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

    if (!this.eventList.isEmpty()) {
      for (EventRep existing : existingSched.eventsPlanned()) {
        for (EventRep newEvent : newUser.get(userID).eventsPlanned()) {
          if (!existing.overlapsWith(newEvent)) {
            existingEvents.add(newEvent);
            this.eventList.add(newEvent);
          }
        }
      }
    }
    else {
      existingEvents.addAll(newUser.get(userID).eventsPlanned());
    }

    this.allSchedules.put(userID, existingSched);
  }

  @Override
  public void modifyName(EventRep event, String eventName) {
    if (event == null || eventName == null || eventName.isEmpty()) {
      throw new IllegalArgumentException("the given event and event name cannot be null");
    }
    eventNotInSystemException(event);

    int eventIdx = this.eventList.indexOf(event);
    EventRep eventToModify = this.eventList.get(eventIdx);
    try {
      eventToModify.modifyName(eventName);
    } catch (IllegalStateException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void modifyTime(EventRep event, Time time) {
    eventNullException(event);
    if (time == null) {
      throw new IllegalArgumentException("time cannot be null");
    }
    eventNotInSystemException(event);

    int eventIdx = this.eventList.indexOf(event);
    EventRep eventToModify = this.eventList.get(eventIdx);
    EventRep eventCopy = new Event(eventToModify.getName(),
            eventToModify.getTime(), eventToModify.getLocation(),
            eventToModify.getInvitedUsers());
    try {
      eventCopy.modifyTime(time);
    } catch (IllegalStateException e) {
      throw new IllegalStateException(e);
    }

    if (anyUserTimeConflictWithNewTime(eventToModify, eventCopy)) {
      throw new IllegalStateException("at least one user has a time conflict");
    }
    else {
      try {
        eventToModify.modifyTime(time);
      } catch (IllegalStateException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  /**
   * Determines if any of the users currently in the system will have the time conflict
   * in their schedule once the given event to be modified becomes the given event that
   * has been modified.
   * @param eventToModify original event yet to change the time of
   * @param eventCopy same as original event but with a time change
   * @return true iff any user has the new event overlapping with any of their other events
   */
  private boolean anyUserTimeConflictWithNewTime(EventRep eventToModify, EventRep eventCopy) {
    return allSchedules.values().stream()
            .anyMatch(s -> eventToModify.getInvitedUsers().contains(s.scheduleOwner())
                    && s.eventsPlanned().stream().anyMatch(e -> e.overlapsWith(eventCopy)
                    && !e.equals(eventToModify)));
  }

  @Override
  public void modifyLocation(EventRep event, Location loc) {
    eventNullException(event);
    if (loc == null) {
      throw new IllegalArgumentException("location cannot be null");
    }
    eventNotInSystemException(event);

    int eventIdx = this.eventList.indexOf(event);
    EventRep eventToModify = this.eventList.get(eventIdx);

    try {
      eventToModify.modifyLocation(loc);
    } catch (IllegalStateException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd) {
    eventNullException(event);
    if (invitees == null || invitees.isEmpty()) {
      throw new IllegalArgumentException("invitees cannot be null or empty");
    }
    if (invitees.contains(event.getInvitedUsers().get(0)) && !toAdd) {
      throw new IllegalArgumentException("you cannot remove the host");
    }
    if (inviteesContainsDuplicates(invitees)) {
      throw new IllegalArgumentException("invitees must be unique");
    }
    eventNotInSystemException(event);
    if (invitees.size() >= event.getInvitedUsers().size() && !toAdd) {
      throw new IllegalStateException("too many invitees to remove");
    }

    int eventIdx = this.eventList.indexOf(event);
    EventRep eventToModify = this.eventList.get(eventIdx);

    addOrRemoveInvitees(event, invitees, toAdd, eventToModify);
  }

  private void addOrRemoveInvitees(EventRep event, List<String> invitees,
                                   boolean toAdd, EventRep eventToModify) {
    if (toAdd) {
      List<String> usersToAdd = new ArrayList<String>();
      for (String invitee : invitees) {
        if (event.getInvitedUsers().stream().noneMatch(f -> f.equals(invitee))) {
          usersToAdd.add(invitee);
        }
      }
      for (String user : usersToAdd) {
        if (allSchedules.containsKey(user)) {
          allSchedules.get(user).addEvent(eventToModify);
        }
      }
      eventToModify.modifyInvitees(usersToAdd, true);
    }
    else {
      List<String> usersToRemove = new ArrayList<String>();
      for (String invitee : invitees) {
        if (event.getInvitedUsers().stream().anyMatch(f -> f.equals(invitee))) {
          usersToRemove.add(invitee);
        }
      }
      for (String user : usersToRemove) {
        if (allSchedules.containsKey(user)) {
          allSchedules.get(user).removeEvent(eventToModify);
        }
      }
      eventToModify.modifyInvitees(usersToRemove, false);
    }
  }

  /**
   * Checks if given list of invitees has multiple of same value.
   * @param invitees invitees in question
   * @return true iff there are duplicated invitee names
   */
  private boolean inviteesContainsDuplicates(List<String> invitees) {
    for (int i = 0; i < invitees.size() - 1; i++) {
      for (int j = i + 1; j < invitees.size(); j++) {
        if (i != j && invitees.get(i).equals(invitees.get(j))) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void removeEvent(EventRep event, String uid) {
    eventNullException(event);
    if (uid == null || uid.isEmpty()) {
      throw new IllegalArgumentException("uid cannot be null or empty");
    }
    eventNotInSystemException(event);
    if (!event.getInvitedUsers().contains(uid)) {
      throw new IllegalStateException("the given user must be invited to the event");
    }
    if (uid.equals(event.getInvitedUsers().get(0))) {
      for (ScheduleRep sched : allSchedules.values()) {
        if (event.getInvitedUsers().contains(sched.scheduleOwner())) {
          sched.removeEvent(event);
        }
      }
    }
    else {
      allSchedules.get(uid).removeEvent(event);
    }
  }

  /**
   * Throws an exception if the given event is not currently in the system.
   * @param event event in question
   */
  private void eventNotInSystemException(EventRep event) {
    if (!eventList.contains(event)) {
      throw new IllegalStateException("event must be in system");
    }
  }

  @Override
  public void addUser(File file) {
    if (file == null) {
      throw new IllegalArgumentException("file cannot be null");
    }
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

  @Override
  public Map<String, ScheduleRep> usersSchedules() {
    Map<String, ScheduleRep> copy = new HashMap<>();
    copy.putAll(this.allSchedules); // removes aliasing
    return copy;
  }

  @Override
  public Set<String> usersInSystem() {
    Set<String> users = new HashSet<>();
    users.addAll(this.usersSchedules().keySet());
    return users; // copy -> not a reference
  }

  @Override
  public boolean doesOverlap(EventRep event) {
    List<ScheduleRep> invitedSchedule = new ArrayList<>();
    for (String user : event.getInvitedUsers()) {
      invitedSchedule.add(this.allSchedules.get(user));
    }
    boolean ret = false;
    for (ScheduleRep scheduleRep : invitedSchedule) {
      ret = ret || scheduleRep.overlapWith(event);
    }
    return ret;
  }

  @Override
  public List<EventRep> getUserEvents(String uid) {
    if (uid == null || uid.isEmpty()) {
      throw new IllegalArgumentException("uid cannot be null/empty");
    }
    return new ArrayList<>(this.allSchedules.get(uid).eventsPlanned());
  }

  @Override
  public List<EventRep> getSystemEvents() {
    return new ArrayList<>(this.eventList);
  }
}
