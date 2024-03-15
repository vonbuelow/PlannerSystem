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
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Event;
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

  // testing the reader
  @Test
  public void testReadingAttributes() {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(
              new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
                      + "PlannerSystem\\src\\prof.xml"));
      xmlDoc.getDocumentElement().normalize();

      Node scheduleNode = xmlDoc.getElementsByTagName("schedule").item(0);
      NamedNodeMap name = scheduleNode.getAttributes();
      Node nameAt = name.getNamedItem("id");
      String userID = nameAt.getNodeValue();
      assertEquals("Prof. Lucia", userID); // testing that we were able to get attributes

    }
    catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

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

  @Test
  public void testReadingSchedule() {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      XMLReader reader = new XMLReader(new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
              + "PlannerSystem\\src\\shortProf.xml"));

      Map<String, Schedule> profLuciaSched = reader.readXML();
      Set<String> keys = profLuciaSched.keySet();
      /*
      <event>
        <name>"CS3500 Morning Lecture"</name>
        <time>
            <start-day>Tuesday</start-day>
            <start>0950</start>
            <end-day>Tuesday</end-day>
            <end>1130</end>
        </time>
        <location>
            <online>false</online>
            <place>"Churchill Hall 101"</place>
        </location>
        <users>
            <uid>"Prof. Lucia"</uid>
            <uid>"Student Anon"</uid>
            <uid>"Chat"</uid>
        </users>
    </event>
       */
      Time time = new Time(Day.TUESDAY, "0950", Day.TUESDAY, "1130");
      Location location = new Location(false, "\"Churchill Hall 101\"");
      List<String> invitees = new ArrayList<String>(
              Arrays.asList("\"Prof. Lucia\"", "\"Student Anon\"", "\"Chat\""));
      Event event = new Event("\"CS3500 Morning Lecture\"", time, location, invitees);
      Schedule schedule = new Schedule("Prof. Lucia", new ArrayList<>(Arrays.asList(event)));
      assertEquals(schedule.eventsPlanned(), profLuciaSched.get("Prof. Lucia").eventsPlanned()); // we were able to get the correct user

    }
    catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }
  // testing the writer

}
