package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import controller.Features;
import controller.NUController;
import controller.NUFeature;
import controller.NUPlannerGUIController;
import view.NUPlannerTextView;
import view.NUPlannerView;

import static org.junit.Assert.assertEquals;

/**
 * Tests that an NUPlannerSystem has its methods called appropriately (with the
 * correct inputs at the correct time) by an NUController or Features.
 */
public class MockModelTests {
  Appendable mockModelOutput;
  NUPlannerView view;
  NUController controller;
  NUPlannerSystem model;
  Features features;
  String[] outLines;
  File fileToSave;

  @Before
  public void setUp() {
    mockModelOutput = new StringBuilder();
    model = new MockModel(mockModelOutput);
    view = new NUPlannerTextView(model);
    controller = new NUPlannerGUIController(view);
    features = new NUFeature(model, view);
    fileToSave =
            new File(".\\src\\xmlfunc");
  }

  @Test
  public void testSaveSchedule() {
    features.saveUsers(fileToSave);
    outLines = mockModelOutput.toString().split("\n");
    assertEquals("user is saving a file to this path: " + fileToSave.getAbsolutePath(),
            outLines[0]);
  }
}
