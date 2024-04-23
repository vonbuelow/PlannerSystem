package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.SatStartDay;
import model.eventfields.SatStartTime;
import model.eventfields.Time;
import model.eventfields.TimeRep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * The testing of event specific functionality.
 * Along with silly edge cases.
 */
public class SatStartEventTests {
  TimeRep time1;
  TimeRep time2;
  TimeRep time3;
  TimeRep time4;
  TimeRep time5;
  TimeRep time6;
  TimeRep time7;
  TimeRep time8;
  TimeRep time9;
  TimeRep time10;

  Location loc1;
  Location loc2;
  Location loc3;
  Location loc4;
  Location loc5;
  Location loc6;
  Location loc7;

  List<String> invitees1;
  List<String> invitees2;
  List<String> invitees3;
  List<String> invitees4;
  List<String> invitees5;

  EventRep event1;
  EventRep event2;
  EventRep event3;
  EventRep event4;
  EventRep event5;
  EventRep event6;
  EventRep event7;
  EventRep event8;
  EventRep event9;
  EventRep event10;
  EventRep event11;

  @Before
  public void setup() {
    time1 = new SatStartTime(SatStartDay.MONDAY, "0900",
            SatStartDay.MONDAY, "1000");
    time2 = new SatStartTime(SatStartDay.TUESDAY, "0950",
            SatStartDay.TUESDAY, "1130");
    time3 = new SatStartTime(SatStartDay.FRIDAY, "1130",
            SatStartDay.TUESDAY, "0950");
    time4 = new SatStartTime(SatStartDay.WEDNESDAY, "1600",
            SatStartDay.WEDNESDAY, "1559");
    time5 = new SatStartTime(SatStartDay.MONDAY, "0900",
            SatStartDay.MONDAY, "1000");
    time6 = new SatStartTime(SatStartDay.MONDAY, "0900",
            SatStartDay.MONDAY, "1000");
    time7 = new SatStartTime(SatStartDay.TUESDAY, "1000",
            SatStartDay.TUESDAY, "1100");
    time8 = new SatStartTime(SatStartDay.WEDNESDAY, "1400",
            SatStartDay.WEDNESDAY, "1800");
    time9 = new SatStartTime(SatStartDay.MONDAY, "0900",
            SatStartDay.MONDAY, "0930");
    time10 = new SatStartTime(SatStartDay.WEDNESDAY, "1600",
            SatStartDay.WEDNESDAY, "1800");

    loc1 = new Location(false, "ur mom's house");
    loc2 = new Location(true, "my mom's house");
    loc3 = new Location(false, "Churchill Hall 101");
    loc4 = new Location(true, "my mom's house");
    loc5 = new Location(false, "Churchill Hall 101");
    loc6 = new Location(false, "Churchill Hall 101");
    loc7 = new Location(false, "Chuck E. Cheese's");

    invitees1 = new ArrayList<String>(
            Arrays.asList("Prof. Lucia", "Noelis Aponte", "Emma Vonbuelow"));
    invitees2 = new ArrayList<String>(
            Arrays.asList("ur mom", "ur dad", "ur uncle", "ur dog", "me"));
    invitees3 = new ArrayList<String>(
            Arrays.asList("Prof. Lucia", "Noelis Aponte", "Emma Vonbuelow",
                    "Lisandra", "Felix Sr.", "Felix Jr."));
    invitees4 = new ArrayList<String>(
            Arrays.asList("Prof. Hater", "Hater1", "Hater2"));
    invitees5 = new ArrayList<String>(
            Arrays.asList("Prof. Other", "Other1", "Other2"));

    event1 = new SatStartEvent("CS3500", time2, loc3, invitees1);
    event2 = new SatStartEvent("BBQ", time4, loc1, invitees2);
    event3 = new SatStartEvent("CS3500", time2, loc3, invitees1);
    event4 = new SatStartEvent("CS3500", time2, loc3, invitees1);
    event5 = new SatStartEvent("dilly dally", time7, loc2, invitees2);
    event6 = new SatStartEvent("not BBQ", time8, loc2, invitees1); // wed - wed
    event7 = new SatStartEvent("my bday party", time1, loc7, invitees3);
    event8 = new SatStartEvent("haters' bday party", time9, loc7, invitees4);
    event9 = new SatStartEvent("others' bday party", time10, loc7, invitees5);
    event10 = new SatStartEvent("dilly dally", time6, loc4, invitees2);
    event11 = new SatStartEvent("dilly dally", time3, loc3, invitees1); // fri - tues
  }

  @Test
  public void testEquals() {
    assertEquals(event1, event3);
    assertEquals(event3, event1);
    assertEquals(event3, event4);
    assertEquals(event1, event4);
    assertNotEquals(event2, event4);
  }

  @Test
  public void testOverlapsWith() {
    assertThrows("event cannot be null",
            IllegalArgumentException.class, () -> event1.overlapsWith(null));
    assertTrue(event1.overlapsWith(event3));
    assertTrue(event1.overlapsWith(event5));
    assertTrue(event5.overlapsWith(event1));
    assertFalse(event2.overlapsWith(event1));
    assertTrue(event2.overlapsWith(event6));
    assertTrue(event7.overlapsWith(event8));
    assertTrue(event9.overlapsWith(event6));
    assertFalse(event5.overlapsWith(event10));
    assertFalse(event11.overlapsWith(event6));
  }

}