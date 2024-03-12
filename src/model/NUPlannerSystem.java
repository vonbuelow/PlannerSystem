package model;

import java.util.Map;

/**
 * The interface of a NUPlannerSystem.
 * Will have behavior of adding, modifying, and removing events in a system.
 */
public interface NUPlannerSystem {

  //JAVA DOCSSSS
  void add(Event event);

  void modify(Event event);

  void remove(Event event);

  Map<String, Schedule> usersSchedules();
}
