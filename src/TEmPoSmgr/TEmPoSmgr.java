package TEmPoSmgr;

import Utils.CSVReader;
import View.CsvParserController;
import View.CustomerPageController;
import View.HomeController;
import View.LoginController;
import daos.CUSTOMER;
import daos.USER;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class TEmPoSmgr extends Application {

    private static Stage primaryStage;
    private BorderPane rootLayout;

    //Variable to hold the currently authenticated user in memory
    private String authenticatedUser;
    private String branchId;

    /**
     * the main starting point for the program, sets and initialises the first page.
     * @param primaryStage a Stage object (primary stage, accessable via getStage)
     */
    @Override
    public void start(Stage primaryStage) {
        startupConfiguration();
        TEmPoSmgr.primaryStage = primaryStage;
        TEmPoSmgr.primaryStage.setTitle("TEmPoS Manager (" + this.branchId + ")");
       //ArrayList<Stage> openStages;
        //initialises the container page (RootLayout)
        initRootLayout();
        //shows the LoginPage
        showLoginStage();
    }

    /**
     * Sets the currently authenticated user in each DAO to allow them to pass this to the server when necessary
     * @param authenticatedUser
     */
    public void setAuthenticatedUser(String authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        USER.setAuthenticatedUser(authenticatedUser);
        CUSTOMER.setAuthenticatedUser(authenticatedUser);
        initRootLayout();
    }

    /**
     * Sets the selected Branch Id
     * @param branchId the current branch id
     */
    public void setBranchId(String branchId){
        this.branchId = branchId;
    }

    /**
     * logout cleanly
     */
    public void logout(){
        this.authenticatedUser = "";
        initRootLayout();
        showLoginStage();
    }

    /**
     * Initialises the root layout and adds any menu items to the menu bar
     */
    private void initRootLayout(){

        try{

            //load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/RootLayout.fxml"));
            //loads rootLayout of fxml type BorderPane (this must match the type of fxml used)
            rootLayout = (BorderPane) loader.load();


            MenuBar menuBar = new MenuBar();
            menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
            rootLayout.setTop(menuBar);

            // File menu - new, save, exit
            Menu fileMenu = new Menu("File");
            MenuItem newMenuItem = new MenuItem("Hello");
            MenuItem logoutMenuItem = new MenuItem("Logout");
            MenuItem exitMenuItem = new MenuItem("Exit");
            logoutMenuItem.setOnAction(actionEvent -> logout());
            exitMenuItem.setOnAction(actionEvent -> Platform.exit());

            fileMenu.getItems().addAll(newMenuItem,
                    new SeparatorMenuItem(), exitMenuItem);

            // Tools Menu
            Menu tools = new Menu("Tools");
            MenuItem customerCSV = new MenuItem("Import Customers by CSV");
            customerCSV.setOnAction(actionEcent -> showCustomersCSV());

            tools.getItems().addAll(customerCSV);

            Menu aboutMenu = new Menu("Help");
            MenuItem about = new MenuItem("About");
            aboutMenu.getItems().addAll(about);

            //Menu with example for selectable menu items
            Menu webMenu = new Menu("Test");
            CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
            htmlMenuItem.setSelected(true);
            webMenu.getItems().add(htmlMenuItem);

            CheckMenuItem cssMenuItem = new CheckMenuItem("CSS");
            cssMenuItem.setSelected(true);
            webMenu.getItems().add(cssMenuItem);

            //menu example with nested menu and toggleable group
            Menu sqlMenu = new Menu("SQL");
            ToggleGroup tGroup = new ToggleGroup();
            RadioMenuItem mysqlItem = new RadioMenuItem("MySQL");
            mysqlItem.setToggleGroup(tGroup);

            RadioMenuItem oracleItem = new RadioMenuItem("Oracle");
            oracleItem.setToggleGroup(tGroup);
            oracleItem.setSelected(true);

            sqlMenu.getItems().addAll(mysqlItem, oracleItem,
                    new SeparatorMenuItem());

            Menu tutorialMenu = new Menu("Tutorial");
            tutorialMenu.getItems().addAll(
                    new CheckMenuItem("Java"),
                    new CheckMenuItem("JavaFX"),
                    new CheckMenuItem("Swing"));

            sqlMenu.getItems().add(tutorialMenu);

            // if no user logged in, restrict menus, else give full menus
            if(this.authenticatedUser.equals("")){

                menuBar.getMenus().addAll(fileMenu, aboutMenu);
            }else{
                fileMenu.getItems().add(2, logoutMenuItem);
                menuBar.getMenus().addAll(fileMenu, aboutMenu, tools);//, webMenu, sqlMenu);
            }



            //Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


            //required exception handling - don't worry about hint.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * loads and shows the login stage
     */
    private void showLoginStage(){
        try{

            //load USER fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Login.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane Login = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(Login);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            LoginController controller = loader.getController();
            //calls the setMainApp method from loginPageController to make this the main app in the rootLayout
            controller.setMainApp(this);


            //required exception handling - don't worry about hint.
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the customer management page
     */
    public void showCustomerPage(){
        try{

            //load USER fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/CustomerPage.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane CustomerPage = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(CustomerPage);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            CustomerPageController controller = loader.getController();
            //calls the setMainApp method from loginPageController to make this the main app in the rootLayout
            controller.setMainApp(this);


            //required exception handling - don't worry about hint.
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * loads and shows the home page
     */
    public void showHome(){

        try{

            //load fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Home.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane Home = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(Home);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            HomeController controller = loader.getController();
            //calls the setMainApp method from loginPageController to make this the main app in the rootLayout
            controller.setMainApp(this);


            //required exception handling - don't worry about hint.
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * show the csv parser page
     */
    private void showCustomersCSV(){

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/CsvParser.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Upload Customer CSV");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootLayout.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CsvParserController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * not entirely necessary as of yet but might be useful in future
     * @return the current stage - allows the variable to be private but gives outside access to it
     */
    public static Stage getStage(){
        return primaryStage;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * cleanly handle quit - logout then exit
     */
    public void handleQuit(){
        this.logout();
        System.exit(0);
    }

    /**
     * cleanly exit the program
     */
    @Override
    public void stop(){
        System.out.println("Exiting...");
        this.handleQuit();
        // Save file
    }

    private void startupConfiguration(){
        HashMap loadedConfiguration = CSVReader.parseConfigurationCSV("configuration.csv");
        this.branchId = (String) loadedConfiguration.get("branchId");
        this.authenticatedUser = "";
    }
}
