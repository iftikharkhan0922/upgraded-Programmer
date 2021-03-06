package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewManager manager = new ViewManager();			// Instantiate UD View manager.
			primaryStage = manager.getMainStage();								// Get mainStage from UD View.
			primaryStage.show();								
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
