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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SemesterController implements Initializable {
    @FXML private TableView<SemesterProp> semesterTable;
    @FXML private TableColumn<SemesterProp, Integer> year, semester, credits;
    @FXML private TableColumn<SemesterProp, Double> gradeAvg;
    @FXML private Button closeBtn;

    public void initialize(URL location, ResourceBundle resources) {
        year.setCellValueFactory(param -> param.getValue().getYear().asObject());
        semester.setCellValueFactory(param -> param.getValue().getSemester().asObject());
        credits.setCellValueFactory(param -> param.getValue().getCredits().asObject());
        gradeAvg.setCellValueFactory(param -> param.getValue().getGradeAvg().asObject());
        closeBtn.setOnAction(event -> close());
    }
    public void setSemesterController(int inputID, DBConnect con) {
        semesterTable.setItems(con.createSemesterData(inputID));
    }
    private void close() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
