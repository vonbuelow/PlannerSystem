package provider.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a read only version of the system.
 * Only has observational methods.
 */
public interface ReadOnlySystemInterface {

  /**
   * Retrieves a user based on their username.
   * @param username the user's username
   * @return the user
   */
  UserInterface getUser(String username);

  /**
   * Returns a list of all the users in the system.
   * @return list of all users
   */
  List<String> getAllUsers();

  /**
   * Checks if an event conflicts with all users invited to said event.
   * @param event the event
   * @return true if conflicting, false otherwise
   */
  boolean isConflictWithAllUsers(EventInterface event);

  /**
   * Retrieves the list of events for a given user.
   * @param user the user to retrieve events for
   * @return list of events
   */
  List<EventInterface> getUserEventList(UserInterface user);

  /**
   * View events for a given user based on the given time.
   * @param user the user to view the event for
   * @param time the time that an event may be occurring
   * @return the events occurring
   */
  List<EventInterface> seeEvent(UserInterface user, LocalDateTime time);
}
