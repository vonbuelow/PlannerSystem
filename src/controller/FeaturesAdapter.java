package controller;
import java.time.LocalDateTime;
import java.util.List;

import model.Event;
import model.EventRep;
import model.adapters.AbstractAdapter;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;
import provider.controller.Features;
import provider.model.EventInterface;


/**
 * The adapter which represents the relationship with provider features and our controller.
 */
public class FeaturesAdapter extends AbstractAdapter implements Features {

  // i believe instead of a controller it would take in our features
  private final controller.Features features;


  public FeaturesAdapter(controller.Features features) {
    this.features = features;
  }


  @Override
  public void addCalendar() {
    // how to add a calendar without a file?
    // features.addUser();
  }

  @Override
  public void saveCalendars() {
    // how to save a calendar without a directory?
    //features.saveUsers();
  }

  @Override
  public void selectUser(String selectedUser) {
    // can add a field in features to account for tracking the selected user.
  }

  @Override
  public void createEventFrame() {
    // ????
  }

  @Override
  public void createEvent(String name, String startDay, String startTime,
                          String endDay, String endTime, String location, boolean isOnline,
                          List<String> participants) {
    Time time = new Time(getDay(startDay), startTime, getDay(endDay), endTime);
    Location loc = new Location(isOnline, location);

    EventRep event = new Event(name, time, loc, participants);
    features.create(event);
  }

  /**
   * Get a given day based on the String entered.
   * @param     day The string rep. of a day
   * @return    The respective day enum to represent a day.
   */
  private Day getDay(String day) {
    if (day.equalsIgnoreCase("sunday")) {
      return Day.SUNDAY;
    }
    else if (day.equalsIgnoreCase("monday")) {
      return Day.MONDAY;
    }
    else if (day.equalsIgnoreCase("tuesday")) {
      return Day.TUESDAY;
    }
    else if (day.equalsIgnoreCase("wednesday")) {
      return Day.WEDNESDAY;
    }
    else if (day.equalsIgnoreCase("thursday")) {
      return Day.THURSDAY;
    }
    else if (day.equalsIgnoreCase("friday")) {
      return Day.FRIDAY;
    }
    else if (day.equalsIgnoreCase("saturday")) {
      return Day.SATURDAY;
    }
    else {
      throw new IllegalArgumentException("not a valid day");
    }
  }

  @Override
  public void scheduleEventFrame() {
    //????
  }

  @Override
  public void scheduleEvent(String name, String location, boolean isOnline,
                            String duration, List<String> participants) {
    Location loc = new Location(isOnline, location);

    //features.schedule(name, loc, duration.replaceAll("[^0-9]", ""),
    //        participants, );

    // how to make a strategy from a disconnected model NEEDS A STRATEGY
  }

  @Override
  public void modifyOrRemoveEventFrame(EventInterface event) {

  }

  @Override
  public void modifyEvent(String name, String startDayString, String startTimeString,
                          String endDayString, String endTimeString, String location,
                          boolean isOnline, List<String> participants, EventInterface oldEvent) {

  }

  @Override
  public void removeEvent(EventInterface event) {
    String place = event.getLocation();
    boolean isonline = event.isOnline();
    String name = event.getName();
    LocalDateTime endtime = event.getEndTime();
    LocalDateTime starttime = event.getStartTime();
    List<String> invitees = event.getUsers();

    EventRep eventRep = new Event(name,
            new Time(getDayFromVal(starttime.getDayOfWeek().getValue()), starttime.toString(),
                    getDayFromVal(endtime.getDayOfWeek().getValue()), endtime.toString()),
            new Location(isonline, place), invitees);

    features.remove(eventRep, event.getHost());
  }
}
