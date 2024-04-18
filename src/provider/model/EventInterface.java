package provider.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The event interface the provider gave us.
 */
public interface EventInterface {

  String getName();

  void setName(String name);

  LocalDateTime getStartTime();

  void setStartTime(LocalDateTime startTime);

  LocalDateTime getEndTime();

  void setEndTime(LocalDateTime endTime);

  String getLocation();

  void setLocation(String location);

  boolean isOnline();

  void setIsOnline(boolean isOnline);

  String getHost();

  List<String> getUsers();

  void setUsers(List<String> users);
}
