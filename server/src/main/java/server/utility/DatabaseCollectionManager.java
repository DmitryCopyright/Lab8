package server.utility;

import common.data.*;
import common.exceptions.DatabaseHandlingException;
import common.interaction.GroupRaw;
import common.interaction.User;
import common.utility.Outputer;
import server.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Operates the database collection itself.
 */
public class DatabaseCollectionManager {
    // GROUP_TABLE
    private final String SELECT_ALL_GROUPS = "SELECT * FROM " + DatabaseHandler.GROUP_TABLE;
    private final String SELECT_GROUP_BY_ID = SELECT_ALL_GROUPS + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_GROUP_BY_ID_AND_USER_ID = "SELECT * FROM " + DatabaseHandler.GROUP_TABLE + " WHERE "
            + DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_GROUP = "INSERT INTO " +
            DatabaseHandler.GROUP_TABLE + " (" +
            DatabaseHandler.GROUP_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + "," +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + "," +
            DatabaseHandler.GROUP_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_STUDENTS_COUNT_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_EXPELLED_STUDENTS_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_AVERAGE_MARK_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_FORM_OF_EDUCATION_COLUMN + ", " +
            DatabaseHandler.PERSON_TABLE_N_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_WEIGHT_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_HAIR_COLOR_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_NATIONALITY_COLUMN + ", " +
            DatabaseHandler.LOCATION_X_TABLE_STUDY_GROUP_ID_COLUMN +", "+
            DatabaseHandler.LOCATION_Y_TABLE_STUDY_GROUP_ID_COLUMN +", "+
            DatabaseHandler.LOCATION_Z_TABLE_STUDY_GROUP_ID_COLUMN +", "+
            DatabaseHandler.LOCATION_N_TABLE_STUDY_GROUP_ID_COLUMN + ", " +//TODO BELOW
            DatabaseHandler.GROUP_TABLE_GROUP_OWNER_COLUMN +
            //") VALUES (?, ?, ?, ?, ?, ?, ?, ::form_of_education, ?, ?, ::hair_color, ::country, ?, ?, ?, ?, ?)";
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_GROUP_BY_ID = "DELETE FROM " + DatabaseHandler.GROUP_TABLE +
            " WHERE " + DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_GROUP_NAME_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_COORDINATESX_BY_GROUP_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_COORDINATESY_BY_GROUP_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_STUDENTS_COUNT_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_STUDENTS_COUNT_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_GROUP_EXPELLED_STUDENTS_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_EXPELLED_STUDENTS_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_GROUP_AVERAGE_MARK_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_AVERAGE_MARK_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_GROUP_FORM_OF_EDUCATION_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_FORM_OF_EDUCATION_COLUMN + " = ?::form_of_education" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_PERSON_TABLE_NAME_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.PERSON_TABLE_N_COLUMN + " = ?, " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_WEIGHT_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_WEIGHT_COLUMN + " = ?" +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HAIR_COLOR_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_HAIR_COLOR_COLUMN + " = ?::hair_color" +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_NATIONALITY_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.GROUP_TABLE_NATIONALITY_COLUMN + " = ?::country" +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LOCATION_X_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.LOCATION_X_TABLE_STUDY_GROUP_ID_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LOCATION_Y_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.LOCATION_Y_TABLE_STUDY_GROUP_ID_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LOCATION_Z_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.LOCATION_Z_TABLE_STUDY_GROUP_ID_COLUMN + ", " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LOCATION_NAME_BY_ID = "UPDATE " + DatabaseHandler.GROUP_TABLE + " SET " +
            DatabaseHandler.LOCATION_N_TABLE_STUDY_GROUP_ID_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.GROUP_TABLE_ID_COLUMN + " = ?";




    private DatabaseHandler databaseHandler;
    private DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseHandler databaseHandler, DatabaseUserManager databaseUserManager) {
        this.databaseHandler = databaseHandler;
        this.databaseUserManager = databaseUserManager;
    }

    /**
     * Create Group.
     *
     * @param resultSet Result set parametres of Group.
     * @return New Group.
     * @throws SQLException When there's exception inside.
     */
    private StudyGroup createStudyGroup(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DatabaseHandler.GROUP_TABLE_ID_COLUMN);
        String name = resultSet.getString(DatabaseHandler.GROUP_TABLE_NAME_COLUMN);
        Coordinates coordinates = new Coordinates(resultSet.getLong(DatabaseHandler.COORDINATES_TABLE_X_COLUMN),resultSet.getLong(DatabaseHandler.COORDINATES_TABLE_Y_COLUMN));        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseHandler.GROUP_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        int studentsCount = resultSet.getInt(DatabaseHandler.GROUP_TABLE_STUDENTS_COUNT_COLUMN);
        int expelledStudents = resultSet.getInt(DatabaseHandler.GROUP_TABLE_EXPELLED_STUDENTS_COLUMN);
        int averageMark = resultSet.getInt(DatabaseHandler.GROUP_TABLE_AVERAGE_MARK_COLUMN);
        FormOfEducation formOfEducation = FormOfEducation.valueOf(resultSet.getString(DatabaseHandler.GROUP_TABLE_FORM_OF_EDUCATION_COLUMN));
        Person person = new Person(resultSet.getString(DatabaseHandler.PERSON_TABLE_N_COLUMN), resultSet.getLong(DatabaseHandler.GROUP_TABLE_WEIGHT_COLUMN),
                Color.valueOf(resultSet.getString(DatabaseHandler.GROUP_TABLE_HAIR_COLOR_COLUMN)),
                Country.valueOf(resultSet.getString(DatabaseHandler.GROUP_TABLE_NATIONALITY_COLUMN)), new Location ( resultSet.getDouble(DatabaseHandler.LOCATION_X_TABLE_STUDY_GROUP_ID_COLUMN),
                resultSet.getLong(DatabaseHandler.LOCATION_Y_TABLE_STUDY_GROUP_ID_COLUMN),
                resultSet.getDouble(DatabaseHandler.LOCATION_Z_TABLE_STUDY_GROUP_ID_COLUMN),
                resultSet.getString(DatabaseHandler.LOCATION_N_TABLE_STUDY_GROUP_ID_COLUMN)));
        User owner = databaseUserManager.getUserById(resultSet.getLong(DatabaseHandler.GROUP_TABLE_GROUP_OWNER_COLUMN));
        return new StudyGroup(
                id,
                name,
                coordinates,
                creationDate,
                studentsCount,
                expelledStudents,
                averageMark,
                formOfEducation,
                person,
                owner
        );

    }

    /**
     * @return List of Group.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public NavigableSet<StudyGroup> getCollection() throws DatabaseHandlingException {
        NavigableSet<StudyGroup> GroupList = new TreeSet<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = databaseHandler.getPreparedStatement(SELECT_ALL_GROUPS, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                GroupList.add(createStudyGroup(resultSet));
            }
            App.logger.info("Got collection!"); //TODO
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectAllStatement);
        }
        return GroupList;
    }

    public StudyGroup insertGroup(GroupRaw groupRaw, User user) throws DatabaseHandlingException {

        App.logger.info("DatabaseCollectionManager insertGroup");//TODO
        App.logger.info(user.toString() + "\n" + groupRaw.toString());

        StudyGroup studyGroup;
        PreparedStatement preparedInsertStudyGroupStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            LocalDateTime creationTime = LocalDateTime.now();

            preparedInsertStudyGroupStatement = databaseHandler.getPreparedStatement(INSERT_GROUP, true);
            App.logger.info("got prepStatement");//TODO

            preparedInsertStudyGroupStatement.setString(1, groupRaw.getName());
            App.logger.info("set 1");//TODO
            App.logger.info(groupRaw.getCoordinates());//TODO
            App.logger.info(groupRaw.getCoordinates().getX());//TODO
            App.logger.info(groupRaw.getCoordinates().getY());//TODO
            preparedInsertStudyGroupStatement.setDouble(2, groupRaw.getCoordinates().getX());
            App.logger.info("set 2");//TODO
            preparedInsertStudyGroupStatement.setLong(3, groupRaw.getCoordinates().getY());
            App.logger.info("set 3");//TODO
            preparedInsertStudyGroupStatement.setTimestamp(4, Timestamp.valueOf(creationTime));
            App.logger.info("set 4");//TODO
            preparedInsertStudyGroupStatement.setInt(5, groupRaw.getStudentCount());
            App.logger.info("set 5");//TODO
            preparedInsertStudyGroupStatement.setInt(6, groupRaw.getExpelledStudent());
            App.logger.info("set 6");//TODO
            preparedInsertStudyGroupStatement.setInt(7, groupRaw.getAverageMark());
            App.logger.info("set 7");//TODO
            preparedInsertStudyGroupStatement.setString(8, groupRaw.getFormOfEducation().toString());
            App.logger.info("set 8");//TODO
            preparedInsertStudyGroupStatement.setString(9, groupRaw.getPerson().getName());
            App.logger.info("set 9");//TODO
            preparedInsertStudyGroupStatement.setLong(10, groupRaw.getPerson().getWeight());
            App.logger.info("set 10");//TODO
            preparedInsertStudyGroupStatement.setString(11, (String.valueOf(groupRaw.getPerson().getHairColor())));
            App.logger.info("set 11");//TODO
            preparedInsertStudyGroupStatement.setString(12, (String.valueOf(groupRaw.getPerson().getNationality())));
            App.logger.info("set 12");//TODO
            preparedInsertStudyGroupStatement.setDouble(13, groupRaw.getPerson().getLocation().getX());
            App.logger.info("set 13");//TODO
            preparedInsertStudyGroupStatement.setLong(14, groupRaw.getPerson().getLocation().getY());
            App.logger.info("set 14");//TODO
            App.logger.info(groupRaw.getPerson().getLocation().getZ());//TODO
            preparedInsertStudyGroupStatement.setDouble(15, groupRaw.getPerson().getLocation().getZ());
            App.logger.info("set 15");//TODO
            preparedInsertStudyGroupStatement.setString(16, groupRaw.getPerson().getLocation().getName());
            App.logger.info("set 16");//TODO
            preparedInsertStudyGroupStatement.setString(17, user.getUsername());
            App.logger.info("after setting prepStatement params");//TODO
            if (preparedInsertStudyGroupStatement.executeUpdate() == 0) throw new SQLException();
            App.logger.info("updated the statement");//TODO
            ResultSet generatedGroupKeys = preparedInsertStudyGroupStatement.getGeneratedKeys();
            App.logger.info("got generated keys");//TODO

            long studyGroupId;
            App.logger.info("before IF");//TODO

            if (generatedGroupKeys.next()) {
                App.logger.info("in IF");//TODO
                studyGroupId = generatedGroupKeys.getLong("group_id");
            } else throw new SQLException();
            App.logger.info("Выполнен запрос INSERT_GROUP.");

            studyGroup = new StudyGroup(
                    studyGroupId,
                    groupRaw.getName(),
                    groupRaw.getCoordinates(),
                    creationTime,
                    groupRaw.getStudentCount(),
                    groupRaw.getExpelledStudent(),
                    groupRaw.getAverageMark(),
                    groupRaw.getFormOfEducation(),
                    groupRaw.getPerson(),
                    user
            );

            databaseHandler.commit();
            return studyGroup;
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении группы запросов на добавление нового объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertStudyGroupStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * @param groupRaw Group raw.
     * @param groupId  Id of Group.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void updateGroupById(long groupId, GroupRaw groupRaw) throws DatabaseHandlingException {
        PreparedStatement preparedUpdateGroupNameByIdStatement = null;
        PreparedStatement preparedUpdateStudentsCountByIdStatement = null;
        PreparedStatement preparedUpdateExpelledStudentsByIdStatement = null;
        PreparedStatement preparedUpdateAverageMarkByIdStatement = null;
        PreparedStatement preparedUpdateFormOfEducationByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesXByGroupIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesYByGroupIdStatement = null;
        PreparedStatement preparedUpdateNamePersonByIdStatement = null;
        PreparedStatement preparedUpdateWeightPersonByIdStatement = null;
        PreparedStatement preparedUpdateHairColorPersonByIdStatement = null;
        PreparedStatement preparedUpdateNationalityPersonByIdStatement = null;
        PreparedStatement preparedUpdateXLocationByIdStatement = null;
        PreparedStatement preparedUpdateYLocationByIdStatement = null;
        PreparedStatement preparedUpdateZLocationByIdStatement = null;
        PreparedStatement preparedUpdateNameLocationByIdStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            preparedUpdateGroupNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_GROUP_NAME_BY_ID, false);
            preparedUpdateCoordinatesXByGroupIdStatement = databaseHandler.getPreparedStatement(UPDATE_COORDINATESX_BY_GROUP_ID, false);
            preparedUpdateCoordinatesYByGroupIdStatement = databaseHandler.getPreparedStatement(UPDATE_COORDINATESY_BY_GROUP_ID, false);
            preparedUpdateStudentsCountByIdStatement = databaseHandler.getPreparedStatement(UPDATE_STUDENTS_COUNT_BY_ID, false);
            preparedUpdateExpelledStudentsByIdStatement = databaseHandler.getPreparedStatement(UPDATE_GROUP_EXPELLED_STUDENTS_BY_ID, false);
            preparedUpdateAverageMarkByIdStatement = databaseHandler.getPreparedStatement(UPDATE_GROUP_AVERAGE_MARK_BY_ID, false);
            preparedUpdateFormOfEducationByIdStatement = databaseHandler.getPreparedStatement(UPDATE_GROUP_FORM_OF_EDUCATION_BY_ID, false);
             preparedUpdateNamePersonByIdStatement = databaseHandler.getPreparedStatement(UPDATE_PERSON_TABLE_NAME_BY_ID, false);
             preparedUpdateWeightPersonByIdStatement = databaseHandler.getPreparedStatement(UPDATE_WEIGHT_BY_ID, false);
             preparedUpdateHairColorPersonByIdStatement = databaseHandler.getPreparedStatement(UPDATE_HAIR_COLOR_BY_ID, false);
             preparedUpdateNationalityPersonByIdStatement = databaseHandler.getPreparedStatement(UPDATE_NATIONALITY_BY_ID, false);
             preparedUpdateXLocationByIdStatement = databaseHandler.getPreparedStatement(UPDATE_LOCATION_X_BY_ID, false);
             preparedUpdateYLocationByIdStatement = databaseHandler.getPreparedStatement(UPDATE_LOCATION_Y_BY_ID, false);
             preparedUpdateZLocationByIdStatement = databaseHandler.getPreparedStatement(UPDATE_LOCATION_Z_BY_ID, false);
             preparedUpdateNameLocationByIdStatement =databaseHandler.getPreparedStatement(UPDATE_LOCATION_NAME_BY_ID, false);
            if (groupRaw.getName() != null) {
                preparedUpdateGroupNameByIdStatement.setString(1, groupRaw.getName());
                preparedUpdateGroupNameByIdStatement.setLong(2, groupId);
                if (preparedUpdateGroupNameByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_GROUP_NAME_BY_ID.");
            }
            if (groupRaw.getCoordinates() != null) {
                preparedUpdateCoordinatesXByGroupIdStatement.setDouble(1, groupRaw.getCoordinates().getX());
                preparedUpdateCoordinatesXByGroupIdStatement.setLong(2, groupId);
                preparedUpdateCoordinatesYByGroupIdStatement.setLong(1, groupRaw.getCoordinates().getY());
                preparedUpdateCoordinatesYByGroupIdStatement.setLong(2, groupId);
                if (preparedUpdateCoordinatesXByGroupIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_XCOORDINATES_BY_GROUP_ID.");
                if (preparedUpdateCoordinatesYByGroupIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_YCOORDINATES_BY_GROUP_ID.");
            }
            if (groupRaw.getStudentCount() != -1) {
                preparedUpdateStudentsCountByIdStatement.setInt(1, groupRaw.getStudentCount());
                preparedUpdateStudentsCountByIdStatement.setLong(2, groupId);
                if (preparedUpdateStudentsCountByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_STUDENTS_COUNT_BY_ID.");
            }
            if (groupRaw.getExpelledStudent() != -1) {
                preparedUpdateExpelledStudentsByIdStatement.setInt(1, groupRaw.getExpelledStudent());
                preparedUpdateExpelledStudentsByIdStatement.setLong(2, groupId);
                if (preparedUpdateExpelledStudentsByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_EXPELLED_STUDENTS_BY_ID.");
            }
            if (groupRaw.getAverageMark() != -1) {
                preparedUpdateAverageMarkByIdStatement.setInt(1, groupRaw.getAverageMark());
                preparedUpdateAverageMarkByIdStatement.setLong(2, groupId);
                if (preparedUpdateAverageMarkByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_GROUP_AVERAGE_MARK_BY_ID.");
            }
            if (groupRaw.getFormOfEducation() != null) {
                preparedUpdateFormOfEducationByIdStatement.setString(1, groupRaw.getFormOfEducation().toString());
                preparedUpdateFormOfEducationByIdStatement.setLong(2, groupId);
                if (preparedUpdateFormOfEducationByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_GROUP_FORM_OF_EDUCATION_BY_ID.");
            }
            if (groupRaw.getPerson() != null) {
                preparedUpdateNamePersonByIdStatement.setString(1, groupRaw.getPerson().getName());
                preparedUpdateNamePersonByIdStatement.setLong(2, groupId);
                if (preparedUpdateNamePersonByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_NAME_PERSON_BY_ID.");
                preparedUpdateWeightPersonByIdStatement.setLong(1, groupRaw.getPerson().getWeight());
                preparedUpdateWeightPersonByIdStatement.setLong(2, groupId);
                if (preparedUpdateWeightPersonByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_WEIGHT_PERSON_BY_ID.");
                preparedUpdateHairColorPersonByIdStatement.setString(1, String.valueOf(groupRaw.getPerson().getHairColor()));
                preparedUpdateHairColorPersonByIdStatement.setLong(2, groupId);
                if (preparedUpdateHairColorPersonByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_HAIR_COLOR_PERSON_BY_ID.");
                preparedUpdateNationalityPersonByIdStatement.setString(1, String.valueOf(groupRaw.getPerson().getNationality()));
                preparedUpdateNationalityPersonByIdStatement.setLong(2, groupId);
                if (preparedUpdateNationalityPersonByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_NATIONALITY_PERSON_BY_ID.");
                preparedUpdateXLocationByIdStatement.setDouble(1, groupRaw.getPerson().getLocation().getX());
                preparedUpdateXLocationByIdStatement.setLong(2, groupId);
                if (preparedUpdateXLocationByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_X_LOCATION_BY_ID.");
                preparedUpdateYLocationByIdStatement.setLong(1, groupRaw.getPerson().getLocation().getY());
                preparedUpdateYLocationByIdStatement.setLong(2, groupId);
                if (preparedUpdateYLocationByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_Y_LOCATION_BY_ID.");
                preparedUpdateZLocationByIdStatement.setDouble(1, groupRaw.getPerson().getLocation().getZ());
                preparedUpdateZLocationByIdStatement.setLong(2, groupId);
                if (preparedUpdateZLocationByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_Z_LOCATION_BY_ID.");
                preparedUpdateNameLocationByIdStatement.setString(1, groupRaw.getPerson().getLocation().getName());
                preparedUpdateNameLocationByIdStatement.setLong(2, groupId);
                if (preparedUpdateNameLocationByIdStatement.executeUpdate() == 0) throw new SQLException();
                App.logger.info("Выполнен запрос UPDATE_NAME_LOCATION_BY_ID.");

            }

            databaseHandler.commit();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedUpdateGroupNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateCoordinatesXByGroupIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateCoordinatesYByGroupIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateStudentsCountByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateExpelledStudentsByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateAverageMarkByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateFormOfEducationByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateNamePersonByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateWeightPersonByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateHairColorPersonByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateNationalityPersonByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateXLocationByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateYLocationByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateZLocationByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateNameLocationByIdStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * Delete Group by id.
     *
     * @param groupId Id of Group.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void deleteGroupById(long groupId) throws DatabaseHandlingException {

        PreparedStatement preparedDeletePersonByIdStatement = null;
        try {
            preparedDeletePersonByIdStatement = databaseHandler.getPreparedStatement(DELETE_GROUP_BY_ID, false);
            preparedDeletePersonByIdStatement.setLong(1, groupId);
            if (preparedDeletePersonByIdStatement.executeUpdate() == 0) Outputer.println(3);
            App.logger.info("Выполнен запрос DELETE_GROUP_BY_ID.");
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса DELETE_GROUP_BY_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedDeletePersonByIdStatement);
        }
    }

    /**
     * Checks Group user id.
     *
     * @param groupId Id of Group.
     * @param user Owner of group.
     * @throws DatabaseHandlingException When there's exception inside.
     * @return Is everything ok.
     */
    public boolean checkGroupUserId(long groupId, User user) throws DatabaseHandlingException {
        PreparedStatement preparedSelectGroupByIdAndUserIdStatement = null;
        try {
            preparedSelectGroupByIdAndUserIdStatement = databaseHandler.getPreparedStatement(SELECT_GROUP_BY_ID_AND_USER_ID, false);
            preparedSelectGroupByIdAndUserIdStatement.setLong(1, groupId);
            ResultSet resultSet = preparedSelectGroupByIdAndUserIdStatement.executeQuery();
            App.logger.info("Выполнен запрос SELECT_GROUP_BY_ID_AND_USER_ID.");
            return resultSet.next();
        } catch (SQLException exception) {
            App.logger.error("Произошла ошибка при выполнении запроса SELECT_GROUP_BY_ID_AND_USER_ID!");
            App.logger.error(exception.toString() + "\n" + SELECT_GROUP_BY_ID_AND_USER_ID + "\n" + exception.getMessage() + "\n" + SELECT_GROUP_BY_ID_AND_USER_ID + "\n" + exception.getSQLState() + "\n" + SELECT_GROUP_BY_ID_AND_USER_ID + "\n" + exception.getErrorCode());

            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectGroupByIdAndUserIdStatement);
        }
    }

    /**
     * Clear the collection.
     *
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void clearCollection() throws DatabaseHandlingException {
        NavigableSet<StudyGroup> groupList = getCollection();
        for (StudyGroup studyGroup : groupList) {
            deleteGroupById(studyGroup.getId());
        }
    }
}