package library.application.module.user;

import java.sql.Timestamp;

public record UserRecord(
  String id,
  String firstName,
  String lastName,
  String email,
  String password,
  String status,
  String role,
  Timestamp createdAt,
  Timestamp updatedAt
) {

}
