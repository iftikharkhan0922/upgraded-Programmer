package model;

public enum SHIP {
	
	BLUE("view/resources/shipChooser/playerShip1_blue.png", "view/resources/shipChooser/playerLife1_blue.png"),
	GREEN("view/resources/shipChooser/playerShip1_green.png", "view/resources/shipChooser/playerLife1_green.png"),
	ORANGE("view/resources/shipChooser/playerShip1_orange.png", "view/resources/shipChooser/playerLife1_orange.png"),
	RED("view/resources/shipChooser/playerShip1_red.png", "view/resources/shipChooser/playerLife1_red.png");
	
	private String urlShip;
	private String urlLife;
	
	// Constructor.
	private SHIP(String urlShip, String urlLife) {
		this.urlShip = urlShip;
		this.urlLife = urlLife;
	} // End Constructor.
	
	// Return ship url.
	public String getUrl() {
		return this.urlShip;
	} // End getUrl
	
	// Return urlLife.
	public String getUrlLife() {
		return this.urlLife;
	} // End urlLife.
	
} // End enum SHIP
