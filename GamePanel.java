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
	//removed duplicate constants
	static final int LARGE_WINDOW = 800;	//refactored to be "LRG_WINDOW" since window is square
	static final int PANEL_WIDTH = 1100;	//more appropiate name for this constant
	static final int SMALL_WIN = 250;		//refactored to be "SMALL_WIN" since windows are square also clarity
	static final int LARGE_GRID = (LARGE_WINDOW-100)/10;	//refactored to be "Large_Grid" since grid boxes are square
	static final int SMALL_GRID= SMALL_WIN/10;	//refactored grid to specify "SMALL_GRID" since grid size is always square
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
		this.setPreferredSize(new Dimension(PANEL_WIDTH,LARGE_WINDOW));
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
		g.drawString("Press Enter To Move", LARGE_WINDOW+BORDER/2+3, LARGE_WINDOW/2);
	}

	//there are 3 windows this function will take the dimensions for those 3 windows and draw then on our panel
	public void drawWindows(Graphics g, int x, int y, int winSize, int gridSize, Critter[][] panel){
		int r,c;
		//keeps track of where the last grid box was drawn
		int gridX = x;	
		int gridY = y;
		g.setColor(Color.gray);
		g.fillRect(x,y, winSize, winSize);
		//from the x/y starting point draw 10x10 or ROWSxROWS number of grid boxes
		//draw either a T or a * or nothing
		for (r = 0; r<regular.ROWS; r++) {

		}
	}

	//this method will draw the coordinates for each window
	public void drawCoordinates(Graphics g){

	}

	public void drawThirdState(Graphics g) {
		int r,c;
		g.setColor(Color.gray);
		g.fillRect(LARGE_WINDOW, LARGE_WINDOW-BORDER-SMALL_WIN, SMALL_WIN , SMALL_WIN);
		for (r = 0; r<regular.ROWS; r++) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN,15));
			g.drawString(""+r, SMALL_GRID+LARGE_WINDOW-45, r* +LARGE_WINDOW-BORDER-SMALL_WIN+18);
			g.drawString(""+r, r*SMALL_GRID+LARGE_WINDOW+8, SMALL_GRID+LARGE_WINDOW-BORDER-SMALL_WIN-30);
			g.setFont(new Font("TimesRoman", Font.BOLD,15));
			g.drawString("Taminator State", SMALL_GRID+LARGE_WINDOW+50, SMALL_GRID+LARGE_WINDOW-BORDER*2-SMALL_WIN);
			for (c= 0; c<regular.COLS; c++) {
				if(thirdPanel[r][c].getAppearance() == '*') {
					g.setColor(Color.yellow);
					g.fillRect(c*SMALL_GRID+LARGE_WINDOW, r*SMALL_GRID+LARGE_WINDOW-BORDER-SMALL_WIN, SMALL_GRID, SMALL_GRID);
				}else if(thirdPanel[r][c].getAppearance() == 'T') {
					g.setColor(Color.red);
					g.fillRect(c*SMALL_GRID+LARGE_WINDOW, r*SMALL_GRID+LARGE_WINDOW-BORDER-SMALL_WIN, SMALL_GRID, SMALL_GRID);
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.BOLD,20));
					g.drawString("T", c*SMALL_GRID+LARGE_WINDOW+6, r*SMALL_GRID+LARGE_WINDOW-BORDER-SMALL_WIN+19);
				}
				g.setColor(Color.black);
				g.drawRect(c*SMALL_GRID+LARGE_WINDOW, r*SMALL_GRID+LARGE_WINDOW-BORDER-SMALL_WIN, SMALL_GRID,SMALL_GRID);
				
			}
		}
	}
	public void drawPrevState(Graphics g) {
		int r,c;
		g.setColor(Color.gray);
		g.fillRect(LARGE_WINDOW, BORDER,SMALL_WIN , SMALL_WIN);
		for (r = 0; r<regular.ROWS; r++) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN,15));
			g.drawString(""+r, SMALL_GRID+LARGE_WINDOW-45, r*SMALL_GRID+BORDER+18);
			g.drawString(""+r, r*SMALL_GRID+LARGE_WINDOW+8, SMALL_GRID+BORDER-30);
			g.setFont(new Font("TimesRoman", Font.BOLD,15));
			g.drawString("Previous State", SMALL_GRID+LARGE_WINDOW+50, SMALL_GRID);
			for (c= 0; c<regular.COLS; c++) {
				if(previousPanel[r][c].getAppearance() == '*') {
					g.setColor(Color.yellow);
					g.fillRect(c*SMALL_GRID+LARGE_WINDOW, r*SMALL_GRID+BORDER, SMALL_GRID, SMALL_GRID);
				}else if(previousPanel[r][c].getAppearance() == 'T') {
					g.setColor(Color.red);
					g.fillRect(c*SMALL_GRID+LARGE_WINDOW, r*SMALL_GRID+BORDER, SMALL_GRID, SMALL_GRID);
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.BOLD,20));
					g.drawString("T", c*SMALL_GRID+LARGE_WINDOW+6, r*SMALL_GRID+BORDER+19);
				}
				
				g.setColor(Color.black);
				g.drawRect(c*SMALL_GRID+LARGE_WINDOW, r*SMALL_GRID+BORDER, SMALL_GRID,SMALL_GRID);
				
			}
		}
	}
	public void drawSecondState(Graphics g) {
		int r,c;
		g.setColor(Color.white);
		g.fillRect(0, 0, PANEL_WIDTH, PANEL_WIDTH);
		// g.fillRect(0, 0, LARGE_WINDOW, BORDER);
		// g.fillRect(LARGE_WINDOW-BORDER, 0, LARGE_WINDOW, LARGE_WINDOW);
		// g.fillRect(0, LARGE_WINDOW-BORDER, LARGE_WINDOW, BORDER);
		g.setColor(Color.gray);
		g.fillRect(BORDER, BORDER, LARGE_WINDOW-(BORDER*2), LARGE_WINDOW-(BORDER*2));
		for (r = 0; r<regular.ROWS; r++) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN,30));
			g.drawString(""+r, BORDER-20, r*LARGE_GRID+90);
			g.drawString(""+r, r*LARGE_GRID+84, BORDER-20);
			g.drawString("^Births and Deaths State^", LARGE_WINDOW/2-130, LARGE_WINDOW-BORDER/2);
			for (c= 0; c<regular.COLS; c++) {
				if(secondPanel[r][c].getAppearance() == '*') {
					g.setColor(Color.yellow);
					g.fillRect(c*LARGE_GRID+BORDER, r*LARGE_GRID+BORDER, LARGE_GRID, LARGE_GRID);
				} else if(previousPanel[r][c].getAppearance() == 'T') {
					g.setColor(Color.red);
					g.fillRect(c*LARGE_GRID+BORDER, r*LARGE_GRID+BORDER, LARGE_GRID, LARGE_GRID);
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.BOLD,60));
					g.drawString("T", c*LARGE_GRID+BORDER+15, r*LARGE_GRID+BORDER+55);
				}
				
				g.setColor(Color.black);
				g.drawRect(c*LARGE_GRID+BORDER, r*LARGE_GRID+BORDER, LARGE_GRID,LARGE_GRID);
				
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
	