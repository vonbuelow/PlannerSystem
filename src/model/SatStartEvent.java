package model;

import java.util.List;

import model.eventfields.Location;
import model.eventfields.SatStartTime;
import model.eventfields.Time;

public class SatStartEvent implements EventRep {



  @Override
  public SatStartTime getTime() {
    return null;
  }

  @Override
  public boolean overlapsWith(EventRep e) {
    return false;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public List<String> getInvitedUsers() {
    return null;
  }

  @Override
  public Location getLocation() {
    return null;
  }

  @Override
  public void modifyName(String name) {

  }

  @Override
  public void modifyTime(Time time) {

  }

  @Override
  public void modifyLocation(Location loc) {

  }

  @Override
  public void modifyInvitees(List<String> invitees, boolean toAdd) {

  }
}
