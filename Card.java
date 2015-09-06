/**
 * This class represents the cards in the game which are allocated to each player, a card has a type, either 
 * Weapon, Player or Room, and each card also has a name	
 * @author danhunt
 * @author rayg
 *
 */
public class Card {
	String type;
	String name;
	//image? each time created given name type and image..
	
	
	 /**			
	  * @param type Card type, either weapon, player or room
	  * @param name name of the card
	  */
	public Card(String type, String name){	
		this.type = type;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	/**
	 *returns Type: Name 
	 */
	public String toString(){
		return this.type + ": " + this.name;
	}
}
