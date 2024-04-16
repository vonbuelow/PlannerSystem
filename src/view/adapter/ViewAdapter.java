package view.adapter;

import java.io.IOException;
import java.util.Objects;

import controller.Features;
import model.EventRep;
import provider.view.MainSystemFrameInterface;
import view.NUPlannerView;

public class ViewAdapter implements NUPlannerView {

  private final MainSystemFrameInterface adaptee;

  public ViewAdapter(MainSystemFrameInterface adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }


  @Override
  public void setListener(Features executer) throws IOException {

  }

  @Override
  public void display() throws IOException {

  }

  @Override
  public void openEventFrame(EventRep event, String selectedUser) throws IOException {

  }
}
