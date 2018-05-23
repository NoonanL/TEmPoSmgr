package TEmPoSmgr;

import View.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TEmPoSmgr extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TEmPoS Manager");

        //initialises the container page (RootLayout)
        initRootLayout();
        //shows the LoginPage
        showLoginStage();
    }

    //the init method for the root layout
    public void initRootLayout(){

        try{

            //load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(TEmPoSmgr.class.getResource("/View/RootLayout.fxml"));
            //loads rootLayout of fxml type BorderPane (this must match the type of fxml used)
            rootLayout = (BorderPane) loader.load();

            //Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


            //required exception handling - don't worry about hint.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this method shows the Login prompt where users can either login or register an account
    public void showLoginStage(){

        try{

            //load Login fxml page
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


    //getter method for LoginStage - not important, just here because it's recommended to have.
    public Stage getLoginStage(){
        return primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
