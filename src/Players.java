import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;


public class Players extends Rectangle {
	Image skin;
	private int x,y,pos;
	private String image;
	public int id, diceRoll;
	
	public Players(int x, int y, int height, int width, int pos, int id, int diceRoll, String image) {
		super(x,y,height,width);
		this.x = x;
		this.y = y;
		this.pos = pos;
		this.diceRoll = diceRoll;
		this.id = id;
		this.image = image;
	}
	
	
	public int getPos() {
		return this.pos;
	}
	
	public void setPos(int newPos) {
		this.pos = newPos;
	}
	
	public int getYcoord() {
		return this.y;
	}
	
	public void moveY(int speed) {
		this.y += speed;
	}
	
	public int getXcoord() {
		return this.x;
	}
	
	public void moveX(int speed) {
		this.x += speed;
	}
	
	public int move() {
		int roll = rollDice();
		System.out.println("");
		System.out.print("Player " + id + " rolled a " + roll);
		pos += roll;
		if(pos > 100)
			pos = 100 - (pos - 100);
		System.out.print(" and is now on square " + pos + ".");
		return pos;
		
	}
	
	public int rollDice() {
		int rand = (int)(Math.random()*(6-1+1))+1;
		return rand;
	}
	
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == 32)
		{
			move();
		}
	}
	

	 public int check() {
		 int result = getPos();
			switch(result)
			{
			case 1:
				System.out.print(", then up a ladder to square 38.");
				result = 38;
				break;
			case 4: 
				System.out.print(", then up a ladder to square 14.");
				result = 14;
				break;
			case 9:
				System.out.print(", then up a ladder to square 31.");
				result = 31;
				break;
			case 16:
				System.out.print(", then down a snake to square 6.");
				result = 6;
				break;
			case 21:
				System.out.print(", then up a ladder to square 42.");
				result = 42;
				break;
			case 28:
				System.out.print(", then up a ladder to square 84.");
				result = 84;
				break;
			case 36: 
				System.out.print(", then up a ladder to square 44.");
				result = 44;
				break;
			case 48:
				System.out.print(", then down a snake to square 30.");
				result = 30;
				break;
			case 51:
				System.out.print(", then up a ladder to square 67.");
				result = 67;
				break;
			case 64:
				System.out.print(", then down a snake to square 60.");
				result = 60;
				break;
			case 71:
				System.out.print(", then up a ladder to square 91.");
				result = 91;
				break;
			case 79:
				System.out.print(", then down a snake to square 19.");
				result = 19;
				break;
			case 80:
				System.out.print(", then up a ladder to square 100, nice!");
				result = 100;
				break;
			case 93:
				System.out.print(", then down a snake to square 68.");
				result = 68;
				break;
			case 95:
				System.out.print(", then down a snake to square 24, how unlucky!");
				result = 24;
				break;
			case 97:
				System.out.print(", then down a snake to square 76.");
				result = 76;
				break;
			case 98: 
				System.out.print(", then down a snake to square 78.");
				result = 78;
				break;
			default:
				System.out.print("");
				break;
			}
			
			if(result > 100)
				result = 100 - (result - 100);
			
			return result;
	 }
	
	public void draw(Graphics g) {

		skin = new ImageIcon(getClass().getClassLoader().getResource(image)).getImage();
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(skin, x, y, width, height, null);
		//g2D.setColor(Color.BLUE);
		//g2D.fillRect(x, y, height, width);
		
		
	}
	
}
