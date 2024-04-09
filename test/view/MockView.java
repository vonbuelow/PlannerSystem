package view;

import java.io.IOException;

import controller.Features;
import controller.NUFeature;
import model.EventRep;

/**
 * Mocks an NUPlannerView but logs the inputs given from an NUController to the
 * current view.
 */
public class MockView implements NUPlannerView {
  public Appendable output;

  public MockView(Appendable output) {
    this.output = output;
  }

  @Override
  public void setListener(Features executer) throws IOException {
    // constructing
    output.append("The NUFeatures input: ").append(String.valueOf(executer)).append("\n");
  }

  @Override
  public void display() throws IOException {
    // calls this.setVisible
    output.append("setVisible was called\n");
  }

  @Override
  public void openEventFrame(EventRep event, String selectedUser) throws IOException {

  }
}
