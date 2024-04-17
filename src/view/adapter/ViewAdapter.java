package view.adapter;

import java.io.IOException;
import java.util.Objects;

import javax.swing.*;

import controller.Features;
import controller.FeaturesAdapter;
import model.EventRep;
import model.adapters.ModelAdapter;
import provider.view.MainSystemFrameInterface;
import provider.view.ScheduleEventFrame;
import view.EventScheduleFrame;
import view.NUPlannerView;

public class ViewAdapter implements NUPlannerView {

  private final MainSystemFrameInterface adaptee;
  private ModelAdapter model;

  public ViewAdapter(MainSystemFrameInterface adaptee, ModelAdapter model) {
    this.adaptee = Objects.requireNonNull(adaptee);
    this.model = model;
  }


  @Override
  public void setListener(Features executer) throws IOException {
    this.adaptee.addFeatures(new FeaturesAdapter(executer));
  }

  @Override
  public void display() throws IOException {
    this.adaptee.render();
  }

  @Override
  public void openEventFrame(EventRep event, String selectedUser) throws IOException {
    JDialog eventFrame = new JDialog(new ScheduleEventFrame(model, selectedUser));
  }
}
