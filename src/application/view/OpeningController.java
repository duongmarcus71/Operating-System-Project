package application.view;
import application.Main;
import javafx.event.ActionEvent;

public class OpeningController {
	public void generate(ActionEvent e) {
		Main.stage.setScene(Main.sceneMain);
	}
}
