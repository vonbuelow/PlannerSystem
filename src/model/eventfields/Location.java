package model.eventfields;

import java.util.Objects;

/**
 * Represents a location.
 * Given if it is online or not and a place.
 */
public class Location {
  boolean online;
  String place;

  public Location(boolean online, String place) {
    this.online = online;
    if (place.equals("") || place == null) {
      throw new IllegalArgumentException("cannot have null or empty location place");
    }
    this.place = place;
  }

  @Override
  public String toString() {
    return "location: " + this.place + "\nonline: " + online;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Location) {
      Location e = (Location)o;

      return this.online == e.online && this.place.equals(e.place);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this);
  }

  public String isOnline() {
    return Boolean.toString(this.online);
  }

  public String getPlace() {
    return this.place;
  }
}
