import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Junit tests for the cluedo game. Many of these tests are not relevant for Beef, but they all still pass.
 * @author danhunt 
 * @author rayg
 *
 */
public class CluedoTests {
	
	@Test
	public void testGameInitiation(){
		Game g = new Game();
		assertTrue(g.getBoard() != null); //check that the board was initiated
		assertTrue(g.getPlayers()!= null); //check that the players were initiated
		assertTrue(g.getWeapons() != null); //check that the weapons were initiaited
	}

	@Test
	public void testBoardgetLocation(){
		Game g = new Game();
		Board b = g.getBoard();
		assertTrue(b.getLocation(0, 0).equals("Kitchen"));
		assertTrue(b.getLocation(0, 12).equals("%"));
		assertTrue(b.getLocation(6, 6).equals("Hallway"));
		assertTrue(b.getLocation(-1, 0).equals("Out of bounds")); 
		
	}
	@Test
	public void testMovePlayer(){
		Game g = new Game();
		Board b = g.getBoard();
		b.movePlayer(g.getPlayers()[0], 3, "R");
		assertTrue(g.getPlayers()[0].getXLocation()==3);
	}
	
	@Test
	public void testMovePlayerToRoom(){
		Game g = new Game();
		Board b = g.getBoard();
		b.movePlayerToRoom(g.getPlayers()[3], "Kitchen");
		assertTrue(g.getPlayers()[3].getBoardLoc().equals("K"));
	}
	
	@Test
	public void testMoveWeaponToRoom(){
		Game g = new Game();
		Board b = g.getBoard();
		b.moveWeaponToRoom(g.getWeapons()[3], "Conservatory");
		assertTrue(g.getWeapons()[3].getBoardLoc().equals("C"));
	}
	
	@Test
	public void testBoardInRoom(){
		Game g = new Game();
		Board b = g.getBoard();
		assertFalse(b.inRoom(g.getPlayers()[2]));
		b.movePlayerToRoom(g.getPlayers()[2], "Lounge");
		assertTrue(b.inRoom(g.getPlayers()[2]));
	}
	
	@Test
	public void testRoomAdjacentTo(){
		Game g = new Game();
		Board b = g.getBoard();
		assertNull(b.roomAdjacentTo(g.getPlayers()[0]));
		b.movePlayer(g.getPlayers()[0], 3, "R");
		b.movePlayer(g.getPlayers()[0], 1, "U");
		assertTrue(b.roomAdjacentTo(g.getPlayers()[0]).equals("Kitchen"));
	}
	
	@Test
	public void testValidPlayerInitialistion(){
		Player p = new Player("Biggie Smalls");
		assertNotNull(p.getInitials());
		assertFalse(p.getXLocation()==-1);
		assertNotNull(p.getBoardLoc());
		assertFalse(p.getYLocation()==-1);
	}
	@Test
	public void testInvalidPlayerInitialisation(){
		Player p = new Player("Donald Duck");
		assertNull(p.getInitials());
		assertTrue(p.getXLocation()==0);
		assertNull(p.getBoardLoc());
		assertTrue(p.getYLocation()==0);
	}
	
	@Test
	public void testMoveLeft(){
		Player p = new Player("Eminem");
		p.moveLeft(3);
		assertTrue(p.getXLocation() == 21);
	}
	@Test
	public void testMoveRight(){
		Player p = new Player("Biggie Smalls");
		p.moveRight(3);
		assertTrue(p.getXLocation() == 3);
	}
	@Test
	public void testMoveDown(){
		Player p = new Player("Dr Dre");
		p.moveDown(2);
		assertTrue(p.getYLocation() == 2);
	}
	@Test
	public void testMoveUp(){
		Player p = new Player("Snoop Dogg");
		p.moveUp(4);
		assertTrue(p.getYLocation() == 20);
	}
	@Test
	public void testPlayerMoveToRoom(){
		Player p = new Player("Mrs White");
		p.moveTo("Kitchen");
		assertTrue(p.getBoardLoc().equals("K"));
	}
	@Test
	public void testInvalidWeaponInitialisation(){
		Weapon w = new Weapon("Nife");
		assertNull(w.getInitials());
		assertTrue(w.getXLocation()==0);
		assertNull(w.getBoardLoc());
		assertTrue(w.getYLocation()==0);	
	}
	@Test
	public void testValidWeaponInitialisation(){
		Weapon w = new Weapon("Knife");
		assertNotNull(w.getInitials());
		assertTrue(w.getXLocation()!=0);
		assertNotNull(w.getBoardLoc());
		assertTrue(w.getYLocation()!=0);
	}
	
	@Test
	public void testWeaponMoveToRoom(){
		Weapon w = new Weapon("Revolver");
		w.moveTo("Library");
		assertTrue(w.getBoardLoc().equals("Y"));		
	}
	
}
