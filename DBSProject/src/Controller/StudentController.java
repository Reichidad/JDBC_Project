package Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import DB.*;

public class StudentController implements Initializable{
    @FXML private Button graduateBtn, semesterBtn;
    @FXML private Pagination pagination;
    @FXML private TableView<TakesProp> takes;
    @FXML private TableColumn<TakesProp, String> tableTitle, tableDeptName,  tableCategory;
    @FXML private TableColumn<TakesProp, Integer> tableCredits, tableSemester, tableYear;
    @FXML private TableColumn<TakesProp, Double> tableGrade;
    @FXML private Label idLabel, avgLabel, sumLabel;

    private int id, sum;
    private double avg;
    private DBConnect connect;
    private ObservableList<TakesProp> takesProps;

    public void setStudentController (int inputID, double inputAvg, int inputSum, DBConnect inputC) {
        this.id = inputID;
        this.avg = inputAvg;
        this.sum = inputSum;
        this.connect = inputC;
        idLabel.setText(String.valueOf(id));
        avgLabel.setText(String.valueOf(avg));
        sumLabel.setText(String.valueOf(sum));
    }
    public void initialize(URL location, ResourceBundle resources) {
        tableTitle.setCellValueFactory(param -> param.getValue().getTitle());
        tableDeptName.setCellValueFactory(param -> param.getValue().getDeptName());
        tableCategory.setCellValueFactory(param -> param.getValue().getCategory());
        tableCredits.setCellValueFactory(param -> param.getValue().getCredits().asObject());
        tableSemester.setCellValueFactory(param -> param.getValue().getSemester().asObject());
        tableYear.setCellValueFactory(param -> param.getValue().getYear().asObject());
        tableGrade.setCellValueFactory(param -> param.getValue().getGrade().asObject());
        pagination.setPageFactory(this::initPagination);
        graduateBtn.setOnAction(event -> graduateEvent());
        semesterBtn.setOnAction(event -> semesterEvent());
    }
    private void graduateEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/graduate.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            GraduateController graduateController = loader.<GraduateController>getController();
            graduateController.setGraduateController(id, connect);
            newStage.setTitle("졸업요건 검색 / 학번 : " + id);
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void semesterEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/semester.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            SemesterController semesterController = loader.<SemesterController>getController();
            semesterController.setSemesterController(id, connect);
            newStage.setTitle("학기별 검색 / 학번 : " + id);
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private Node initPagination(int index) {
        takesProps = connect.createTakesData(id);
        int rowCount = 9;
        int fromNum = index * rowCount;
        int toNum = Math.min(fromNum + rowCount, takesProps.size());
        if(fromNum <= takesProps.size())
            takes.setItems(FXCollections.observableArrayList(takesProps.subList(fromNum, toNum)));
        else
            takes.setItems(FXCollections.observableArrayList(takesProps.subList(0, 0)));
        return new BorderPane(takes);

    }
}
