package library.application.module.user;

import library.application.exceptions.NotFoundException;

public class UserController {
  public UserRecord findUserByEmail(String email) throws NotFoundException {
    UserRepository userRepository = new UserRepository();
    UserRecord user = userRepository.getUserByEmail(email);
    if (user == null) {
      throw new NotFoundException("User not found");
    }
    return user;
  }
}
