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
public class TimeTests {

  Time time1;
  Time time2;
  Time time3;
  Time time4;
  Time time5;
  Time time6;

  @Before
  public void setup() {
    /*time1 = new Time(Day.MONDAY, 0900, Day.MONDAY, 1000);
    time2 = new Time(Day.TUESDAY, 0950, Day.TUESDAY, 1130);
    time3 = new Time(Day.FRIDAY, 1130, Day.TUESDAY, 0950);*/
    time4 = new Time(Day.WEDNESDAY, 1600, Day.WEDNESDAY, 1559);
    //time5 = new Time(Day.MONDAY, 0900, Day.MONDAY, 1000);
    //time6 = new Time(Day.MONDAY, 0900, Day.MONDAY, 1000);
  }

  @Test
  public void testToString() {
    /*assertEquals("time: Monday: 0900 -> Monday: 1000", time1.toString());
    assertEquals("time: Tuesday: 0950 -> Tuesday: 1130", time2.toString());
    assertEquals("time: Friday: 1130 -> Tuesday: 0950", time3.toString());*/
    assertEquals("time: Wednesday: 1600 -> Wednesday: 1559", time4.toString());
  }
  @Test
  public void testEquals() {
    //assertEquals(time1, time1); // by reflexivity
    assertEquals(time1, time5);
    assertEquals(time5, time1); // by symmetry
    assertNotEquals(time2, time1);
    assertNotEquals(time3, time4);
    assertEquals(time5, time6);
    assertEquals(time1, time6); // by transitivity
  }

}
