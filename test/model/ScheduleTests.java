package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;
import model.eventfields.TimeRep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for ScheduleRep methods.
 */
public class ScheduleTests {
  Map<String, ScheduleRep> noUsersMap;
  List<EventRep> noEventsList;
  NUPlannerSystem emptySystem;

  String profLucia;
  String emmaVB;
  String noelisA1;
  String noelisA2;

  TimeRep time1;

  Location loc1;

  EventRep event1;
  EventRep oldEvent1;
  EventRep event2;
  EventRep event3;
  EventRep event4;

  List<EventRep> profLuciaEvents;
  List<EventRep> emmaVBEvents;
  List<EventRep> noelisA1Events;

  ScheduleRep profLuciaSched;
  ScheduleRep emmaVBSched;
  ScheduleRep noelisA1Sched;
  ScheduleRep noelisA2Sched;

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
            Arrays.asList(profLucia, emmaVB, noelisA1));
    oldEvent1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            Arrays.asList(profLucia, emmaVB, noelisA1));
    event2 = new Event("CS3500 Day 2",
            new Time(Day.FRIDAY, "0950", Day.FRIDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            Arrays.asList(profLucia, emmaVB, noelisA1));
    event3 = new Event("BBQ",
            new Time(Day.FRIDAY, "0950", Day.FRIDAY, "1130"),
            new Location(true, "Not Churchill"), Arrays.asList(emmaVB));
    event4 = new Event("Event 4",
            new Time(Day.MONDAY, "2300", Day.FRIDAY, "2300"),
            new Location(true, "somewhere"), Arrays.asList(noelisA1));

    profLuciaEvents = new ArrayList<EventRep>(Arrays.asList(event1));
    emmaVBEvents = new ArrayList<EventRep>(Arrays.asList(event1));
    noelisA1Events = new ArrayList<EventRep>(Arrays.asList(event1));

    profLuciaSched = new Schedule(profLucia, profLuciaEvents);
    emmaVBSched = new Schedule(emmaVB, emmaVBEvents);
    noelisA1Sched = new Schedule(noelisA1, noelisA1Events);
    noelisA2Sched = new Schedule(noelisA2, new ArrayList<EventRep>());
  }

  /**
   * Confirms that the uid of the owner of the schedule can be retrieved.
   */
  @Test
  public void testScheduleOwner() {
    assertEquals("Prof. Lucia", profLuciaSched.scheduleOwner());
    assertEquals("Emma Vonbuelow", emmaVBSched.scheduleOwner());
    assertEquals("Noelis Aponte", noelisA1Sched.scheduleOwner());
  }

  /**
   * Confirms that the list of events from the current schedule can be retrieved.
   */
  @Test
  public void testEventsPlanned() {
    assertEquals(profLuciaEvents, profLuciaSched.eventsPlanned());
    assertEquals(emmaVBEvents, emmaVBSched.eventsPlanned());
    assertEquals(noelisA1Events, noelisA1Sched.eventsPlanned());
  }

  /**
   * Tests exceptions thrown in addEvent.
   */
  @Test
  public void testAddEventExceptions() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () -> profLuciaSched.addEvent(null));
    assertThrows("event exists already, conflicts with another, or owner isn't invited",
            IllegalStateException.class, () -> emmaVBSched.addEvent(event1));
    assertThrows("event exists already, conflicts with another, or owner isn't invited",
            IllegalStateException.class, () -> noelisA1Sched.addEvent(event4));
    // conflict with event1
    assertThrows("event exists already, conflicts with another, or owner isn't invited",
            IllegalStateException.class, () -> noelisA1Sched.addEvent(event3)); // not invited
  }

  /**
   * Confirms that when given a valid new event, not conflicting with current
   * schedules, the schedule can add the event.
   */
  @Test
  public void testAddEventValid() {
    assertFalse(profLuciaSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event2));

    profLuciaSched.addEvent(event2);
    emmaVBSched.addEvent(event2);
    noelisA1Sched.addEvent(event2);

    assertTrue(profLuciaSched.eventsPlanned().contains(event2));
    assertTrue(emmaVBSched.eventsPlanned().contains(event2));
    assertTrue(noelisA1Sched.eventsPlanned().contains(event2));
  }

  /**
   * Tests exceptions thrown in removeEvent.
   */
  @Test
  public void testRemoveEventExceptions() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () -> profLuciaSched.removeEvent(null));
    assertThrows("event must be in schedule",
            IllegalStateException.class, () -> emmaVBSched.removeEvent(event3));
  }

  /**
   * Confirms that when given a valid existing event, the schedule can remove it.
   */
  @Test
  public void testRemoveEventValid() {
    assertTrue(profLuciaSched.eventsPlanned().contains(event1));
    assertTrue(emmaVBSched.eventsPlanned().contains(event1));
    assertTrue(noelisA1Sched.eventsPlanned().contains(event1));

    profLuciaSched.removeEvent(event1);
    emmaVBSched.removeEvent(event1);
    noelisA1Sched.removeEvent(event1);

    assertFalse(profLuciaSched.eventsPlanned().contains(event1));
    assertFalse(emmaVBSched.eventsPlanned().contains(event1));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event1));
  }

  /**
   * Tests exception in overlapWith.
   */
  @Test
  public void testOverlapWithException() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () -> noelisA1Sched.overlapWith(null));
  }

  /**
   * Confirms the cases in which the current schedule has a time conflict
   * with a given valid event.
   */
  @Test
  public void testOverlapWithValidAndTrue() {
    assertTrue(profLuciaSched.overlapWith(event1)); // same event
    assertTrue(profLuciaSched.overlapWith(event4)); // two conflicts
    assertFalse(emmaVBSched.overlapWith(event3)); // not invited yet
  }

  /**
   * Confirms the cases in which the current schedule has no time conflict
   * with a given valid event.
   */
  @Test
  public void testOverlapWithValidAndFalse() {
    assertFalse(noelisA2Sched.overlapWith(event1)); // no existing events for conflict
    assertFalse(emmaVBSched.overlapWith(event2)); // different times
  }
}