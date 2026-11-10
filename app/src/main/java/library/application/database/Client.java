package library.application.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * Database Client
 */
public class Client {
    private final String user;
    private final String password;
    private final String url;
    private Connection connection;

    /**
     * Constructor for the Client class.
     * Initializes the database connection parameters.
     * @param url The database URL.
     * @param user The database user.
     * @param password The database password.
     */
    public Client(String url, String user, String password) {
        this.connection = null;
        this.user = user;
        this.password = password;
        this.url = url;
    }

    /**
     * Establishes a connection to the database using the provided URL, user, and password.
     * If the connection is successful, it prints a success message to the console.
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the database if a connection is established.
     * It checks if the connection is not null and not already closed before attempting to close it.
     * If the disconnection is successful, it prints a success message to the console.
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a query against the database.
     * @param query The SQL query to execute.
     * @param params The parameters to bind to the query.
     * @return The result set of the query.
     */
    public ResultSet executeQuery(String query, Object... params) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Query execution failed", e);
        }
    }
}
