package library.application.module.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserRecordTest {

  @Test 
  public void testUserRecordCreation() {
    UserRecord user = new UserRecord(
      "1", "testuser", "test@example.com",
      "password", "active", "user",
      new java.sql.Timestamp(System.currentTimeMillis()),
      new java.sql.Timestamp(System.currentTimeMillis())
    );

    assertNotNull(user);
    assertEquals("1", user.id());
    assertEquals("testuser", user.username());
    assertEquals("test@example.com", user.email());
    assertEquals("password", user.password());
    assertEquals("active", user.status());
    assertEquals("user", user.role());
  }
}
