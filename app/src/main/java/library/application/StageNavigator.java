package library.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageNavigator {
  private final Stage primaryStage;

  public StageNavigator(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void switchView(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();

      if (primaryStage.getScene() == null) {
        primaryStage.setScene(new Scene(root));
      } else {
        primaryStage.getScene().setRoot(root);
      }
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
