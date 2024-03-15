package xmlfunc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.Event;
import model.EventRep;
import model.Schedule;
import model.ScheduleRep;

/**
 * Am instance of an XMLWriter to create an XML file.
 * This saves a users schedule to a designated folder, opening implementation for a new location.
 */
public class XMLWriter {

  /**
   * Write a given schedule as a file and to a specific directory.
   * @param     sched a selected users schedule.
   */
  public static void writeToFile(ScheduleRep sched, String directoryPath) {
    // Use the provided directory path
    File directory = new File(directoryPath);
    if (!directory.exists()) {
      directory.mkdirs(); // Create the directory if it does not exist
    }
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
  private static void eventWriter(Writer file, EventRep event) throws IOException {
    file.write("<event>\n");
    // Add in the name of the event
    file.write("<name>" + event.getName() + "</name>\n");
    // Time:
    file.write("<time>\n");
    file.write("<start-day>" + event.getTime().getStartDay() + "</start-day>\n");
    file.write("<start>" + event.getTime().getStartTime() + "</start>\n");
    file.write("<end-day>" + event.getTime().getEndDay() + "</end-day>\n");
    file.write("<end>" + event.getTime().getEndTime() + "</end>\n");
    file.write("</time>\n");
    // Location
    file.write("<location>\n");
    file.write("<online>" + event.getLocation().isOnline() + "</online>\n");
    file.write("<place>" + event.getLocation().getPlace() + "</place>\n");
    file.write("</location>\n");
    // Those who are invited
    file.write("<users>\n");
    for(String invited: event.getInvitedUsers()) {
      file.write("<uid>" + invited + "</uid>\n");
    }
    file.write("</users>\n");
    file.write("</event>\n");
  }

}
