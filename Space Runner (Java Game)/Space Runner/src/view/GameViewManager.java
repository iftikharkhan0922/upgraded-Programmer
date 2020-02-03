package view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

public class GameViewManager {

	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;

	private static int GAME_WIDTH = 600;
	private static int GAME_HEIGHT = 800;

	private Stage menuStage;
	private ImageView ship;

	// Arrow keys logic holders.
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private AnimationTimer gameTimer;
	private int angle;

	private GridPane gridPane1;
	private GridPane gridPane2;
	private static final String BACKGROUND_IMAGE = "view/resources/purple.png";

	// Meteor images path holders.
	private static final String METEOR_BROWN_IMAGE = "view/resources/meteorBrown_med3.png";
	private static final String METEOR_GREY_IMAGE = "view/resources/meteorGrey_med2.png";

	// Arrays to hold the meteor images.
	private ImageView[] brownMeteors;
	private ImageView[] greyMeteors;

	// Random position generator for the meteors.
	Random randomPositionGenerator;

	private ImageView star;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLives;
	private int playerLife;
	private int points;
	private static final String GOLD_STAR_IMAGE = "view/resources/star_gold.png";

	// circle radius used for collision logic.
	private static final int STAR_RADIUS = 12;
	private static final int SHIP_RADIUS = 27;
	private static final int METEOR_RADIUS = 20;

	// Constructor.
	public GameViewManager() {
		initializeStage(); // Initialize the Stage and its components.
		createKeyListeners(); // Call the key listener method for arrows key controls.
		randomPositionGenerator = new Random(); // Initialize random generator for meteors.
	} // End Constructor.

	// Create listeners to take action from the arrow keys.
	private void createKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				} // End if-else.
			} // End handle().
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				} // End if-else.
			} // End handle().
		});
	} // End createKeyListeners().

	// Initialize Stage by using private method instead of in Constructor.
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	} // End initializeStage().

	// Start new game by bring up the game window.
	public void createNewGame(Stage menuStage, SHIP chosenShip) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		createShip(chosenShip);
		createGameElements(chosenShip);
		createGameLoop();
		gameStage.show();
	} // End createNewGame().

	// Create elements on the game window.
	private void createGameElements(SHIP chosenShip) {
		playerLife = 2; // Initialize the number of lives for the player.

		// create the starts
		star = new ImageView(GOLD_STAR_IMAGE);
		setNewElementPosition(star);
		gamePane.getChildren().add(star);

		// Create the points label.
		pointsLabel = new SmallInfoLabel("POINTS: 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);

		playerLives = new ImageView[3];
		for (int i = 0; i < playerLives.length; i++) {
			playerLives[i] = new ImageView(chosenShip.getUrlLife());
			playerLives[i].setLayoutX(455 + (i * 50));
			playerLives[i].setLayoutY(80);
			gamePane.getChildren().add(playerLives[i]);
		} // End for loop.

		brownMeteors = new ImageView[3];
		for (int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i] = new ImageView(METEOR_BROWN_IMAGE);
			setNewElementPosition(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		} // End for loop.

		greyMeteors = new ImageView[3];
		for (int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i] = new ImageView(METEOR_GREY_IMAGE);
			setNewElementPosition(greyMeteors[i]);
			gamePane.getChildren().add(greyMeteors[i]);
		} // End for loop.
	} // End gameElements().

	// make the meteors move.
	private void moveGameElements() {

		// move the star.
		star.setLayoutY(star.getLayoutY() + 5);

		for (int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 7);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4);
		} // End for loop.

		for (int i = 0; i < greyMeteors.length; i++) {
			greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + 7);
			greyMeteors[i].setRotate(greyMeteors[i].getRotate() + 4);
		} // End for loop.
	} // End moveGameElements()

	// If elements are behind the ship relocate them over the top.
	private void checkIfElementsAreBehindTheShipAndRelocate() {

		// Relocate star.
		if (star.getLayoutY() > 1200) {
			setNewElementPosition(star);
		} // End if.

		for (int i = 0; i < brownMeteors.length; i++) {
			if (brownMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(brownMeteors[i]);
			}
		} // End for loop.

		for (int i = 0; i < greyMeteors.length; i++) {
			if (greyMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(greyMeteors[i]);
			}
		} // End for loop.

	} // End checkIfElementsAreBehindTheShipAndRelocate

	// Now put the meteors in random pos.
	private void setNewElementPosition(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));
	} // End setNewElementPosition().

	// Create a ship on the game window.
	private void createShip(SHIP chosenShip) {
		ship = new ImageView(chosenShip.getUrl());
		ship.setLayoutX((GAME_WIDTH / 2) - 55);
		ship.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(ship);
	} // End createShip().

	// Animation method to loop the game.
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBackground();
				moveGameElements();
				checkIfElementsAreBehindTheShipAndRelocate();
				checkIfElementsCollide();
				moveShip();
			} // End handle.
		}; // AnimationTimer code block.

		gameTimer.start();
	} // End createGameLoop().

	// Create a method to move the ship with button press.
	private void moveShip() {

		if (isLeftKeyPressed && !isRightKeyPressed) {
			if (angle > -30) {
				angle -= 5;
			}
			ship.setRotate(angle);
			if (ship.getLayoutX() > -20) {
				ship.setLayoutX(ship.getLayoutX() - 3);
			}
		} // End outer if.

		if (isRightKeyPressed && !isLeftKeyPressed) {
			if (angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if (ship.getLayoutX() < 522) {
				ship.setLayoutX(ship.getLayoutX() + 3);
			}
		} // End out if.

		if (!isLeftKeyPressed && !isRightKeyPressed) {
			if (angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);
		} // End out if.

		if (isLeftKeyPressed && isRightKeyPressed) {
			if (angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);
		} // End outer if.
	} // End moveShip().

	// Fill the back ground with the image.
	private void createBackground() {
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();

		for (int i = 0; i < 12; i++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
			GridPane.setConstraints(backgroundImage1, i % 3, i / 3);
			GridPane.setConstraints(backgroundImage2, i % 3, i / 3);
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		} // End for loop.

		gridPane2.setLayoutY(-1024);

		gamePane.getChildren().addAll(gridPane1, gridPane2);

	} // End createBackground().

	// Move background image.
	private void moveBackground() {
		gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
		gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);

		if (gridPane1.getLayoutY() >= 1024) {
			gridPane1.setLayoutY(-1024);
		}

		if (gridPane2.getLayoutY() >= 1024) {
			gridPane2.setLayoutY(-1024);
		}
	} // End moveBackground().

	// Collision logic.
	private void checkIfElementsCollide() {

		if (SHIP_RADIUS + STAR_RADIUS > calulateDistance(ship.getLayoutX() + 49, star.getLayoutX() + 15,
				ship.getLayoutY() + 37, star.getLayoutY() + 15)) {

			setNewElementPosition(star);

			points++;
			String textToSet = "POINTS: ";
			if (points < 10) {
				textToSet = textToSet + "0";
			} // End if.
			pointsLabel.setText(textToSet + points);

		} // End outer-if.

		// Brown meteor collision
		for (int i = 0; i < brownMeteors.length; i++) {
			if (SHIP_RADIUS + METEOR_RADIUS > calulateDistance(ship.getLayoutX() + 49,
					brownMeteors[i].getLayoutX() + 20, ship.getLayoutY() + 37, brownMeteors[i].getLayoutY() + 20)) {
				removeLife();
				setNewElementPosition(brownMeteors[i]);
			} // End outer-if.
		} // End for.

		// Grey meteor collision.
		for (int i = 0; i < greyMeteors.length; i++) {
			if (SHIP_RADIUS + METEOR_RADIUS > calulateDistance(ship.getLayoutX() + 49, greyMeteors[i].getLayoutX() + 20,
					ship.getLayoutY() + 37, greyMeteors[i].getLayoutY() + 20)) {
				removeLife();
				setNewElementPosition(greyMeteors[i]);
			} // End outer-if.
		} // End for.

	} // End checkIfElemetsCollide().

	// method to remove life in-case the player crashes into a meteor.
	private void removeLife() {
		gamePane.getChildren().remove(playerLives[playerLife]);
		playerLife--;
		if (playerLife < 0) {
			gameStage.close();
			gameTimer.stop();
			menuStage.show();
		} // End if.
	} // End removeLife().

	// Helper method to calculate the distance between two points.
	private double calulateDistance(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	} // End CalulateDistance().

} // End GameViewManager.
