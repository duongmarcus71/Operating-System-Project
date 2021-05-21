package application;
	
import application.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Main extends Application {
	public static Scene sceneMain;
	public static Scene sceneOpening;
	public static Scene sceneExit;
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root1 = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
			sceneMain = new Scene(root1);
			Parent root2 = FXMLLoader.load(MainController.class.getResource("Opening.fxml"));
			sceneOpening = new Scene(root2);
			Parent root3 = FXMLLoader.load(MainController.class.getResource("exit.fxml"));
			sceneExit = new Scene(root3);
			stage = primaryStage;
			stage.setScene(sceneOpening);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		 launch(args);		 
	}
}
