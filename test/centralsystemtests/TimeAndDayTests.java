package centralsystemtests;

import org.junit.Before;
import org.junit.Test;

import model.eventfields.Day;
import model.eventfields.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests functionality of the Time class (parameter of CentralSystem, representing the
 * time of an event.
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
