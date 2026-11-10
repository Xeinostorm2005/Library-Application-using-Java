package library.application;

import io.github.cdimascio.dotenv.Dotenv;
import library.application.database.Client;

public class App {
    private static Client dbClient;

    /**
     * Static block to initialize the database client using environment variables.
     * This block will execute when the class is loaded, ensuring that the database client is ready for use throughout the application.
     * It fetches the database URL, user, and password from environment variables and establishes a connection to the database.
     * If any of the required environment variables are missing, it throws a RuntimeException to prevent the application from running without a valid database connection.
     */
    static {
        Dotenv dotenv = Dotenv.configure().directory("../").load();

        String url = dotenv.get("DATABASE_URL");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("DATABASE_URL environment variable is not set.");
        }

        String user = dotenv.get("DATABASE_USER");
        if (user == null || user.isEmpty()) {
            throw new RuntimeException("DATABASE_USER environment variable is not set.");
        }

        String password = dotenv.get("DATABASE_PASSWORD");
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("DATABASE_PASSWORD environment variable is not set.");
        }
        
        dbClient = new Client(url, user, password);
        dbClient.connect();
    }

    /**
     * Returns the database client instance for use in the application.
     * This method provides access to the database client that was initialized in the static block.
     * @return The database client instance.
     */
    public static Client getDbClient() {
        return dbClient;
    }

    /**
     * The main entry point for the application.
     * This method is called when the application is started.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Your application logic here
        System.out.println("Application started.");
    }
}
