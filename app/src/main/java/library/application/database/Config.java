package library.application.database;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * The Config class is responsible for creating and configuring the database client used in the application.
 * It reads the necessary database connection parameters from environment variables and constructs a Client instance.
 * Config
 */
public class Config {

  /**
   * Creates and returns a new Client instance configured with database connection parameters.
   * It reads the database host, port, name, user, and password from environment variables, and constructs the JDBC URL accordingly.
   * If any required environment variable is missing, it throws an IllegalStateException to prevent the application from running without a valid database connection.
   * @return A new Client instance connected to the specified database.
   */
  public static Client createClient() {
    Dotenv dotenv = Dotenv.configure().directory("../").ignoreIfMissing().load();

    String host = getEnv(dotenv, "DATABASE_HOST", "localhost");
    String port = getEnv(dotenv, "DATABASE_PORT", "3306");
    String dbName = getRequiredEnv(dotenv, "DATABASE_NAME");
    String user = getRequiredEnv(dotenv, "DATABASE_USER");
    String password = getRequiredEnv(dotenv, "DATABASE_PASSWORD");

    String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

    Client client = new Client(url, user, password);
    client.connect();
    return client;
  }

  /**
   * Retrieves the value of a required environment variable, first checking the .env file and then the system environment variables.
   * If the variable is not set in either location, it throws an IllegalStateException.
   * @param dotenv The Dotenv instance used to read from the .env file.
   * @param key The name of the environment variable to retrieve.
   * @return The value of the environment variable.
   * @throws IllegalStateException if the environment variable is not set in either the .env file or the system environment variables.
   */
  private static String getRequiredEnv(Dotenv dotenv, String key) {
    String value = dotenv.get(key);
    if (value == null || value.isEmpty()) {
      value = System.getenv(key);
    }
    if (value == null || value.isEmpty()) {
      throw new IllegalStateException("Required environment variable " + key + " is not set.");
    }
    return value;
  }

  /**
   * Retrieves the value of an environment variable, first checking the .env file and then the system environment variables.
   * If the variable is not set in either location, it returns the provided default value.
   * @param dotenv The Dotenv instance used to read from the .env file.
   * @param key The name of the environment variable to retrieve.
   * @param defaultValue The default value to return if the environment variable is not set.
   * @return The value of the environment variable, or the default value if it is not set.
   */
  private static String getEnv(Dotenv dotenv, String key, String defaultValue) {
    String value = dotenv.get(key);
    if (value == null || value.isEmpty()) {
      value = System.getenv(key);
    }
    return (value == null || value.isEmpty()) ? defaultValue : value;
  }
}