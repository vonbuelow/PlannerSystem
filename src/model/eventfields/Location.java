package model.eventfields;

import java.util.Objects;

/**
 * Represents a location.
 * Given if it is online or not and a place.
 */
public class Location {
  private boolean online;
  private String place;

  /**
   * Location representation.
   * @param online if the location is taking place online
   * @param place the name of the location place
   * @throws IllegalArgumentException if the place is null or empty
   */
  public Location(boolean online, String place) {
    this.online = online;
    if (place == null || place.isEmpty()) {
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

  /**
   * Returns a string stating whether the location is online.
   * Public observation for XML writer reference.
   * @return a string of true or false
   */
  public String isOnline() {
    String online = "";
    online += Boolean.toString(this.online);
    return online;
  }

  /**
   * Returns the name of the place of this location.
   * Public observation for XML writer reference.
   * @return a string of the location place
   */
  public String getPlace() {
    String place = "";
    place += this.place;
    return place;
  }
}
