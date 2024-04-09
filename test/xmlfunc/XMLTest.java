package xmlfunc;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.CentralSystem;
import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.Schedule;
import model.ScheduleRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing XML reader and writer.
 */
public class XMLTest {

  // testing the reader of a user
  // XML reader testing
  @Test
  public void testReadingElements() {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      XMLReader reader = new XMLReader(new File("C:\\Users\\Owner\\Desktop\\cs3500\\"
              + "PlannerSystem\\src\\shortProf.xml"));

      Map<String, ScheduleRep> profLuciaSched = reader.readXML();
      Set<String> keys = profLuciaSched.keySet();
      assertTrue(keys.contains("Prof. Lucia")); // we were able to get the correct user
    }
    catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  // test the reader makes an instance of a schedule
  @Test
  public void testReadingSchedule() throws IOException {
    XMLReader reader = new XMLReader(new File("C:\\Users\\emmaj\\OneDrive\\"
            + "Desktop\\cs3000\\PlannerSystem\\src\\shortProf.xml"));
    // a reader
    Map<String, ScheduleRep> profLuciaSched = reader.readXML();
    Time time = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
    Location location = new Location(false, "\"Churchill Hall 101\"");
    List<String> invitees = new ArrayList<String>(
            Arrays.asList("\"Prof. Lucia\"", "\"Student Anon\"", "\"Chat\""));
    EventRep event = new Event("\"CS3500 Morning Lecture\"", time, location, invitees);
    ScheduleRep schedule = new Schedule("Prof. Lucia", new ArrayList<>(Arrays.asList(event)));
    // model testing
    NUPlannerSystem model = new CentralSystem();
    model.addUser(new File("C:\\Users\\emmaj\\OneDrive\\"
            + "Desktop\\cs3000\\PlannerSystem\\src\\shortProf.xml"));

    //System.out.println(model.getUserEvents("Prof. Lucia"));
    assertEquals(schedule.eventsPlanned(), profLuciaSched.get("Prof. Lucia").eventsPlanned());
  }

  // testing the writer
  @Test
  public void testWriter() throws IOException {
    XMLWriter writer = new XMLWriter();
    File usersxmls = new File("C:\\Users\\Owner\\Desktop\\cs3500\\PlannerSystem\\src\\xmlfunc");
    XMLReader reader = new XMLReader(new File("C:\\Users\\Owner\\Desktop\\cs3500\\"
            + "PlannerSystem\\src\\shortProf.xml"));

    Map<String, ScheduleRep> profLuciaSched = reader.readXML();
    Time time = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
    Location location = new Location(false, "\"Churchill Hall 101\"");
    List<String> invitees = new ArrayList<String>(
            Arrays.asList("\"Prof. Lucia\"", "\"Student Anon\"", "\"Chat\""));
    EventRep event = new Event("\"CS3500 Morning Lecture\"", time, location, invitees);
    NUPlannerSystem system = new CentralSystem(profLuciaSched,
            new ArrayList<>(Arrays.asList(event)));

    system.saveSchedule(usersxmls);
    // contains lucias schedule!!!!!!!
    assertEquals("Prof. Lucia-sched.xml",
            Objects.requireNonNull(usersxmls.listFiles())[0].getName());

  }

}
