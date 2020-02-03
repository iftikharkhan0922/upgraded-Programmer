package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

public class ViewManager {
	
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private static final int MENU_BUTTON_START_X = 100;
	private static final int MENU_BUTTON_START_Y = 150;

	private SpaceRunnerSubScene shipChooserScene;
	private SpaceRunnerSubScene scoreSubScene;
	private SpaceRunnerSubScene helpSubScene;
	private SpaceRunnerSubScene creditsSubScene;
	private SpaceRunnerSubScene exitSubScene;

	private SpaceRunnerSubScene sceneToHide;
	
	List<SpaceRunnerButton> menuButtons;
	
	List<ShipPicker> shipsList;
	private SHIP chosenShip;
	
	// Constructor.
	public ViewManager() {
		
		// Initialize the fields and set the Scene.
		menuButtons = new ArrayList<SpaceRunnerButton>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createSubScenes();
		createButtons();
		createBackGround();
		createLogo();
		
		
	} // End Constructor.
	
	// Show or Hide a Sub Scene.
	private void showSubScene(SpaceRunnerSubScene subScene) {
		if(sceneToHide != null) {
			sceneToHide.moveSubScene();
		} //End if.
		
		// Now show the subScene and set it to to be the new scene to hide.
		subScene.moveSubScene();
		sceneToHide = subScene;
	} // End showSubScene(). 
	
	// Create SubScenes.
	private void createSubScenes() {
		
		// Initialize start SubScene.
		shipChooserScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(shipChooserScene);
		
		// Initialize scores SubScene.
		scoreSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(scoreSubScene);
		
		// Initialize help SubScene.
		helpSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(helpSubScene);
		
		// Initialize credits SubScene.
		creditsSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		// Initialize exit SubScene.
		exitSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(exitSubScene);
	
		// Create ship chooser sub scene.
		createShipChooserSubScene();
		
	} // End createSubScenes.
	
	// Create ship choosing sub scene Info Label.
	private void createShipChooserSubScene() {
		shipChooserScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(shipChooserScene);
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		shipChooserScene.getPane().getChildren().add(chooseShipLabel);
		shipChooserScene.getPane().getChildren().add(createShipsToChoose());
		shipChooserScene.getPane().getChildren().add(createButtonToStart());
		
	} // End createShipChooserSubScene().
	
	// Create Ship choosing sub scene ship list.
	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipsList = new ArrayList<>();
		
		// For each ship add images or picker.
		for(SHIP ship: SHIP.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			box.getChildren().add(shipToPick);
			shipsList.add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for(ShipPicker ship: shipsList) {
						ship.setIsCircleChosen(false);
					} // End for loop.
					shipToPick.setIsCircleChosen(true);
					chosenShip = shipToPick.getShip();
				}
			});
		} // End for loop.
		// Set box layout.
		box.setLayoutX(300 - (118 * 2));
		box.setLayoutY(100);
		
		return box;
	} // End createShipsToChoose().
	
	// Create start button to start playing the game.
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		// Add action listener for functionality to the start button.
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(chosenShip != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, chosenShip);
				}
				
			} // End handle().

		});
		
		return startButton;
	} // End createButtonToStart().

	// Getter for mainStage.
	public Stage getMainStage() {
		return mainStage;
	} // End Getter mainStage.
	
	
	// Add menu buttons.
	private void addMenuButton(SpaceRunnerButton button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	} // End addMenuButton().
	
	
	// Create Buttons.
	private void createButtons() {
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	} // End createButton().
	
	// Create Start Button.
	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
		addMenuButton(startButton);
		
		// add action event handler to the start button.
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(shipChooserScene);			
			} // End handle().
		});
	} // End createStartButton().

	// Create Scores Button.
	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoresButton);
		
		// add action event handler to the scores button.
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoreSubScene);			
			} // End handle().
		});
	} // End createScoresButton().
	
	// Create Help Button.
	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
		addMenuButton(helpButton);
		
		// add action event handler to the help button.
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubScene);			
			} // End handle().
		});
	} // End createHelpButton().
	
	// Create Credits Button.
	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
		
		// add action event handler to the credits button.
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubScene);			
			} // End handle().
		});
		
	} // End createCreditsButton().
	
	// Create Exit Button.
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
		addMenuButton(exitButton);
		
		// add action event handler to the exit button.
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();;			
			} // End handle().
		});
	} // End createExitButton().
	
	
	// Create a background.
	private void createBackGround() {
		Image backgroundImage = new Image("view/resources/purple.png", 256, 256, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	} // End createBackGround().
	
	// Create Logo.
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/SpaceRunnerLogo.gif");
		logo.setLayoutX(400);
		logo.setLayoutY(50);
		
		// Create shadow effect on mouse..
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			} // End handle().
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			} // End handle().
		});
		
		// add logo to the mainPane.
		mainPane.getChildren().add(logo);
	} // End createLogo.

} // End Class ViewManager.
