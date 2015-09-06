import java.util.Scanner;

/**
 * This class builds holds and maintains the game board
 * @author danhunt
 * @author rayg
 *
 */
public class Board {
	
	private String[][] board; //holds the game board, a 2D array of strings
	private String[] key; //holds the keys for the characters on the game board
	private Player[] players; //all the players in the game
	private Weapon[] weapons; //all the weapons in the game
	private String[] solution; //solution[0] = player,solution[1] = weapon,solution[2] = room
	
	public Board(Player[] players, Weapon[] weapons){
		this.players = players;
		this.weapons = weapons;
		
		//create the key array
		this.key = new String[25];
		key[0] = "W = Wall, D = Door";
		key[1] = "Players";
		key[2] = "A = Miss Scarlet";
		key[3] = "M = Colonel Mustard";
		key[4] = "T = Mrs White";
		key[5] = "R = The Reverend Green";
		key[6] = "Q = Mrs Peacock";
		key[7] = "P = Professor Plum";
		key[8] = "Weapons";
		key[9] = "@ = Spanner";
		key[10] = "$ = Lead Pipe";
		key[11] = "% = Dagger";
		key[12] = "& = Rope";
		key[13] = "* = Revolver";
		key[14] = "! = Candlestick";
		key[15] = "Rooms";
		key[16] = "K = Kitchen";
		key[17] = "B = Ballroom";
		key[18] = "C = Conservatory";
		key[19] = "G = Dining Room";
		key[20] = "L = Lounge";
		key[21] = "H = Hall Room";
		key[22] = "S = Study";
		key[23] = "Y = Library";
		key[24] = "I = Billard Room";
		
		//create the board array
		
		String[][] board = new String[25][];
		board[0] = new String[]{"K","K","K","K","K","W","A",".","W","B","B","B","B","W",".","M",".","W","C","C","C","C","C","C","C"};
		board[1] = new String[]{"K","K","K","K","K","W",".",".","W","B","B","B","B","W",".",".",".","W","C","C","$","C","C","C","C"};
		board[2] = new String[]{"@","K","K","K","K","W",".",".","W","B","B","B","B","W",".",".",".","W","C","C","C","C","C","C","C"};
		board[3] = new String[]{"K","K","K","K","K","D",".",".","W","B","B","B","B","D",".",".",".","W","C","C","C","C","C","C","C"};
		board[4] = new String[]{"K","K","K","K","K","W",".",".","D","B","B","B","B","W",".",".",".","W","D","W","W","W","W","W","W"};
		board[5] = new String[]{"W","W","W","D","W","W",".",".","W","D","W","W","W","W",".",".",".",".",".",".",".",".",".",".","."};
		board[6] = new String[]{".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","."};
		board[7] = new String[]{"P",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","W","W","W","W","W","W"};
		board[8] = new String[]{".",".",".",".",".",".",".",".",".","W","W","W","W","W","W","W",".",".",".","D","I","I","I","I","I"};
		board[9] = new String[]{"W","W","W","W","W","W","W",".",".","W","O","O","O","O","O","W",".",".",".","W","I","I","I","I","I"};
		board[10] = new String[]{"G","G","G","G","G","G","W",".",".","W","O","O","O","O","O","W",".",".",".","W","I","I","I","I","I"};
		board[11] = new String[]{"G","G","G","G","G","G","W",".",".","W","O","O","O","O","O","W",".",".",".","W","W","W","D","W","W"};
		board[12] = new String[]{"%","G","G","G","G","G","D",".",".","W","O","O","O","O","O","W",".",".",".",".",".",".",".",".","."};
		board[13] = new String[]{"G","G","G","G","G","G","W",".",".","W","O","O","O","O","O","W",".",".",".",".",".",".",".",".","T"};
		board[14] = new String[]{"G","G","G","G","G","G","W",".",".","W","W","W","W","W","W","W",".",".","W","W","D","W","W","W","W"};
		board[15] = new String[]{"W","W","W","D","W","W","W",".",".",".",".",".",".",".",".",".",".",".","W","Y","Y","Y","Y","Y","!"};
		board[16] = new String[]{".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".",".","D","Y","Y","Y","Y","Y","Y"};
		board[17] = new String[]{"Q",".",".",".",".",".",".",".",".","W","W","D","D","W","W",".",".",".","W","W","W","W","W","W","W"};
		board[18] = new String[]{"W","W","W","W","D","W","W",".",".","W","H","H","H","H","W",".",".",".",".",".",".",".",".",".","."};
		board[19] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".",".",".",".",".",".",".",".","."};
		board[20] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".","W","D","W","W","W","W","W","W"};
		board[21] = new String[]{"L","L","L","L","L","L","D",".",".","W","H","H","H","H","W",".",".","W","S","S","S","S","S","S","S"};
		board[22] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","D",".",".","W","S","S","S","S","S","S","S"};
		board[23] = new String[]{"L","L","L","L","L","L","W",".",".","W","H","H","H","H","W",".",".","W","S","S","S","S","S","S","S"};
		board[24] = new String[]{"L","L","*","L","L","L","W",".",".","W","H","&","H","H","W","R",".","W","S","S","S","S","S","S","S"};
		this.board = board;
	}
	
	/**
	 * Gives a player the options for moving out of the room, then moves them.
	 * @param player player to be moved.
	 */
	public void exitRoom(Player player){// chose not to break it up as each room has different exit options.
		Scanner scan = new Scanner( System.in );
		String input ; // = the user input
		if(player.getBoardLoc().equals(".")||(player.getBoardLoc().equals("D"))){
			return;
		}

		int x = player.getXLocation();
		int y = player.getYLocation();
		switch(player.getBoardLoc()){
		case "K":
			System.out.println("There are two exits in the kitchen, and one secret passage way.");
			System.out.println("Which would you like? A) East B) South C) Passage to Study.");
			input =  scan.next(); // = the user input
			//turn into capitals
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "K";
				player.setXLocation(6);
				player.setYLocation(3);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "K";
				player.setXLocation(3);
				player.setYLocation(6);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
			else if(input.equalsIgnoreCase("C")){
				movePlayerToRoom(player, "Study");
			}
		break;
		case "B": 
			System.out.println("There are three exits in the ballroom.");
			System.out.println("Which would you like? a. East b. South c. West.");
			input = scan.next();// get the user input
			//turn into capitals
			if(input.equalsIgnoreCase("A")){ 
				this.board[y][x] = "B";
				player.setXLocation(14);
				player.setYLocation(3);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "B";
				player.setXLocation(9);
				player.setYLocation(6);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
			else if(input.equalsIgnoreCase("C")){
				this.board[y][x] = "B";
				player.setXLocation(7);
				player.setYLocation(4);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
		break;
		case "C": 
			System.out.println("There is one exit in the conservatory, and one secret passage way.");
			System.out.println("Which would you like? a. South b. Passage to Lounge.");
			input = scan.next();//get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "C";
				player.setXLocation(18);
				player.setYLocation(5);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				movePlayerToRoom(player, "Lounge");
			}
		break;
		case "G": 
			System.out.println("There are two exits in the dining room.");
			System.out.println("Which would you like? a. East b. South.");
			input = scan.next();//get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "G";
				player.setXLocation(7);
				player.setYLocation(12);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "G";
				player.setXLocation(3);
				player.setYLocation(16);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
		break;
		case "L": 
			System.out.println("There are two exits in the lounge, and one secret passage way.");
			System.out.println("Which would you like? a. East b. North c. Passage to Conservatory.");
			input = scan.next();// get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "L";
				player.setXLocation(7);
				player.setYLocation(21);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "L";
				player.setXLocation(4);
				player.setYLocation(18);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
			else if(input.equalsIgnoreCase("C")){
				movePlayerToRoom(player, "Conservatory");
			}
		break;
		case "H": 
			System.out.println("There are two exits in the hall.");
			System.out.println("Which would you like? a. East b. North.");
			input = scan.next();//get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "H";
				player.setXLocation(15);
				player.setYLocation(22);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "H";
				player.setXLocation(12);
				player.setYLocation(16);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
		break;
		case "S": 
			System.out.println("There are one exit in the study, and one secret passage way.");
			System.out.println("Which would you like? a. North b. Passage to Kitchen.");
			input = scan.next();// get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "S";
				player.setXLocation(18);
				player.setYLocation(19);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				movePlayerToRoom(player, "Kitchen");
			}
		break;
		case "Y": 
			System.out.println("There are two exits in the library.");
			System.out.println("Which would you like? a. North b. West.");
			input = scan.next();//get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "Y";
				player.setXLocation(20);
				player.setYLocation(13);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "Y";
				player.setXLocation(17);
				player.setYLocation(16);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
		break;
		case "I": 
			System.out.println("There are two exits in the billard room.");
			System.out.println("Which would you like? a.South b.West.");
			input = scan.next();//get the user input
			//turn into capitals
			
			if(input.equalsIgnoreCase("A")){
				this.board[y][x] = "I";
				player.setXLocation(22);
				player.setYLocation(12);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
				}
			else if(input.equalsIgnoreCase("B")){
				this.board[y][x] = "I";
				player.setXLocation(18);
				player.setYLocation(8);
				player.setLocation(".");
				this.board[player.getYLocation()][player.getXLocation()] = player.getInitials();
			}
		default:
		}
		//}
	}
	/**
	 * Determines whether a player is in a room or not.
	 * @param player
	 * @return true if player is in room, false if not.
	 */
	public boolean inRoom(Player player){
		switch(player.getBoardLoc()){
		case "K": return true;
		case "B": return true;
		case "C": return true;
		case "G": return true;
		case "L": return true;
		case "H": return true;
		case "S": return true;
		case "Y": return true;
		case "I": return true;
		default: return false;
		}
	}
	
	
	
	/**
	 * This method moves a player into a room when an suggestion is made.
	 * @Param player is the player being moved
	 * @Param room is room being moved to.
	 */
	public void movePlayerToRoom(Player player, String room){
		int x = player.getXLocation();
		int y = player.getYLocation();
		this.board[y][x] = player.getBoardLoc();
		player.moveTo(room);
		x = player.getXLocation();
		y = player.getYLocation();
		this.board[y][x] = player.getInitials();
	}
	
	/**
	 * This method moves player around the board
	 * @param player the player being moved
	 * @param steps number of steps to move
	 * @param direction direction to move; U D L R
	 * @return return true if move was successful, false if not.
	 */
	public boolean movePlayer(Player player, int steps, String direction){
		int x = player.getXLocation();
		int y = player.getYLocation();
		switch(direction){
			case("U"):
				if(y<steps){
					return false;
				}
				for(int i=1; i<=steps; i++){
					if(this.board[y-i][x].equals("W")){
						return false;
					}
				}break;
			case("D"):
				if(y+steps>24){
					return false;
				}
				for(int i=1; i<=steps; i++){
					if(this.board[y+i][x].equals("W")){
						return false;
					}
				}break;
			case("L"):
				if(x<steps){
					return false;
				}
				for(int i=1; i<=steps; i++){
					if(this.board[y][x-i].equals("W")){
						return false;
					}
				}break;
			case("R"):
				if(x+steps>24){
					return false;
				}
				for(int i=1; i<=steps; i++){
					if(this.board[y][x+i].equals("W")){
						return false;
					}
				}break;
			default:
		}
		
		this.board[y][x] = player.getBoardLoc();
		switch(direction){
			case("U"):
				player.moveUp(steps);
				break;
			case("D"):
				player.moveDown(steps);
				break;
			case("L"):
				player.moveLeft(steps);
				break;
			case("R"): 
				player.moveRight(steps);
			 	break;
			default:
		}
		
		x = player.getXLocation();
		y = player.getYLocation();
		player.setLocation(this.board[y][x]);
		this.board[y][x] = player.getInitials();
		return true;
		
	}
	/**
	 * Checks to see whether or not a player is in a location adjacent to a door to a room
	 * @param player player being checked
	 * @return room that is adjacent to the player, or null if none
	 */
	public String roomAdjacentTo(Player player){
		int x = player.getXLocation();
		int y = player.getYLocation();
		String room = "";
		//first check if the player is on a door
		if(player.getBoardLoc().equals("D")){
			if(!this.board[y+1][x].equals(".") && !this.board[y+1][x].equals("W")){
				room = this.board[y+1][x];
			}
			else if(!this.board[y-1][x].equals(".") && !this.board[y-1][x].equals("W")){
				room = this.board[y-1][x];
			}
			else if(!this.board[y][x+1].equals(".") && !this.board[y][x+1].equals("W")){
				room = this.board[y][x+1];
			}
			else if(!this.board[y][x-1].equals(".") && !this.board[y][x-1].equals("W")){
				room = this.board[y][x-1];
			}
		}
		//now check for doors in squares adjacent to the player
		if(y<24){
			if(this.board[y+1][x].equals("D")){
				room = this.board[y+2][x];
			}
		}
		if(y>0){
			if(this.board[y-1][x].equals("D")){
				room = this.board[y-2][x];
			}
		}
		if(x<24){
			if(this.board[y][x+1].equals("D")){
				room = this.board[y][x+2];
			}
		}
		if(x>0){
			if(this.board[y][x-1].equals("D")){
				room = this.board[y][x-2];
			}
		}
		// now check if player is in a room
		switch(player.getBoardLoc()){
		case "K": return "Kitchen";
		case "B": return "Ballroom";
		case "C": return "Conservatory";
		case "G": return "Dining Room";
		case "L": return "Lounge";
		case "H": return "Hall Room";
		case "S": return "Study";
		case "Y": return "Library";
		case "I": return "Billard Room";
		case "O": return "Cluedo";
		case "D": return "Door";
		default:
	
		}
		switch(room){
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
			default: return null;
		}
	}
	
	/**
	 * Moves a weapon into a room when a suggestion is made involving the weapon and the room
	 * @param weapon weapon being moved
	 * @param room room being moved into
	 */
	public void moveWeaponToRoom(Weapon weapon, String room){
		int x = weapon.getXLocation();
		int y = weapon.getYLocation();
		this.board[y][x] = weapon.getBoardLoc();
		weapon.moveTo(room);
		x = weapon.getXLocation();
		y = weapon.getYLocation();
		this.board[y][x] = weapon.getInitials();
	}
	/**
	 * Draws the game board from the board field and the key along side it
	 */
	public void draw(){
		System.out.println("####################################################");
		for(int i=0; i<25; i++){
			System.out.print("#");
			for(int j=0; j<25; j++){
				System.out.print(this.board[i][j]+" ");
			}
			System.out.println("#" +"     "+this.key[i]);
		}
		System.out.println("####################################################");
	}

	/**
	 * Gives the full name of the x and y location
	 * @param x the x location
	 * @param y the y location
	 * @return the name of the location that x and y refer to
	 */
	public String getLocation(int x, int y){
		if(x<0 || y<0 || x>24 || y>24){
			return "Out of bounds";
		}
		String code =  board[y][x]; //code of the board location for the coordinates
		
		switch(code){
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
		default : return code;
		}
	}
}

