import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Handles the graphics, the drawing of the board, cards dice etc.
 * 
 * @author rayg
 * @author danhunt
 *
 */
public class BoardCanvas extends JPanel implements MouseListener{

	//ImageIcon backgroundicon = new ImageIcon(BoardCanvas.class.getResource("images/clue board.png"));
	//the dice ImageIcons
	private ImageIcon diceicon1 = new ImageIcon(BoardCanvas.class.getResource("images/dice1.png"));
	private ImageIcon diceicon2 = new ImageIcon(BoardCanvas.class.getResource("images/dice2.png"));
	private ImageIcon diceicon3 = new ImageIcon(BoardCanvas.class.getResource("images/dice3.png"));
	private ImageIcon diceicon4 = new ImageIcon(BoardCanvas.class.getResource("images/dice4.png"));
	private ImageIcon diceicon5 = new ImageIcon(BoardCanvas.class.getResource("images/dice5.png"));
	private ImageIcon diceicon6 = new ImageIcon(BoardCanvas.class.getResource("images/dice6.png"));

	//player card images
	private ImageIcon biggieCard = new ImageIcon(BoardCanvas.class.getResource("images/Biggie-Smalls-Card.png"));
	private ImageIcon tupacCard = new ImageIcon(BoardCanvas.class.getResource("images/Tupac-Card.png"));
	private ImageIcon drdreCard = new ImageIcon(BoardCanvas.class.getResource("images/Dr-Dre-Card.png"));
	private ImageIcon eminemCard = new ImageIcon(BoardCanvas.class.getResource("images/Eminem-Card.png"));
	private ImageIcon snoopdoggCard = new ImageIcon(BoardCanvas.class.getResource("images/Snoop-Dogg-Card.png"));
	private ImageIcon jayzCard = new ImageIcon(BoardCanvas.class.getResource("images/Jay-Z-Card.png"));
	//weapon card images
	private ImageIcon glockCard = new ImageIcon(BoardCanvas.class.getResource("images/Glock-Card.png"));
	private ImageIcon axeCard = new ImageIcon(BoardCanvas.class.getResource("images/Axe-Card.png"));
	private ImageIcon sawCard = new ImageIcon(BoardCanvas.class.getResource("images/Saw-Card.png"));
	private ImageIcon knifeCard = new ImageIcon(BoardCanvas.class.getResource("images/Knife-Card.png"));
	private ImageIcon crowbarCard = new ImageIcon(BoardCanvas.class.getResource("images/Crow-Bar-Card.png"));
	private ImageIcon potplantCard = new ImageIcon(BoardCanvas.class.getResource("images/Pot-Plant-Card.png"));
	//room card images
	private ImageIcon bedroomCard = new ImageIcon(BoardCanvas.class.getResource("images/Bed-Room-Card.png"));
	private ImageIcon bathroomCard = new ImageIcon(BoardCanvas.class.getResource("images/Bath-Room-Card.png"));
	private ImageIcon studioCard = new ImageIcon(BoardCanvas.class.getResource("images/Studio-Card.png"));
	private ImageIcon basementCard = new ImageIcon(BoardCanvas.class.getResource("images/Basement-Card.png"));
	private ImageIcon jailCard = new ImageIcon(BoardCanvas.class.getResource("images/Jail-Card.png"));
	private ImageIcon hoodCard = new ImageIcon(BoardCanvas.class.getResource("images/Hood-Card.png"));
	private ImageIcon caliCard = new ImageIcon(BoardCanvas.class.getResource("images/Cali-Card.png"));
	private ImageIcon brooklynCard = new ImageIcon(BoardCanvas.class.getResource("images/Brooklyn-Card.png"));
	private ImageIcon comptonCard = new ImageIcon(BoardCanvas.class.getResource("images/Compton-Card.png"));
	//JLabels to hold the cards on the canvas
	private JLabel card1 = new JLabel();
	private JLabel card2 = new JLabel();
	private JLabel card3 = new JLabel();
	private JLabel card4 = new JLabel();
	private JLabel card5 = new JLabel();
	private JLabel card6 = new JLabel();
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	//creates the dice labels
	JLabel dice1 = new JLabel();
	JLabel dice2 = new JLabel();
	
	private String[][] stringBoard;
	private Tile[][] tileBoard;
	private Player[] players;
	private Weapon[] weapons;

	private int diceRoll1=0; //first/left dice
	private int diceRoll2=0; //second/right dice
	
	private ArrayList<Player> controlledPlayers; //human players
	private int playerCounter =0; //which player in this it is
	private Player currentPlayer;
	
	//The frame it is held by
	private BoardFrame frame;
	private boolean rolledAlready = false; //if this player has already rolled dice
	
	/**
	 * Constructor:
	 * @param players Players to be drawn on the board
	 * @param weapons Weapons to be drawn on the board
	 * @param frame Frame which holds the canvas
	 */
	public BoardCanvas(Player[] players, Weapon[] weapons,BoardFrame frame){
		this.players = players;
		this.weapons = weapons;
		this.frame = frame;
		setLayout(null);
		this.stringBoard = createStringBoard(); //string representation of board
		this.tileBoard = createTileBoard(); //tile representation of board
		addMouseListener(this);
		createCardAndDiceLabels();
		createMakeSuggestionButton();//displays suggestion button
		createMakeAccusationButton(); //displays accusation button
	}
	/**
	 * 
	 * @return Get the player who's turn it is currently
	 */
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}

	/**
	 * Simulates two die being rolled. resulting in a number between 2 and 12.
	 * @return int between 2 and 12.
	 */
	public void rollDice(){
		int min = 1;
		int max = 6;
		diceRoll1 = min + (int)(Math.random() * ((max - min) + 1));
		diceRoll2 = min + (int)(Math.random() * ((max - min) + 1));
	}

	/**
	 * Get the total dice rolled
	 * @return
	 */
	public int getDiceRolled(){
		return diceRoll1+diceRoll2;
	}

	/**
	 * Creates the roll dice button, when clicked will doRollDice() which will return the number rolled
	 */
	public void createRollButton(){
		JButton rollButton = new JButton("Roll Dice");
		rollButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				if(!rolledAlready){
				rollDice();
				displayDice();
				repaint();
				rolledAlready = true;
				}
			}
		});
		rollButton.setBounds(35,562,100,20);
		rollButton.setFocusable(false); //to make sure button doesn't take all focus and other listeners 
										//continue to work
		rollButton.setToolTipText("Click to roll the dice");
		add(rollButton);
	}
	
	/**
	 * Creates the nextTurn button, when clicked will change current player
	 */
	public void createNextTurnButton(){
		JButton nextButton = new JButton("Next Turn");
		nextButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				playerCounter++;
				if(playerCounter==controlledPlayers.size()){
					playerCounter=0;
				}
				currentPlayer = controlledPlayers.get(playerCounter); 
				displayCards();
				repaint();
				rolledAlready = false;
			}
		});
		nextButton.setBounds(35,585,100,20);
		nextButton.setFocusable(false); //to make sure button doesnt take all focus and ither listeners 
										//continue to work
		nextButton.setToolTipText("Click to change turn to the next player");
		add(nextButton);
	}
	
	/**
	 * Creates the make suggestion button, when clicked will change current player
	 */
	public void createMakeSuggestionButton(){
		JButton suggestionButton = new JButton("Make Suggestion");
		suggestionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				frame.askMakeSuggestion(); //this needs to talk to frame.. hmm 
				frame.checkSuggestion();
				repaint();
			}
		});
		suggestionButton.setBounds(15,630,145,20);
		suggestionButton.setFocusable(false); //to make sure button doesnt take all focus and ither listeners 
										//continue to work
		suggestionButton.setToolTipText("Click to make a suggestion");
		add(suggestionButton);
	}
	
	/**
	 * Creates the make accusation button, when clicked will change current player
	 */
	public void createMakeAccusationButton(){
		JButton accusationButton = new JButton("Make Accusation");
		accusationButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				frame.askMakeAccusation();
				if(!frame.checkAccusation()){
					controlledPlayers.remove(currentPlayer);
					frame.displayLost();
				}
				else{
					frame.displayWin();
				}
				repaint();
			}
		});
		accusationButton.setBounds(15,608,145,20);
		accusationButton.setFocusable(false); //to make sure button doesnt take all focus and ither listeners 
										//continue to work
		accusationButton.setToolTipText("Click to make an accusation");
		add(accusationButton);
	}
	
	/**
	 * Fills the controlled player ArrayList so we can work with the current player and what not
	 */
	public void setControlledPlayers(ArrayList<Player> players){
		controlledPlayers = players;
		currentPlayer = controlledPlayers.get(0);
	}

	/**
	 * Create the Jlabels to hold the dice images and add them to the JPanel
	 */
	public void displayDice(){

		switch(diceRoll1){
		case(1):dice1.setIcon(diceicon1);
		break;
		case(2):dice1.setIcon(diceicon2);
		break;
		case(3):dice1.setIcon(diceicon3);
		break;
		case(4):dice1.setIcon(diceicon4);
		break;
		case(5):dice1.setIcon(diceicon5);
		break;
		case(6):dice1.setIcon(diceicon6);
		break;
		}
		switch(diceRoll2){
		case(1):dice2.setIcon(diceicon1);
		break;
		case(2):dice2.setIcon(diceicon2);
		break;
		case(3):dice2.setIcon(diceicon3);
		break;
		case(4):dice2.setIcon(diceicon4);
		break;
		case(5):dice2.setIcon(diceicon5);
		break;
		case(6):dice2.setIcon(diceicon6);
		break;
		}
		//add them to the JPanel
		add(dice1);
		add(dice2);
	}

	/**
	 * Clear the Jlabels to hold the card images for each player so they a player cant
	 * accidentaly hold the player befores card if he has less cards
	 */
	public void clearCardLabels(){
		remove(card1);
		remove(card2);
		remove(card3);
		remove(card4);
		remove(card5);
		remove(card6);
	}

	/**
	 *Sets the location of the Card labels and add them to the labels arrayList
	 */
	public void createCardAndDiceLabels(){
		labels.add(card6);
		labels.add(card5);
		labels.add(card4);
		labels.add(card3);
		labels.add(card2);
		labels.add(card1);
		
		card1.setBounds(170,520,75,120);
		card2.setBounds(250,520,75,120);
		card3.setBounds(330,520,75,120);
		card4.setBounds(410,520,75,120);
		card5.setBounds(490,520,75,120);
		card6.setBounds(570,520,75,120);
		
		dice1.setBounds(35,510,50,50);
		dice2.setBounds(90,510,50,50);
	}
	
	/**
	 * Gets the String representation of the game board
	 * @return Returns the String Board
	 */
	public String[][] getStringBoard(){
		return this.stringBoard;
	}
	
	/**
	 * Displays the players hand of cards on the canvas
	 */
	public void displayCards(){
		int i=0;
		clearCardLabels();
		for(Card c:currentPlayer.getCards()){
			//characters
			if(i<labels.size()){
				switch(c.getName()){
				case("Biggie Smalls"):
					labels.get(i).setIcon(biggieCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Biggie Smalls");
				break;
				case("Tupac"):
					labels.get(i).setIcon(tupacCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Tupac");
				break;
				case("Dr Dre"):
					labels.get(i).setIcon(drdreCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Dr Dre");
				break;
				case("Eminem"):
					labels.get(i).setIcon(eminemCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Eminem");
				break;
				case("Snoop Dogg"):
					labels.get(i).setIcon(snoopdoggCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Snoop Dogg");
				break;
				case("Jay-Z"):
					labels.get(i).setIcon(jayzCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Jay-Z");
				break;
				//weapons
				case("Glock"):
					labels.get(i).setIcon(glockCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Glock");
				break;
				case("Axe"):
					labels.get(i).setIcon(axeCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Axe");
				break;
				case("Saw"):
					labels.get(i).setIcon(sawCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Saw");
				break;
				case("Knife"):
					labels.get(i).setIcon(knifeCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Knife");
				break;
				case("Crow Bar"):
					labels.get(i).setIcon(crowbarCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Crow Bar");
				break;
				case("Pot Plant"):
					labels.get(i).setIcon(potplantCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Pot Plant");
				break;
				//rooms
				case("Bed Room"):
					labels.get(i).setIcon(bedroomCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Bed Room");
				break;
				case("Bath Room"):
					labels.get(i).setIcon(bathroomCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Bath Room");
				break;
				case("Studio"):
					labels.get(i).setIcon(studioCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Studio");
				break;
				case("Basement"):
					labels.get(i).setIcon(basementCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Basement");
				break;
				case("Jail"):
					labels.get(i).setIcon(jailCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Jail");
				break;
				case("Hood"):
					labels.get(i).setIcon(hoodCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Hood");
				break;
				case("Cali"):
					labels.get(i).setIcon(caliCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Cali");
				break;
				case("Brooklyn"):
					labels.get(i).setIcon(brooklynCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Brooklyn");
				break;
				case("Compton"):
					labels.get(i).setIcon(comptonCard);
					add(labels.get(i));
					labels.get(i).setToolTipText("Compton");
				break;
				}
				i++;
			}
		}
		//add imageicons to the JPanel
		add(card1);
		add(card2);
		add(card3);
		add(card4);
		add(card5);
		add(card6);
	}
/**
 * Paints the canvas
 * @param g Graphics pane being drawn to
 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				this.tileBoard[j][i].drawTile(g);
			}
		}
		if(this.players!=null){
		for(int i=0; i<this.players.length; i++){
			players[i].draw(g);
		}
		}
		if(this.weapons != null){
			for(int i=0; i<this.weapons.length; i++){
				weapons[i].draw(g);
			}
		}
	}

	/**
	 * Gets the preferred size of the canvas
	 * @return The preferred size of the canvas
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(650, 650);
	}
	
	/**
	 * Creates a String representation of the game board.
	 * @return A 2D String array which represents the basic game board.
	 */
	private String[][] createStringBoard(){
		String[][] board = new String[25][];
		board[0] = new String[]{"K","K","K","K","K","W",".",".","W","B","B","B","B","W",".",".",".","W","C","C","C","C","C","C","C"};
		board[1] = new String[]{"K","K","K","K","K","W",".",".","W","B","B","B","B","W",".",".",".","W","C","C","C","C","C","C","C"};
		board[2] = new String[]{"K","K","K","K","K","W",".",".","W","B","B","B","B","W",".",".",".","W","C","C","C","C","C","C","C"};
		board[3] = new String[]{"K","K","K","K","K","D",".",".","W","B","B","B","B","D",".",".",".","W","C","C","C","C","C","C","C"};
		board[4] = new String[]{"K","K","K","K","K","W",".",".","D","B","B","B","B","W",".",".",".","W","D","W","W","W","W","W","W"};
		board[5] = new String[]{"W","W","W","D","W","W",".",".","W","D","W","W","W","W",".",".",".",".",".",".",".",".",".",".","."};
		board[6] = new String[]{".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."};
		board[7] = new String[]{".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","W","W","W","W","W","W"};
		board[8] = new String[]{".",".",".",".",".",".",".",".",".","W","W","W","W","W","W","W",".",".",".","D","I","I","I","I","I"};
		board[9] = new String[]{"W","W","W","W","W","W","W",".",".","W","O","O","O","O","O","W",".",".",".","W","I","I","I","I","I"};
		board[10] = new String[]{"G","G","G","G","G","G","W",".",".","W","O","O","O","O","O","W",".",".",".","W","I","I","I","I","I"};
		board[11] = new String[]{"G","G","G","G","G","G","W",".",".","W","O","O","O","O","O","W",".",".",".","W","W","W","D","W","W"};
		board[12] = new String[]{"G","G","G","G","G","G","D",".",".","W","O","O","O","O","O","W",".",".",".",".",".",".",".",".","."};
		board[13] = new String[]{"G","G","G","G","G","G","W",".",".","W","O","O","O","O","O","W",".",".",".",".",".",".",".",".","."};
		board[14] = new String[]{"G","G","G","G","G","G","W",".",".","W","W","W","W","W","W","W",".",".","W","W","D","W","W","W","W"};
		board[15] = new String[]{"W","W","W","D","W","W","W",".",".",".",".",".",".",".",".",".",".",".","W","Y","Y","Y","Y","Y","Y"};
		board[16] = new String[]{".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","D","Y","Y","Y","Y","Y","Y"};
		board[17] = new String[]{".",".",".",".",".",".",".",".",".","W","W","D","D","W","W",".",".",".","W","W","W","W","W","W","W"};
		board[18] = new String[]{"W","W","W","W","D","W","W",".",".","W","H","H","H","H","W",".",".",".",".",".",".",".",".",".","."};
		board[19] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".",".",".",".",".",".",".",".","."};
		board[20] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".","W","D","W","W","W","W","W","W"};
		board[21] = new String[]{"L","L","L","L","L","L","D",".",".","W","H","H","H","H","W",".",".","W","S","S","S","S","S","S","S"};
		board[22] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","D",".",".","W","S","S","S","S","S","S","S"};
		board[23] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".","W","S","S","S","S","S","S","S"};
		board[24] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".","W","S","S","S","S","S","S","S"};
		return board;
	}

	/**
	 * Creates a Tile representation of the game board based on the String representation.
	 * @return A 2D array of Tiles which represent the game board
	 */
	private Tile[][] createTileBoard(){
		Tile[][] tiles = new Tile[25][25];
		for(int y=0; y<25; y++){
			for(int x=0; x<25; x++){
				String type = this.stringBoard[y][x];
				Color color = getColorFromType(type);
				tiles[x][y] = new Tile(color, type, x, y);				
			}
		}
		return tiles;
	}

	/**
	 * Helper method for createTileBoard, assigns a color based on the room's String.
	 * @param type String denoting the type of room
	 * @return A color based open the type of the room.
	 */
	private Color getColorFromType(String type) {
		switch(type){
		case("K"): return Color.ORANGE;
		case("Y"): return Color.BLUE;
		case("S"): return Color.GREEN;
		case("L"): return Color.cyan;
		case("G"): return Color.PINK;
		case("H"): return Color.RED;
		case("I"): return Color.YELLOW;
		case("B"): return Color.GRAY;
		case("C"): return Color.MAGENTA;
		case("."): return Color.WHITE;
		case("D"): return Color.LIGHT_GRAY;
		case("W"): return Color.BLACK;
		default: return null;
		}

	}

	/**
	 * Responds to the clicking of the mouse on the canvas. Moves the currently selected player around the game board.
	 * @param e The click event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getX() >=67 && e.getX() < 567 && e.getY() < 500 && this.stringBoard[e.getY()/20][(e.getX()-67)/20]!= "W"){
		currentPlayer.setXLocation((e.getX()-67)/20);
		currentPlayer.setYLocation((e.getY()/20));
		repaint();
		}
	}

	/**
	 * Inherited from MouseListener
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Responds to the mouse leaving the canvas. Will once per game pop up with a method explaining 
	 * how to play to the user.
	 * @param e Mouse Event
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	/**
	 * Inherited from MouseListener	
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {		
	}
	
	/**
	 * Inherited from MouseListener
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getX() >=67 && e.getX() < 567 && e.getY() < 500 && this.stringBoard[e.getY()/20][(e.getX()-67)/20]!= "W"){
			currentPlayer.setXLocation((e.getX()-67)/20);
			currentPlayer.setYLocation((e.getY()/20));
			repaint();
			}
	}
}

