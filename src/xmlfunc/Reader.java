package xmlfunc;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.EventRep;
import model.Schedule;
import model.ScheduleRep;

/**
 * Interface to represent the functionality of an XML reader.
 */
public interface Reader {

  /**
   * A public method to be called in the central system to read XML files in a single class.
   * @return A mapping of the user to a specific schedule created in this class.
   */
  public Map<String, ScheduleRep> readXML();
}
