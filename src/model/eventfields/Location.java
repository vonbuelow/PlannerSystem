package model.eventfields;

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

  @Override
  public boolean equals(Object o) {
    if (o instanceof Location) {
      Location e = (Location)o;

      return this.online.equals(e.online) && this.place.equals(e.place);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return 37 * place.length() * String.valueOf(online);
  }
}
