package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.strategy.ScheduleStrat;
import model.Event;
import model.EventRep;
import model.NUPlannerSystem;
import model.eventfields.Day;
import model.eventfields.Location;
import model.eventfields.Time;
import view.NUPlannerView;

/**
 * The class which ties in the features a controller has to a model.
 * Given the view calls back onto this class.
 */
public class NUFeature implements Features {
  private NUPlannerSystem model;
  private NUPlannerView view;

  public NUFeature(NUPlannerSystem model, NUPlannerView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void addUser(File file) {
    try {
      this.model.addUser(file);
    } catch (IOException e) {
      // bad appendable for adding a user
    }
  }

  @Override
  public void saveUsers(File dir) {
    try {
      this.model.saveSchedule(dir);
    } catch (IOException e) {
      // bad appendable for saving a schedule
    }
  }

  @Override
  public void handleClick(double time, int day, String selectedUser) {
    int hour = (int) time;
    int min = (int) ((time - hour) * 60);
    EventRep event = makeEvent(hour , min, makeDay(day), selectedUser);
    EventRep eventDisplay = event; // don't want to use null so it will change
    boolean openEventFrame = false;

    for (EventRep e: this.model.getUserEvents(selectedUser)) {
      if (!e.overlapsWith(event)) {
        eventDisplay = e;
        openEventFrame = true;
        break;
      }
    }

    if (openEventFrame) {
      try {
        this.view.openEventFrame(eventDisplay, selectedUser);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void modify(EventRep event) {
    // will modify given event
  }

  @Override
  public void remove(EventRep event, String user) {
    try {
      this.model.removeEvent(event, user);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void create(EventRep event) {
    // create a new event
  }

  @Override
  public void schedule(String name, Location loc, int duration,
                       List<String> invitees, ScheduleStrat strat) {
    // will schedule event with given parameters using the given strategy
    strat.schedule(name, loc, duration, invitees);
  }

  /**
   * Given an int from the cell click make a day object to represent the starting day.
   * @param     day The int representation of an event.
   * @return    a day enum which is used to make an instance of Time.
   */
  private Day makeDay(int day) {
    switch (day) {
      case 0:
        return Day.SUNDAY;
      case 1:
        return Day.MONDAY;
      case 2:
        return Day.TUESDAY;
      case 3:
        return Day.WEDNESDAY;
      case 4:
        return Day.THURSDAY;
      case 5:
        return Day.FRIDAY;
      case 6:
        return Day.SATURDAY;
      default:
        return null;
    }
  }

  /**
   * Given an hour and minute from the clicked time and taking out the hours and minutes.
   * Make an event based on the given time. MOCK EVENT TO CHECK FOR OVERLAP.
   * @param hour start and end hour of event made
   * @param min minutes of event time
   * @param selectedUser user to invite to made event
   * @return a mock event based on the given parameters
   */
  private EventRep makeEvent(int hour, int min, Day day, String selectedUser) {
    String startTime = String.format("%04d", (hour * 100) + min);
    String endTime = String.format("%04d",(hour * 100) + (min + 1));
    Time time = new Time(day, startTime, day, endTime);
    Location loc = new Location(true, "mock place");
    List<String> invitee = new ArrayList<>(Arrays.asList(selectedUser, "Mock Partier"));
    return new Event("Mock Place", time, loc, invitee);
  }

}