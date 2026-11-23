from pathlib import Path
from mysql.connector import connect, Error
from dotenv import load_dotenv
import os


def create_connection():
    try:
        connection = connect(
            host=os.environ.get("DATABASE_HOST"),
            user=os.environ.get("DATABASE_USER"),
            port=os.environ.get("DATABASE_PORT") or 3306,
            password=os.environ.get("DATABASE_PASSWORD"),
            database=os.environ.get("DATABASE_NAME"),
            use_pure=True
        )
        return connection
    except Error as e:
        print(f"Error connecting to MySQL: {e}")
        return None


def execute_sql_file(connection, file_path):
    try:
        sql_script = Path(file_path).read_text(encoding="utf-8")

        with connection.cursor() as cursor:
            cursor.execute(sql_script)
            while cursor.nextset():
                pass

            connection.commit()
            print("SQL setup script executed successfully!")

    except Error as e:
        print(f"Error executing SQL script: {e}")
    except FileNotFoundError:
        print(f"Could not find the SQL file at: {file_path}")


def main():
    load_dotenv(dotenv_path=Path('./.env'))

    connection = create_connection()

    if connection:
        try:
            schema_path = Path("./setup/schema.sql")
            execute_sql_file(connection, schema_path)
        finally:
            connection.close()
            print("Database connection closed.")


main()
