package library.application;

import library.application.database.Client;
import library.application.database.Config;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * App
 */
public class App extends Application {
    private static Client dbClient;

    /**
     * Gets the database client instance.
     * @throws IllegalStateException if the database client has not been initialized yet.
     * @return The database client instance.
     */
    public static Client getDbClient() {
        if (dbClient == null) {
            throw new IllegalStateException("Database client has not been initialized yet.");
        }
        return dbClient;
    }

    /**
     * The main entry point for the application.
     * This method is called when the application is started.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Initialize the database client
        try {
            System.out.println("Initializing the database client...");
            dbClient = Config.createClient();
            System.out.println("Database client initialized successfully.");
        } catch (Exception e) {
            System.err.println("Failed to initialize the database client: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Your application logic here
        System.out.println("Application started.");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Library Application");
        primaryStage.show();
    }
}
