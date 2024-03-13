package xmlfunc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import model.NUPlannerSystem;
import model.Schedule;

public class XMLWriter {


  public static void writeToFile(Schedule sched) {
    try {
      Writer file = new FileWriter("sample-written.xml"); // the name of the user
      file.write("<?xml version=\"1.0\"?>\n"); // whatever version
      file.write("<schedule id=\"You\">"); // the users name

      // write in the additional tags based on what the user needs from their list of events

      file.write("</schedule>"); // start off the users schedule depending on their events

      file.close();
    }
    catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
