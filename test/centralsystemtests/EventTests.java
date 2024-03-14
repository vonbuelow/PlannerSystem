package centralsystemtests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Event;
import model.EventRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * The testing of event specific functionality.
 * Along with silly edge cases.
 */
public class EventTests {
  /*
  - an event should only span a MAX of one week (Noelis)
    - ex. wed 4pm to wed 3:59pm (valid), 4pm to 4pm (invalid)
    - if invalid (too long), then error in model class, message to user to adjust (controller)
- invalid start days
    - ex. valid examples only?
    - METHOD: total amount of hours between start and end dates?
    - start and end day are EXACTLY ONE WEEK 24*7 = testing methods return amt.
  ---------------------------------------------------------------------------------------------
    - an event with a an invalid time (a day not in the enum) -> enforced by the constructor
    - online elements spelled without being exactly "true" or "false" -> error out
    - user element with no uid tags -> error out

- two events overlapping
    - one ends at 4pm, other starts at 3pm same day --> do nothing
   */

  //@Test

  Time time1;
  Time time2;
  Time time3;
  Time time4;
  Time time5;
  Time time6;
  Time time7;
  Time time8;
  Time time9;
  Time time10;

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
    time1 = new Time(Day.MONDAY, "0900", Day.MONDAY, "1000");
    time2 = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
    time3 = new Time(Day.FRIDAY, "1130", Day.TUESDAY, "0950");
    time4 = new Time(Day.WEDNESDAY, "1600", Day.WEDNESDAY, "1559");
    time5 = new Time(Day.MONDAY, "0900", Day.MONDAY, "1000");
    time6 = new Time(Day.MONDAY, "0900", Day.MONDAY, "1000");
    time7 = new Time(Day.TUESDAY, "1000", Day.TUESDAY, "1100");
    time8 = new Time(Day.WEDNESDAY, "1400", Day.WEDNESDAY, "1800");
    time9 = new Time(Day.MONDAY, "0900", Day.MONDAY, "0930");
    time10 = new Time(Day.WEDNESDAY, "1600", Day.WEDNESDAY, "1800");

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

    event1 = new Event("CS3500", time2, loc3, invitees1);
    event2 = new Event("BBQ", time4, loc1, invitees2);
    event3 = new Event("CS3500", time2, loc3, invitees1);
    event4 = new Event("CS3500", time2, loc3, invitees1);
    event5 = new Event("dilly dally", time7, loc2, invitees2);
    event6 = new Event("not BBQ", time8, loc2, invitees1);
    event7 = new Event("my bday party", time1, loc7, invitees3);
    event8 = new Event("haters' bday party", time9, loc7, invitees4);
    event9 = new Event("others' bday party", time10, loc7, invitees5);
    event10 = new Event("dilly dally", time6, loc4, invitees2);
    event11 = new Event("dilly dally", time3, loc3, invitees1);
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

  /**
   * Testing the invalid elements.
   */
  public void testInvalidElements() {
    // when reading an XML file add in a "True" and "fAlse"
    // cannot translate into a boolean -> restrain to either "true" or "false"

    // when reading an XML file add no child elements to the uid tag -> error out

    // an existing schedule with an event with
    Time time = new Time(Day.MONDAY, "0900", Day.MONDAY, "1200");
    Location loc = new Location(false, "mother's house");
    Event eventOverlap = new Event("party", time, loc,
            new ArrayList<String>(Arrays.asList("emma")));

    //add.event(); // an event between current events

    // do nothing!!
  }
}
