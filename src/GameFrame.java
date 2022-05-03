import java.awt.Color;
import javax.swing.JFrame;


public class GameFrame extends JFrame {
	
	public GameFrame() {
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setTitle("PlaySnakesAndLadders");
		this.setLayout(null);
		
	}

}
