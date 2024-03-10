package model;

/**
 * The interface of a NUPlannerSystem.
 * Will have behavior of adding, modifying, and removing events in a system.
 */
public interface NUPlannerSystem {

  void add(Event event);

  void modify(Event event);

  void remove(Event event);
}
