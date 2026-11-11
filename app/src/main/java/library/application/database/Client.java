package library.application.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.function.Function;

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
     * @param mapper The function to map the result set to the desired type.
     * @param params The parameters to bind to the query.
     * @return The result of the query.
     */
    public <T> T executeQuery(String query, Function<ResultSet, T> mapper, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapper.apply(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query execution failed", e);
        }
    }
    
    /**
     * Executes an insert query against the database.
     * @param query The SQL query to execute.
     * @param mapper The function to map the result set to the desired type.
     * @param params The parameters to bind to the query.
     * @return The result of the insert.
     */
    public <T> T executeInsert(String query, Function<ResultSet, T> mapper, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating record failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                return mapper.apply(generatedKeys);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insert execution failed", e);
        }
    }

    /**
     * Executes an update query against the database.
     * @param query The SQL query to execute.
     * @param params The parameters to bind to the query.
     * @return The number of rows affected by the update.
     */
    public int executeUpdate(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Update execution failed", e);
        }
    }
}
