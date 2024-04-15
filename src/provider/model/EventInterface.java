package model;

import java.time.LocalDateTime;
import java.util.List;

public interface EventInterface {
  public String getName();
  public void setName(String name);
  public LocalDateTime getStartTime();
  public void setStartTime(LocalDateTime startTime);
  public LocalDateTime getEndTime();
  public void setEndTime(LocalDateTime endTime);
  public String getLocation();
  public void setLocation(String location);
  public boolean isOnline();
  public void setIsOnline(boolean isOnline);
  public String getHost();
  public List<String> getUsers();
  public void setUsers(List<String> users); 
}
