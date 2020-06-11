package Controller;

import DB.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
            int tempID;
            try {
                tempID = Integer.parseInt(inputID);
                if(connect.searchStudentID(tempID))
                    openStudentWindow(connect, tempID);
                else
                    errorText.setText("존재하지 않는 ID입니다.");
            } catch (NumberFormatException numEx) {
                errorText.setText("존재하지 않는 ID입니다.");
            }
        }
        // DB Connection fail
        else
            errorText.setText("DB 연결에 실패하였습니다.");
    }
    // change scene to student
    private void openStudentWindow(DBConnect connect, int id) {
        // implement studentController init
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/student.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            StudentController studentController = loader.<StudentController>getController();
            studentController.setStudentController(id, connect.getAvg(id), connect.getSum(id), connect);
            newStage.setTitle("학생 성적 조회 시스템 / 학번 : " + id);
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.close();
        // show student stage

    }
}
