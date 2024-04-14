package provider.controller;

import provider.model.SystemInterface;

/**
 * Controller interface that represents the run method.
 * Run renders the view to show up.
 */
public interface ControllerInterface {

  /**
   * Runs the program.
   * @param model the model
   */
  void run(SystemInterface model);
}
