package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox{
	
	private ImageView circleImage;
	private ImageView shipImage;
	
	private String circleNotChosen = "view/resources/shipChooser/grey_circle.png";
	private String circleChosen = "view/resources/shipChooser/yellowCircleChosen.png";
	
	private SHIP ship;
	
	private boolean isCircleChosen;
	
	
	// Constructor.
	public ShipPicker(SHIP ship) {
		
		circleImage = new ImageView(circleNotChosen);
		shipImage = new ImageView(ship.getUrl());
		this.ship = ship;
		isCircleChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(shipImage);
		
	} // End Constructor.
	
	// Return Ship.
	public SHIP getShip() {
		return ship;
	} // End getShip.
	
	// Return if the circle is chosen or not.
	public boolean getIsCircleChosen() {
		return isCircleChosen;
	} // End getIsCircleChosen().
	
	public void setIsCircleChosen(boolean isCircleChosen) {
		
		this.isCircleChosen = isCircleChosen;
		String imageToSet = this.isCircleChosen? circleChosen: circleNotChosen;
		circleImage.setImage(new Image(imageToSet));
		
		
	} // End setIsCircleChosen().
	
} // End class ShipPicker.
