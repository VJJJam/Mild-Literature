import java.util.*;

public class Player {

	public int id;
	public int team;
	public List<String> hand;
	
	public Player(int id, List<String> hand, int team) {
		this.id = id;
		this.hand = hand;
		this.team = team;
	}
	
	public int getID() {
		return id;
	}
	
	public int getTeam() {
		return team;
	}
	
	public List<String> getHand() {
		return hand;
	}
	
	/**
	 * Searches through hand for specified card
	 * 
	 * Parameters:
	 *   card : The card being searched for
	 * 
	 * Returns:
	 *   The index of card in the hand
	 *   Returns -1 otherwise
	 */
	public int contains(String card) {
		int i;
		for(i = 0; i < hand.size(); i++) {
			if( hand.get(i).equals(card) ) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Asks if opponent has specified card. If opponent does, it will automatically be removed
	 * 
	 * Parameters:
	 *   opponent - the opponent player
	 *   card - the card the current player wants
	 *   
	 * Returns:
	 *    true if opponent has card
	 *    false otherwise
	 */
	public boolean askPlayer(Player opponent, String card) {
		int index = opponent.contains(card);
		if(index == -1) {
			return false;
		}
		// Remove from opponent
		opponent.hand.remove(index);
		// Add to hand
		hand.add(card);
		
		return true;
	}
	
	/**
	 * Checks if player has a lower suit
	 * 
	 * Parameters:
	 *   None.
	 * 
	 * Returns:
	 *   true if has a lower suit
	 *   Returns false otherwise
	 */
	public char checkLowerSuit() {
		/* Pre-check for efficiency */
		if(hand.size() < 6) {
			return '\0';
		}
		// Search order: diamonds, clubs, hearts, spades
		String suits = "DCHS";
		int i;
		/* This outer loop is for the suit */
		for(i = 0; i < suits.length(); i++) {
			char suit = suits.charAt(i);
			int count = 3;
			/* This while loop checks off 3-8 cards of the suit*/
			while(count <= 8) {
				if(contains( Integer.toString(count) + suit ) == -1) {
					// Doesn't contain this particular card.
					// Check to see if this is last suit
					if(i == suits.length() - 1) {
						return '\0';
					}
					break;
				} else if (count == 8) {
					// Has lower suit
					// Begin to delete...can change later
					count = 3;
					while(count <= 8) {
						hand.remove(Integer.toString(count)+suit);
						count++;
					}
					return suit;
				}
				count++;
			}
		}
		
		return '\0';
	}
	
	/**
	 * Checks if player has a higher-suit.
	 * 
	 * Parameters:
	 *   None.
	 * 
	 * Returns:
	 *   true if has a higher-suit
	 *   false otherwise
	 */
	public char checkHigherSuit() {
		/* Pre-check for efficiency */
		if(hand.size() < 6) {
			return '\0';
		}
		
		// Search order: diamonds, clubs, hearts, spades
		String suits = "DCHS";
		/* This outer loop is for the suit */
		for(int i = 0; i < suits.length(); i++) {
			char suit = suits.charAt(i);
			int count = 9;
			/* This while loop checks off 9-A (Jack = 11, ..., A = 14) cards of the suit*/
			while(count <= 14) {
				if(contains( Integer.toString(count) + suit ) == -1) {
					// Doesn't contain this particular card.
					// Check to see if this is last suit
					if(i == suits.length() - 1) {
						return '\0';
					}
					break;
				} else if (count == 14) {
					// Has lower suit
					// Begin to delete...can change later
					count = 9;
					while(count <= 14) {
						hand.remove(Integer.toString(count)+suit);
						count++;
					}
					return suit;
				}
				count++;
			}
		}
		
		return '\0';
	}
}