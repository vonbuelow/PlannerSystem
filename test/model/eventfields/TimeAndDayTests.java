package model.eventfields;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests functionality of the Time class (parameter of CentralSystem, representing the
 * time of an event).
 */
public class TimeAndDayTests {
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
  Time time11;
  Time time12;
  Time time13;

  @Before
  public void setup() {
    time1 = new Time(Day.MONDAY, "0900", Day.MONDAY, "1000");
    time2 = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
    time3 = new Time(Day.FRIDAY, "1130", Day.TUESDAY, "0950");
    time4 = new Time(Day.WEDNESDAY, "1600", Day.WEDNESDAY, "1559");
    time5 = new Time(Day.MONDAY, "0900", Day.MONDAY, "1000");
    time6 = new Time(Day.MONDAY, "0900", Day.MONDAY, "1000");
    time7 = new Time(Day.MONDAY, "0900", Day.MONDAY, "0930");
    time8 = new Time(Day.MONDAY, "0930", Day.MONDAY, "1000");
    time9 = new Time(Day.MONDAY, "0930", Day.MONDAY, "1030");
    time10 = new Time(Day.FRIDAY, "0930", Day.SUNDAY, "1030");
    time11 = new Time(Day.WEDNESDAY, "1600", Day.FRIDAY, "1030");
    time12 = new Time(Day.FRIDAY, "0930", Day.TUESDAY, "1030");
    time13 = new Time(Day.WEDNESDAY, "2359", Day.THURSDAY, "1030");
  }

  @Test
  public void testConstructorExceptions() {
    assertThrows("Valid time format has to be 4 digits XXXX. "
            + "You entered: Monday: 05393\nMonday: 2300",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "05393", Day.MONDAY, "2300"));
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 053\nMonday: 2300",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "053", Day.MONDAY, "2300"));
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 2300\nMonday: 05393",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "2300", Day.MONDAY, "05393"));
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 2300\nMonday: 053",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "2300", Day.MONDAY, "053"));
    assertThrows("Start time is NOT IN MILITARY TIME: 0068",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "0068", Day.MONDAY, "2300"));
    assertThrows("End time is NOT IN MILITARY TIME: 2400",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "2300", Day.MONDAY, "2400"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class,
            () -> new Time(null, "2300", Day.MONDAY, "2300"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "2300", null, "2300"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, null, Day.MONDAY, "2300"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "2300", Day.MONDAY, null));
    assertThrows("start and end times must be different",
            IllegalArgumentException.class,
            () -> new Time(Day.MONDAY, "2300", Day.MONDAY, "2300"));
  }

  @Test
  public void testDayToString() {
    assertEquals("Monday: ", Day.MONDAY.toString());
    assertEquals("Tuesday: ", Day.TUESDAY.toString());
    assertEquals("Wednesday: ", Day.WEDNESDAY.toString());
    assertEquals("Thursday: ", Day.THURSDAY.toString());
    assertEquals("Friday: ", Day.FRIDAY.toString());
    assertEquals("Saturday: ", Day.SATURDAY.toString());
    assertEquals("Sunday: ", Day.SUNDAY.toString());
  }

  @Test
  public void testOrderOfDayInWeek() {
    assertEquals(1, Day.MONDAY.orderOfDayInWeek());
    assertEquals(2, Day.TUESDAY.orderOfDayInWeek());
    assertEquals(3, Day.WEDNESDAY.orderOfDayInWeek());
    assertEquals(4, Day.THURSDAY.orderOfDayInWeek());
    assertEquals(5, Day.FRIDAY.orderOfDayInWeek());
    assertEquals(6, Day.SATURDAY.orderOfDayInWeek());
    assertEquals(0, Day.SUNDAY.orderOfDayInWeek());
  }

  @Test
  public void testTimeToString() {
    assertEquals("time: Monday: 0900 -> Monday: 1000", time1.toString());
    assertEquals("time: Tuesday: 0950 -> Tuesday: 1130", time2.toString());
    assertEquals("time: Friday: 1130 -> Tuesday: 0950", time3.toString());
    assertEquals("time: Wednesday: 1600 -> Wednesday: 1559", time4.toString());
  }

  @Test
  public void testTimeEquals() {
    //assertEquals(time1, time1); // by reflexivity
    assertEquals(time1, time5);
    assertEquals(time5, time1); // by symmetry
    assertNotEquals(time2, time1);
    assertNotEquals(time3, time4);
    assertEquals(time5, time6);
    assertEquals(time1, time6); // by transitivity
  }

  @Test
  public void testTimeOverlapsWithOtherTime() {
    assertThrows("time cannot be null",
            IllegalArgumentException.class,
            () -> time1.overlapsWith(null));
    assertTrue(time1.overlapsWith(time5));
    assertFalse(time1.overlapsWith(time2));
    assertTrue(time6.overlapsWith(time7));
    assertTrue(time6.overlapsWith(time8));
    assertTrue(time6.overlapsWith(time9));
    assertTrue(time4.overlapsWith(time10));
    assertTrue(time4.overlapsWith(time11));
    assertTrue(time4.overlapsWith(time12));
    assertTrue(time4.overlapsWith(time13));
  }

  @Test
  public void testObservationsAndReaderAccessors() {
    assertEquals(Day.MONDAY, time1.getStartDayDefault());
    assertEquals("Friday", time10.getStartDay());
    assertEquals("Sunday", time10.getEndDay());
    assertEquals("1600", time4.getStartTime());
    assertEquals("0930", time7.getEndTime());
  }

}
