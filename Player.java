import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
/**
 * This class represents the players of the game.
 * @author danhunt
 * @author rayg
 *
 */
public class Player {

	private String name; // players name
	private int xLoc; // current x location
	private int yLoc; // current y location
	private String location; // keeps track of the square of board which the
								// player is sitting on/replaced
	private boolean controlled = false; // determines whether the player is
										// being controlled or not
	private Set<Card> cards = new HashSet<Card>();
	private BufferedImage img;
	private String intials; // the character that represents the player on the board.
	private int size = 20;
	List<String> charactersSuggested = new ArrayList<String>();
	// list of characters suggested by this player already

	List<String> weaponsSuggested = new ArrayList<String>();
	// list of weapons suggested by this player already

	List<String> roomsSuggested = new ArrayList<String>();
	// list of rooms suggested by this player already
	/**
	 * Constructor: Takes a string as a name; and then assigns starting values to the fields 
	 * based on the name
	 * @param name Name of the character being created
	 */
	public Player(String name) {
		this.name = name;
		switch(this.name){
			case("Biggie Smalls"):
				this.xLoc = 0;
				this.yLoc = 7;
				this.location = ".";
				this.intials = "P";
				try {
				    img = ImageIO.read(new File("src/images/Biggie-Smalls-small.png"));
				    
				} catch (IOException e) {
				}
				break;
			case("Tupac"):
				this.xLoc = 6;
				this.yLoc = 0;
				this.location = ".";
				this.intials = "A";
				try {
				    img = ImageIO.read(new File("src/images/Tupac-small.png"));
				    
				} catch (IOException e) {
				}				
				break;
			case("Dr Dre"):
				this.xLoc = 15;
				this.yLoc = 0;
				this.location = ".";
				this.intials = "M";
				try {
				    img = ImageIO.read(new File("src/images/Dr-Dre-small.png"));
				    
				} catch (IOException e) {
				}
				break;
			case("Eminem"):
				this.xLoc = 24;
				this.yLoc = 13;
				this.location = ".";
				this.intials = "T";
				try {
				    img = ImageIO.read(new File("src/images/Eminem-small.png"));
				    
				} catch (IOException e) {
				}
				break;
			case("Snoop Dogg"):
				this.xLoc = 15;
				this.yLoc = 24;
				this.location = ".";
				this.intials = "R";
				try {
				    img = ImageIO.read(new File("src/images/snoop-dogg-small.png"));
				    
				} catch (IOException e) {
				}
				break;
			case("Jay-Z"):
				this.xLoc = 0;
				this.yLoc = 17;
				this.location = ".";
				this.intials = "Q";
				try {
				    img = ImageIO.read(new File("src/images/Jay-Z-small.png"));
				    
				} catch (IOException e) {
				}
				break;
			default:
		}
	}
	/**
	 * Draws the players image on the board
	 * @param g Graphics pane to draw on
	 * @throws IOException 
	 */
	public void draw(Graphics g){
		Color oldColor = g.getColor(); // get the graphics original color
		g.drawImage(img, (this.size*this.xLoc+67), (this.size*this.yLoc), this.size, this.size, null); //draw the player on the graphics pane
		g.setColor(Color.BLACK); 
		g.drawRect((this.size*this.xLoc+67), (this.size*this.yLoc), this.size, this.size);
		g.setColor(oldColor); //reset the graphics to the original color
	}
	/**
	 * this method returns the board character that the character is occupying
	 */
	public String getBoardLoc(){
		return this.location;
	}

	public boolean isControlled() {
		return this.controlled;
	}
	
	public void setControlled(){
		controlled = true;
	}

	public String getName() {
		return this.name;
	}

	public int getXLocation() {
		return this.xLoc;
	}

	public int getYLocation() {
		return this.yLoc;
	}

	public Set<Card> getCards() {
		return cards;
	}
	/**
	 * Moves the player up the board
	 * @param steps number of places to move player
	 */
	public void moveUp(int steps) { 
		this.yLoc = this.yLoc - steps;
	}
	/**
	 * Moves the player down the board
	 * @param steps number of places to move player
	 */
	public void moveDown(int steps) {
		this.yLoc = this.yLoc + steps;
	}
	/**
	 * Moves the player right along the board
	 * @param steps number of places to move player
	 */
	public void moveRight(int steps) {
		this.xLoc = this.xLoc + steps;
	}
	/**
	 * Moves the player left along the board
	 * @param steps number of places to move player
	 */
	public void moveLeft(int steps) {
		this.xLoc = this.xLoc - steps;
	}
	
	public void setConrolled(boolean controlled){
		this.controlled = controlled;		
	}
	/**
	 * Gives the full name of the room the player is in rather than just the board location.
	 * @return
	 */
	public String getLocation() {
		String code = this.location;

		switch (code) {
		case "K": return "Kitchen";
		case "B": return "Ballroom";
		case "C": return "Conservatory";
		case "G": return "Dining Room";
		case "L": return "Lounge";
		case "W": return "Wall";
		case "H": return "Hall Room";
		case "S": return "Study";
		case "Y": return "Library";
		case "I": return "Billard Room";
		case "O": return "Cluedo";
		case "D": return "Door";
		case ".": return "Hallway";
		default: return code;
		}
	}
	public void setXLocation(int x){
		this.xLoc = x;
	}
	public void setYLocation(int y){
		this.yLoc = y;
	}
	
	public String getInitials(){
		return this.intials;
	}
	/**
	 * Moves the player to the room when an accusation is made which features them.
	 * @param room room player being moved to.
	 */
	public void moveTo(String room){
		switch (room) {
		case "Kitchen":
			this.location = "K";
			this.xLoc = 3;
			this.yLoc = 3;
			break;
		case "Ballroom":
			this.location = "B";
			this.xLoc = 10;
			this.yLoc = 3;
			break;
		case "Conservatory":
			this.location = "C";
			this.xLoc = 21;
			this.yLoc = 3;
			break;
		case "Dining Room":
			this.location = "G";
			this.xLoc = 4;
			this.yLoc = 12;
			break;
		case "Lounge":
			this.location = "L";
			this.xLoc = 3;
			this.yLoc = 21;
			break;
		case "Hall Room":
			this.location = "H";
			this.xLoc = 11;
			this.yLoc = 20;
			break;
		case "Study":
			this.location = "S";
			this.xLoc = 19;
			this.yLoc = 22;
			break;
		case "Library":
			this.location = "Y";
			this.xLoc = 21;
			this.yLoc = 15;
			break;
		case "Billard Room":
			this.location = "I";
			this.xLoc = 23;
			this.yLoc = 9;
			break;
		default:
		}
	}
	
	public void addToCards(Card c){
		cards.add(c);
	}
	
	/**
		not sure if we even need this atm as we might not have to care about past suggestions in this
		verion of the game.
		
	* Allows this player to make a suggestion as to who he/she thinks committed the murder.
	* saves each suggested player,weapon and room, allowing to be referenced again the solution.
	* @param player suggested player to have committed the murder
	* @param weapon suggested weapon to have committed the murder
	* @param room suggested room to have committed the murder in
	*/

	public void makeSuggestion(String player,String weapon,String room){
	charactersSuggested.add(player);
	weaponsSuggested.add(weapon);
	roomsSuggested.add(room);
	}
	
	public void setLocation(String location) {
		this.location = location;
		
	}

}
