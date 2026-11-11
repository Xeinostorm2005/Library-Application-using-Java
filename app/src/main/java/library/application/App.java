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

        String user = dotenv.get("DATABASE_USER");
        if (user == null || user.isEmpty()) {
            throw new RuntimeException("DATABASE_USER environment variable is not set.");
        }
        
        String host = dotenv.get("DATABASE_HOST");
        if (host == null || host.isEmpty()) {
            throw new RuntimeException("DATABASE_HOST environment variable is not set.");
        }


        String password = dotenv.get("DATABASE_PASSWORD");
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("DATABASE_PASSWORD environment variable is not set.");
        }
        
        String port = dotenv.get("DATABASE_PORT");
        if (port == null || port.isEmpty()) {
            port = "3306";
        }

        String dbName = dotenv.get("DATABASE_NAME");
        if (dbName == null || dbName.isEmpty()) {
            throw new RuntimeException("DATABASE_NAME environment variable is not set.");
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

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
