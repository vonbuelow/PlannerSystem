package centralsystemtests;

import org.junit.Before;
import org.junit.Test;

import model.eventfields.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests functionality of the Location class.
 */
public class LocationTests {
  Location loc1;
  Location loc2;
  Location loc3;
  Location loc4;
  Location loc5;
  Location loc6;

  @Before
  public void setup() {
    loc1 = new Location(false, "ur mom's house");
    loc2 = new Location(true, "my mom's house");
    loc3 = new Location(false, "Churchill Hall 101");
    loc4 = new Location(true, "my mom's house");
    loc5 = new Location(false, "Churchill Hall 101");
    loc6 = new Location(false, "Churchill Hall 101");
  }

  @Test
  public void testEquals() {
    assertNotEquals(loc1, loc2);
    assertNotEquals(loc3, loc2);
    assertNotEquals(loc1, loc3);
    assertEquals(loc2, loc4);
    assertEquals(loc2, loc4);
    assertEquals(loc3, loc5);
    assertEquals(loc5, loc6);
    assertEquals(loc3, loc6);
  }
}