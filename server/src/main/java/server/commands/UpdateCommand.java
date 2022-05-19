package server.commands;

import common.data.*;
import common.exceptions.*;
import common.interaction.GroupRaw;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update", "<ID> {element}", "update an element in the collection");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            StudyGroup oldStudyGroup = collectionManager.getById(id);
            if (oldStudyGroup == null) throw new GroupNotFoundException();
            if (!oldStudyGroup.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkGroupUserId(oldStudyGroup.getId(), user)) throw new ManualDatabaseEditException();
            GroupRaw groupRaw = (GroupRaw) objectArgument;


            String name = groupRaw.getName() == null ? oldStudyGroup.getName() : groupRaw.getName();
            Coordinates coordinates = groupRaw.getCoordinates() == null ? oldStudyGroup.getCoordinates() : groupRaw.getCoordinates();
            LocalDateTime creationDate = oldStudyGroup.getCreationDate();
            int studentCount = groupRaw.getStudentCount() == -1 ? oldStudyGroup.getStudentsCount() : groupRaw.getStudentCount();
            int expelledStudent = groupRaw.getExpelledStudent() == -1 ? oldStudyGroup.getExpelledStudents() : groupRaw.getExpelledStudent();
            int averageMark = groupRaw.getAverageMark() == -1 ? oldStudyGroup.getAverageMark() : groupRaw.getAverageMark();
            FormOfEducation formOfEducation = groupRaw.getFormOfEducation() == null ? oldStudyGroup.getFormOfEducation() : groupRaw.getFormOfEducation();
            Person person = groupRaw.getPerson() == null ? oldStudyGroup.getPerson() : groupRaw.getPerson();

            collectionManager.removeFromCollection(oldStudyGroup);
            collectionManager.addToCollection(new StudyGroup(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    studentCount,
                    expelledStudent,
                    averageMark,
                    formOfEducation,
                    person,
                    user
            ));
            ResponseOutputer.appendln("GroupChange");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
            ResponseOutputer.appendargs(getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("CollectionIsEmptyException");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("IdMustBeNumberException");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("IdOfUpdateException");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("ClientObjectException");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("DatabaseHandlingException");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("NoughRightsException");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("ManualDatabaseException");
        }
        return false;
    }
}