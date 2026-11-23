package library.application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import library.application.StageNavigator;
import library.application.exceptions.NotFoundException;
import library.application.module.user.UserController;
import library.application.module.user.UserRecord;
import library.application.ControlledNavigator;

public class AuthController implements ControlledNavigator {
  private StageNavigator navigator;
  @FXML
  private TextField emailField;
  @FXML 
  private PasswordField passwordField;

  @Override
  public void setNavigator(StageNavigator navigator) {
    this.navigator = navigator;
  }

  @FXML
  private void viewAuthPage() {
    System.out.println("Switching to auth view...");
    if (navigator != null) {
      navigator.switchView("/fxml/auth.fxml");
    }
  }

  @FXML
  private void loginRequest() {
    String email = emailField.getText();
    String password = passwordField.getText();

    // Implement your login logic here, e.g., validate credentials against the database
    System.out.println("Attempting login with email: " + email + " and password: " + password);
    if (email.isEmpty() || password.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Login Error", "Please fill in all fields.");
    } else {
      try {
        UserController userController = new UserController();
        UserRecord user = userController.findUserByEmail(email);
        showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back!");
        navigator.switchView("/fxml/main.fxml");
      } catch (NotFoundException e) {
        showAlert(Alert.AlertType.ERROR, "Login Error", "An error occurred during login: " + e.getMessage());
      }
    }
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null); // Removes the default header text line
    alert.setContentText(message);
    alert.showAndWait(); // Pauses until the user clicks OK
  }
}
