package model;

import java.io.File;
import java.util.List;
import java.util.Map;

import xmlfunc.Reader;
import xmlfunc.SatStartXMLReader;

/**
 * Represents a planner system that considers the first day of the week Saturday.
 */
public class SatStartModel extends CentralSystem {

  /**
   * Creates the default central system (empty) for uploading user schedules into.
   */
  public SatStartModel() {
    super();
  }

  /**
   * NEW: new constructor keeping track of a list of schedules.
   */
  public SatStartModel(List<Schedule> schedules) {
    super(schedules);
  }

  /**
   * A testing constructor for a system with schedules loaded in.
   * @param allS all schedules for users in the system
   * @param events all unique events in the system
   */
  public SatStartModel(Map<String, ScheduleRep> allS, List<EventRep> events) {
    super(allS, events);
  }

  @Override
  public void addUser(File file) {
    if (file == null) {
      throw new IllegalArgumentException("file cannot be null");
    }
    try {
      Reader reader = new SatStartXMLReader(file);
      addNewUser(reader.readXML());
      // INVARIANT CHECKING EVENT OVERLAP
      // IF A USER SHOULD BE ADDED TO A NEW EVENT THAT HAS BEEN LOADED IN
      // EVERY USER IS UNIQUE
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
