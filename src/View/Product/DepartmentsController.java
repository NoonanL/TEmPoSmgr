package View.Product;

import Model.Department;
import TEmPoSmgr.TEmPoSmgr;
import daos.DEPARTMENT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class DepartmentsController {

    private Stage dialogStage;

    public DepartmentsController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML private TextField departmentField;
    @FXML private Label error;
    @FXML private TableView<Department> departmentTable;
    @FXML private TableColumn<Department, String> departmentColumn;

    private ObservableList<Department> departmentData = FXCollections.observableList(DEPARTMENT.getDepartments());

    @FXML
    private void createNewClicked() throws IOException, JSONException {
        error.setText("");
        //assign the text currently in the username and password text boxes to variables
        String department = departmentField.getText();

        error.setText(createDepartment(department));
        refreshTable();
    }

    @FXML
    private void editClicked() throws IOException, JSONException {
        error.setText("");
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();

        if(selectedDepartment == null){
            error.setText("No department selected.");
        }else {
            error.setText(selectedDepartment.getDepartment() + " selected.");
            showEditDepartmentDialog(selectedDepartment);
        }
        refreshTable();
    }


    private String createDepartment(String department) throws IOException, JSONException {

        String returnString;

        if(!department.equals("")){

            Department newDepartment = new Department();
            newDepartment.setDepartment(department);

            if(DEPARTMENT.createDepartment(newDepartment)){
                //System.out.println("New user created");
                returnString = "New department created";
            }else{
                returnString = "Error creating new Department.";
            }
        }else {
            returnString = "Please complete all marked fields.";
        }
        return(returnString);
    }

    private void showEditDepartmentDialog(Department department) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/EditDepartment.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Department");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditDepartmentController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSelectedDepartment(department);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void backClicked() {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        error.setText("");
        //Set columns to their appropriate value factory
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        departmentTable.setItems(departmentData);

    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    private void refreshTable() throws IOException, JSONException {
        departmentData = FXCollections.observableList(DEPARTMENT.getDepartments());
        departmentTable.refresh();
        //error.setText("");
        departmentTable.setItems(departmentData);
    }


    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}
