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
	static final int LARGE_WIN = 700;	//refactored to be "LRG_WINDOW" since window is square
	static final int PANEL_WIDTH = 1100;	//more appropiate name for this constant
	static final int PANEL_HEIGHT = 800;
	static final int SMALL_WIN = 250;		//refactored to be "SMALL_WIN" since windows are square also clarity
	static final int LARGE_GRID = LARGE_WIN/10;	//refactored to be "Large_Grid" since grid boxes are square, also bug where we were randomly subbing by 100
	static final int SMALL_GRID= SMALL_WIN/10;	//refactored grid to specify "SMALL_GRID" since grid size is always square
	static final int BORDER = 50;
	//creating constants for each windows X/Y coordinate rather than computing it inline repeatedly 
	static final int TAM_WIN_X = 800;
	static final int TAM_WIN_Y = 500;
	static final int PREV_WIN_X = 800;
	static final int PREV_WIN_Y = 50;
	static final int MAIN_WIN_X = 50;
	static final int MAIN_WIN_Y = 50;

	final int DELAY = 300;
	Biosphere regular = new Biosphere(FileInitialization.read());
	Timer timer;
	Scanner in = new Scanner(System.in);
	Critter[][] previousPanel = new Critter[regular.ROWS][regular.COLS];
	Critter[][] secondPanel = new Critter[regular.ROWS][regular.COLS];
	Critter[][] thirdPanel = new Critter[regular.ROWS][regular.COLS];
	public GamePanel() {

		// regular.copyArray(previousPanel,regular.current);
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		// regular.copyArray(secondPanel,regular.current);
		// regular.copyArray(thirdPanel, regular.current);
		startGame();
	}
	
	public void startGame() {	
		timer = new Timer(DELAY, this);
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//make background white base
		g.setColor(Color.white);
		g.fillRect(0, 0, PANEL_WIDTH, PANEL_WIDTH);

		//Using the refactored helper window method instead of 3 seperate methods with duplicated code
		drawWindow(g, MAIN_WIN_X, MAIN_WIN_Y, LARGE_WIN, LARGE_GRID, regular.current);
		drawWindow(g, TAM_WIN_X, TAM_WIN_Y, SMALL_WIN, SMALL_GRID, regular.current);  //make this current for now but this needs to change to tam state
		drawWindow(g, PREV_WIN_X,PREV_WIN_Y,SMALL_WIN, SMALL_GRID, regular.prev);

		//using helper function to draw in text/coordinates for each window
		drawText(g, TAM_WIN_X, TAM_WIN_Y, SMALL_GRID, "Taminator State");
		drawText(g, MAIN_WIN_X, MAIN_WIN_Y, LARGE_GRID, "Current State");
		drawText(g, PREV_WIN_X, PREV_WIN_Y, SMALL_GRID, "Previous State");

		//drawing instructions on how to advance turns
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("Press Enter To Move", LARGE_WIN+BORDER*2, LARGE_WIN/2+BORDER);
	}

	//there are 3 windows this function will take the dimensions for those 3 windows and draw then on our panel
	public void drawWindow(Graphics g, int x, int y, int winSize, int gridSize, Critter[][] panel){
		g.setColor(Color.gray);
		g.fillRect(x,y, winSize, winSize);
		//from the x/y starting point draw 10x10 or ROWSxROWS number of grid boxes
		//draw either a T or a * or nothing
		for (int r = 0; r<regular.ROWS; r++) {
			for (int c = 0; c<regular.COLS; c++) {
				if(panel[r][c].getAppearance() == '*') {
					//draws a yellow box if there is a cell here
					g.setColor(Color.yellow);
					g.fillRect(c*gridSize + x, r*gridSize + y, gridSize, gridSize);
				} else if (panel[r][c].getAppearance() == 'T') {
					//draws a T if there's a taminator there
					g.setColor(Color.red);
					g.fillRect(c*gridSize + x, r*gridSize + y, gridSize, gridSize);
					g.setColor(Color.black);
					//change font size depending on gridsize
					if (gridSize == LARGE_GRID) {
						g.setFont(new Font("TimesRoman", Font.BOLD,60));
						g.drawString("T", c*gridSize+x+15, r*gridSize+y+55);
					}
					else {
						g.setFont(new Font("TimesRoman", Font.BOLD,20));
						g.drawString("T", c*gridSize + x+6, r*gridSize +y+19);
					}
				}
				//draw grid borders 
				g.setColor(Color.black);
				g.drawRect(c*gridSize + x, r*gridSize + y, gridSize, gridSize);
			}
		}
	}

	//this method will draw the coordinates/names
	public void drawText(Graphics g, int x, int y, int gridSize, String winName){
		//if our window is the main window it will use different dimensions
		for (int r = 0; r<regular.ROWS; r++) {
			if (gridSize == LARGE_GRID){
				//drawing out coord nums across top/side
				g.setColor(Color.black);
				g.setFont(new Font("TimesRoman", Font.PLAIN,30));
				g.drawString("" + r, x - 25, r*gridSize + 95);
				g.drawString("" + r, r*gridSize + 80, y - 15);
				//drawing the window label
				g.drawString("Current State", LARGE_WIN/2-30, LARGE_WIN + 80);
			} else {
				g.setColor(Color.black);
				//drawing out coord nums across the top/side
				g.setFont(new Font("TimesRoman", Font.PLAIN,15));
				g.drawString("" + r, x - 12, r*gridSize + y + 18);
				g.drawString("" + r, r*gridSize + x + 8, y - 5);
				//drawing out the string label
				g.setFont(new Font("TimesRoman", Font.BOLD,15));
				g.drawString(winName, x+66, y-25);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		regular.display();
		
		// regular.copyArray(secondPanel,regular.second);
		// regular.copyArray(thirdPanel, regular.third);
		repaint();
		timer.stop();
		
	}
	public class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				// regular.copyArray(previousPanel,regular.current);
				timer.start();
				break;
			default:
				System.out.print("This key does nothing Press Enter to advance turns");
				break;
			}
			
		}
	}
}
	