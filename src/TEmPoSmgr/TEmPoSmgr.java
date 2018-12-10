package TEmPoSmgr;

import Model.Configuration;
import View.*;
import View.Customer.CustomerPageController;
import View.Product.BrandsController;
import View.Product.DepartmentsController;
import View.Product.ProductsPageController;
import View.Stock.GoodsInPageController;
import View.Stock.PurchaseOrderPageController;
import View.Stock.StockPageController;
import View.Util.ConfigurationPageController;
import View.Util.CsvParserController;
import View.Util.DistributorPageController;
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

public class TEmPoSmgr extends Application {

    private static Stage primaryStage;
    private BorderPane rootLayout;

    //Create configuration. This will hold important stuff in memory. Such as the logged in user, loaded branch config, etc.
    public static Configuration configuration;

    /**
     * the main starting point for the program, sets and initialises the first page.
     * @param primaryStage a Stage object (primary stage, accessable via getStage)
     */
    @Override
    public void start(Stage primaryStage) {

        configuration = new Configuration();

        TEmPoSmgr.primaryStage = primaryStage;
        TEmPoSmgr.primaryStage.setTitle("TEmPoS Manager (" + configuration.getBranchId() + ")");

        //initialises the container page (RootLayout)
        initRootLayout();
        //shows the LoginPage
        showLoginStage();
    }


    /**
     * logout cleanly
     */
    public void logout(){
        configuration.setAuthenticatedUser("");
        //this.authenticatedUser = "";
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
            MenuItem customerCSV = new MenuItem("CSV Data Importer");
            customerCSV.setOnAction(actionEvent -> showCustomersCSV());

            MenuItem configuration = new MenuItem("Configure Till");
            configuration.setOnAction(actionEvent -> showConfiguration());

            MenuItem departments = new MenuItem("Departments");
            departments.setOnAction(actionEvent -> showDepartments());

            MenuItem brands = new MenuItem("Brands");
            brands.setOnAction(actionEvent -> showBrands());

            MenuItem distributors = new MenuItem("Distributors");
            distributors.setOnAction(actionEvent -> showDistributor());

            tools.getItems().addAll(customerCSV, configuration, departments, brands, distributors);

            Menu aboutMenu = new Menu("Help");
            MenuItem about = new MenuItem("About");
            aboutMenu.getItems().addAll(about);
//
//            //Menu with example for selectable menu items
//            Menu webMenu = new Menu("Test");
//            CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
//            htmlMenuItem.setSelected(true);
//            webMenu.getItems().add(htmlMenuItem);
//
//            CheckMenuItem cssMenuItem = new CheckMenuItem("CSS");
//            cssMenuItem.setSelected(true);
//            webMenu.getItems().add(cssMenuItem);
//
//            //menu example with nested menu and toggleable group
//            Menu sqlMenu = new Menu("SQL");
//            ToggleGroup tGroup = new ToggleGroup();
//            RadioMenuItem mysqlItem = new RadioMenuItem("MySQL");
//            mysqlItem.setToggleGroup(tGroup);
//
//            RadioMenuItem oracleItem = new RadioMenuItem("Oracle");
//            oracleItem.setToggleGroup(tGroup);
//            oracleItem.setSelected(true);
//
//            sqlMenu.getItems().addAll(mysqlItem, oracleItem,
//                    new SeparatorMenuItem());
//
//            Menu tutorialMenu = new Menu("Tutorial");
//            tutorialMenu.getItems().addAll(
//                    new CheckMenuItem("Java"),
//                    new CheckMenuItem("JavaFX"),
//                    new CheckMenuItem("Swing"));
//            sqlMenu.getItems().add(tutorialMenu);

            // if no user logged in, restrict menus, else give full menus
            if(this.configuration.getAuthenticatedUser().equals("")){
                menuBar.getMenus().addAll(fileMenu, aboutMenu);
            }else{
                fileMenu.getItems().add(2, logoutMenuItem);
                menuBar.getMenus().addAll(fileMenu, aboutMenu, tools);//, webMenu, sqlMenu);
            }



            //Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            updateTitle();
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
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Customer/CustomerPage.fxml"));
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


    public void showProductsPage(){
        try{

            //load USER fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Product/ProductsPage.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane ProductsPage = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(ProductsPage);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            ProductsPageController controller = loader.getController();
            //calls the setMainApp method from loginPageController to make this the main app in the rootLayout
            controller.setMainApp(this);


            //required exception handling - don't worry about hint.
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void showGoodsInPage(){
        try{

            //load USER fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Stock/GoodsInPage.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane GoodsInPage = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(GoodsInPage);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            GoodsInPageController controller = loader.getController();
            //calls the setMainApp method from loginPageController to make this the main app in the rootLayout
            controller.setMainApp(this);


            //required exception handling - don't worry about hint.
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void showPurchaseOrderPage(){
        try{

            //load USER fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Stock/PurchaseOrderPage.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane PurchaseOrderPage = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(PurchaseOrderPage);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            PurchaseOrderPageController controller = loader.getController();
            //calls the setMainApp method from loginPageController to make this the main app in the rootLayout
            controller.setMainApp(this);


            //required exception handling - don't worry about hint.
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void showStockPage(){
        try{

            //load USER fxml page
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/Stock/StockPage.fxml"));
            //load LoginPage of fxml type AnchorPane
            AnchorPane StockPage = (AnchorPane) loader.load();

            //Set login page to the centre of root layout
            rootLayout.setCenter(StockPage);

            //this is important. This loads the loginPageController so that the Main App can access its methods
            StockPageController controller = loader.getController();
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

            initRootLayout();
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
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Util/CsvParser.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Import from CSV");
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

    public void showDepartments(){

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/Departments.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Departments");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootLayout.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DepartmentsController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBrands(){

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/Brands.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Brands");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootLayout.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            BrandsController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDistributor(){

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Util/DistributorPage.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Distributors");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootLayout.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DistributorPageController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * show the csv parser page
     */
    private void showConfiguration(){

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Util/ConfigurationPage.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Configure Till");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootLayout.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ConfigurationPageController controller = loader.getController();
            controller.setConfiguration(configuration);
            controller.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.loadConfiguration();
        updateTitle();
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

    private void updateTitle(){
        TEmPoSmgr.primaryStage.setTitle("TEmPoS Manager (" + configuration.getBranchId() + ") - Welcome " + configuration.getAuthenticatedUser() + "!");
    }



}
