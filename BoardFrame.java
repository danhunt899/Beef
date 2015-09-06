import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


/**
 * This class creates the frame for the board, along with the board itself and 
 * all the other pop ups which go along with it.
 *
 * @author rayg
 * @author danhunt
 *
 */
public class BoardFrame extends JFrame implements KeyListener{
	private BoardCanvas canvas; 
	private JMenu helpMenu;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem restart;
	private JMenuItem quitGame;
	private JMenuItem help;

	private JPanel panel = new JPanel(); //panel for asking character questions
	private JPanel wPanel = new JPanel(); //panel for asking weapon questions
	private JPanel rPanel = new JPanel(); //panel for asking room questions
	private ArrayList<JRadioButton> characterButtons = new ArrayList<JRadioButton>(); //character buttons
	private ArrayList<JRadioButton> weaponButtons = new ArrayList<JRadioButton>(); //weapon buttons
	private ArrayList<JRadioButton> roomButtons = new ArrayList<JRadioButton>(); //room buttons
	private ButtonGroup characterButtonGroup = new ButtonGroup(); //group of chsaracter buttons.. usefullish
	private ButtonGroup weaponButtonGroup = new ButtonGroup();
	private ButtonGroup roomButtonGroup = new ButtonGroup();

	private ArrayList<String> controlledPlayers = new ArrayList<String>(); //human players
	private String[] inQuestion = new String[3]; //the character,weapon and room in suggestion

	//images for each character
	private ImageIcon beefIcon = new ImageIcon(BoardFrame.class.getResource("images/Beef-Card.png"));
	private Player[] players;
	private Weapon[] weapons;
	
	private List<Card> cards = new ArrayList<Card>(); //list of cards
	private String[] solution = new String[3]; //array of solution strings
	/**
	 * Constructor:
	 * @param players Players to be drawn on the board
	 * @param weapons Weapons to be drawn on the board
	 */
	public BoardFrame(Player[] players, Weapon[] weapons) {
		super("Beef");
		this.weapons = weapons;
		this.players = players;
		createCharacterButtons();
		createWeaponButtons();
		createRoomButtons();
		//build the menus
		buildMenus();
		music();
	

		canvas = new BoardCanvas(this.players, this.weapons,this); // create canvas
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.width;
		double height = screenSize.height;
		setBounds((int)(width/2)-325, (int)(height/2)-350, 0, 0);
		setLayout(new BorderLayout()); // use border layout
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(true);
		setVisible(true); // make sure we are visible!
		addKeyListener(this);
	}
	
	/**
	 * Set the cards.
	 * @param cards
	 */
	public void setCards(List<Card> cards){
		this.cards = cards;
	}

	/**
	 * Fills the solution envelope, removing the cards it adds from the set to ensure they cannot be given to
	 * players. three loops not ideal but getting job done, can change if need be
	 */
	public void makeSolution(){
		for(Card c:cards){
			if(c.getType().equalsIgnoreCase("player")){
				solution[0] = c.getName();
				cards.remove(c);
				break;
			}
		}
		for(Card c:cards){
			if(c.getType().equalsIgnoreCase("weapon")){
				solution[1] = c.getName();
				cards.remove(c);
				break;
			}
		}
		for(Card c:cards){
			if(c.getType().equalsIgnoreCase("room")){
				solution[2] = c.getName();
				cards.remove(c);
				break;
			}
		}
	}

	/**
	 * Checks a players accusation against the the cards held in the solution envelope
	 * @param player, accused player
	 * @param weapon, accused weapon
	 * @param room, accused room
	 * @return true if matches solution, player wins
	 *  	   false if doesnt match, player loses
	 */
	public boolean checkAccusation(){
		if(solution[0].equalsIgnoreCase(inQuestion[0])){
			if(solution[1].equalsIgnoreCase(inQuestion[1])){
				if(solution[2].equalsIgnoreCase(inQuestion[2])){
					return true; //was correct, player wins
				}
			}
		}
		return false; //accusation was incorrect, player loses
	}

	/**
	 * Takes the suggested player, weapon and room and checks every players hand of cards to see if they can
	 * appeal the suggestion. 
	 * @param player, suggested player
	 * @param weapon. suggested weapon
	 * @param room, suggested room
	 * @return returns false if someone contains the suggested card
	 * 		   otherwise true if the suggestion cannot be denied
	 */
	public boolean checkSuggestion(){
		for(int i=0;i<players.length;i++){ //changed to all players not just controlled
			for(Card c: players[i].getCards()){ //checks each card in each players hand 
				if(c.getName().equalsIgnoreCase(inQuestion[0]) || c.getName().equalsIgnoreCase(inQuestion[1]) ||
						c.getName().equalsIgnoreCase(inQuestion[2])){
					return false;
				}
			}		
		}
		return true;
	}
	/**
	 * Gets the board canvas
	 * @return the Canvas field.
	 */
	public BoardCanvas getBoardCanvas(){
		return this.canvas;
	}
	
	/**
	 * Build the menu bar for the frame.
	 */
	public void buildMenus(){
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		helpMenu = new JMenu("Help");
		help = new JMenuItem("Help            H");
		helpMenu.add(help);
		menuBar.add(helpMenu);

		//Build second menu.
		gameMenu = new JMenu("Game");
		restart = new JMenuItem("Restart");
		gameMenu.add(restart);
		quitGame = new JMenuItem("Quit Game       Q");
		gameMenu.add(quitGame);
		menuBar.add(gameMenu);

		/*Responds to click. Closes window down (quits game) */
		quitGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {//this stuff may need be in canvas for JPanel stuff
				System.exit(0); // closes window
			}
		});

		//displays help
		help.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {//this stuff may need be in canvas for JPanel stuff
				JOptionPane.showMessageDialog(null, "Get Playing!\nTo play, click the roll dice button "
						+"and then click the\nboard or use the directional keys to move the\nplayer! \n\n"
						+"Press i for info!");
			}
		});
		
		// put the menubar on the frame
		this.setJMenuBar(menuBar);
	}

	/**
	 * Ask the players for their names.
	 */
	public void askPlayersNames(){
		JTextField field = new JTextField();
	}

	/**
	 * prompt the user to input how many players will be playing this round of the game
	 * @return the int of how mant players
	 */
	public int askManyPlayers(){
		//uses type object, not sure if i like this. unsure how to change atm tho
		String[] possibleValues = { "Three", "Four", "Five", "Six" };
		Object selectedValue = JOptionPane.showInputDialog(null,
				"How many Players?", "Input",
				JOptionPane.INFORMATION_MESSAGE, beefIcon,
				possibleValues, possibleValues[0]);

		//convert input from string to int
		if(selectedValue==null){
			System.exit(0); // closes window as they have exited or something like that
		}
		else if(selectedValue.equals("Three")){
			return 3;
		}
		else if(selectedValue.equals("Four")){
			return 4;
		}
		else if(selectedValue.equals("Five")){
			return 5;
		}
		else if(selectedValue.equals("Six")){
			return 6;
		}
		return 0; //should be unreachable
	}

	/**
	 * shows the user that they won!
	 * @param numPlayers
	 */
	public void displayWin(){
		JOptionPane.showMessageDialog(null,"You Won!!","You Won!",0,beefIcon);

	}
	
	/**
	 * notifies the user that they lost
	 * @param numPlayers
	 */
	public void displayLost(){
		JOptionPane.showMessageDialog(null,"That was incorrect, you are removed from the game","Incorrect Accusation", 0,beefIcon);

	}
	
	/**
	 * Adds radio buttons and prompts for players to pick the character they would like to bes
	 */
	public void askWhichPlayer(int numPlayers){
		int x=1;
		boolean found = false;

		//loops through each player to pick their character
		while(x < numPlayers+1){ //plus 1 to start from player 1 not player 0
			int i = JOptionPane.showConfirmDialog(null,panel,"Player "+x+" pick your character",JOptionPane.CANCEL_OPTION,0,beefIcon);
			if(i==2 || i==-1){
				System.exit(0);
			}
			for(JRadioButton j:characterButtons){ //loops through to check which has been selected
				if(j.isSelected()){
					found = true;
					j.setEnabled(false);
					characterButtonGroup.clearSelection(); //clears selected button so cant add same one twice
					controlledPlayers.add(j.getActionCommand());
				}
			}
			x++;
			if(!found){
				x--;
			}
			found= false;
		}
	}

	/**
	 * prompts the user to input their name
	 * @param numPlayers, number of players who are going to play/need to be asked
	 */
	public void askPlayersNames(int numPlayers){
		int x=1;
		while(x < numPlayers+1){ //plus 1 to start from player 1 not player 0
			Object input  = JOptionPane.showInputDialog(null,"Please enter your name player "+x+":","Player "+x+"'s name",JOptionPane.ERROR_MESSAGE,beefIcon,null,null);
			x++;
			if(input==null){
				System.exit(0);
			}
			if(input.equals("")){
				x--;
			}
		}
	}

	/**
	 * Tells the canvas to display the roll dice button
	 */
	public void displayDice(){
		//canvas.displayDice();
		canvas.createRollButton();
		//return canvas.getDiceRolled();
	}
	
	/**
	 * Tells the canvas to display the next turn button
	 */
	public void displayNextTurnButton(){
		canvas.createNextTurnButton();
	}
	
	/**
	 * Tells the canvas to display the next turn button
	 */
	public void setControlledPlayers(ArrayList<Player> players){
		canvas.setControlledPlayers(players);
	}

	/**
	 * tells canvas to draw this players cards
	 */
	public void displayCards(){
		canvas.displayCards();
		repaint();
	}

	/**
	 * This method asks the user if they would like to enter room. This is called when the move
	 * they have suggested taking encounters a door.
	 * @return true if they would like to enter
	 *         false otherwise
	 */
	public boolean askEnterRoom(){

		int i = JOptionPane.showConfirmDialog(null,"Would you like to enter this room?","Entering a room",JOptionPane.YES_NO_OPTION,0,beefIcon);
		if(i==0){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * asks if user would like to end turn or make an accusation
	 * @return true if they would like to make an accusation
	 */
	public boolean askEndTurnOrAccusation(){
		JPanel panel = new JPanel();

		//create JRadioButtons
		JRadioButton acusationButton = new JRadioButton("Make Acusation");
		acusationButton.setActionCommand("Make Acusation");

		JRadioButton endTurnButton = new JRadioButton("End turn");
		endTurnButton.setActionCommand("End turn");

		//add buttons to group
		ButtonGroup group = new ButtonGroup();
		group.add(acusationButton);

		//add to JPanel
		panel.add(acusationButton);
		panel.add(endTurnButton);

		int i = JOptionPane.showConfirmDialog(null,panel,
				"Acusation or end turn",JOptionPane.CANCEL_OPTION,0,beefIcon);
		if(i==0){
			addToSuggestedPlayer();
			addToSuggestedWeapon();
			addToSuggestedRoom();
			return true;
		}
		else if(i==1){
			return false;
		}
		else{
			System.exit(0);
		}
		return false; //should be unreachable
	}

	/**
	 * This method asks the user if they would like to make a suggestion. This is called when the player
	 * moves into a room.
	 * @return true if they would like to make a suggestion
	 *         false otherwise
	 */
	public boolean askMakeSuggestion(){
		int i = JOptionPane.showConfirmDialog(null,"Would you like to make a suggestion?","Suggestion",JOptionPane.YES_NO_OPTION,0,beefIcon);
		if(i==0){
			addToSuggestedPlayer();
			addToSuggestedWeapon();
			addToSuggestedRoom();
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Prompts the user for which character they think committed the murder and adds that character into the inQuestion array as the first
	 * element. inQuestion[0] will always reference the player in question
	 */
	public void addToSuggestedPlayer(){
		boolean found = false;

		setButtonsVisible();
		while(!found){
			int i = JOptionPane.showConfirmDialog(null,panel,"Who dunnit?",JOptionPane.CANCEL_OPTION,0,beefIcon);
			if(i==2){
				System.exit(0);
			}
			for(JRadioButton j:characterButtons){
				if(j.isSelected()){
					found = true;
					characterButtonGroup.clearSelection();
					inQuestion[0] = j.getActionCommand();
				}
			}
		}
	}
	
	

	/**
	 * Prompts the user and adds the weapon which the player thinks is the murder weapon into the inQuestion array as the second
	 * element. inQuestion[1] 
	 */
	public void addToSuggestedWeapon(){
		boolean found =false;

		setButtonsVisible();
		while(!found){
			int i = JOptionPane.showConfirmDialog(null,wPanel,"Wit what?",JOptionPane.CANCEL_OPTION,0,beefIcon);
			if(i==2){
				System.exit(0);
			}
			for(JRadioButton j:weaponButtons){
				if(j.isSelected()){
					found = true;
					characterButtonGroup.clearSelection();
					inQuestion[1] = j.getActionCommand();
				}
			}
		}
	}

	/**
	 * Prompts the user and adds the room which the player thinks is the murder room into the inQuestion array as the second
	 * element. inQuestion[2] 
	 */
	public void addToSuggestedRoom(){
		boolean found=false;

		setButtonsVisible();
		while(!found){
			int i = JOptionPane.showConfirmDialog(null,rPanel,"Where did it go down?",JOptionPane.CANCEL_OPTION,0,beefIcon);
			if(i==2){
				System.exit(0);
			}
			for(JRadioButton j:roomButtons){
				if(j.isSelected()){
					found = true;
					characterButtonGroup.clearSelection();
					inQuestion[2] = j.getActionCommand();
				}
			}
		}
	}

	/**
	 * Asks the player if they would like to play again
	 */
	public boolean askPlayAgain(){

		int i = JOptionPane.showConfirmDialog(null,"Would you like to play again?","Play again",JOptionPane.YES_NO_OPTION,0,beefIcon);
		if(i==0){
			return true;
		}
		else{
			System.exit(0);
			return false;
		}
	}


	/**
	 * Asks the user if they would like to roll the dice or make an accusation
	 * @return true if roll dice
	 * 			false if accusation
	 */		   
	public boolean rollDiceOrAccusation(){
		JPanel panel = new JPanel();

		//create JRadioButtons
		JRadioButton diceButton = new JRadioButton("Roll Dice");
		diceButton.setActionCommand("Roll Dice");

		JRadioButton acusationButton = new JRadioButton("Make Acusation");
		acusationButton.setActionCommand("Make Acusation");


		//add buttons to group
		ButtonGroup group = new ButtonGroup();
		group.add(diceButton);
		group.add(acusationButton);

		//add to JPanel
		panel.add(diceButton);
		panel.add(acusationButton);

		int i = JOptionPane.showConfirmDialog(null,panel,
				"Acusation or roll dice",JOptionPane.CANCEL_OPTION,0,beefIcon);
		if(i==0){
			return true;
		}
		else if(i==1){
			addToSuggestedPlayer();
			addToSuggestedWeapon();
			addToSuggestedRoom();
			return false;
		}
		else{
			System.exit(0);
		}
		return false; //should be unreachable
	}

	/**
	 * Makes an accusation
	 */
	public void askMakeAccusation(){
		addToSuggestedPlayer();
		addToSuggestedWeapon();
		addToSuggestedRoom();
	}

	//helper methods:

	/**
	 * helper method asking questions about Characters
	 * creates all the JRadioButtons
	 */
	public void createCharacterButtons(){
		//creates the buttons
		JRadioButton biggieButton = new JRadioButton("Biggie Smalls");
		biggieButton.setMnemonic(KeyEvent.VK_B);
		biggieButton.setActionCommand("Biggie Smalls");

		JRadioButton tupacButton = new JRadioButton("Tupac");
		tupacButton.setMnemonic(KeyEvent.VK_T);
		tupacButton.setActionCommand("Tupac");

		JRadioButton drdreButton = new JRadioButton("Dr Dre");
		drdreButton.setMnemonic(KeyEvent.VK_D);
		drdreButton.setActionCommand("Dr Dre");

		JRadioButton eminemButton = new JRadioButton("Eminem");
		eminemButton.setMnemonic(KeyEvent.VK_E);
		eminemButton.setActionCommand("Eminem");

		JRadioButton snoopButton = new JRadioButton("Snoop Dogg");
		snoopButton.setMnemonic(KeyEvent.VK_S);
		snoopButton.setActionCommand("Snoop Dogg");

		JRadioButton jayzButton = new JRadioButton("Jay-Z");
		jayzButton.setMnemonic(KeyEvent.VK_J);
		jayzButton.setActionCommand("Jay-Z");

		//add buttons to list of button
		characterButtons.add(biggieButton);
		characterButtons.add(tupacButton);
		characterButtons.add(drdreButton);
		characterButtons.add(eminemButton);
		characterButtons.add(snoopButton);
		characterButtons.add(jayzButton);

		//add all buttons to the buttonGroup
		characterButtonGroup.add(biggieButton);
		characterButtonGroup.add(tupacButton);
		characterButtonGroup.add(drdreButton);
		characterButtonGroup.add(eminemButton);
		characterButtonGroup.add(snoopButton);
		characterButtonGroup.add(jayzButton);

		//add buttons to the panel
		panel.add(biggieButton);
		panel.add(tupacButton);
		panel.add(drdreButton);
		panel.add(eminemButton);
		panel.add(snoopButton);
		panel.add(jayzButton);
	}

	/**
	 * helper method asking questions about Weapons
	 * creates all the JRadioButtons
	 */
	public void createWeaponButtons(){
		//creates the buttons
		JRadioButton glockButton = new JRadioButton("Glock");
		glockButton.setMnemonic(KeyEvent.VK_G);
		glockButton.setActionCommand("Glock");

		JRadioButton axeButton = new JRadioButton("Axe");
		axeButton.setMnemonic(KeyEvent.VK_A);
		axeButton.setActionCommand("Axe");

		JRadioButton sawButton = new JRadioButton("Saw");
		sawButton.setMnemonic(KeyEvent.VK_S);
		sawButton.setActionCommand("Saw");

		JRadioButton knifeButton = new JRadioButton("Knife");
		knifeButton.setMnemonic(KeyEvent.VK_K);
		knifeButton.setActionCommand("Knife");

		JRadioButton crowbarButton = new JRadioButton("Crow Bar");
		crowbarButton.setMnemonic(KeyEvent.VK_C);
		crowbarButton.setActionCommand("Crow bar");

		JRadioButton potplantButton = new JRadioButton("Pot Plant");
		potplantButton.setMnemonic(KeyEvent.VK_P);
		potplantButton.setActionCommand("Pot Plant");

		//add buttons to list of button
		weaponButtons.add(glockButton);
		weaponButtons.add(axeButton);
		weaponButtons.add(sawButton);
		weaponButtons.add(knifeButton);
		weaponButtons.add(crowbarButton);
		weaponButtons.add(potplantButton);

		//add all buttons to the buttonGroup
		weaponButtonGroup.add(glockButton);
		weaponButtonGroup.add(axeButton);
		weaponButtonGroup.add(sawButton);
		weaponButtonGroup.add(knifeButton);
		weaponButtonGroup.add(crowbarButton);
		weaponButtonGroup.add(potplantButton);

		//add buttons to the panel
		wPanel.add(glockButton);
		wPanel.add(axeButton);
		wPanel.add(sawButton);
		wPanel.add(knifeButton);
		wPanel.add(crowbarButton);
		wPanel.add(potplantButton);
	}

	/**
	 * helper method asking questions about Rooms for accusations
	 * creates all the JRadioButtons
	 */
	public void createRoomButtons(){
		//creates the buttons
		JRadioButton bedroomButton = new JRadioButton("Bed Room");
		bedroomButton.setMnemonic(KeyEvent.VK_B);
		bedroomButton.setActionCommand("Bed Room");

		JRadioButton bathroomButton = new JRadioButton("Bath Room");
		bathroomButton.setMnemonic(KeyEvent.VK_A);
		bathroomButton.setActionCommand("Bath Room");

		JRadioButton studioButton = new JRadioButton("Studio");
		studioButton.setMnemonic(KeyEvent.VK_S);
		studioButton.setActionCommand("Studio");

		JRadioButton basementButton = new JRadioButton("Basement");
		basementButton.setMnemonic(KeyEvent.VK_E);
		basementButton.setActionCommand("Basement");

		JRadioButton jailButton = new JRadioButton("Jail");
		jailButton.setMnemonic(KeyEvent.VK_J);
		jailButton.setActionCommand("Jail");

		JRadioButton hoodButton = new JRadioButton("Hood");
		hoodButton.setMnemonic(KeyEvent.VK_H);
		hoodButton.setActionCommand("Hood");
		
		JRadioButton caliButton = new JRadioButton("Cali");
		caliButton.setMnemonic(KeyEvent.VK_C);
		hoodButton.setActionCommand("Cali");
		
		JRadioButton brooklynButton = new JRadioButton("Brooklyn");
		brooklynButton.setMnemonic(KeyEvent.VK_R);
		hoodButton.setActionCommand("Brooklyn");
		
		JRadioButton comptonButton = new JRadioButton("Compton");
		comptonButton.setMnemonic(KeyEvent.VK_C);
		hoodButton.setActionCommand("Compton");

		//add buttons to list of button
		roomButtons.add(bedroomButton);
		roomButtons.add(bathroomButton);
		roomButtons.add(studioButton);
		roomButtons.add(basementButton);
		roomButtons.add(jailButton);
		roomButtons.add(hoodButton);
		roomButtons.add(caliButton);
		roomButtons.add(brooklynButton);
		roomButtons.add(comptonButton);

		//add all buttons to the buttonGroup
		roomButtonGroup.add(bedroomButton);
		roomButtonGroup.add(bathroomButton);
		roomButtonGroup.add(studioButton);
		roomButtonGroup.add(basementButton);
		roomButtonGroup.add(jailButton);
		roomButtonGroup.add(hoodButton);
		roomButtonGroup.add(caliButton);
		roomButtonGroup.add(brooklynButton);
		roomButtonGroup.add(comptonButton);

		//add buttons to the panel
		rPanel.add(bedroomButton);
		rPanel.add(bathroomButton);
		rPanel.add(studioButton);
		rPanel.add(basementButton);
		rPanel.add(jailButton);
		rPanel.add(hoodButton);
		rPanel.add(caliButton);
		rPanel.add(brooklynButton);
		rPanel.add(comptonButton);
	}

	/**
	 * sets all the character buttons to unselected and visible(non grayed)
	 */
	public void setButtonsVisible(){
		for(JRadioButton j:characterButtons){
			j.setEnabled(true);
			j.setSelected(false);
		}
	}

	/**
	 * returns the array of strings representing the things in question
	 */
	public String[] getInQuestion(){
		return inQuestion;
	}

	/**
	 * Get the controlled players
	 * @return ArrayLis of controlled players
	 */
	public ArrayList<String> getControlledPlayers(){
		return controlledPlayers;
	}


	//KeyListener Methods
	@Override
	public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == KeyEvent.VK_Q){
				gameMenu.setSelected(true); //to make it show blue first
				quitGame.doClick(); //shortcut is Q to quit game
			}
	}

	/**
	 * The keyPressed method allows the player to move around the board
	 * with the arrow keys and also displays info with i.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 72){
			JOptionPane.showMessageDialog(null, "Get Playing!\nTo play, click the roll dice button "
					+"and then click the\nboard or use the directional keys to move the\nplayer! \n\n"
					+"Press i for info!");
		}
		else if(e.getKeyCode() == 81){
			gameMenu.setSelected(true); //to make it show blue first
			quitGame.doClick(); //shortcut is Q to quit game
		}
		else if(e.getKeyCode() == 73){
			JOptionPane.showMessageDialog(null, "This is a remake of the tradition Cluedo game, featuring American\n"
					+"rappers from the 1990s. The rooms, weapons and players\nhave all been renamed to fit the theme!");
		}
		else if(e.getKeyCode() == 37){
			Player p = this.canvas.getCurrentPlayer();
			if(p.getXLocation()>0 && this.canvas.getStringBoard()[p.getYLocation()][p.getXLocation()-1]!="W"){
				p.setXLocation(p.getXLocation()-1);
				this.canvas.repaint();
			}
		}
		else if(e.getKeyCode() == 38){
			Player p = this.canvas.getCurrentPlayer();
			if(p.getYLocation()>0 && this.canvas.getStringBoard()[p.getYLocation()-1][p.getXLocation()]!="W"){
				p.setYLocation(p.getYLocation()-1);
				this.canvas.repaint();
			}
		}
		else if(e.getKeyCode() == 39){
			Player p = this.canvas.getCurrentPlayer();
			if(p.getXLocation()<24 && this.canvas.getStringBoard()[p.getYLocation()][p.getXLocation()+1]!="W"){
				p.setXLocation(p.getXLocation()+1);
				this.canvas.repaint();
			}
		}
		else if(e.getKeyCode() == 40){
			Player p = this.canvas.getCurrentPlayer();
			if(p.getYLocation()<24 && this.canvas.getStringBoard()[p.getYLocation()+1][p.getXLocation()]!="W"){
				p.setYLocation(p.getYLocation()+1);
				this.canvas.repaint();
			}
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {		
	}
	
	public void music(){
		  try {
			   File file = new File("src/Beef.wav");
			   Clip clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   clip.start();
			   //Thread.sleep(clip.getMicrosecondLength());
			  } catch (Exception e) {
			   System.err.println(e.getMessage());
			  }
			 }
	
}

