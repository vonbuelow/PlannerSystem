package xmlfunc;

import java.util.Map;
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
