package client.controllers;

import client.controllers.tools.ObservableResourceFactory;
import client.utility.OutputerUI;
import common.data.*;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.GroupRaw;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Sets the controller window.
 */
public class AskWindowController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label coordinatesXLabel;
    @FXML
    private Label coordinatesYLabel;
    @FXML
    private Label studentsCountLabel;
    @FXML
    private Label expelledStudentsLabel;
    @FXML
    private Label averageMarkFieldLabel;
    @FXML
    private Label formOfEducationLabel;
    @FXML
    private Label personNameFieldLabel;
    @FXML
    private Label personWeightLabel;
    @FXML
    private Label personHairColorLabel;
    @FXML
    private Label personNationalityLabel;
    @FXML
    private Label locationNameLabel;
    @FXML
    private Label coordinatesXLocationLabel;
    @FXML
    private Label coordinatesYLocationLabel;
    @FXML
    private Label coordinatesZLocationLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField coordinatesXField;
    @FXML
    private TextField coordinatesYField;
    @FXML
    private TextField studentsCountField;
    @FXML
    private TextField expelledStudentsField;
    @FXML
    private TextField averageMarkField;
    @FXML
    private ComboBox<FormOfEducation> formOfEducationBox;
    @FXML
    private TextField personNameField;
    @FXML
    private TextField personWeight;
    @FXML
    private ComboBox<Color> personHairColorBox;
    @FXML
    private ComboBox<Country> personNationalityBox;
    @FXML
    private TextField locationName;
    @FXML
    private TextField coordinatesXLocation;
    @FXML
    private TextField coordinatesYLocation;
    @FXML
    private TextField coordinatesZLocation;
    @FXML
    private Button enterButton;

    private Stage askStage;
    private GroupRaw resultGroup;
    private ObservableResourceFactory resourceFactory;

    /**
     * Initialize ask window.
     */
    public void initialize() {
        personHairColorBox.setItems(FXCollections.observableArrayList(Color.values()));
        personNationalityBox.setItems(FXCollections.observableArrayList(Country.values()));
        formOfEducationBox.setItems(FXCollections.observableArrayList(FormOfEducation.values()));
    }

    /**
     * Enter button on action.
     */
    @FXML
    private void enterButtonOnAction() {
        try {
            resultGroup = new GroupRaw(
                    convertName(),
                    new Coordinates(
                            convertCoordinatesxX(),
                            convertCoordinatesxY()
                    ),
                    convertStudentsCount(),
                    convertExpelledStudents(),
                    convertAverageMark(),
                    formOfEducationBox.getValue(),
                    new Person(
                            convertPersonName(),
                            convertPersonWeight(),
                            personHairColorBox.getValue(),
                            personNationalityBox.getValue(),
                            new Location(
                                    convertCoordinatesXLocation(),
                                    convertCoordinatesYLocation(),
                                    convertCoordinatesZLocation(),
                                    convertLocationName()
                            )
                    )
            );
            askStage.close();
        } catch (IllegalArgumentException exception) { /* ? */ }
    }

    /**
     * Binds interface language.
     */
    private void bindGuiLanguage() {
        nameLabel.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        coordinatesXLabel.textProperty().bind(resourceFactory.getStringBinding("CoordinatesXColumn"));
        coordinatesYLabel.textProperty().bind(resourceFactory.getStringBinding("CoordinatesYColumn"));
        studentsCountLabel.textProperty().bind(resourceFactory.getStringBinding("StudentsCountColumn"));
        expelledStudentsLabel.textProperty().bind(resourceFactory.getStringBinding("ExpelledStudentColumn"));
        averageMarkFieldLabel.textProperty().bind(resourceFactory.getStringBinding("AverageMarkColumn"));
        formOfEducationLabel.textProperty().bind(resourceFactory.getStringBinding("FormOfEducationColumn"));
        personNameFieldLabel.textProperty().bind(resourceFactory.getStringBinding("PersonNameColumn"));
        personWeightLabel.textProperty().bind(resourceFactory.getStringBinding("PersonWeightColumn"));
        personHairColorLabel.textProperty().bind(resourceFactory.getStringBinding("PersonWeightColumn"));
        personNationalityLabel.textProperty().bind(resourceFactory.getStringBinding("NationalityColumn"));
        coordinatesXLocationLabel.textProperty().bind(resourceFactory.getStringBinding("XLocationColumn"));
        coordinatesYLocationLabel.textProperty().bind(resourceFactory.getStringBinding("YLocationColumn"));
        coordinatesZLocationLabel.textProperty().bind(resourceFactory.getStringBinding("ZLocationColumn"));
        locationNameLabel.textProperty().bind(resourceFactory.getStringBinding("NameLocationColumn"));
        enterButton.textProperty().bind(resourceFactory.getStringBinding("EnterButton"));
    }

    /**
     * Convert name.
     *
     * @return Name.
     */
    private String convertName() throws IllegalArgumentException {
        String name;
        try {
            name = nameField.getText();
            if (name.equals("")) throw new MustBeNotEmptyException();
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("NameEmptyException");
            throw new IllegalArgumentException();
        }
        return name;
    }

    /**
     * Convert Coordinates X.
     *
     * @return X.
     */
    private long convertCoordinatesxX() throws IllegalArgumentException {
        String strX;
        long x;
        try {
            strX = coordinatesXField.getText();
            x = Long.parseLong(strX);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinatesXFormatException");
            throw new IllegalArgumentException();
        }
        return x;
    }

    /**
     * Convert Coordinates Y.
     *
     * @return Y.
     */
    private Long convertCoordinatesxY() throws IllegalArgumentException {
        String strY;
        long y;
        try {
            strY = coordinatesYField.getText();
            y = Long.parseLong(strY);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinatesYFormatException");
            throw new IllegalArgumentException();
        }
        return y;
    }

    /**
     * Convert students_count.
     *
     * @return students_count.
     */
    private int convertStudentsCount() throws IllegalArgumentException {
        String strStudentsCount;
        int students_count;
        try {
            strStudentsCount = studentsCountField.getText();
            students_count = Integer.parseInt(strStudentsCount);
        } catch (NumberFormatException exception) {
            OutputerUI.error("StudentsCountFormatException");
            throw new IllegalArgumentException();
        }
        return students_count;
    }
    /**
     * Convert expelled_students.
     *
     * @return expelled_students.
     */
    private int convertExpelledStudents() throws IllegalArgumentException {
        String strExpelledStudents;
        int expelled_students;
        try {
                    strExpelledStudents = expelledStudentsField.getText();
            expelled_students = Integer.parseInt(strExpelledStudents);
        } catch (NumberFormatException exception) {
            OutputerUI.error("ExpelledStudentsFormatException");
            throw new IllegalArgumentException();
        }
        return expelled_students;
    }
    /**
     * Convert average_mark.
     *
     * @return average_mark.
     */
    private int convertAverageMark() throws IllegalArgumentException {
        String strAverageMark;
        int averageMark;
        try {
            strAverageMark = averageMarkField.getText();
            averageMark = Integer.parseInt(strAverageMark);
        } catch (NumberFormatException exception) {
            OutputerUI.error("AverageMarkFormatException");
            throw new IllegalArgumentException();
        }
        return averageMark;
    }

    /**
     * Convert chapter name.
     *
     * @return Chapter name.
     */
    private String convertPersonName() throws IllegalArgumentException {
        String personName;
        try {
            personName = personNameField.getText();
            if (personName.equals("")) throw new MustBeNotEmptyException();
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("PersonNameEmptyException");
            throw new IllegalArgumentException();
        }
        return personName;
    }

    /**
     * Convert weight.
     *
     * @return weight.
     */
    private long convertPersonWeight() throws IllegalArgumentException {
        String strPersonWeight;
        long personWeight;
        try {
            strPersonWeight = this.personWeight.getText();
            personWeight = Long.parseLong(strPersonWeight);
        } catch (NumberFormatException exception) {
            OutputerUI.error("PersonWeightFormatException");
            throw new IllegalArgumentException();
        }
        return personWeight;
    }
    /**
     * Convert XLocation.
     *
     * @return XLocation.
     */
    private double convertCoordinatesXLocation() throws IllegalArgumentException {
        String strCoordinatesXLocation;
        double coordinatesXLocation;
        try {
            strCoordinatesXLocation = this.coordinatesXLocation.getText();
            coordinatesXLocation = Long.parseLong(strCoordinatesXLocation);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinaresXFormatException");
            throw new IllegalArgumentException();
        }
        return coordinatesXLocation;
    }

    /**
     * Convert YLocation.
     *
     * @return YLocation.
     */
    private long convertCoordinatesYLocation() throws IllegalArgumentException {
        String strCoordinatesYLocation;
        long coordinatesYLocation;
        try {
            strCoordinatesYLocation = this.coordinatesYLocation.getText();
            coordinatesYLocation = Long.parseLong(strCoordinatesYLocation);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinaresYFormatException");
            throw new IllegalArgumentException();
        }
        return coordinatesYLocation;
    }

    /**
     * Convert ZLocation.
     *
     * @return ZLocation.
     */
    private double convertCoordinatesZLocation() throws IllegalArgumentException {
        String strCoordinatesZLocation;
        long coordinatesZLocation;
        try {
            strCoordinatesZLocation = this.coordinatesZLocation.getText();
            coordinatesZLocation = Long.parseLong(strCoordinatesZLocation);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinaresZFormatException");
            throw new IllegalArgumentException();
        }
        return coordinatesZLocation;
    }

    /**
     * Convert Location name.
     *
     * @return Location name.
     */
    private String convertLocationName() throws IllegalArgumentException {
        String locationName;
        try {
            locationName = personNameField.getText();
            if (locationName.equals("")) throw new MustBeNotEmptyException();
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("LocationNameEmptyException");
            throw new IllegalArgumentException();
        }
        return locationName;
    }

    /**
     * Set StudyGroup.
     *
     * @param studyGroup Group to set.
     */
    public void setGroup(StudyGroup studyGroup) {
        nameField.setText(studyGroup.getName());
        coordinatesXField.setText(studyGroup.getCoordinates().getX() + "");
        coordinatesYField.setText(studyGroup.getCoordinates().getY() + "");
        studentsCountField.setText(studyGroup.getStudentsCount() + "");
        personNameField.setText(studyGroup.getPerson().getName());
        personWeight.setText(studyGroup.getPerson().getWeight() + "");
        coordinatesXLocation.setText(studyGroup.getPerson().getLocation().getX() + "");
        coordinatesYLocation.setText(studyGroup.getPerson().getLocation().getY() + "");
        coordinatesZLocation.setText(studyGroup.getPerson().getLocation().getZ() + "");
        locationName.setText(studyGroup.getPerson().getLocation().getName() + "");
    }

    /**
     * Clear StudyGroup.
     */
    public void clearGroup() {
        nameField.clear();
        coordinatesXField.clear();
        coordinatesYField.clear();
        studentsCountField.clear();
        personNameField.clear();
        personWeight.clear();
        personHairColorBox.setValue(Color.BROWN);
        personNationalityBox.setValue(Country.JAPAN);
        coordinatesXLocation.clear();
        coordinatesYLocation.clear();
        coordinatesZLocation.clear();
        locationName.clear();
    }

    /**
     * Get and clear Group.
     *
     * @return Group to return.
     */
    public GroupRaw getAndClear() {
        GroupRaw groupToReturn = resultGroup;
        resultGroup = null;
        return groupToReturn;
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    /**
     * Init langs.
     *
     * @param resourceFactory Resource factory to set.
     */
    public void initLangs(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        bindGuiLanguage();
    }
}