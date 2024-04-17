package controller;
import java.util.List;

import provider.controller.Features;
import provider.model.EventInterface;

public class FeaturesAdapter implements Features {



  @Override
  public void addCalendar() {

  }

  @Override
  public void saveCalendars() {

  }

  @Override
  public void selectUser(String selectedUser) {

  }

  @Override
  public void createEventFrame() {

  }

  @Override
  public void createEvent(String name, String startDay, String startTime, String endDay, String endTime, String location, boolean isOnline, List<String> participants) {

  }

  @Override
  public void scheduleEventFrame() {

  }

  @Override
  public void scheduleEvent(String name, String location, boolean isOnline, String duration, List<String> participants) {

  }

  @Override
  public void modifyOrRemoveEventFrame(EventInterface event) {

  }

  @Override
  public void modifyEvent(String name, String startDayString, String startTimeString, String endDayString, String endTimeString, String location, boolean isOnline, List<String> participants, EventInterface oldEvent) {

  }

  @Override
  public void removeEvent(EventInterface event) {

  }
}
