package model.eventFields;

import java.util.Objects;

/**
 * Represents a location.
 * Given if it is online or not and a place.
 */
public class Location {
  boolean online;
  String place;

  Location(boolean online, String place) {
    this.online = online;
    if (place.equals("") || place == null) {
      throw new IllegalArgumentException("cannot have null or empty location place");
    }
    this.place = place;
  }
}
