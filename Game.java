import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/*
 * This method is the cluedo game, it controls and creates the board, players, weapons etc 
 */
public class Game {
	private Board board;
	private Player[] players;
	private Weapon[] weapons;
	private ArrayList<Player> controlledPlayers = new ArrayList<Player>();// human players
	private List<Card> cards = new ArrayList<Card>();
	private BoardFrame frame;
	private String[] solution = new String[3];

	private ArrayList<String> controlledPlayersStrings =  new ArrayList<String>();
	private int nplayers;


	public Game(){

		//create the players, then pass the players to the board
		this.players = new Player[6];
		players[0] = new Player("Biggie Smalls");
		players[1] = new Player("Tupac");
		players[2] = new Player("Dr Dre");
		players[3] = new Player("Eminem");
		players[4] = new Player("Snoop Dogg");
		players[5] = new Player("Jay-Z");

		//create the weapons
		this.weapons = new Weapon[6];
		weapons[0] = new Weapon("Glock");
		weapons[1] = new Weapon("Axe");
		weapons[2] = new Weapon("Saw");
		weapons[3] = new Weapon("Knife");
		weapons[4] = new Weapon("Crow Bar");
		weapons[5] = new Weapon("Pot Plant");

		/*creates all player cards*/
		cards.add(new Card("Player","Biggie Smalls"));
		cards.add(new Card("Player","Tupac"));
		cards.add(new Card("Player","Dr Dre"));
		cards.add(new Card("Player","Eminem"));
		cards.add(new Card("Player","Snoop Dogg"));
		cards.add(new Card("Player","Jay-Z"));
		/*creates all weapon cards*/
		cards.add(new Card("Weapon","Glock"));
		cards.add(new Card("Weapon","Axe"));
		cards.add(new Card("Weapon","Saw"));
		cards.add(new Card("Weapon","Knife"));
		cards.add(new Card("Weapon","Crow Bar"));
		cards.add(new Card("Weapon","Pot Plant"));
		/*creates all room cards*/
		cards.add(new Card("Room","Bed Room"));
		cards.add(new Card("Room","Bath Room"));
		cards.add(new Card("Room","Studio"));
		cards.add(new Card("Room","Basement"));
		cards.add(new Card("Room","Jail"));
		cards.add(new Card("Room","Hood"));
		cards.add(new Card("Room","Cali"));
		cards.add(new Card("Room","Brooklyn"));
		cards.add(new Card("Room","Compton"));

		this.board = new Board(players,weapons);
		this.frame = new BoardFrame(players, weapons); 
	}

	/**
	 * Builds an arraylist of they players who are controlled (human)
	 * this can then be used to determine who is left in the game or who has lost
	 * @param letter, hardcoded letters representing characters. A = profesor plum and so forth
	 */
	public void buildControlled(){	
		for(int i=0;i<controlledPlayersStrings.size();i++){
			for(int j=0;j<players.length;j++){
				if(players[j].getName().equals(controlledPlayersStrings.get(i))){
					controlledPlayers.add(players[j]);
				}
			}
		}
	}

	/**
	 * Deals all the cards to the controlled players (humans), giving each an amount relative to 
	 * how many people are playing
	 * @param numPlayers, number of controlled players
	 */
	public void dealCards(int numPlayers){
		int i=0;
		Collections.shuffle(cards);
		for(Card c:cards){
			controlledPlayers.get(i).addToCards(c); // give this card to this player
			i++; //increment to next player while incrementing to next card
			if(i==numPlayers){ //start from first player again
				i=0;
			}
		}
	}
	
	/**
	 *
	 * @return The board for testing purposes
	 */
	public Board getBoard(){
		return this.board;
	}
	/**
	 * 
	 * @return the player array, for testing
	 */
	public Player[] getPlayers(){
		return this.players;
	}
	/**
	 * 
	 * @return the weapon array, for testing.
	 */
	public Weapon[] getWeapons(){
		return this.weapons;
	}

	/**
	 * 
	 * @return The solution to the game 
	 */
	public String[] getSolution(){
		return this.solution;
	}

	/**
	 * This method practically is the game, controls responses to the user commands. 
	 * and creates the game flow from player to player
	 */
	public void run(){
		boolean diceRolled = false; // indicator int for rolling dice or making accusation
		int numPlayers;
		String roomNear; // the room this player is near and able to enter
		int i=0;

		numPlayers=frame.askManyPlayers();
		nplayers = numPlayers;
		frame.askPlayersNames(numPlayers );
		frame.askWhichPlayer(numPlayers);
		frame.setCards(cards);
		frame.makeSolution();
		controlledPlayersStrings = frame.getControlledPlayers();
		buildControlled(); //builds arraylist of players who are controlled (human)
		frame.setControlledPlayers(controlledPlayers);
		dealCards(numPlayers);

				frame.displayDice();
				frame.displayCards();
				frame.displayNextTurnButton();
				
	}

	private void movePlayer(String playerSuggest, String room) {
		switch(playerSuggest){
		case("Professor Plum"):
			board.movePlayerToRoom(players[0], room);
		break;
		case("Colonel Mustard"):
			board.movePlayerToRoom(players[1], room);
		break;
		case("Mrs White"):
			board.movePlayerToRoom(players[2], room);
		break;
		case("Miss Scarlet"):
			board.movePlayerToRoom(players[3], room);
		break;
		case("Reverend Green"):
			board.movePlayerToRoom(players[4], room);
		break;
		case("Mrs Peacock"):
			board.movePlayerToRoom(players[5], room);
		break;
		default: break;
		}

	}

	/**
	 * Asks the user if they would like to play again
	 * @return
	 */
	public boolean askPlayAgain(){
		if(frame.askPlayAgain()){
			return true;
		}
		return false;
	}

	/**
	 * sets field weapon to be the weapon that is needing to be moved
	 */
	public void moveWeapon(String weaponSuggest, String room){
		switch(weaponSuggest){
		case("Candlestick"):
			board.moveWeaponToRoom(weapons[0], room);
		break;
		case("Dagger"):
			board.moveWeaponToRoom(weapons[1], room);
		break;
		case("Lead Pipe"):
			board.moveWeaponToRoom(weapons[2], room);
		break;
		case("Revolver"):
			board.moveWeaponToRoom(weapons[3], room);
		break;
		case("Rope"):
			board.moveWeaponToRoom(weapons[4], room);
		break;
		case("Spanner"):
			board.moveWeaponToRoom(weapons[5], room);
		break;
		default: break;
		}
	}
}