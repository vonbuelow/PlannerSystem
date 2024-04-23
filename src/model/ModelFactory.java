package model;

/**
 * The factory used for making strategies based on name.
 */
public class ModelFactory {

  /**
   * Making the given model to use.
   * @param      model The given model.
   * @return The model the user wants to use.
   */
  public NUPlannerSystem makeModel(String model) {
    if (model == null) {
      throw new IllegalArgumentException("cannot have a null strat");
    }
    else if (model.equalsIgnoreCase("saturday")) {
      return new SatStartModel();
    }
    else if (model.equalsIgnoreCase("sunday")) {
      return new SatStartModel();
    }
    else {
      throw new IllegalArgumentException("not a valid model");
    }
  }
}
