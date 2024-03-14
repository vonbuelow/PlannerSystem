package xmlfunc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.Event;
import model.Schedule;

/**
 * Am instance of an XMLWriter to create an XML file.
 * This saves a users schedule to a designated folder, opening implementation for a new location.
 */
public class XMLWriter {

  /**
   * Write a given schedule as a file and to a specific directory.
   * @param     sched a selected users schedule.
   */
  public static void writeToFile(Schedule sched) {
    File directory = new File("C:\\Users\\emmaj\\OneDrive\\Desktop\\cs3000\\"
            + "PlannerSystem\\src\\userxmls"); // is this too hard coded?
    File fileToWrite = new File(directory, sched.scheduleOwner() + "-sched.xml");

    try {
      Writer file = new FileWriter(fileToWrite); // file to write to
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\" " + sched.scheduleOwner() + "\">");

      for (int event = 0; event < sched.eventsPlanned().size(); event++) {
        eventWriter(file, sched.eventsPlanned().get(event));
      }

      file.write("</schedule>");
      file.close();
    }
    catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Write out the specific schedule XML tags for individual events.
   * @param     file the file we are writing to
   * @param     event the current event we are writing about
   * @throws    IOException catching excpetions
   */
  private static void eventWriter(Writer file, Event event) throws IOException {
    file.write("<event>");
    // add in the name of the event
    file.write("<name>" + "name of event" + "</name>"); // ADD IN THE NAME
    // time:
    file.write("<time>");
    file.write("<start-day>" + "name of the starting day" + "</start-day>"); // START DAY
    file.write("<start>" + "start time" + "</start>");
    file.write("<end-day>" + "name of the ending day" + "</end-day>");
    file.write("<end>" + "end time" + "</end>");
    file.write("</time>");
    // location
    file.write("<location>");
    file.write("<online>" + false + "</online>");
    file.write("<place>" + false + "</place>");
    file.write("</location>");
    // those who are invited
    file.write("<users>");
    /*for(String invited: ArrayList<String>) {
      file.write("<uid>" + "user" + "</uid>");
    }*/
    // see how many users we need to be able to write
    file.write("</users>");
    file.write("</event>");
  }
}
