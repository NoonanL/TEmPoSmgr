package View;

import Model.Department;
import Model.User;
import daos.DEPARTMENT;
import daos.USER;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class EditDepartmentController {

    private Stage dialogStage;
    private Department selectedDepartment;

    public EditDepartmentController(){}

    @FXML
    private Button back;
    @FXML Button submit;
    @FXML private TextField departmentField;
    @FXML private Label error;

    public void setSelectedDepartment(Department editDepartment){
        this.selectedDepartment = editDepartment;
        departmentField.setText(selectedDepartment.getDepartment());
    }

    @FXML
    private void submit() throws IOException, JSONException {
        //send update request here

        String department = departmentField.getText();

        Department editedDepartment = new Department();
        editedDepartment.setDepartment(department);
        editedDepartment.setId(this.selectedDepartment.getId());

        if(DEPARTMENT.editDepartment(editedDepartment)){
            error.setText("Changes saved.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Changes Saved");
            alert.setHeaderText("Changes Saved");
            alert.setContentText("Changes to user " + department + " saved.");
            alert.showAndWait();
            dialogStage.close();
        }else{
            error.setText("Error saving changes.");
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
     *
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        submit.setDefaultButton(true);
        error.setText("");

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
