package Controller;

import DB.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    @FXML private Button loginBtn;
    @FXML private TextField inputID;
    @FXML private Label errorText;

    private Stage stage;

    public void initialize(URL location, ResourceBundle resources) {
        loginBtn.setOnAction(event -> logInEvent(inputID.getText()));
    }
    // LOGIN Button click event
    private void logInEvent(String inputID) {
        this.stage = (Stage) loginBtn.getScene().getWindow();
        DBConnect connect = new DBConnect();
        // try DB Connect
        if(connect.connectDB()) {
            if(inputID.equals("admin"))
                openAdminWindow();
            else {
                int tempID;
                try {
                    tempID = Integer.parseInt(inputID);
                    if(connect.searchStudentID(tempID))
                        openStudentWindow();
                    else
                        errorText.setText("존재하지 않는 ID입니다.");
                } catch (NumberFormatException numEx) {
                    errorText.setText("존재하지 않는 ID입니다.");
                }
            }
        }
        // DB Connection fail
        else
            errorText.setText("DB 연결에 실패하였습니다.");
    }
    // change scene to student
    private void openStudentWindow() {
        StudentController studentController = new StudentController();
        // implement studentController init
        stage.close();
        // show student stage

    }
    // change scene to admin
    private void openAdminWindow() {
        AdminController adminController = new AdminController();
        // implement adminController init
        stage.close();
        // show admin stage
    }
}
