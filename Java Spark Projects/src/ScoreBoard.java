/**
 * 
 */
package com.iftikhar.java.scoreBoard;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Iftikhar Khan
 *
 */
public class ScoreBoard {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Instantiate a list of players.
		List<Player> players = new ArrayList<>();

		// Instantiate a players.
		Player player1 = new Player("Farhan", 1500);
		Player player2 = new Player("Manal", 900);
		Player player3 = new Player("Shuraym", 400);
		Player player4 = new Player("Sheheryar", 50);

		// Add players to the list.
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);

		// Function call for player's position display.
		displayHighScorePosition(players);

	} // End main() method.

	// Function to display player's position.
	public static void displayHighScorePosition(List<Player> players) {

		String name; // holds the player's name.
		int score; // holds the player's score.

		// You should display the players name along with a message like " managed to
		// get into position " and the position they got and a further message " on the
		// high score table".

		// Print results below.
		System.out.println("=======Score Board=======");

		for (int i = 0; i < players.size(); i++) {

			// Initialize name and score.
			name = players.get(i).getName();
			score = players.get(i).getScore();

			System.out.println(name + " managed to get into position " + calculateHighScorePosition(score)
					+ " with the score of " + score);

		} // end of for loop.

	} // End displayHighScorePosition() method.

	public static int calculateHighScorePosition(int score) {

		int position; // holds the player's position.

		// find the player's position.
		if (score >= 1000) {
			position = 1;
		} else if ((score >= 500) && (score < 1000)) {
			position = 2;
		} else if ((score >= 100) && (score < 500)) {
			position = 3;
		} else
			position = 4;

		return position;
	} // End calculateHighScorePosition() method.

} // End class.

class Player {

	private String name; // Name of the player.
	private int position; // Player's position on the score board.
	private int score; // Player's score.

	// Null Constructor.
	public Player() {
	}

	// Parameterized Constructor.
	public Player(String name, int score) {
		this.name = name;
		this.score = score;
	} // end constructor.

	// Parameterized Constructor.
	public Player(String name, int score, int postion) {
		this(name, score);
		this.position = postion;
	} // end constructor.

	// Accessors and Mutators.
	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPostion() {
		return position;
	}

	public void setPostion(int postion) {
		this.position = postion;
	}// End of Accessors and Mutators.
} // End Player class.
