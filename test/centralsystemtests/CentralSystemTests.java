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
import model.NUPlannerSystem;
import model.Schedule;

/**
 * Tests the functionality of CentralSystem's methods implemented from NUPlannerSystem.
 * Contains examples of a CentralSystem and its features such as a set of users and their
 * schedules and lists of Event.
 */
public class CentralSystemTests {
  Map<String, Schedule> noUsersMap;
  List<Event> noEventsList;
  NUPlannerSystem emptySystem;

  String profLucia;
  String emmaVB;
  String noelisA1;
  String noelisA2;

  //Event event1 = new Event;

  List<Event> profLuciaEvents = new ArrayList<Event>(Arrays.asList());

  //Schedule profLuciaSched = new Schedule(profLucia, )

  Map<String, Schedule> allSchedulesInSystem1;
  List<Event> allEventsInSystem1;

  NUPlannerSystem system1;

  @Before
  public void setup() {
    noUsersMap = new HashMap<String, Schedule>();
    noEventsList = new ArrayList<Event>();
    emptySystem = new CentralSystem(noUsersMap, noEventsList);

    profLucia = "Prof. Lucia";
    emmaVB = "Emma Vonbuelow";
    noelisA1 = "Noelis Aponte";
    noelisA2 = "Noelis Aponte";

    system1 = new CentralSystem(allSchedulesInSystem1, allEventsInSystem1);


  }

  @Test
  public void testSaveScheduleValid() {

  }
}
