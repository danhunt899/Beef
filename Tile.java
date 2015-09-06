import java.awt.Color;
import java.awt.Graphics;

/**
 * Visually represents each square on the board. 
 * @author danhunt
 * @author rayg
 *
 */
public class Tile {
	private Color color;
	private String type;
	private int xLoc;
	private int yLoc;
	private int size = 20;
	
	/**
	 * Creates a new tile
	 * @param color Color that the tile will be 
	 * @param type Type of tile- Jail, Cali, etc
	 * @param xLoc X Location of the tile in the original board array.
	 * @param yLoc Y Location of the tile in the original board array
	 */
	public Tile(Color color, String type, int xLoc, int yLoc){
		this.color = color;
		this.type = type;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	
	
	/**
	 * 
	 * @return The Color of the tile
	 */
	public Color getColor(){
		return this.color;
	}
	
	/**
	 * 
	 * @return The type of the tile
	 */
	public String getType(){
		return this.type;
	}
	
	/**
	 * 
	 * @return The X Location
	 */
	public int  getXLoc(){
		return this.xLoc;
	}
	
	/**
	 * 
	 * @return The Y Location
	 */
	public int getYLoc(){
		return this.yLoc;
	}
	
	public boolean contains(int x, int y){
		if(x<(this.size*this.xLoc) || y<(this.size*this.yLoc)){
			return false;
		}
		if(x>((this.size*this.xLoc)+this.size) || y>((this.size*this.yLoc)+this.size)){
			return false;
		}
		return true;
	}
	
	/**
	 * Draws the tile on the graphics panel
	 * @param g The graphics panel to be drawn on.
	 */
	public void drawTile(Graphics g){
		Color oldColor = g.getColor(); // get the graphics original color
		g.setColor(this.color); // set it to the tiles color
		g.fillRect((this.size*this.xLoc+67), (this.size*this.yLoc), this.size, this.size); //draw the tile on the graphics pane
		g.setColor(Color.BLACK); 
		g.drawRect((this.size*this.xLoc+67), (this.size*this.yLoc), this.size, this.size);
		g.setColor(oldColor); //reset the graphics to the original color
	}
}
