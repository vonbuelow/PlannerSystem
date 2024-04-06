package view;

import javax.swing.JTextField;

/**
 * The basic representation of an event panel.
 */
public abstract class AEventPanel {
  private final JTextField eventNameField;
  private final JTextField locationField;


  AEventPanel () {
    this.eventNameField = new JTextField();
    this.locationField = new JTextField();
  }

}
