package model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.eventfields.Location;
import model.eventfields.Time;

/**
 * Mocks an NUPlannerSystem but logs the inputs given from an NUController to the
 * current view.
 */
public class MockModel implements NUPlannerSystem {
  public Appendable output;

  public MockModel(Appendable output) {
    this.output = output;
  }

  @Override
  public void saveSchedule(File fileToSave) throws IOException {
    output.append("user is saving a file to this path: " + fileToSave.getAbsolutePath() + "\n");
  }

  @Override
  public void addEventToAllSchedules(EventRep event) throws IOException {
    output.append("Adding an event to all schedules" + event.toString() + "\n");
  }

  @Override
  public void addEventToInviteeSchedule(String uid, EventRep event) throws IOException {
    this.output.append("Add event: " +event.toString()+ "to " + uid + " schedule" + "\n");
  }

  @Override
  public void addNewUser(Map<String, ScheduleRep> newUser) throws IOException {
    this.addNewLine(this.output.append("Add " + newUser.keySet() + "'s schedule" + newUser.values() + "\n"));
  }

  @Override
  public void modifyName(EventRep event, String eventName) throws IOException {
    this.addNewLine(this.output.append("Modify" + event.toString() + " to have a"
            + " new name of" + eventName + "\n"));
  }

  /**
   * Adds a new line to the end of the given log.
   * @param log the output log to show inputs to a model method
   */
  private void addNewLine(Appendable log) throws IOException {
    log.append("\n");
  }

  @Override
  public void modifyTime(EventRep event, Time time) throws IOException {
    this.output.append("Modify" + event.toString() +" to have a"
            +" new time of" + time.toString() + "\n");
  }

  @Override
  public void modifyLocation(EventRep event, Location loc) throws IOException {
    this.output.append("Modify" + event.toString() +" to have a"
            +" new location of" + loc + "\n");
  }

  @Override
  public void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd)
          throws IOException {
    this.output.append("Modify" + event.toString() +" to have"
            +" new invitees" + invitees + ". Are we adding them? " + toAdd + "\n");
  }

  @Override
  public void removeEvent(EventRep event, String uid) throws IOException {
    this.output.append("Removing this event: " + event.toString() + " For " + uid + "\n");
  }

  @Override
  public void addUser(File file) throws IOException {
    this.output.append("Adding in a new users schedule from" + file.getAbsolutePath() + "\n");
  }

  @Override
  public Map<String, ScheduleRep> usersSchedules() {
    // Observational methods, this method, are not being used by controller.
    return null;
  }

  @Override
  public Set<String> usersInSystem() {
    // Observational methods, this method, are not being used by controller.
    return null;
  }

  @Override
  public boolean doesOverlap(EventRep event) {
    // Observational methods, this method, are not being used by controller.
    return false;
  }

  @Override
  public List<EventRep> getUserEvents(String uid) {
    // Observational methods, this method, are not being used by controller.
    return null;
  }

  @Override
  public List<EventRep> getSystemEvents() {
    // Observational methods, this method, are not being used by controller.
    return null;
  }
}
