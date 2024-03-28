package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality of CentralSystem's methods implemented from NUPlannerSystem.
 * Contains examples of a CentralSystem and its features such as a set of users and their
 * schedules and lists of events.
 */
public class CentralSystemTests {
  Map<String, ScheduleRep> noUsersMap;
  List<EventRep> noEventsList;
  NUPlannerSystem emptySystem;

  String profLucia;
  String emmaVB;
  String noelisA1;
  String noelisA2;

  Time time1;

  Location loc1;

  EventRep event1;
  EventRep oldEvent1;
  EventRep event2;
  EventRep event3;

  List<EventRep> profLuciaEvents;
  List<EventRep> emmaVBEvents;
  List<EventRep> noelisA1Events;

  ScheduleRep profLuciaSched;
  ScheduleRep emmaVBSched;
  ScheduleRep noelisA1Sched;
  ScheduleRep noelisA2Sched;

  Map<String, ScheduleRep> allSchedulesInSystem1;
  List<EventRep> allEventsInSystem1;

  NUPlannerSystem system1;

  @Before
  public void setup() {
    noUsersMap = new HashMap<String, ScheduleRep>();
    noEventsList = new ArrayList<EventRep>();
    emptySystem = new CentralSystem(noUsersMap, noEventsList);

    profLucia = "Prof. Lucia";
    emmaVB = "Emma Vonbuelow";
    noelisA1 = "Noelis Aponte";
    noelisA2 = "Noelis Aponte2";

    time1 = new Time(Day.MONDAY, "1500", Day.MONDAY, "2000");

    loc1 = new Location(false, "restaurant");

    event1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    oldEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    event2 = new Event("CS3500 Day 2",
            new Time(Day.FRIDAY, "0950", Day.FRIDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    event3 = new Event("BBQ",
            new Time(Day.FRIDAY, "0950", Day.FRIDAY, "1130"),
            new Location(true, "Not Churchill"),
            new ArrayList<String>(Arrays.asList(emmaVB)));

    profLuciaEvents = new ArrayList<EventRep>(Arrays.asList(event1));
    emmaVBEvents = new ArrayList<EventRep>(Arrays.asList(event1));
    noelisA1Events = new ArrayList<EventRep>(Arrays.asList(event1));

    profLuciaSched = new Schedule(profLucia, profLuciaEvents);
    emmaVBSched = new Schedule(emmaVB, emmaVBEvents);
    noelisA1Sched = new Schedule(noelisA1, noelisA1Events);
    noelisA2Sched = new Schedule(noelisA2, new ArrayList<EventRep>());

    allSchedulesInSystem1 = new HashMap<String, ScheduleRep>();
    allSchedulesInSystem1.put(profLucia, profLuciaSched);
    allSchedulesInSystem1.put(emmaVB, emmaVBSched);
    allSchedulesInSystem1.put(noelisA1, noelisA1Sched);
    allEventsInSystem1 = new ArrayList<EventRep>();
    allEventsInSystem1.add(event1);

    system1 = new CentralSystem(allSchedulesInSystem1, allEventsInSystem1);
  }

  /*@Test
  public void testSaveSchedule() {
    //TO TEST
  }*/

  /**
   * Tests exceptions for addEventToAllSchedules in CentralSystem.
   */
  @Test
  public void testAddEventToAllSchedulesExceptions() {
    assertThrows("event cannot be null", IllegalArgumentException.class, () ->
            system1.addEventToAllSchedules(null));
    assertThrows("event already exists in system", IllegalStateException.class, () ->
            system1.addEventToAllSchedules(event1));
  }

  /**
   * Tests adding an event to all invited users' schedules.
   * Event does not conflict with any invited user's existing events.
   */
  @Test
  public void testAddEventToAllSchedulesNoConflicts() {
    assertFalse(profLuciaSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event2));
    assertFalse(allEventsInSystem1.contains(event2));
    assertFalse(event1.overlapsWith(event2));
    system1.addEventToAllSchedules(event2);
    assertTrue(profLuciaSched.eventsPlanned().contains(event2));
    assertTrue(emmaVBSched.eventsPlanned().contains(event2));
    assertTrue(noelisA1Sched.eventsPlanned().contains(event2));
    assertTrue(allEventsInSystem1.contains(event2));
  }

  /**
   * Tests adding an event to all invited users' schedules.
   * Event conflicts with at least one invited user's existing events.
   * Event does not get added to the system's list of events.
   */
  @Test
  public void testAddEventToAllSchedulesSomeConflict() {
    // one user is invited to an event
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event3));
    system1.addEventToInviteeSchedule(emmaVB, event3);
    assertTrue(emmaVBSched.eventsPlanned().contains(event3));

    // that same user is only one not invited to event
    assertFalse(profLuciaSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event2));
    assertFalse(allEventsInSystem1.contains(event2));
    assertFalse(event1.overlapsWith(event2));
    assertTrue(event3.overlapsWith(event2));
    assertThrows("event exists already, conflicts with another, or owner isn't invited",
            IllegalStateException.class, () -> system1.addEventToAllSchedules(event2));
    assertFalse(profLuciaSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event2));
    assertFalse(allEventsInSystem1.contains(event2));
  }

  /**
   * Tests adding an event to all invited users' schedules.
   * Event conflicts with all invited users' existing events.
   * Event does not get added to the system's list of events.
   */
  @Test
  public void testAddEventToAllSchedulesTotalConflict() {
    assertFalse(profLuciaSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event2));
    assertFalse(allEventsInSystem1.contains(event2));
    assertFalse(event1.overlapsWith(event2));
    system1.addEventToAllSchedules(event2);
    assertTrue(profLuciaSched.eventsPlanned().contains(event2));
    assertTrue(emmaVBSched.eventsPlanned().contains(event2));
    assertTrue(noelisA1Sched.eventsPlanned().contains(event2));
    assertTrue(allEventsInSystem1.contains(event2));

    assertFalse(profLuciaSched.eventsPlanned().contains(event3));
    assertFalse(emmaVBSched.eventsPlanned().contains(event3));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event3));
    assertFalse(allEventsInSystem1.contains(event3));
    assertTrue(event2.overlapsWith(event3));
    assertThrows("event exists already, conflicts with another, or owner isn't invited",
            IllegalStateException.class, () -> system1.addEventToAllSchedules(event3));
    assertFalse(profLuciaSched.eventsPlanned().contains(event3));
    assertFalse(emmaVBSched.eventsPlanned().contains(event3));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event3));
    assertFalse(allEventsInSystem1.contains(event3));
  }

  /**
   * Tests exceptions for addEventToInviteeSchedule in CentralSystem.
   */
  @Test
  public void testAddEventToInviteeScheduleExceptions() {
    assertThrows("event cannot be null", IllegalArgumentException.class, () ->
            system1.addEventToInviteeSchedule(emmaVB, null));
    assertThrows("uid cannot be null or empty", IllegalArgumentException.class, () ->
            system1.addEventToInviteeSchedule(null, event1));
    assertThrows("uid cannot be null or empty", IllegalArgumentException.class, () ->
            system1.addEventToInviteeSchedule("", event1));
    assertThrows("uid is not in system", IllegalStateException.class, () ->
            system1.addEventToInviteeSchedule("hi", event1));
  }

  /*@Test
  public void testAddEventToInviteeSchedule() {
    //
  }*/

  /**
   * Tests exceptions for modifyName in CentralSystem.
   */
  @Test
  public void testModifyNameExceptions() {
    assertThrows("the given event and event name cannot be null/empty",
            IllegalArgumentException.class, () -> system1.modifyName(null, null));
    assertThrows("the given event and event name cannot be null/empty",
            IllegalArgumentException.class, () ->
                    system1.modifyName(null, "event1"));
    assertThrows("the given event and event name cannot be null/empty",
            IllegalArgumentException.class, () -> system1.modifyName(event1, ""));
    assertThrows("event must be in system",
            IllegalStateException.class, () -> system1.modifyName(event3, "event3"));
  }

  /**
   * Tests the usual valid case when calling modifyName.
   * The given name is different from the current event name.
   * Old event cannot be found in system, while the new event can be.
   * Invitees' schedules have the updated event.
   */
  @Test
  public void testModifyNameValid() {
    EventRep newEvent1 = new Event("CS3500 Lecture Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyName(oldEvent1, "CS3500 Lecture Day 1");

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests exceptions for modifyTime in CentralSystem.
   */
  @Test
  public void testModifyTimeExceptions() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () -> system1.modifyTime(null, time1));
    assertThrows("time cannot be null",
            IllegalArgumentException.class, () -> system1.modifyTime(event1, null));
    assertThrows("event must be in system",
            IllegalStateException.class, () -> system1.modifyTime(event2, time1));
  }

  /**
   * Tests modifying the time of an event to a different start hour.
   */
  @Test
  public void testModifyTimeValidDifferentStartHour() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0850", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyTime(oldEvent1,
            new Time(Day.TUESDAY, "0850", Day.TUESDAY, "1130"));

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests modifying the time of an event to a different end hour.
   */
  @Test
  public void testModifyTimeValidDifferentEndHour() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1230"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyTime(oldEvent1,
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1230"));

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests modifying the time of an event to a different start day.
   */
  @Test
  public void testModifyTimeValidDifferentStartDay() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.SUNDAY, "0950", Day.THURSDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyTime(oldEvent1,
            new Time(Day.SUNDAY, "0950", Day.THURSDAY, "1130"));

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests modifying the time of an event to a different end day.
   */
  @Test
  public void testModifyTimeValidDifferentEndDay() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.THURSDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyTime(oldEvent1,
            new Time(Day.TUESDAY, "0950", Day.THURSDAY, "1130"));

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests modifying the time of an event where at least one invitee (non-host)
   * has a time conflict. This means the event is not modified (exception thrown).
   */
  @Test
  public void testModifyTimeOneUserTimeConflict() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.FRIDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    emmaVBSched.addEvent(event3); // BBQ conflicts with lecture time to be modified
    assertThrows("at least one user has a conflict with the new time",
            IllegalStateException.class, () ->
                    system1.modifyTime(oldEvent1, new Time(Day.FRIDAY, "0950",
                    Day.TUESDAY, "1130")));

    // therefore event does not change times
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));
  }

  /**
   * Tests exceptions for modifyLocation in CentralSystem.
   */
  @Test
  public void testModifyLocationExceptions() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () -> system1.modifyLocation(null, loc1));
    assertThrows("location cannot be null",
            IllegalArgumentException.class, () -> system1.modifyLocation(event1, null));
    assertThrows("event must be in system",
            IllegalStateException.class, () -> system1.modifyLocation(event3, loc1));
  }

  /**
   * Tests modifying the location of an event.
   * Changes the online status.
   */
  @Test
  public void testModifyLocationOnline() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(true, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyLocation(oldEvent1,
            new Location(true, "Churchill Hall 101"));

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests modifying the location of an event.
   * Changes the place of the event.
   */
  @Test
  public void testModifyLocationPlace() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "somewhere else"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    system1.modifyLocation(oldEvent1,
            new Location(false, "somewhere else"));

    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
  }

  /**
   * Tests exceptions for modifyInvitees in CentralSystem.
   */
  @Test
  public void testModifyInviteesExceptions() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () ->
                    system1.modifyInvitees(null, new ArrayList<String>(),
                    false));
    assertThrows("invitees cannot be null or empty", IllegalArgumentException.class, () ->
            system1.modifyInvitees(event1, null, true));
    assertThrows("invitees cannot be null or empty", IllegalArgumentException.class,() ->
            system1.modifyInvitees(event1, new ArrayList<String>(), true));
    assertThrows("you cannot remove the host", IllegalArgumentException.class, () ->
            system1.modifyInvitees(event1,
                    new ArrayList<String>(Collections.singletonList(profLucia)), false));
    assertThrows("invitees must be unique",
            IllegalArgumentException.class, () -> system1.modifyInvitees(event1,
                    new ArrayList<String>(Arrays.asList(noelisA1, noelisA1)), true));
    assertThrows("event must be in system",
            IllegalStateException.class, () -> system1.modifyInvitees(event2,
                    new ArrayList<String>(Collections.singletonList(emmaVB)),
                    false));
    assertThrows("too many invitees to remove",
            IllegalStateException.class, () -> system1.modifyInvitees(event1,
                    new ArrayList<String>(Arrays.asList(emmaVB, noelisA1, noelisA2)),
                    false));
  }

  /**
   * Tests valid case for adding/removing invitees.
   * Adding at least one new invitee for event with at least two invitees.
   */
  @Test
  public void testModifyInviteesAddOneNewInvitee() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1, noelisA2)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    Map<String, ScheduleRep> newUser = new HashMap<String, ScheduleRep>();
    newUser.put(noelisA2, noelisA2Sched);
    system1.modifyInvitees(oldEvent1,
            new ArrayList<String>(Arrays.asList(noelisA2)), true);
    system1.addNewUser(newUser);

    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA2).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA2).get(0));
    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
  }

  /**
   * Tests valid case for adding/removing invitees.
   * Adding people that already exist and at least one does not.
   */
  @Test
  public void testModifyInviteesAddingExistingAndNew() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1, noelisA2)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    Map<String, ScheduleRep> newUser = new HashMap<String, ScheduleRep>();
    newUser.put(noelisA2, noelisA2Sched);
    system1.modifyInvitees(oldEvent1,
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA2)),
            true);
    system1.addNewUser(newUser);

    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertEquals(newEvent1, system1.getUserEvents(noelisA2).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA2).get(0));
    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
  }

  /**
   * Tests valid case for adding/removing invitees.
   * Removing people that exist and don't.
   */
  @Test
  public void testModifyInviteesRemovingExistingAndNew() {
    EventRep newEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, noelisA1)));
    assertEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(emmaVB).get(0));
    assertNotEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(oldEvent1));
    assertFalse(system1.getSystemEvents().contains(newEvent1));

    Map<String, ScheduleRep> newUser = new HashMap<String, ScheduleRep>();
    newUser.put(noelisA2, noelisA2Sched);
    system1.addNewUser(newUser);
    system1.modifyInvitees(event1,
            new ArrayList<String>(Arrays.asList(emmaVB, noelisA2)),
            false);

    assertEquals(newEvent1, system1.getUserEvents(profLucia).get(0));
    assertTrue(system1.getUserEvents(emmaVB).isEmpty());
    assertEquals(newEvent1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getUserEvents(noelisA2).isEmpty());
    assertNotEquals(oldEvent1, system1.getUserEvents(profLucia).get(0));
    assertNotEquals(oldEvent1, system1.getUserEvents(noelisA1).get(0));
    assertFalse(system1.getSystemEvents().contains(oldEvent1));
    assertTrue(system1.getSystemEvents().contains(newEvent1));
  }

  /**
   * Tests exceptions for removeEvent in CentralSystem.
   */
  @Test
  public void testRemoveEventExceptions() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () ->
                    system1.removeEvent(null, "profLucia"));
    assertThrows("uid cannot be null or empty",
            IllegalArgumentException.class, () -> system1.removeEvent(event1, null));
    assertThrows("uid cannot be null or empty",
            IllegalArgumentException.class, () -> system1.removeEvent(event1, ""));
    assertThrows("event must be in system",
            IllegalStateException.class, () -> system1.removeEvent(event3, emmaVB));
    assertThrows("the given user must be invited to the event",
            IllegalStateException.class, () -> system1.removeEvent(event1, noelisA2));
  }

  /**
   * Tests host removing valid existing events for removeEvent in CentralSystem.
   */
  @Test
  public void testRemoveEventValidByHost() {
    assertEquals(event1, system1.getUserEvents(profLucia).get(0));
    assertEquals(event1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(event1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(event1));

    system1.removeEvent(event1, profLucia);

    // removes event for all users
    assertTrue(system1.getUserEvents(profLucia).isEmpty());
    assertTrue(system1.getUserEvents(emmaVB).isEmpty());
    assertTrue(system1.getUserEvents(noelisA1).isEmpty());
    assertTrue(system1.getSystemEvents().contains(event1));
  }

  /**
   * Tests non-host removing valid existing events for removeEvent in CentralSystem.
   */
  @Test
  public void testRemoveEventValidByNonHost() {
    assertEquals(event1, system1.getUserEvents(profLucia).get(0));
    assertEquals(event1, system1.getUserEvents(emmaVB).get(0));
    assertEquals(event1, system1.getUserEvents(noelisA1).get(0));
    assertTrue(system1.getSystemEvents().contains(event1));

    system1.removeEvent(event1, noelisA1);

    // removes event for just one user
    assertEquals(event1, system1.getUserEvents(profLucia).get(0));
    assertEquals(event1, system1.getUserEvents(emmaVB).get(0));
    assertTrue(system1.getUserEvents(noelisA1).isEmpty());
    assertTrue(system1.getSystemEvents().contains(event1));
  }

  /**
   * Tests exception thrown by addUser.
   */
  @Test
  public void testAddUserException() {
    assertThrows("file cannot be null",
            IllegalArgumentException.class, () -> system1.addUser(null));
  }
}