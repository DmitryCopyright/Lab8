package server.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static server.utility.DatabaseHandler.*;

public class Initializer {
    private final Connection connection;


    public Initializer(Connection connection) {
        this.connection = connection;
    }

    public void initialize() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");

      String createGroupTable = "CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " (" +
                GROUP_TABLE_ID_COLUMN + " SERIAL NOT NULL PRIMARY KEY, " +
                GROUP_TABLE_NAME_COLUMN + " varchar (255) NOT NULL, " +
                COORDINATES_TABLE_X_COLUMN + " real, " +
                COORDINATES_TABLE_Y_COLUMN + " bigint, " +
                GROUP_TABLE_CREATION_DATE_COLUMN + " date DEFAULT (current_date), " +
                GROUP_TABLE_STUDENTS_COUNT_COLUMN + " int, " +
                GROUP_TABLE_EXPELLED_STUDENTS_COLUMN + " int, " +
                GROUP_TABLE_AVERAGE_MARK_COLUMN + " int, " +
                GROUP_TABLE_FORM_OF_EDUCATION_COLUMN + " varchar (255), " +
              PERSON_TABLE_N_COLUMN + " varchar (255), " +
                GROUP_TABLE_WEIGHT_COLUMN + " int, " +
                GROUP_TABLE_HAIR_COLOR_COLUMN + " varchar (255), " +
                GROUP_TABLE_NATIONALITY_COLUMN + " varchar (255), " +
                LOCATION_X_TABLE_STUDY_GROUP_ID_COLUMN +" int, "+
                LOCATION_Y_TABLE_STUDY_GROUP_ID_COLUMN +" int, "+
                 LOCATION_Z_TABLE_STUDY_GROUP_ID_COLUMN +" int, "+
              LOCATION_N_TABLE_STUDY_GROUP_ID_COLUMN + " varchar (255)," +
              GROUP_TABLE_GROUP_OWNER_COLUMN + " varchar (255))";

      statement.executeUpdate(createGroupTable);


            String createUserTable = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" +
                    USER_TABLE_ID_COLUMN + " SERIAL NOT NULL PRIMARY KEY, " +
                    USER_TABLE_USERNAME_COLUMN + " varchar (255), " +
                    USER_TABLE_PASSWORD_COLUMN + " BYTEA DEFAULT(null))";
        //App.logger.error(createUserTable);
            statement.executeUpdate(createUserTable);

    }
}
