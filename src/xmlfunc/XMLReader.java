package xmlfunc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Schedule;

public class XMLReader {
  File file;

  /**
   * Allows an XML File Reader to be implemented
   * @param     f
   * @throws     IOException
   * @throws     SAXException
   * @throws     ParserConfigurationException
   */
  public XMLReader(File f) throws IOException, SAXException, ParserConfigurationException {
    this.file = f;
  }

  public Map<String, Schedule> read() throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.parse(this.file);
    doc.getDocumentElement().normalize(); //  document hierarchy isn’t affected by any extra white spaces or new lines within nodes.

    NodeList nodeList = doc.getElementsByTagName("schedule");
    Node first = nodeList.item(0);

    Node first2 = doc.getElementsByTagName("tutorial").item(0);
    NamedNodeMap attrList = first2.getAttributes();

    // assertEquals(4, nodeList.getLength());
    // assertEquals(Node.ELEMENT_NODE, first.getNodeType());
    // assertEquals("tutorial", first.getNodeName());
    return null;
  }

  public static void readXML() {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File("tutorial.xml"));
      xmlDoc.getDocumentElement().normalize();

      Node tutorialsNode = xmlDoc.getElementsByTagName("tutorials").item(0);
      //This result isn't as nice...
      System.out.println("Investigating the textContent straight from the outermost element:");
      System.out.println(tutorialsNode.getTextContent());

      //So let's dig deeper into the other elements!
      NodeList nodeList = tutorialsNode.getChildNodes();
      for (int item = 0; item < nodeList.getLength(); item++) {
        Node current = nodeList.item(item);
        //We need to search for Elements (actual tags) and there
        //is only one: the tutorial tag
        if (current.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) current;
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
          for (int childIdx = 0; childIdx < elemChildren.getLength(); childIdx++ ) {
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
  public void whenGetElementByTag_thenSuccess() {
    //NodeList nodeList = doc.getElementsByTagName("tutorial");
    //Node first = nodeList.item(0);

    // assertEquals(4, nodeList.getLength());
    // assertEquals(Node.ELEMENT_NODE, first.getNodeType());
    // assertEquals("tutorial", first.getNodeName());
  }

  public void whenGetFirstElementAttributes_thenSuccess() {
    //Node first = doc.getElementsByTagName("tutorial").item(0);
    //NamedNodeMap attrList = first.getAttributes();

    /*assertEquals(2, attrList.getLength());

    assertEquals("tutId", attrList.item(0).getNodeName());
    assertEquals("01", attrList.item(0).getNodeValue());

    assertEquals("type", attrList.item(1).getNodeName());
    assertEquals("java", attrList.item(1).getNodeValue());*/
  }


}
