package controller;

import java.io.File;
import java.io.IOException;

import model.NUPlannerSystem;

public class NUFeature implements Features {
  private NUPlannerSystem model;

  public NUFeature (NUPlannerSystem model) {
    this.model = model;
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

}
