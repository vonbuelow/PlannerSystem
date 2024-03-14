package model.eventfields;

import java.util.ArrayList;
import java.util.Arrays;

import model.Event;

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

  /**
   * Testing the invalid elements.
   */
  public void testInvalidElements() {
    // when reading an XML file add in a "True" and "fAlse"
    // cannot translate into a boolean -> restrain to either "true" or "false"

    // when reading an XML file add no child elements to the uid tag -> error out

    // an existing schedule with an event with
    Time time = new Time(Day.MONDAY, 900, Day.MONDAY, 1200);
    Location loc = new Location(false, "mother's house");
    Event eventOverlap = new Event("party", time, loc,
            new ArrayList<String>(Arrays.asList("emma")));

    //add.event(); // an event between current events

    // do nothing!!
  }
}
