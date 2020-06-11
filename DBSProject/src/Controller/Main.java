package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/UI/login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("학생 성적 조회 시스템 / 로그인");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
