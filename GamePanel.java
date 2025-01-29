import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener{
	static final int HEIGHT_SECOND_WINDOW = 800;
	static final int WIDTH_SECOND_WINDOW = 800;
	static final int WIDTH = 1100;
	static final int HEIGHT_THIRD = 250;
	static final int WIDTH_THIRD = 250;
	static final int GRID_SIZEH = (HEIGHT_SECOND_WINDOW-100)/10;
	static final int GRID_SIZEW = (WIDTH_SECOND_WINDOW-100)/10;
	static final int GRIDH= HEIGHT_THIRD/10;
	static final int GRIDW= WIDTH_THIRD/10;
	static final int BORDER = 50;
	final int DELAY = 300;
	Biosphere regular = new Biosphere(FileInitialization.read());
	Timer timer;
	Scanner in = new Scanner(System.in);
	Critter[][] previousPanel = new Critter[regular.ROWS][regular.COLS];
	Critter[][] secondPanel = new Critter[regular.ROWS][regular.COLS];
	Critter[][] thirdPanel = new Critter[regular.ROWS][regular.COLS];
	public GamePanel() {
		regular.copyArray(previousPanel,regular.current);
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT_SECOND_WINDOW));
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		regular.copyArray(secondPanel,regular.current);
		regular.copyArray(thirdPanel, regular.current);
		startGame();
	}
	
	public void startGame() {	
		timer = new Timer(DELAY, this);
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawSecondState(g);
		drawThirdState(g);
		drawPrevState(g);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("Press Enter To Move", WIDTH_SECOND_WINDOW+BORDER/2+3, HEIGHT_SECOND_WINDOW/2);
	}
	public void drawThirdState(Graphics g) {
		int r,c;
		g.setColor(Color.gray);
		g.fillRect(WIDTH_SECOND_WINDOW, WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD, HEIGHT_THIRD , WIDTH_THIRD);
		for (r = 0; r<regular.ROWS; r++) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN,15));
			g.drawString(""+r, GRIDW+WIDTH_SECOND_WINDOW-45, r*GRIDH+WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD+18);
			g.drawString(""+r, r*GRIDW+WIDTH_SECOND_WINDOW+8, GRIDH+WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD-30);
			g.setFont(new Font("TimesRoman", Font.BOLD,15));
			g.drawString("Taminator State", GRIDW+WIDTH_SECOND_WINDOW+50, GRIDH+WIDTH_SECOND_WINDOW-BORDER*2-HEIGHT_THIRD);
			for (c= 0; c<regular.COLS; c++) {
				if(thirdPanel[r][c].getAppearance() == '*') {
					g.setColor(Color.yellow);
					g.fillRect(c*GRIDW+WIDTH_SECOND_WINDOW, r*GRIDH+WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD, GRIDH, GRIDH);
				}else if(thirdPanel[r][c].getAppearance() == 'T') {
					g.setColor(Color.red);
					g.fillRect(c*GRIDW+WIDTH_SECOND_WINDOW, r*GRIDH+WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD, GRIDH, GRIDH);
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.BOLD,20));
					g.drawString("T", c*GRIDW+WIDTH_SECOND_WINDOW+6, r*GRIDH+WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD+19);
				}
				g.setColor(Color.black);
				g.drawRect(c*GRIDW+WIDTH_SECOND_WINDOW, r*GRIDH+WIDTH_SECOND_WINDOW-BORDER-HEIGHT_THIRD, GRIDH,GRIDH);
				
			}
		}
	}
	public void drawPrevState(Graphics g) {
		int r,c;
		g.setColor(Color.gray);
		g.fillRect(WIDTH_SECOND_WINDOW, BORDER,HEIGHT_THIRD , WIDTH_THIRD);
		for (r = 0; r<regular.ROWS; r++) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN,15));
			g.drawString(""+r, GRIDW+WIDTH_SECOND_WINDOW-45, r*GRIDH+BORDER+18);
			g.drawString(""+r, r*GRIDW+WIDTH_SECOND_WINDOW+8, GRIDH+BORDER-30);
			g.setFont(new Font("TimesRoman", Font.BOLD,15));
			g.drawString("Previous State", GRIDW+WIDTH_SECOND_WINDOW+50, GRIDH);
			for (c= 0; c<regular.COLS; c++) {
				if(previousPanel[r][c].getAppearance() == '*') {
					g.setColor(Color.yellow);
					g.fillRect(c*GRIDW+WIDTH_SECOND_WINDOW, r*GRIDH+BORDER, GRIDH, GRIDH);
				}else if(previousPanel[r][c].getAppearance() == 'T') {
					g.setColor(Color.red);
					g.fillRect(c*GRIDW+WIDTH_SECOND_WINDOW, r*GRIDH+BORDER, GRIDH, GRIDH);
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.BOLD,20));
					g.drawString("T", c*GRIDW+WIDTH_SECOND_WINDOW+6, r*GRIDH+BORDER+19);
				}
				
				g.setColor(Color.black);
				g.drawRect(c*GRIDW+WIDTH_SECOND_WINDOW, r*GRIDH+BORDER, GRIDH,GRIDH);
				
			}
		}
	}
	public void drawSecondState(Graphics g) {
		int r,c;
		g.setColor(Color.white);
		g.fillRect(0, 0, BORDER, HEIGHT_SECOND_WINDOW);
		g.fillRect(0, 0, WIDTH_SECOND_WINDOW, BORDER);
		g.fillRect(WIDTH_SECOND_WINDOW-BORDER, 0, WIDTH_SECOND_WINDOW, HEIGHT_SECOND_WINDOW);
		g.fillRect(0, HEIGHT_SECOND_WINDOW-BORDER, WIDTH_SECOND_WINDOW, BORDER);
		g.setColor(Color.gray);
		g.fillRect(BORDER, BORDER, WIDTH_SECOND_WINDOW-(BORDER*2), HEIGHT_SECOND_WINDOW-(BORDER*2));
		for (r = 0; r<regular.ROWS; r++) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN,30));
			g.drawString(""+r, BORDER-20, r*GRID_SIZEH+90);
			g.drawString(""+r, r*GRID_SIZEH+84, BORDER-20);
			g.drawString("^Births and Deaths State^", WIDTH_SECOND_WINDOW/2-130, HEIGHT_SECOND_WINDOW-BORDER/2);
			for (c= 0; c<regular.COLS; c++) {
				if(secondPanel[r][c].getAppearance() == '*') {
					g.setColor(Color.yellow);
					g.fillRect(c*GRID_SIZEW+BORDER, r*GRID_SIZEH+BORDER, GRID_SIZEH, GRID_SIZEH);
				} else if(previousPanel[r][c].getAppearance() == 'T') {
					g.setColor(Color.red);
					g.fillRect(c*GRID_SIZEW+BORDER, r*GRID_SIZEH+BORDER, GRID_SIZEH, GRID_SIZEH);
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.BOLD,60));
					g.drawString("T", c*GRID_SIZEW+BORDER+15, r*GRID_SIZEH+BORDER+55);
				}
				
				g.setColor(Color.black);
				g.drawRect(c*GRID_SIZEW+BORDER, r*GRID_SIZEH+BORDER, GRID_SIZEH,GRID_SIZEH);
				
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println("before this");
		regular.display();
		System.out.println("Appearance of PreviousPanel: "+previousPanel[3][3].getAppearance());
		regular.copyArray(secondPanel,regular.second);
		regular.copyArray(thirdPanel, regular.third);
		repaint();
		timer.stop();
		
	}
	public class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				regular.copyArray(previousPanel,regular.current);
				timer.start();
				break;
			default:
				System.out.print("This key does nothing Press Enter to advance turns");
				break;
			}
			
		}
	}
}
	