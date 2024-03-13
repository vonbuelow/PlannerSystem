package xmlfunc;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
      System.out.println(userID); // not print but to have the name
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

  // testing the writer


}
