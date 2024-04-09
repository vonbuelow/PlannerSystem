package view;

import org.junit.Before;
import org.junit.Test;

import controller.Features;
import controller.NUController;
import controller.NUFeature;
import controller.NUPlannerGUIController;
import model.CentralSystem;
import model.NUPlannerSystem;

import static org.junit.Assert.assertEquals;

/**
 * Tests that an NUPlannerView has its methods called appropriately (with the
 * correct inputs at the correct time) by an NUController or Features.
 */
public class MockViewTests {
  Appendable mockViewLog;
  NUPlannerView view;
  NUController controller;
  NUPlannerSystem model;
  Features features;
  String[] outLines;

  @Before
  public void setup() {
    mockViewLog = new StringBuilder();
    view = new MockView(mockViewLog);
    controller = new NUPlannerGUIController(view);
    model = new CentralSystem();
    features = new NUFeature(model);
  }

  @Test
  public void testRunPlanner() {
    controller.runPlanner(model);
    outLines = mockViewLog.toString().split("\n");
    //assertEquals("The NUFeatures input: ",  outLines[0]);
    assertEquals("setVisible was called",  outLines[1]);
    System.out.println(features);
  }

}
