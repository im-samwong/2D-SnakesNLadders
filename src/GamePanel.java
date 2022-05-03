import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.Scanner;



public class GamePanel extends JPanel implements Runnable {

	final static int SCREEN_W = 565;
	final static int SCREEN_H = 565;
	final static Dimension SCREEN_SIZE = new Dimension(SCREEN_W,SCREEN_H);
	final static int PLAYER_W = 25;
	final static int PLAYER_H = 25;
	final static int velocity = 1;
	static Thread gameThread;
	Image background;
	Image image;
	Graphics graphics;
	Players player1;
	static int[][] coords = new int[2][100];
	int currentPos;
	
	Scanner input = new Scanner(System.in);
	
	Queue<Players> playersUnordered = new LinkedList<Players>();
	
	Queue<Players> players = new LinkedList<Players>();
	
	int[] order;
	String[] charLeft = {"1","2","3","4"};
	
	int playersPlaying;	
	int coordX = 5;
	int coordY = 9;
	
	boolean gameOver = true;
	boolean turnOver = true;
	
	
	public GamePanel() { 
		this.setPreferredSize(SCREEN_SIZE);
		this.setLocation(0,0);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		
		int incrementX = 56;
		
		for(int i = 99; i >= 0; i--)
		{
			coords[0][i] = coordX;
			if((i)%10 == 0)
				incrementX /= -1;
			else {
				coordX += incrementX;
			}
	
			coords[1][i] = coordY;
			if((i)%10 == 0)
			{
				coordY += 55;
			}
				
		}
		
		///*
		int figChoice = 0;
		
		
		do
		{
			System.out.println("Please pick number of players: (2-4)");
			{
			
				playersPlaying = input.nextInt();
				if(playersPlaying < 2 || playersPlaying > 4)
				{
					System.out.println("Invalid, try again.");
					continue;
				} else break;
				
			}
		}while(true);
		
		order = new int[playersPlaying];
		
		int number = 1;
		
		do
		{
			
			
			
			System.out.print("Player " + number + ", please pick your character: (Choices ");
			for(int i = 0; i < charLeft.length; i++)
				if(charLeft[i].equals("0"))
					continue;
				else {
					System.out.print(charLeft[i] + " ");
				}
			System.out.print("are left)");
			
			
			{
				boolean validChoice = false;
				figChoice = input.nextInt();
				for(int i = 0; i < charLeft.length; i++)
					if(charLeft[i].equals(String.valueOf(figChoice)))
					{
						charLeft[i] = "0";
						validChoice = true;
						break;
					}
				if(!validChoice)
				{
					System.out.println("Invalid, try again.");
					continue;
				}
				do {
					
					validChoice = false;
					order[number-1] = rollDice();
					System.out.println("roll dice:" + order[number-1]);
					for(int i = 0; i < order.length; i++)
					{
						if(order[number-1] == order[i] && i != number-1)
						{
							validChoice = true;
							System.out.println("Please reroll");
						}
					}
				} while (validChoice);
				
				playersUnordered.add(new Players(0,565-25,PLAYER_H,PLAYER_W,0,number, order[number-1],characterChoice(figChoice)));
				number++;
			}
		}while(playersUnordered.size() < playersPlaying);
		
		
		gameStart();
		gameOver = false;
		//*/
			
			
		
		//player1 = (new Players(0,565-25,PLAYER_H,PLAYER_W,0,1,"fig1.png"));
		
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void gameStart() {
		boolean inOrder;
		System.out.println("To start, all players must roll the dice, the order will be biggest to smallest.");
		for(int i = 0; i < playersUnordered.size(); i++)
		{
			System.out.println("Player " + playersUnordered.peek().id);
			
		}
		for(int i = 0; i < order.length; i++)
		{
			//System.out.println(playersOrdered.peek().diceRoll);
			System.out.println(String.valueOf(order[i]));
		}
		
		do {
			inOrder = true;
			int temp = 0;
			for(int i = 0; i < order.length - 1; i++)
			{
				if(order[i] >= order[i+1])
					continue;
				else {
					inOrder = false;
					temp = order[i];
					order[i] = order[i+1];
					order[i+1] = temp;
					break;
				}
			}
			
		} while (!inOrder);
		
		System.out.println("");
		
		for(int i = 0; i < order.length; i++)
		{
			//System.out.println(playersOrdered.peek().diceRoll);
			System.out.println(String.valueOf(order[i]));
		}
		
		for(int i = 0; i < order.length; i++)
			for(int j = 0; j < playersUnordered.size(); j++)
			{
				if(order[i] == playersUnordered.peek().diceRoll)
				{
					players.add(playersUnordered.poll());
					break;
				}
				else {
					playersUnordered.add(playersUnordered.poll());
				}
			}
		
		
		
	}
	
	public int rollDice() {
		int rand = (int)(Math.random()*(6-1+1))+1;
		return rand;
	}
	
	public String characterChoice(int choice) {
		String figure = null;
		switch(choice)
		{
		case 1:
			figure = "fig1.png";
			break;
		case 2:
			figure = "fig2r.png";
			break;
		case 3:
			figure = "fig3r.png";
			break;
		case 4:
			figure = "fig4r.png";
			break;	
		}
		
		return figure;
	}
	
	public class AL extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(!gameOver)
			if(turnOver)
			{
				turnOver = false;
				currentPos = players.peek().getPos();
				players.peek().keyPressed(e);

			}
		}
			
			
	}
	
	
	
	public void move() {
		
		
		if(!turnOver)
		{
			
			
			if(currentPos%10 == 0 && currentPos != 0)
			{
				if(players.peek().getYcoord() != coords[1][players.peek().getPos()-1])
					displaceY();
				else if (players.peek().getXcoord() != coords[0][players.peek().getPos()-1])
						displaceX();
					else if(players.peek().check() != players.peek().getPos())
						players.peek().setPos(players.peek().check());
						else {
							turnOver = true;
							players.add(players.poll());
						}
			
			} else {
				
				if(String.valueOf(currentPos).charAt(0) != String.valueOf(players.peek().getPos()).charAt(0))
					displaceX();
				else if(players.peek().getXcoord() != coords[0][players.peek().getPos()-1])
				{
						displaceX();
				}
					else if(players.peek().getYcoord() != coords[1][players.peek().getPos()-1])
							displaceY();
						else if(players.peek().check() != players.peek().getPos())
							players.peek().setPos(players.peek().check());
							else {
								turnOver = true;
								players.add(players.poll());
							}
			}
		if(currentPos == 100)
			win();		
		}
		
		
			
	}
	
	public void win() {
		
		System.out.println(" Congrats, Player " + players.peek().id + "! You won!");
		gameOver = true;
		turnOver = true;
		
	}
	
	public void displaceX() {
		int speed = 1;
		int target = coords[0][players.peek().getPos()-1];
		
		
		if(currentPos < 10 && players.peek().getPos() > 10 && currentPos != 4 && players.peek().getPos() != 14)
			target = coords[0][9];
		if(currentPos > 10 && players.peek().getPos() > 10)
		{
			if(String.valueOf(currentPos).charAt(0) != String.valueOf(players.peek().getPos()).charAt(0))
			{
				target = coords[0][(players.peek().getPos()-(players.peek().getPos()%10))-1];
				if(players.peek().getXcoord() == target)
					currentPos = ((players.peek().getPos()-(players.peek().getPos()%10)));
			}
		} else if(players.peek().getXcoord() == target)
		{
			if(target == coords[0][9])
				currentPos = 10;
			else currentPos = players.peek().getPos();
		}
				
		if(players.peek().getXcoord() > target)
			speed *= -1;
		
		players.peek().moveX(speed);
	}
	
	public void displaceY() {
		int speed = 1;
		if(players.peek().getYcoord() > coords[1][players.peek().getPos()-1])
			speed *= -1;
		
		players.peek().moveY(speed);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		background = new ImageIcon(getClass().getClassLoader().getResource("SnakesAndLadders.png")).getImage();
		g2D.drawImage(background, 0, 0, null);
		for(int i = 0; i < players.size(); i++)
		{
			players.peek().draw(g2D);
			players.add(players.poll());
		}
		//player1.draw(g2D);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g2D.drawImage(image, 0, 0, this);
	
	}
	
	
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				repaint();
				delta--;
			}
		}
		
	}
	
}
