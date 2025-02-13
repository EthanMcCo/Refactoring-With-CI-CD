
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	public GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Game of Life");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
