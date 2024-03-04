package model.eventFields;

import model.eventFields.Day;

public class Time {
  Day start;
  Day end;
  int startTime;
  int endTime;


  // ADD SPECIFICATIONS IN THE CONSTRUCTOR FOR A VALID TIME!!!!!!
  // int length = String.valueOf(number).length();
  // new class for start and end times for hours and minutes!!!


  Time(Day start, int startTime, Day end, int endTime) {
    this.start = start;
    this.startTime = startTime;
    this.end = end;
    this.endTime = endTime;
  }

}
