# Project - Library Management Application
---
A robust desktop-based library management application built with Java 25 and JavaFX. Features a modern graphical user interface, secure direct database connectivity with password hashing, and a persistent MySQL relational backend managed via Gradle. Designed to streamline book cataloging, patron tracking, and automated weekly fine calculations.

---
## About me
**Author:** Xeinostorm05
**Degree:** Second-year student - M.S in Computer Science and Science in Engineering (5 years)
**Start Date:** 2026-11-10
**Status:** NOT COMPLETED

---
## Setup
### Environment Variables
```dotenv
# Database configuration
DATABASE_USER=dev_user
DATABASE_PASSWORD=dev_password
DATABASE_HOST=localhost
DATABASE_PORT=3306
DATABASE_NAME=local_db
```
### Database
Install these python dependencies:
```pip
pip install python-dotenv mysql-connector-python
```
After installing the dependencies run the file called `db.py` inside the folder `setup`
```bash
python ./setup/db.py
```
### Java Application
- Install Java 25 JDK
- Install Gradle
- Run the following command:
```bash
./gradlew build
```
- To run the application:
```bash
./gradlew run -q --console=plain
```

---
## ER - Diagram
![ER-Diagram](/docs/er-diagram.svg)

## Class Diagrams
```mermaid
%%{
  init: {
    'theme': 'base',
    'themeVariables': {
      'primaryColor': '#1e293b',
      'primaryTextColor': '#ffffff',
      'primaryBorderColor': '#38bdf8',
      'lineColor': '#00f0ff',
      'tertiaryColor': '#0f172a',
      'edgeLabelBackground': '#0f172a',
      'nodeBorder': '#38bdf8'
    }
  }
}%%
classDiagram

  class ManageUser {}

  class UserController {
    + addUser(User user) User
    + updateUser(User user) User
    + deleteUser(User user) User
    + getUserById(String id) User
    + getUserByEmail(String email) User
  }

  class UserValidation {
    + validateEmail() throws exception
    + validateRole() throws exception
    + validateUUID() throws exception
  }

  class UserRepository {
    + addUser(User user) User
    + updateUser(User user) User
    + deleteUser(User user) User
    + getUserById(String id) User
    + getUserByEmail(String email) User
    - mapUser(ResultSet sr) User  
  }

  ManageUser --|> UserController : Sends Request
  UserController --|> UserValidation
  UserValidation --> UserController : invalid
  UserValidation --|> UserRepository : valid

  class ManageBook {}

  class BookController {
    + addBook(Book book) Book
    + updateBook(Book book) Book
    + deleteBook(Book book) Book
    + getBookById(String id) Book
  }

  class BookValidation {
    + validatePublication() throws exception
  }

  class BookRepository {
    + addBook(Book book) Book
    + updateBook(Book book) Book
    + deleteBook(Book book) Book
    + getBookById(String id) Book
    - mapBook(ResultSet sr) Book  
  }

  ManageBook --|> BookController : Sends Request
  BookController --|> BookValidation
  BookValidation --> BookController : invalid
  BookValidation --|> BookRepository : valid
```