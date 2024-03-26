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
   * @param online if the location is taking place online.
   * @param place where the place of the location is.
   */
  public Location(boolean online, String place) {
    this.online = online;
    if (place.isEmpty() || place == null) {
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
   * @return a string of true or false
   */
  public String isOnline() {
    return Boolean.toString(this.online);
  }

  /**
   * Returns the name of the place of this location.
   * @return a string of the location place
   */
  public String getPlace() {
    return this.place;
  }
}
