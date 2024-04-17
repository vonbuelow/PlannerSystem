package model.adapters;

import provider.model.ScheduleInterface;
import provider.model.UserInterface;

public class UserAdapter implements UserInterface {

  private final String user;

  public UserAdapter(String user) {
    this.user = user;
  }

  @Override
  public String getUsername() {
    return this.user;
  }

  @Override
  public ScheduleInterface getSchedule() {
    // somehow returning the schedule and making an adapter account for that.
    return null;
  }

}
