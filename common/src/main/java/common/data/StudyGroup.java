package common.data;

import common.interaction.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Main character. Is stored in the collection.
 */
public class StudyGroup implements Comparable<StudyGroup>, Serializable {


    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private int studentsCount;
    private int expelledStudents;
    private int averageMark;
    private FormOfEducation formOfEducation;
    private Person person;
    private User owner;

    public StudyGroup(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, int studentsCount,
                      int expelledStudents, int averageMark, FormOfEducation formOfEducation, Person person, User owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.person = person;
        this.owner = owner;
    }

    /**
     * @return ID of the group.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Coordinates of the group.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Creation date of the group.
     */
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return Number of students in the group.
     */
    public int getStudentsCount() {
        return studentsCount;
    }

    /**
     * @return Number of expelled students in the group.
     */
    public int getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * @return Average mark in the group.
     */
    public int getAverageMark() {
        return averageMark;
    }

    /**
     * @return Form of education in the group.
     */
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    /**
     * @return Admin in the group.
     */
    public Person getPerson() {
        return person;
    }
    public User getOwner() {
        return owner;
    }

    @Override
    public int compareTo(StudyGroup studyGroupObj) {
        return id.compareTo(studyGroupObj.getId());
    }


    @Override
    public String toString() {
        String info = "";
        info += "Номер группы:" + id;
        info += " [" + owner.getUsername() + " " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                " в " + creationDate.format(DateTimeFormatter.ofPattern("HH:mm")) + "]";
        info += "\n Название группы:: " + name;
        info += "\n Местоположениие: " + coordinates;
        info += "\n Количество студентов: " + studentsCount;
        info += "\n Количество отчисленных студентов: " + expelledStudents;
        info += "\n Средний балл: " + averageMark;
        info += "\n Форма обучения: " + formOfEducation;
        info += "\n Админ: " + person;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() +  studentsCount + expelledStudents + averageMark +
                formOfEducation.hashCode() + person.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof StudyGroup) {
            StudyGroup studyGroupObj = (StudyGroup) obj;
            return name.equals(studyGroupObj.getName()) && coordinates.equals(studyGroupObj.getCoordinates()) &&
                    (studentsCount == studyGroupObj.getStudentsCount()) && (expelledStudents == studyGroupObj.getExpelledStudents()) &&
                    (averageMark == studyGroupObj.getAverageMark()) && (formOfEducation == studyGroupObj.getFormOfEducation()) &&
                    person.equals(studyGroupObj.getPerson());
        }
        return false;
    }
}