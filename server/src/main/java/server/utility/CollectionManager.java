package server.utility;

import common.data.FormOfEducation;
import common.data.StudyGroup;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.DatabaseHandlingException;
import common.utility.Outputer;
import server.App;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Operates the collection itself.
 */
public class CollectionManager {
    private NavigableSet<StudyGroup> studyGroupCollection;
    private LocalDateTime lastInitTime;
    private DatabaseCollectionManager databaseCollectionManager;

    public CollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        this.databaseCollectionManager = databaseCollectionManager;

        loadCollection();
    }
    public NavigableSet<StudyGroup> getCollection() {
        return studyGroupCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }


    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return studyGroupCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return studyGroupCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public StudyGroup getFirst() {
        return studyGroupCollection.stream().findFirst().orElse(null);
    }

    /**
     * @param id ID of the Study group.
     * @return A Study Group by his ID or null if Study Group isn't found.
     */
    public StudyGroup getById(Long id) {
        return studyGroupCollection.stream().filter(studyGroup -> studyGroup.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * @param studyGroupToFind A study group who's value will be found.
     * @return A study group by his value or null if study group isn't found.
     */
    public StudyGroup getByValue(StudyGroup studyGroupToFind) {
        return studyGroupCollection.stream().filter(studyGroup -> studyGroup.equals(studyGroupToFind)).findFirst().orElse(null);
    }

    /**
     * @return Sum of all students count or 0 if collection is empty.
     */
    public int getSumOfStudentsCount() {
        return studyGroupCollection.stream()
                .reduce(0, (sum, p) -> sum += p.getStudentsCount(), Integer::sum);
    }

    /**
     * @return Collection content or corresponding string if collection is empty.
     */
    public String showCollection() {
        if (studyGroupCollection.isEmpty()) return "Коллекция пуста!";
        return studyGroupCollection.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    /**
     * @return Max form of education.
     * @throws CollectionIsEmptyException If collection is empty.
     */
    public String maxByFormOfEducation() throws CollectionIsEmptyException {
        if (studyGroupCollection.isEmpty()) throw new CollectionIsEmptyException();

        FormOfEducation maxFormOfEducation = studyGroupCollection.stream().map(studyGroup -> studyGroup.getFormOfEducation())
                .max(Enum::compareTo).get();
        return studyGroupCollection.stream()
                .filter(studyGroup -> studyGroup.getFormOfEducation().equals(maxFormOfEducation)).findFirst().get().toString();
    }


    /**
     * Remove study Group greater than the selected one.
     *
     * @param studyGroupToCompare A Study Group to compare with.
     */
    public void removeGreater(StudyGroup studyGroupToCompare) {
        studyGroupCollection.removeIf(studyGroup -> studyGroup.compareTo(studyGroupToCompare) > 0);
    }

    /**
     * Adds a new Study Group to collection.
     *
     * @param studyGroup A studyGroup to add.
     */
    public void addToCollection(StudyGroup studyGroup) {
        App.logger.info("*Collection Manager addToCollection*");//TODO
        App.logger.info(studyGroup.toString());

        studyGroupCollection.add(studyGroup);

        App.logger.info(studyGroupCollection.size()+" "+ Arrays.toString(studyGroupCollection.toArray()));
    }

    /**
     * Removes a new studyGroup to collection.
     *
     * @param studyGroup A studyGroup to remove.
     */
    public void removeFromCollection(StudyGroup studyGroup) {
        studyGroupCollection.remove(studyGroup);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        studyGroupCollection.clear();
    }


    public NavigableSet<StudyGroup> getGreater(StudyGroup studyGroupToCompare) {
        return studyGroupCollection.stream().filter(studyGroup -> studyGroup.compareTo(studyGroupToCompare) > 0).collect(
                TreeSet::new,
                TreeSet::add,
                TreeSet::addAll
        );
    }
    /**
     * Loads the collection from file.
     */
    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        try {
            studyGroupCollection = databaseCollectionManager.getCollection();
            lastInitTime = LocalDateTime.now();
            Outputer.println("Коллекция загружена.");
            App.logger.info("Коллекция загружена.");
        } catch (DatabaseHandlingException exception) {
            studyGroupCollection = new TreeSet<>();
            Outputer.printerror("Коллекция не может быть загружена!");
            App.logger.error("Коллекция не может быть загружена!");
        }
    }
}
