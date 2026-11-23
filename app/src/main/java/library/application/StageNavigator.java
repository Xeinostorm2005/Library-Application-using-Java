package library.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class StageNavigator {
  private final Stage primaryStage;

  public StageNavigator(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void switchView(String fxmlFile) {
    try {
      URL fileUrl = getClass().getResource(fxmlFile);

      if (fileUrl == null) {
        System.err.println("ERROR: Could not find FXML file at path: " + fxmlFile);
        return;
      }

      FXMLLoader loader = new FXMLLoader(fileUrl);
      Parent root = loader.load();

      Object controller = loader.getController();

      if (controller instanceof ControlledNavigator) {
        ((ControlledNavigator) controller).setNavigator(this);
      }

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
