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
public class SatStartTimeAndDayTests {
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
  TimeRep time11;
  TimeRep time12;
  TimeRep time13;

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
    time7 = new SatStartTime(SatStartDay.MONDAY, "0900",
            SatStartDay.MONDAY, "0930");
    time8 = new SatStartTime(SatStartDay.MONDAY, "0930",
            SatStartDay.MONDAY, "1000");
    time9 = new SatStartTime(SatStartDay.MONDAY, "0930",
            SatStartDay.MONDAY, "1030");
    time10 = new SatStartTime(SatStartDay.FRIDAY, "0930",
            SatStartDay.SUNDAY, "1030");
    time11 = new SatStartTime(SatStartDay.WEDNESDAY, "1600",
            SatStartDay.FRIDAY, "1030");
    time12 = new SatStartTime(SatStartDay.FRIDAY, "0930",
            SatStartDay.TUESDAY, "1030");
    time13 = new SatStartTime(SatStartDay.WEDNESDAY, "2359",
            SatStartDay.THURSDAY, "1030");
  }

  @Test
  public void testConstructorExceptions() {
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 05393\nMonday: 2300",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "05393",
                            SatStartDay.MONDAY, "2300"));
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 053\nMonday: 2300",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "053",
                            SatStartDay.MONDAY, "2300"));
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 2300\nMonday: 05393",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "2300",
                            SatStartDay.MONDAY, "05393"));
    assertThrows("Valid time format has to be 4 digits XXXX. "
                    + "You entered: Monday: 2300\nMonday: 053",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "2300", SatStartDay.MONDAY,
                            "053"));
    assertThrows("Start time is NOT IN MILITARY TIME: 0068",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "0068", SatStartDay.MONDAY,
                            "2300"));
    assertThrows("End time is NOT IN MILITARY TIME: 2400",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "2300", SatStartDay.MONDAY,
                            "2400"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class, () ->
                    new SatStartTime(null, "2300", SatStartDay.MONDAY,
                            "2300"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "2300", null,
                            "2300"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, null, SatStartDay.MONDAY,
                            "2300"));
    assertThrows("No days or hours can be null",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "2300", SatStartDay.MONDAY,
                            null));
    assertThrows("start and end times must be different",
            IllegalArgumentException.class, () ->
                    new SatStartTime(SatStartDay.MONDAY, "2300", SatStartDay.MONDAY,
                            "2300"));
  }

  @Test
  public void testDayToString() {
    assertEquals("Monday: ", SatStartDay.MONDAY.toString());
    assertEquals("Tuesday: ", SatStartDay.TUESDAY.toString());
    assertEquals("Wednesday: ", SatStartDay.WEDNESDAY.toString());
    assertEquals("Thursday: ", SatStartDay.THURSDAY.toString());
    assertEquals("Friday: ", SatStartDay.FRIDAY.toString());
    assertEquals("Saturday: ", SatStartDay.SATURDAY.toString());
    assertEquals("Sunday: ", SatStartDay.SUNDAY.toString());
  }

  @Test
  public void testOrderOfDayInWeek() {
    assertEquals(2, SatStartDay.MONDAY.orderOfDayInWeek());
    assertEquals(3, SatStartDay.TUESDAY.orderOfDayInWeek());
    assertEquals(4, SatStartDay.WEDNESDAY.orderOfDayInWeek());
    assertEquals(5, SatStartDay.THURSDAY.orderOfDayInWeek());
    assertEquals(6, SatStartDay.FRIDAY.orderOfDayInWeek());
    assertEquals(0, SatStartDay.SATURDAY.orderOfDayInWeek());
    assertEquals(1, SatStartDay.SUNDAY.orderOfDayInWeek());
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
            IllegalArgumentException.class, () -> time1.overlapsWith(null));
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
    assertEquals(SatStartDay.MONDAY, time1.getStartDayDefault());
    assertEquals("Friday", time10.getStartDay());
    assertEquals("Sunday", time10.getEndDay());
    assertEquals("1600", time4.getStartTime());
    assertEquals("0930", time7.getEndTime());
  }

}

