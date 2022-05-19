package common.interaction;

import common.data.*;

import java.io.Serializable;

/**
 * Class for get Group value.
 */
public class GroupRaw implements Serializable {
    private String name;
    private Coordinates coordinates;
    private int studentCount;
    private int expelledStudents;
    private int averageMark;
    private FormOfEducation formOfEducation;
    private Person person;

    public GroupRaw(String name, Coordinates coordinates, int studentCount, int expelledStudents,
                    int averageMark, FormOfEducation formOfEducation, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.studentCount = studentCount;
        this.expelledStudents = expelledStudents;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.person = person;
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
     * @return Student of the group.
     */
    public int getStudentCount() {
        return studentCount;
    }

    /**
     * @return Expelled student of the group.
     */
    public int getExpelledStudent() {
        return expelledStudents;
    }

    /**
     * @return Average Mark of the group.
     */
    public int getAverageMark() {
        return averageMark;
    }


    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }


    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        String info = "";
        info += "'Сырая' группа";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Количество студентов: " + studentCount;
        info += "\n Количество отчисленных студентов " + expelledStudents;
        info += "\n Средний балл в группе: " + averageMark;
        info += "\n Форма обучения: " + formOfEducation;
        info += "\n Админ: " + person;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + studentCount + expelledStudents + averageMark +
                formOfEducation.hashCode() + person.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof StudyGroup) {
            StudyGroup studyGroupObj = (StudyGroup) obj;
            return name.equals(studyGroupObj.getName()) && coordinates.equals(studyGroupObj.getCoordinates()) &&
                    (studentCount == studyGroupObj.getStudentsCount()) && (expelledStudents == studyGroupObj.getExpelledStudents()) &&
                    (averageMark == studyGroupObj.getAverageMark()) && (formOfEducation == studyGroupObj.getFormOfEducation()) &&
                    person.equals(studyGroupObj.getPerson());
        }
        return false;
    }
}