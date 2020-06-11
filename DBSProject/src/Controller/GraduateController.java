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

public class GraduateController implements Initializable {
    @FXML private Label sumAdvanced, sumPrerequisite, sumElective;
    @FXML private Button closeBtn;

    public void initialize(URL location, ResourceBundle resources) {
        closeBtn.setOnAction(event -> close());
    }
    public void setGraduateController(int inputID, DBConnect con) {
        int[] sums = con.getMySums(inputID);
        int[] conditions = con.getConditions(inputID);
        sumAdvanced.setText(sums[0] + "/" + conditions[0]);
        sumElective.setText(sums[1] + "/" + conditions[1]);
        sumPrerequisite.setText(sums[2] + "/" + conditions[2]);
    }
    private void close() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
