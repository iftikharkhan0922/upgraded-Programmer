package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class SpaceRunnerSubScene extends SubScene {
	
	// Define fields for background image path and font path.
	//private static final String FONT_PATH = "model/resources/kenvector_future.ttf";
	private static final String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";

	private boolean isHidden;
	
	// Constructor.
	public SpaceRunnerSubScene() {
		super(new AnchorPane(), 600, 400);
		prefWidth(600);
		prefHeight(400);
		
		// Set background.
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		
		root2.setBackground(new Background(image));
		
		isHidden = true;
		
		setLayoutX(1024);
		setLayoutY(180);
	} // End Constructor.
	
	// Move the subScene in the main Scene.
	public void moveSubScene() {
		
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if(isHidden) {
			transition.setToX(-676);
			isHidden = false;
		} else {
			transition.setToX(0);
			isHidden = true;
		}
		transition.play();
	} // End moveSubScene().
	
	// Return pane for subScene.
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	} // End getPane().
	
} // End class SpaceRunnerSubscene.




