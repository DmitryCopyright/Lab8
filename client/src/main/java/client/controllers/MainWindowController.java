package client.controllers;


import client.AppClient;
import client.Client;
import client.controllers.tools.ObservableResourceFactory;
import client.utility.OutputerUI;
import common.data.Country;
import common.data.FormOfEducation;
import common.data.StudyGroup;
import common.interaction.GroupRaw;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.table.TableFilter;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Main window controller.
 */
public class MainWindowController {
    public static final String LOGIN_COMMAND_NAME = "login";
    public static final String REGISTER_COMMAND_NAME = "register";
    public static final String REFRESH_COMMAND_NAME = "refresh";
    public static final String INFO_COMMAND_NAME = "info";
    public static final String ADD_COMMAND_NAME = "add";
    public static final String UPDATE_COMMAND_NAME = "update";
    public static final String REMOVE_COMMAND_NAME = "remove_by_id";
    public static final String CLEAR_COMMAND_NAME = "clear";
    public static final String EXIT_COMMAND_NAME = "exit";
    public static final String ADD_IF_MIN_COMMAND_NAME = "add_if_min";
    public static final String REMOVE_GREATER_COMMAND_NAME = "remove_greater";
    public static final String HISTORY_COMMAND_NAME = "history";
    public static final String SUM_OF_STUDENTS_COUNT_COMMAND_NAME = "sum_of_students_count";
    private final long RANDOM_SEED = 1821L;
    private final Duration ANIMATION_DURATION = Duration.millis(800);
    private final double MAX_SIZE = 250;

    @FXML
    private TableView<StudyGroup> studyGroupTable;
    @FXML
    private TableColumn<StudyGroup, Long> idColumn;
    @FXML
    private TableColumn<StudyGroup, String> ownerColumn;
    @FXML
    private TableColumn<StudyGroup, LocalDateTime> creationDateColumn;
    @FXML
    private TableColumn<StudyGroup, String> nameColumn;
    @FXML
    private TableColumn<StudyGroup, Integer> studentsCountColumn;
    @FXML
    private TableColumn<StudyGroup, Long> coordinatesXColumn;
    @FXML
    private TableColumn<StudyGroup, Long> coordinatesYColumn;
    @FXML
    private TableColumn<StudyGroup, Integer> expelledStudentsColumn;
    @FXML
    private TableColumn<StudyGroup, Integer> averageMarkColumn;
    @FXML
    private TableColumn<StudyGroup, FormOfEducation> formOfEducationColumn;
    @FXML
    private TableColumn<StudyGroup, String> personNameColumn;
    @FXML
    private TableColumn<StudyGroup, Long> personWeightColumn;
    @FXML
    private TableColumn<StudyGroup, Color> hairColorColumn;
    @FXML
    private TableColumn<StudyGroup, Country> nationalityColumn;
    @FXML
    private TableColumn<StudyGroup, Double> xLocationColumn;
    @FXML
    private TableColumn<StudyGroup, Long> yLocationColumn;
    @FXML
    private TableColumn<StudyGroup, Double> zLocationColumn;
    @FXML
    private TableColumn<StudyGroup, String> locationNameColumn;
    @FXML
    private AnchorPane canvasPane;
    @FXML
    private Tab tableTab;
    @FXML
    private Tab canvasTab;
    @FXML
    private Button infoButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button sumOfStudentsButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Tooltip infoButtonTooltip;
    @FXML
    private Tooltip addButtonTooltip;
    @FXML
    private Tooltip updateButtonTooltip;
    @FXML
    private Tooltip removeButtonTooltip;
    @FXML
    private Tooltip clearButtonTooltip;
    @FXML
    private Tooltip executeScriptButtonTooltip;
    @FXML
    private Tooltip historyButtonTooltip;
    @FXML
    private Tooltip sumOfStudentsButtonTooltip;
    @FXML
    private Tooltip refreshButtonTooltip;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Label usernameLabel;

    private Client client;
    private Stage askStage;
    private Stage primaryStage;
    private FileChooser fileChooser;
    private AskWindowController askWindowController;
    private Map<String, Color> userColorMap;
    private Map<Shape, Long> shapeMap;
    private Map<Long, Text> textMap;
    private Shape prevClicked;
    private Color prevColor;
    private Random randomGenerator;
    private ObservableResourceFactory resourceFactory;
    private Map<String, Locale> localeMap;

    /**
     * Initialize main window.
     */
    public void initialize() {
        initializeTable();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        userColorMap = new HashMap<>();
        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        randomGenerator = new Random(RANDOM_SEED);
        localeMap = new HashMap<>();
        localeMap.put("English", new Locale("en", "NZ"));
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Deutsche", new Locale("de", "DE"));
        localeMap.put("Dansk", new Locale("da", "DK"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
    }

    /**
     * Initialize table.
     */
    private void initializeTable() {
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        ownerColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getUsername()));
        creationDateColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        studentsCountColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getStudentsCount()));
        coordinatesXColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper(cellData.getValue().getCoordinates().getX()));
        coordinatesYColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        expelledStudentsColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getExpelledStudents()));
        averageMarkColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getAverageMark()));
        formOfEducationColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getFormOfEducation()));
        personNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getName()));
        personWeightColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getWeight()));
        hairColorColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper(cellData.getValue().getPerson().getHairColor()));
        nationalityColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getNationality()));
        xLocationColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getLocation().getX()));
        yLocationColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getLocation().getY()));
        zLocationColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getLocation().getX()));
        locationNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPerson().getLocation().getName()));
    }

    /**
     * Bind gui language.
     */
    private void bindGuiLanguage() {
        resourceFactory.setResources(ResourceBundle.getBundle
                (AppClient.BUNDLE, localeMap.get(languageComboBox.getSelectionModel().getSelectedItem())));

        idColumn.textProperty().bind(resourceFactory.getStringBinding("IdColumn"));
        ownerColumn.textProperty().bind(resourceFactory.getStringBinding("OwnerColumn"));
        creationDateColumn.textProperty().bind(resourceFactory.getStringBinding("CreationDateColumn"));
        nameColumn.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        coordinatesXColumn.textProperty().bind(resourceFactory.getStringBinding("CoordinatesXColumn"));
        coordinatesYColumn.textProperty().bind(resourceFactory.getStringBinding("CoordinatesYColumn"));
        studentsCountColumn.textProperty().bind(resourceFactory.getStringBinding("StudentsCountColumn"));
        expelledStudentsColumn.textProperty().bind(resourceFactory.getStringBinding("ExpelledStudentColumn"));
        averageMarkColumn.textProperty().bind(resourceFactory.getStringBinding("AverageMarkColumn"));
        formOfEducationColumn.textProperty().bind(resourceFactory.getStringBinding("FormOfEducationColumn"));
        personNameColumn.textProperty().bind(resourceFactory.getStringBinding("PersonNameColumn"));
        personWeightColumn.textProperty().bind(resourceFactory.getStringBinding("PersonWeightColumn"));
        hairColorColumn.textProperty().bind(resourceFactory.getStringBinding("HairColorColumn"));
        nationalityColumn.textProperty().bind(resourceFactory.getStringBinding("NationalityColumn"));
        xLocationColumn.textProperty().bind(resourceFactory.getStringBinding("XLocationColumn"));
        yLocationColumn.textProperty().bind(resourceFactory.getStringBinding("YLocationColumn"));
        zLocationColumn.textProperty().bind(resourceFactory.getStringBinding("ZLocationColumn"));
        locationNameColumn.textProperty().bind(resourceFactory.getStringBinding("NameLocationColumn"));// вот здесь было на месте key locationNameColumn вместо NameLocationColumn

        tableTab.textProperty().bind(resourceFactory.getStringBinding("TableTab"));
        canvasTab.textProperty().bind(resourceFactory.getStringBinding("CanvasTab"));

        infoButton.textProperty().bind(resourceFactory.getStringBinding("InfoButton"));
        addButton.textProperty().bind(resourceFactory.getStringBinding("AddButton"));
        updateButton.textProperty().bind(resourceFactory.getStringBinding("UpdateButton"));
        removeButton.textProperty().bind(resourceFactory.getStringBinding("RemoveButton"));
        clearButton.textProperty().bind(resourceFactory.getStringBinding("ClearButton"));
        executeScriptButton.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButton"));
        historyButton.textProperty().bind(resourceFactory.getStringBinding("HistoryButton"));
        sumOfStudentsButton.textProperty().bind(resourceFactory.getStringBinding("SumOfStudentsButton"));
        refreshButton.textProperty().bind(resourceFactory.getStringBinding("RefreshButton"));

        infoButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("InfoButtonTooltip"));
        addButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("AddButtonTooltip"));
        updateButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("UpdateButtonTooltip"));
        removeButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RemoveButtonTooltip"));
        clearButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("ClearButtonTooltip"));
        executeScriptButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButtonTooltip"));
        historyButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("HistoryButtonTooltip"));
        sumOfStudentsButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("SumOfStudentsTooltip"));
        refreshButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RefreshButtonTooltip"));
    }

    /**
     * Refresh button on action.
     */
    @FXML
    public void refreshButtonOnAction() {
        requestAction(REFRESH_COMMAND_NAME);
    }

    /**
     * Info button on action.
     */
    @FXML
    private void infoButtonOnAction() {
        requestAction(INFO_COMMAND_NAME);
    }

    /**
     * Add button on action.
     */
    @FXML
    private void addButtonOnAction() {
        askWindowController.clearGroup();
        askStage.showAndWait();
        GroupRaw groupRaw = askWindowController.getAndClear();
        if (groupRaw != null) requestAction(ADD_COMMAND_NAME, "", groupRaw);
    }

    /**
     * Update button on action.
     */
    @FXML
    private void updateButtonOnAction() {
        if (!studyGroupTable.getSelectionModel().isEmpty()) {
            long id = studyGroupTable.getSelectionModel().getSelectedItem().getId();
            askWindowController.setGroup(studyGroupTable.getSelectionModel().getSelectedItem());
            askStage.showAndWait();
            GroupRaw groupRaw = askWindowController.getAndClear();
            if (groupRaw != null) requestAction(UPDATE_COMMAND_NAME, id + "", groupRaw);
        } else OutputerUI.error("UpdateButtonSelectionException");

    }

    /**
     * Remove button on action.
     */
    @FXML
    private void removeButtonOnAction() {
        if (!studyGroupTable.getSelectionModel().isEmpty())
            requestAction(REMOVE_COMMAND_NAME,
                    studyGroupTable.getSelectionModel().getSelectedItem().getId().toString(), null);
        else OutputerUI.error("RemoveButtonSelectionException");
    }

    /**
     * Clear button on action.
     */
    @FXML
    private void clearButtonOnAction() {
        requestAction(CLEAR_COMMAND_NAME);
    }

    /**
     * Execute script button on action.
     */
    @FXML
    private void executeScriptButtonOnAction() {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) return;
        if (client.processScriptToServer(selectedFile)) Platform.exit();
        else refreshButtonOnAction();
    }



    /**
     * History button on action.
     */
    @FXML
    private void historyButtonOnAction() {
        requestAction(HISTORY_COMMAND_NAME);
    }

    /**
     * Sum of students count button on action.
     */
    @FXML
    private void sumOfStudentsCountButtonOnAction() {
        requestAction(SUM_OF_STUDENTS_COUNT_COMMAND_NAME);
    }

    /**
     * Request action.
     */
    private void requestAction(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        NavigableSet<StudyGroup> responsedGroups = client.processRequestToServer(commandName, commandStringArgument,
                commandObjectArgument);
        if (responsedGroups != null) {
            ObservableList<StudyGroup> groupList = FXCollections.observableArrayList(responsedGroups);
            studyGroupTable.setItems(groupList);
            TableFilter.forTableView(studyGroupTable).apply();
            studyGroupTable.getSelectionModel().clearSelection();
            refreshCanvas();
        }
    }

    /**
     * Binds request action.
     */
    private void requestAction(String commandName) {
        requestAction(commandName, "", null);
    }

    /**
     * Refreshes canvas.
     */
    private void refreshCanvas() {
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> canvasPane.getChildren().remove(s));
        textMap.clear();
        for (StudyGroup studyGroup : studyGroupTable.getItems()) {
            if (!userColorMap.containsKey(studyGroup.getOwner().getUsername()))
                userColorMap.put(studyGroup.getOwner().getUsername(),
                        Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));

            int size = (int) Math.min(studyGroup.getStudentsCount(), MAX_SIZE);

            Shape circleObject = new Circle(size, userColorMap.get(studyGroup.getOwner().getUsername()));
            circleObject.setOnMouseClicked(this::shapeOnMouseClicked);
            circleObject.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(studyGroup.getCoordinates().getX()));
            circleObject.translateYProperty().bind(canvasPane.heightProperty().divide(2).subtract(studyGroup.getCoordinates().getY()));

            Text textObject = new Text(studyGroup.getId().toString());
            textObject.setOnMouseClicked(circleObject::fireEvent);
            textObject.setFont(Font.font(size / 3));
            textObject.setFill(userColorMap.get(studyGroup.getOwner().getUsername()).darker());
            textObject.translateXProperty().bind(circleObject.translateXProperty().subtract(textObject.getLayoutBounds().getWidth() / 2));
            textObject.translateYProperty().bind(circleObject.translateYProperty().add(textObject.getLayoutBounds().getHeight() / 4));

            canvasPane.getChildren().add(circleObject);
            canvasPane.getChildren().add(textObject);
            shapeMap.put(circleObject, studyGroup.getId());
            textMap.put(studyGroup.getId(), textObject);

            ScaleTransition circleAnimation = new ScaleTransition(ANIMATION_DURATION, circleObject);
            ScaleTransition textAnimation = new ScaleTransition(ANIMATION_DURATION, textObject);
            circleAnimation.setFromX(0);
            circleAnimation.setToX(1);
            circleAnimation.setFromY(0);
            circleAnimation.setToY(1);
            textAnimation.setFromX(0);
            textAnimation.setToX(1);
            textAnimation.setFromY(0);
            textAnimation.setToY(1);
            circleAnimation.play();
            textAnimation.play();
        }
    }

    /**
     * Shape on mouse clicked.
     */
    private void shapeOnMouseClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        long id = shapeMap.get(shape);
        for (StudyGroup studyGroup : studyGroupTable.getItems()) {
            if (studyGroup.getId() == id) {
                studyGroupTable.getSelectionModel().select(studyGroup);
                break;
            }
        }
        if (prevClicked != null) {
            prevClicked.setFill(prevColor);
        }
        prevClicked = shape;
        prevColor = (Color) shape.getFill();
        shape.setFill(prevColor.brighter());
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAskWindowController(AskWindowController askWindowController) {
        this.askWindowController = askWindowController;
    }

    public void initLangs(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
                languageComboBox.getSelectionModel().select(localeName);
        }
        if (languageComboBox.getSelectionModel().getSelectedItem().isEmpty())
            languageComboBox.getSelectionModel().selectFirst();
        languageComboBox.setOnAction((event) ->
                resourceFactory.setResources(ResourceBundle.getBundle
                        (AppClient.BUNDLE, localeMap.get(languageComboBox.getValue()))));
        bindGuiLanguage();
    }
}