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
    this.addNewLine(output.append("user is saving a file to this path: ")
            .append(fileToSave.getAbsolutePath()));
  }

  @Override
  public void addEventToAllSchedules(EventRep event) throws IOException {
    this.addNewLine(output.append("Adding an event to all schedules")
            .append(event.toString()));
  }

  @Override
  public void addEventToInviteeSchedule(String uid, EventRep event) throws IOException {
    this.addNewLine(this.output.append("Add event: ").append(event.toString())
            .append("to ").append(uid).append(" schedule"));
  }

  @Override
  public void addNewUser(Map<String, ScheduleRep> newUser) throws IOException {
    this.addNewLine(this.output.append("Add ")
            .append(String.valueOf(newUser.keySet()))
            .append("'s schedule").append(String.valueOf(newUser.values())));
  }

  @Override
  public void modifyName(EventRep event, String eventName) throws IOException {
    this.addNewLine(this.output.append("Modify").append(event.toString())
            .append(" to have a").append(" new name of").append(eventName));
  }

  @Override
  public void modifyTime(EventRep event, Time time) throws IOException {
    this.addNewLine(this.output.append("Modify").append(event.toString())
            .append(" to have a").append(" new time of").append(time.toString()));
  }

  @Override
  public void modifyLocation(EventRep event, Location loc) throws IOException {
    this.addNewLine(this.output.append("Modify").append(event.toString())
            .append(" to have a").append(" new location of")
            .append(String.valueOf(loc)));
  }

  @Override
  public void modifyInvitees(EventRep event, List<String> invitees, boolean toAdd)
          throws IOException {
    this.addNewLine(this.output.append("Modify").append(event.toString())
            .append(" to have").append(" new invitees")
            .append(String.valueOf(invitees)).append(". Are we adding them? ")
            .append(String.valueOf(toAdd)));
  }

  @Override
  public void removeEvent(EventRep event, String uid) throws IOException {
    this.addNewLine(this.output.append("Removing this event: ")
            .append(event.toString()).append(" For ").append(uid));
  }

  @Override
  public void addUser(File file) throws IOException {
    this.addNewLine(this.output.append("Adding in a new users schedule from")
            .append(file.getAbsolutePath()));
  }

  /**
   * Adds a new line to the end of the given log.
   * @param log the output log to show inputs to a model method
   */
  private void addNewLine(Appendable log) throws IOException {
    log.append("\n");
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
