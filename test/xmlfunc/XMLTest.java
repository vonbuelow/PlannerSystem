package xmlfunc;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
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
import model.NUPlannerSystem;
import model.Schedule;
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
      XMLReader reader = new XMLReader(new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
              + "PlannerSystem\\src\\prof.xml"));

      Map<String, Schedule> profLuciaSched = reader.readXML();
      Set<String> keys = profLuciaSched.keySet();
      assertTrue(keys.contains("Prof. Lucia")); // we were able to get the correct user
    }
    catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  // test the reader makes an instance of a schedule
  @Test
  public void testReadingSchedule() {
    XMLReader reader = new XMLReader(new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
            + "PlannerSystem\\src\\shortProf.xml"));

    Map<String, Schedule> profLuciaSched = reader.readXML();
    Time time = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
    Location location = new Location(false, "\"Churchill Hall 101\"");
    List<String> invitees = new ArrayList<String>(
            Arrays.asList("\"Prof. Lucia\"", "\"Student Anon\"", "\"Chat\""));
    Event event = new Event("\"CS3500 Morning Lecture\"", time, location, invitees);
    Schedule schedule = new Schedule("Prof. Lucia", new ArrayList<>(Arrays.asList(event)));
    assertEquals(schedule.eventsPlanned(), profLuciaSched.get("Prof. Lucia").eventsPlanned());
  }

  // testing the writer
  @Test
  public void testWriter() {
    XMLWriter writer = new XMLWriter();
    File usersxmls = new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
            + "PlannerSystem\\src\\userxmls");
    XMLReader reader = new XMLReader(new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
            + "PlannerSystem\\src\\shortProf.xml"));

    Map<String, Schedule> profLuciaSched = reader.readXML();
    Time time = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
    Location location = new Location(false, "\"Churchill Hall 101\"");
    List<String> invitees = new ArrayList<String>(
            Arrays.asList("\"Prof. Lucia\"", "\"Student Anon\"", "\"Chat\""));
    Event event = new Event("\"CS3500 Morning Lecture\"", time, location, invitees);
    NUPlannerSystem system = new CentralSystem(profLuciaSched,
            new ArrayList<>(Arrays.asList(event)));

    system.saveSchedule(usersxmls);
    // contains lucias schedule!!!!!!!
    assertEquals("Prof. Lucia-sched.xml",
            Objects.requireNonNull(usersxmls.listFiles())[0].getName());

  }

}
