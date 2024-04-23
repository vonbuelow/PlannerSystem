package model;

import java.io.File;

import xmlfunc.Reader;
import xmlfunc.SatStartXMLReader;

public class SatStartModel extends CentralSystem {

  @Override
  public void addUser(File file) {
    if (file == null) {
      throw new IllegalArgumentException("file cannot be null");
    }
    try {
      Reader reader = new SatStartXMLReader(file);
      addNewUser(reader.readXML());
      // INVARIANT CHECKING EVENT OVERLAP
      // IF A USER SHOULD BE ADDED TO A NEW EVENT THAT HAS BEEN LOADED IN
      // EVERY USER IS UNIQUE
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
