package view;

import java.io.IOException;

import controller.NUFeature;

public class MockView implements NUPlannerView {
  public Appendable output;

  public MockView(Appendable output) {
    this.output = output;
  }

  @Override
  public void setListener(NUFeature executer) throws IOException {
    // constructing
    output.append("The NUFeatures input: ").append(String.valueOf(executer));
  }

  @Override
  public void display() throws IOException {
    // calls this.setVisible
    output.append("setVisible was called");
  }
}
