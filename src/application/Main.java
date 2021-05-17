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
	public static Stage stage;
	public static Scene sceneMain;
	public static Scene sceneOpening;
	@Override
	public void start(Stage primaryStage) {
		try {	
			Parent rootMain = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
			Parent rootOpening = FXMLLoader.load(MainController.class.getResource("Opening.fxml"));

			Scene scene1 = new Scene(rootMain);
			Scene scene2 = new Scene(rootOpening);
			sceneMain = scene1;
			sceneOpening = scene2;
			
			stage = primaryStage;
			stage.setScene(sceneOpening);
			stage.setTitle("Banker's Algorithm in Operating System");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		 launch(args);		 
	}
}
