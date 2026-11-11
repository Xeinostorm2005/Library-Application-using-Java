from pathlib import Path
from mysql.connector import connect, Error
from dotenv import load_dotenv
import os


def create_connection():
    try:
        connection = connect(
            host=os.environ.get("DB_HOST"),
            user=os.environ.get("DB_USER"),
            port=os.environ.get("DB_PORT") or 3306,
            password=os.environ.get("DB_PASSWORD"),
            database=os.environ.get("DB_NAME")
        )
        return connection
    except Error as e:
        print(f"Error connecting to MySQL: {e}")
        return None


def execute_sql_file(connection, file_path):
    try:
        sql_script = Path(file_path).read_text(encoding="utf-8")

        with connection.cursor() as cursor:
            for result in cursor.execute(sql_script, multi=True):
                if result.with_rows:
                    print(result.fetchall())

            connection.commit()
            print("SQL setup script executed successfully!")

    except Error as e:
        print(f"Error executing SQL script: {e}")
    except FileNotFoundError:
        print(f"Could not find the SQL file at: {file_path}")


def main():
    load_dotenv(dotenv_path=Path('../.env'))

    connection = create_connection()

    if connection:
        try:
            schema_path = Path("schema.sql")
            execute_sql_file(connection, schema_path)
        finally:
            connection.close()
            print("Database connection closed.")


main()
