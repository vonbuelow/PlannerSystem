package xmlfunc;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLReader {
  File file;

  XMLReader(File f) throws IOException, SAXException, ParserConfigurationException {
    this.file = f;
  }

  public void read() throws ParserConfigurationException, IOException, SAXException {
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
