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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Event;
import model.Schedule;

public class XMLReader {
  File file;

  public XMLReader(File f) {
    this.file = f;
  }

  public Map<String, Schedule> readXML() {
    List<Event> ret = new ArrayList<>();
    Map<String, Schedule> schedules = new HashMap<>();
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(this.file);
      xmlDoc.getDocumentElement().normalize();
      // getting the users name from the schedule
      Node scheduleNode = xmlDoc.getElementsByTagName("schedule").item(0);
      NamedNodeMap name = scheduleNode.getAttributes();
      Node nameAt = name.getNamedItem("id");
      String userID = nameAt.getNodeValue();

      //So let's dig deeper into the other elements!
      NodeList nodeList = scheduleNode.getChildNodes(); // nodes within schedule tags
      for (int item = 0; item < nodeList.getLength(); item++) { // dont need if stmnt.
        Node current = nodeList.item(item); // the first child element (should be empty or event/s)

        //We need to search for Elements (actual tags) and there
        //is only one: the event tag
        if (current.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) current; // this is an event tag
          if (elem.getTagName().equals("event")) {

            // go into the children get their values
            // make an event
            // add to the list of events

            // i know that this element should be an event

            //Print out the attributes of this element
            System.out.println("Investigating the attributes:");
            System.out.println(elem.getTagName() + " : " + elem.getAttribute("tutId") + " "
                    + elem.getAttribute("type"));

            //Print out the text that exists inside of this element: it doesn't look pretty...
            //and it erases the other elements
            System.out.println("Investigating the text content inside this element:");
            System.out.println(elem.getTagName() + " : " + elem.getTextContent());

            //... so let's dig even deeper!
            NodeList elemChildren = elem.getChildNodes();
            for (int childIdx = 0; childIdx < elemChildren.getLength(); childIdx++) {
              Node childNode = elemChildren.item(childIdx);
              if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element child = (Element) childNode;
                //Now we're getting something more meaningful from each element!
                System.out.println("Investigating the text content inside the innermost tags");
                System.out.println(child.getTagName() + " : " + child.getTextContent());
              }
            }
          }
        }
      }
      Schedule sched = new Schedule(userID, ret);
      schedules.put(userID, sched);
      return schedules;
    }
    catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    }
    catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    }
    catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }
  }

  public Map<String, Schedule> read() throws Exception {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.parse(this.file);
    doc.getDocumentElement().normalize();

    Map<String, Schedule> schedules = new HashMap<>();
    NodeList events = doc.getElementsByTagName("event");


    for (int i = 0; i < events.getLength(); i++) {
      Node eventNode = events.item(i);
      if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
        Element eventElement = (Element) eventNode;

        // Extract event details
        String eventName = eventElement.getElementsByTagName("name").item(0).getTextContent();
        String startDay = eventElement.getElementsByTagName("start-day").item(0).getTextContent();
        String start = eventElement.getElementsByTagName("start").item(0).getTextContent();
        String endDay = eventElement.getElementsByTagName("end-day").item(0).getTextContent();
        String end = eventElement.getElementsByTagName("end").item(0).getTextContent();
        String online = eventElement.getElementsByTagName("online").item(0).getTextContent();
        String place = eventElement.getElementsByTagName("place").item(0).getTextContent();

        // Create a new Schedule object or a suitable data structure
        // For illustration, we'll just print the details
        System.out.println("Event Name: " + eventName);
        System.out.println("Start Day: " + startDay);
        System.out.println("Start: " + start);
        System.out.println("End Day: " + endDay);
        System.out.println("End: " + end);
        System.out.println("Online: " + online);
        System.out.println("Place: " + place);

        // Assuming Schedule class exists and can store these details
        // schedules.put(eventName, new Schedule(/*parameters*/));
      }
    }

    return schedules;
  }
}
