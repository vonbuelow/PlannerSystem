package xmlfunc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Event;
import model.EventRep;
import model.Schedule;
import model.ScheduleRep;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;

/**
 * The class used to read an XML file.
 * This class transfers all information back to the central system.
 * Used when a user wants to upload a file in the central system to be read in this system.
 */
public class XMLReader implements Reader {
  File file;

  public XMLReader(File f) {
    this.file = f;
  }

  @Override
  public Map<String, ScheduleRep> readXML() {
    ArrayList<EventRep> ret = new ArrayList<EventRep>();
    Map<String, ScheduleRep> schedules = new HashMap<>();
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(this.file);
      xmlDoc.getDocumentElement().normalize();
      // getting the users name from the schedule
      Node scheduleNode = xmlDoc.getElementsByTagName("schedule").item(0);
      NamedNodeMap name = scheduleNode.getAttributes();
      Node nameAt = name.getNamedItem("id");
      String userID = nameAt.getNodeValue();

      NodeList nodeList = scheduleNode.getChildNodes(); // nodes within schedule tags
      getEventsFromSchedule(nodeList, ret);
      ScheduleRep sched = new Schedule(userID, ret);
      schedules.put(userID, sched);
      //System.out.println(schedules.get(userID).eventsPlanned().toString());
      return schedules;
    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }
  }

  private void getEventsFromSchedule(NodeList nodeList, ArrayList<EventRep> ret) {
    for (int item = 0; item < nodeList.getLength(); item++) {
      Node current = nodeList.item(item); // the first child element -> should be empty/event
      //iterate through events
      if (current.getNodeType() == Node.ELEMENT_NODE) {
        Element elem = (Element) current; // this is an event tag
        if (elem.getTagName().equals("event")) {
          // name of the event
          NodeList childrenName = elem.getElementsByTagName("name");
          listLengthException(childrenName);
          String eventName = childrenName.item(0).getTextContent();
          // get the time and its elements
          NodeList childrenTime = elem.getElementsByTagName("time");
          listLengthException(childrenTime);
          Time eventTime = getTimeFields(childrenTime.item(0).getChildNodes());
          // get the location
          NodeList childrenLoc = elem.getElementsByTagName("location");
          listLengthException(childrenLoc);
          Location eventLoc = getLocation(childrenLoc);
          // get the atendees
          NodeList childrenAttend = elem.getElementsByTagName("users");
          listLengthException(childrenAttend);

          List<String> eventAttendees = getAttendees(childrenAttend);

          ret.add(new Event(eventName, eventTime, eventLoc, eventAttendees));
        }
      }
    }
  }

  private List<String> getAttendees(NodeList childrenAttend) {
    NodeList uidNodes = childrenAttend.item(0).getChildNodes();
    List<String> uids = new ArrayList<>();

    for (int i = 0; i < uidNodes.getLength(); i++) {
      Node node = uidNodes.item(i);

      if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("uid")) {
        Element uidElement = (Element) node;
        String uid = uidElement.getTextContent();
        uids.add(uid);
      }
    }

    return uids;
  }


  /**
   * The location a childLoc is taking place with boolean and String representing place.
   * @param     childrenLoc containing the location information.
   * @return    the Location.
   */
  private Location getLocation(NodeList childrenLoc) {
    NodeList locEle = childrenLoc.item(0).getChildNodes();

    // Filter to only element nodes
    List<Node> filteredNodes = new ArrayList<>();
    for (int i = 0; i < locEle.getLength(); i++) {
      if (locEle.item(i).getNodeType() == Node.ELEMENT_NODE) {
        filteredNodes.add(locEle.item(i));
      }
    }

    if (filteredNodes.size() != 2) {
      throw new IllegalArgumentException("Invalid location");
    }

    Node onlineNode = filteredNodes.get(0);
    Node placeNode = filteredNodes.get(1);

    Boolean online = Boolean.parseBoolean(onlineNode.getTextContent());
    String place = placeNode.getTextContent();
    return new Location(online, place);
  }


  /**
   * Get the boolean value if it is a valid one.
   * @param     nodeValue containing the boolean value.
   * @return    the boolean representation.
   */
  private Boolean getBool(String nodeValue) {
    if (!(nodeValue.equals("true") || nodeValue.equals("false"))) {
      throw new IllegalArgumentException("invalid boolean");
    }
    return Boolean.valueOf(nodeValue);
  }

  /**
   * A recurring exception on if this length of this node list isn't 1.
   * @param     childrenName the name of the children.
   */
  private static void listLengthException(NodeList childrenName) {
    if (childrenName.getLength() != 1) {
      throw new IllegalArgumentException("invalid file");
    }
  }

  /**
   * Creates an instance of time with the time tag.
   * @param     childNodes the node containing the time information.
   * @return    the time that should be produced.
   */
  private Time getTimeFields(NodeList childNodes) {
    // Filter to only element nodes
    List<Node> filteredNodes = new ArrayList<>();
    for (int i = 0; i < childNodes.getLength(); i++) {
      if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
        filteredNodes.add(childNodes.item(i));
      }
    }

    // Now check if we have exactly 4 element nodes
    if (filteredNodes.size() != 4) {
      throw new IllegalArgumentException("Invalid time, expected 4 elements.");
    }

    // start-day
    String startDayValue = filteredNodes.get(0).getTextContent();
    if (!validDay(startDayValue)) {
      throw new IllegalArgumentException("Invalid start day: " + startDayValue);
    }
    Day startDay = Day.valueOf(startDayValue.toUpperCase().trim());

    // start time
    String startTime = filteredNodes.get(1).getTextContent().trim();
    if (startTime.length() != 4) {
      throw new IllegalArgumentException("Invalid start time: " + startTime);
    }

    // end-day
    String endDayValue = filteredNodes.get(2).getTextContent();
    if (!validDay(endDayValue)) {
      throw new IllegalArgumentException("Invalid end day: " + endDayValue);
    }
    Day endDay = Day.valueOf(endDayValue.toUpperCase().trim());

    String endTime = filteredNodes.get(3).getTextContent().trim();
    if (endTime.length() != 4) {
      throw new IllegalArgumentException("Invalid end time: " + endTime);
    }

    return new Time(startDay, startTime, endDay, endTime);
  }

  /**
   * Is the given day considered a valid day for a Time.
   * @param     nodeValue the current string being assesed.
   * @return    is the day valid?
   */
  private boolean validDay(String nodeValue) {
    ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    return days.contains(nodeValue);
  }
}
