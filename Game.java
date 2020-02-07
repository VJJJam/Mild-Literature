/**
 * AUTHOR			: Jamie Vue
 * LAST MODIFIED	: 04/30/1018
 */

import java.util.*;

public class Game {

	public Player[] team1;
	public Player[] team2;
	public Visual visual;
	
	public Game() {
		team1 = new Player[3];
		team2 = new Player[3];
		team1 = generateTeam(team1, 1);
		team2 = generateTeam(team2, 2);
		deal();
		
		visual = new Visual(team1, team2);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.visual.currentPlayer = game.team1[0];
		game.visual.startGame();
	}
	
	
	/**
	 * Generate the team
	 * 
	 * Parameters:
	 *   team - an array of Players
	 *   teamNum - the team number
	 *   
	 * Returns:
	 * 	 A team of defined Players (each Player's card has not been determined)
	 */
	private Player[] generateTeam(Player[] team, int teamNum) {
		for(int i = 1; i-1 < team.length; i++) {
			team[i-1] = new Player(i, new ArrayList<String>(), teamNum);
		}
		
		return team;
	}
	
	/**
	 * Generates random cards for players
	 * 
	 * Parameters:
	 *   None.
	 * 
	 * Returns:
	 * 	 Nothing.
	 */
	private void deal() {
		/* Being realistic as possible: make the deck */
		List<String> deck = new ArrayList<>();
		String suits = "DCHS";
		int i;
		for(i = 0; i < suits.length(); i++) {
			char suit = suits.charAt(i);
			int counter = 3;
			// Go through all cards for this suit (A -> 14, K -> 13, ..)
			while(counter <= 14) {
				deck.add(Integer.toString(counter)+suit);
				counter++;
			}
		}
		// Shuffle thrice for luck
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		// Distribute
		i = 0;
		while(i < deck.size()) {
			for(int index = 0; index < team1.length; index++) {
				// Being very careful
				if(i >= deck.size()) {
					break;
				}
				team1[index].hand.add( deck.get(i) );
				i++;
				// Being extremely careful
				if(i >= deck.size()) {
					break;
				}
				team2[index].hand.add( deck.get(i) );
				i++;
			}
		}
	}
}