package centralsystemtests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CentralSystem;
import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.Schedule;
import model.ScheduleRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality of CentralSystem's methods implemented from NUPlannerSystem.
 * Contains examples of a CentralSystem and its features such as a set of users and their
 * schedules and lists of Event.
 */
public class CentralSystemTests {
  Map<String, ScheduleRep> noUsersMap;
  List<EventRep> noEventsList;
  NUPlannerSystem emptySystem;

  String profLucia;
  String emmaVB;
  String noelisA1;
  String noelisA2;

  EventRep event1;
  EventRep event2;

  List<EventRep> profLuciaEvents;
  List<EventRep> emmaVBEvents;
  List<EventRep> noelisA1Events;

  ScheduleRep profLuciaSched;
  ScheduleRep emmaVBSched;
  ScheduleRep noelisA1Sched;

  Map<String, ScheduleRep> profLuciaMap;
  Map<String, ScheduleRep> emmaVBMap;
  Map<String, ScheduleRep> noelisA1Map;

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
    noelisA2 = "Noelis Aponte";

    event1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            Arrays.asList(profLucia, emmaVB, noelisA1));
    event2 = new Event("CS3500 Day 2",
            new Time(Day.FRIDAY, "0950", Day.FRIDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            Arrays.asList(profLucia, emmaVB, noelisA1));

    profLuciaEvents = new ArrayList<EventRep>(Arrays.asList(event1));
    emmaVBEvents = new ArrayList<EventRep>(Arrays.asList(event1));
    noelisA1Events = new ArrayList<EventRep>(Arrays.asList(event1));

    profLuciaSched = new Schedule(profLucia, profLuciaEvents);
    emmaVBSched = new Schedule(emmaVB, profLuciaEvents);
    noelisA1Sched = new Schedule(noelisA1, profLuciaEvents);

    profLuciaMap = new HashMap<String, ScheduleRep>();
    profLuciaMap.put(profLucia, profLuciaSched);
    emmaVBMap = new HashMap<String, ScheduleRep>();
    emmaVBMap.put(emmaVB, emmaVBSched);
    noelisA1Map = new HashMap<String, ScheduleRep>();
    noelisA1Map.put(noelisA1, noelisA1Sched);

    allSchedulesInSystem1 = new HashMap<String, ScheduleRep>();
    allSchedulesInSystem1.put(profLucia, profLuciaSched);
    allSchedulesInSystem1.put(emmaVB, emmaVBSched);
    allSchedulesInSystem1.put(noelisA1, noelisA1Sched);
    allEventsInSystem1 = new ArrayList<EventRep>();
    allEventsInSystem1.add(event1);

    system1 = new CentralSystem(allSchedulesInSystem1, allEventsInSystem1);

  }

  @Test
  public void testSaveScheduleValid() {
    //TO TEST
  }

  @Test
  public void testAddEventToAllSchedulesExceptions() {
    assertThrows("event cannot be null", IllegalArgumentException.class,
            () -> system1.addEventToAllSchedules(null));
    assertThrows("event already exists in system", IllegalStateException.class,
            () -> system1.addEventToAllSchedules(event1));
    assertFalse(profLuciaSched.eventsPlanned().contains(event2));
    assertFalse(emmaVBSched.eventsPlanned().contains(event2));
    assertFalse(noelisA1Sched.eventsPlanned().contains(event2));
    assertFalse(event1.overlapsWith(event2));
    system1.addEventToAllSchedules(event2);
    assertTrue(profLuciaSched.eventsPlanned().contains(event2));
    assertTrue(emmaVBSched.eventsPlanned().contains(event2));
    assertTrue(noelisA1Sched.eventsPlanned().contains(event2));

  }

  @Test
  public void testModifyName() {

  }

  @Test
  public void testModifyTime() {

  }

  @Test
  public void testModifyLocation() {

  }

  @Test
  public void testModifyInvitees() {

  }

  @Test
  public void testRemoveEvent() {

  }
}