package lab1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPortException;

public class Main extends Application {

    public static String comName = "COM2";
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        primaryStage.setTitle(comName);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            Controller.serialPort.closePort();
        } catch (SerialPortException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}