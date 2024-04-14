package provider.view;

import java.awt.Component;

import provider.controller.Features;

/**
 * Interface representing the view for the main system.
 * Not really sure what public methods need to be declared here,
 * so left empty for now.
 */
public interface MainSystemFrameInterface {

  /**
   * Returns the parent frame or component to be used as the parent for dialogues.
   * @return the parent frame or component
   */
  Component getParentComponent();

  /**
   * Sets the view to visible.
   */
  void render();

  /**
   * Refreshes the schedule panel so that it redraws the events and shows
   * the most up-to-date information.
   */
  void refresh();

  /**
   * Refreshes the combo box so that when a calendar or user is added to the system
   * all (new) users are shown.
   */
  void refreshComboBox();

  /**
   * Sets the current user.
   * @param user the user to set it to
   */
  void setCurrentUser(String user);

  /**
   * Retrieves the current user.
   * @return the currently selected user
   */
  String getCurrentUser();

  /**
   * Displays an error message to the client in GUI format.
   * Used to display exceptions when they occur.
   * @param message the error message to be displayed
   */
  void displayMessage(String message);

  /**
   * Adds an action listener to all the Buttons in main system frame to call upon the controller.
   * This allows for the functions in features to do what it is supposed to do to alter the view
   * or model when clicked.
   * Used in the controller.
   * @param features interface that it takes in and its function.
   */
  void addFeatures(Features features);
}
