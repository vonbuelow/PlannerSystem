package controller;

import java.io.File;

import model.NUPlannerSystem;

public class NUFeature implements Features{

  private NUPlannerSystem model;

  public NUFeature (NUPlannerSystem model) {
    this.model = model;
  }


  @Override
  public void addUser(File file) {
    this.model.addUser(file);
  }

  @Override
  public void saveUsers(File dir) {
    this.model.saveSchedule(dir);
  }

}
