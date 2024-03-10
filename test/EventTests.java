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

}
