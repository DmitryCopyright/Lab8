package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
/**
 * Command 'sum_of_students_count'. Prints the sum of students count of all groups.
 */
public class SumOfStudentsCountCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SumOfStudentsCountCommand(CollectionManager collectionManager) {
        super("sum_of_students_count", "", "show sum of students count of all elements");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            int sum_of_students_count = collectionManager.getSumOfStudentsCount();
            if (sum_of_students_count == 0) throw new CollectionIsEmptyException();
            ResponseOutputer.appendln("SumOfStudentsCount:" + sum_of_students_count);
            ResponseOutputer.appendargs(String.valueOf(sum_of_students_count));
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
            ResponseOutputer.appendargs(getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("CollectionIsEmptyException");
        }
        return false;
    }
}