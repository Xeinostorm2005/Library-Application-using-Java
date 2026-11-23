package library.application.module.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import library.application.database.Client;
import library.application.exceptions.NotFoundException;

public class UserRepository {
  private final Client dbClient = library.application.App.getDbClient();

  /**
   * Adds a new user to the database.
   * @param user The User object containing user details to be added.
   * @return The User object representing the newly added user.
   */
  public UserRecord addUser(UserRecord user) {
    String query = "INSERT INTO user (userId, firstName, lastName, email, password, status, role, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    return dbClient.executeInsert(query, resultSet -> {
      try {
        if (!resultSet.next()) {
          throw new RuntimeException("Failed to insert user");
        }

        return mapUser(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException("Error while mapping user result", e);
      }
    },
      user.id(),
      user.firstName(),
      user.lastName(),
      user.email(),
      user.password(),
      user.status(),
      user.role(),
      user.createdAt(),
      user.updatedAt()
    );
  }

  /**
   * Retrieves a user from the database by their ID.
   * @param id The ID of the user to retrieve.
   * @return The User object representing the retrieved user.
   * @throws RuntimeException if the user is not found or if there is an error while mapping the result.
   */
  public UserRecord getUserById(String id) {
    String query = "SELECT * FROM user WHERE userId = ?";

    return dbClient.executeQuery(query, resultSet -> {
      try {
        if (!resultSet.next()) {
          throw new NotFoundException("User not found with id: " + id);
        }

        return mapUser(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException("Error while mapping user result", e);
      }
    }, id);
  }

  public UserRecord getUserByEmail(String email) {
    String query = "SELECT * FROM user WHERE email = ?";

    return dbClient.executeQuery(query, resultSet -> {
      try {
        if (!resultSet.next()) {
          throw new NotFoundException("User not found with email: " + email);
        }

        return mapUser(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException("Error while mapping user result", e);
      }
    }, email);
  }

  /**
   * Updates an existing user in the database.
   * @param user The User object containing updated user details.
   * @return The User object representing the updated user.
   */
  public UserRecord updateUser(UserRecord user) {
    String query = "UPDATE user SET firstName = ?, lastName = ?, email = ?, password = ?, status = ?, role = ?, createdAt = ?, updatedAt = ? WHERE userId = ?";

    return dbClient.executeUpdate(query, 
      user.firstName(),
      user.lastName(),
      user.email(),
      user.password(),
      user.status(),
      user.role(),
      user.createdAt(),
      user.updatedAt(),
      user.id()
    ) > 0 ? user : null;
  }

  /**
   * Deletes a user from the database.
   * @param id The ID of the user to delete.
   * @return true if the user was deleted, false otherwise.
   */
  public boolean deleteUser(String id) {
    String query = "DELETE FROM user WHERE userId = ?";

    return dbClient.executeUpdate(query, id) > 0;
  }

  /**
   * Maps a ResultSet to a User object.
   * @param rs The ResultSet to map.
   * @return The User object representing the mapped user.
   * @throws SQLException if there is an error while mapping the result.
   */
  private static UserRecord mapUser(ResultSet rs) throws SQLException {
    return new UserRecord(
      rs.getString("userId"),
      rs.getString("firstName"),
      rs.getString("lastName"),
      rs.getString("email"),
      rs.getString("password"),
      rs.getString("status"),
      rs.getString("role"),
      rs.getTimestamp("createdAt"),
      rs.getTimestamp("updatedAt")
    );
}
}
