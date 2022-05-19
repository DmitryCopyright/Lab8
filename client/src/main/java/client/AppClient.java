package client;

import client.controllers.AskWindowController;
import client.controllers.LoginWindowController;
import client.controllers.MainWindowController;
import client.controllers.tools.ObservableResourceFactory;
import client.utility.OutputerUI;
import common.exceptions.NotInDeclaredLimitsException;
import common.exceptions.WrongAmountOfElementsException;
import client.utility.Outputer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main client class. Creates all client instances.
 *
 * @author Volnenko Dmitry.
 */
public class AppClient extends Application {
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";
    public static final String BUNDLE = "client.bundles.gui";

    private static final String APP_TITLE = "Collection Keeper";

    private static String host;
    private static int port;
    private static Scanner userScanner;
    private static Client client;
    private static ObservableResourceFactory resourceFactory;
    private Stage primaryStage;

    public static void main(String[] args) {
        resourceFactory = new ObservableResourceFactory();
        resourceFactory.setResources(ResourceBundle.getBundle(BUNDLE));
        OutputerUI.setResourceFactory(resourceFactory);
        Outputer.setResourceFactory(resourceFactory);

        if (initialize(args)) launch(args);
        else System.exit(0);
    }

    /**
     * Controls initialization.
     */
    private static boolean initialize(String[] args) {
        try {
            if (args.length != 2) throw new WrongAmountOfElementsException();
            host = args[0];
            port = Integer.parseInt(args[1]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(AppClient.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.println("Using", "'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Outputer.printerror("PortMustBeNumber");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("PortMustBeNotNegative");
        }
        return false;
    }

    @Override
    public void start(Stage stage) {
        try {
            this.primaryStage = stage;

            FXMLLoader loginWindowLoader = new FXMLLoader();
            loginWindowLoader.setLocation(getClass().getResource("view/LoginWindow.fxml"));
            Parent loginWindowRootNode = loginWindowLoader.load();
            Scene loginWindowScene = new Scene(loginWindowRootNode);
            LoginWindowController loginWindowController = loginWindowLoader.getController();
            loginWindowController.setApp(this);
            loginWindowController.setClient(client);
            loginWindowController.initLangs(resourceFactory);

            primaryStage.setTitle(APP_TITLE);

            primaryStage.setScene(loginWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception exception) {
            // TODO: Обработать ошибки
            System.out.println(exception);
            exception.printStackTrace();
        }
    }

    @Override
    public void init() {
        userScanner = new Scanner(System.in);
        client = new Client(host, port);

        client.run();
    }

    @Override
    public void stop() {
        client.stop();
        userScanner.close();
    }

    public void setMainWindow() {
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            System.out.println("Загрузили FXMLLoader");
            mainWindowLoader.setLocation(getClass().getResource("view/MainWindow.fxml"));
            System.out.println("Загрузили 1");
            Parent mainWindowRootNode = mainWindowLoader.load();
            System.out.println("Загрузили 2");
            Scene mainWindowScene = new Scene(mainWindowRootNode);
            System.out.println("Загрузили 3");
            MainWindowController mainWindowController = mainWindowLoader.getController();
            System.out.println("Загрузили 4");
            mainWindowController.initLangs(resourceFactory);
            System.out.println("Загрузили 5");
            FXMLLoader askWindowLoader = new FXMLLoader();
            System.out.println("Загрузили 6");
            askWindowLoader.setLocation(getClass().getResource("view/AskWindow.fxml"));
            System.out.println("Загрузили 7");
            Parent askWindowRootNode = askWindowLoader.load();
            System.out.println("Загрузили 8");
            Scene askWindowScene = new Scene(askWindowRootNode);
            System.out.println("Загрузили 9");
            Stage askStage = new Stage();
            System.out.println("Загрузили 10");
            askStage.setTitle(APP_TITLE);
            System.out.println("Загрузили 11");
            askStage.setScene(askWindowScene);
            System.out.println("Загрузили 12");
            askStage.setResizable(false);
            System.out.println("Загрузили 13");
            askStage.initModality(Modality.WINDOW_MODAL);
            System.out.println("Загрузили 14");
            askStage.initOwner(primaryStage);
            System.out.println("Загрузили 15");
            AskWindowController askWindowController = askWindowLoader.getController();
            System.out.println("Загрузили 16");
            askWindowController.setAskStage(askStage);
            System.out.println("Загрузили 17");
            askWindowController.initLangs(resourceFactory);
            System.out.println("Загрузили 18");
            mainWindowController.setClient(client);
            System.out.println("Загрузили 19");
            mainWindowController.setUsername(client.getUsername());
            System.out.println("Загрузили 20");
            mainWindowController.setAskStage(askStage);
            System.out.println("Загрузили 21");
            mainWindowController.setPrimaryStage(primaryStage);
            System.out.println("Загрузили 22");
            mainWindowController.setAskWindowController(askWindowController);
            System.out.println("Загрузили 23");
            mainWindowController.refreshButtonOnAction();
            System.out.println("Загрузили 24");
            primaryStage.setScene(mainWindowScene);
            System.out.println("Загрузили 25");
            primaryStage.setMinWidth(mainWindowScene.getWidth());
            System.out.println("Загрузили 26");
            primaryStage.setMinHeight(mainWindowScene.getHeight());
            System.out.println("Загрузили 27");
            primaryStage.setResizable(true);
            System.out.println("Загрузили 28");
        } catch (Exception exception) {
            // TODO: Обработать ошибки
            System.out.println(exception);
            exception.printStackTrace();
        }
    }
}