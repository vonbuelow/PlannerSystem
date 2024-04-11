package controller.strategy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import static org.junit.Assert.assertThrows;

/**
 * The strategies for work hours tests.
 * Testing for scheduling an event in work hours.
 */
public class WorkHoursStratTests {
  String profLucia;
  String emmaVB;
  String noelisA1;
  Time time1;
  Location loc1;
  EventRep event1;
  EventRep event2;
  EventRep event3;
  List<EventRep> profLuciaEvents;
  List<EventRep> emmaVBEvents;
  List<EventRep> noelisA1Events;
  ScheduleRep profLuciaSched;
  ScheduleRep emmaVBSched;
  ScheduleRep noelisA1Sched;
  Map<String, ScheduleRep> allSchedulesInSystem1;
  List<EventRep> allEventsInSystem1;
  NUPlannerSystem system1;
  ScheduleStrat workhours;


  @Before
  public void setUp() {
    profLucia = "Prof. Lucia";
    emmaVB = "Emma Vonbuelow";
    noelisA1 = "Noelis Aponte";

    time1 = new Time(Day.MONDAY, "1500", Day.MONDAY, "2000");

    loc1 = new Location(false, "restaurant");

    event1 = new Event("CS3500 Day 1",
            new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    event2 = new Event("CS3500 Day 2",
            new Time(Day.MONDAY, "0900", Day.MONDAY, "1040"),
            new Location(false, "Churchill Hall 101"),
            new ArrayList<String>(Arrays.asList(profLucia, emmaVB, noelisA1)));
    event3 = new Event("BBQ",
            new Time(Day.FRIDAY, "0950", Day.FRIDAY, "1130"),
            new Location(true, "Not Churchill"),
            new ArrayList<String>(Collections.singletonList(emmaVB)));

    profLuciaEvents = new ArrayList<EventRep>(Collections.singletonList(event1));
    emmaVBEvents = new ArrayList<EventRep>(Collections.singletonList(event1));
    noelisA1Events = new ArrayList<EventRep>(Collections.singletonList(event1));

    profLuciaSched = new Schedule(profLucia, profLuciaEvents);
    emmaVBSched = new Schedule(emmaVB, emmaVBEvents);
    noelisA1Sched = new Schedule(noelisA1, noelisA1Events);

    allSchedulesInSystem1 = new HashMap<String, ScheduleRep>();
    allSchedulesInSystem1.put(profLucia, profLuciaSched);
    allSchedulesInSystem1.put(emmaVB, emmaVBSched);
    allSchedulesInSystem1.put(noelisA1, noelisA1Sched);
    allEventsInSystem1 = new ArrayList<EventRep>();
    allEventsInSystem1.add(event1);

    system1 = new CentralSystem(allSchedulesInSystem1, allEventsInSystem1);

    workhours = new WorkHoursStrat(system1);
  }

  @Test
  public void testSchedule() {
    assertThrows("duration must be at least 1 min but can't be more than 480",
            IllegalArgumentException.class, () -> workhours.schedule("CS3500 Day 2",
                    new Location(false, "Churchill Hall 101"),
                    10080, new ArrayList<String>(
                            Arrays.asList(profLucia, emmaVB, noelisA1))));
    assertThrows("duration must be at least 1 min but can't be more than 480",
            IllegalArgumentException.class, () -> workhours.schedule("CS3500 Day 2",
                    new Location(false, "Churchill Hall 101"),
                    0, new ArrayList<String>(
                            Arrays.asList(profLucia, emmaVB, noelisA1))));
    assertEquals(event2, workhours.schedule("CS3500 Day 2",
            new Location(false, "Churchill Hall 101"),
            100, new ArrayList<String>(
                    Arrays.asList(profLucia, emmaVB, noelisA1))));
  }
}
