package src.main.java;
/* As needed students can write additional methods and add attributes */
import java.util.Scanner;
public class ProsperousBiosphere extends Biosphere // Students can't change this inheritance relation.
{
	public boolean gameGoing = true;
	boolean waitingTurn = true;
	String nextTurn;
	Scanner in = new Scanner(System.in);
    //Students can make any changes except for the relationship.
    public ProsperousBiosphere (Critter[][] aWorld) {
    	super(aWorld);
    }
    public ProsperousBiosphere (){}
    public void runTurn() {
    	updateLocations();
    	copyArray(changes,current);
    	for(int i = 0; i<critterCount; i++) {
    		prosperousBirths(i);
    		prosperousDeaths(i);
    	}
    	printWorlds();
    	while(waitingTurn) {
    		System.out.println("Press 'n' then enter to start the next turn or 'x' to end the game.");
        	nextTurn = in.nextLine();
        	if (nextTurn.equals("n")||(nextTurn.equals("N"))) {
        		waitingTurn = true; 
        		break;
        	} else if (nextTurn.equals("x")||(nextTurn.equals("X"))){
				this.gameGoing = false;
				waitingTurn = false;
			} else {
        		System.out.println("Sorry '"+nextTurn +"' is not valid");
        	}
    	}
    }
    public void prosperousBirths(int index) {
    	int row,col;
    	int neighbours = 0; 
    	if (critterLoc[index].getAppearance() == '*') {
    		row = critterLoc[index].getRow();//looks for a space around the critter at that index
    		col = critterLoc[index].getCol();
    		for(int r = row-1; r<row+2;r++) {
    			for(int c = col-1; c<col+2;c++ ) {
    				if((r <= 9)&&(c <= 9)&&(r > - 1)&&(c > -1)) {//ensuring r/c is in the world
    					if(changes[r][c].getAppearance() == ' ') {//makes sure it only checks the perimeter for the birth condition if it's a space
    						int tempRow = r;
    						int tempCol = c;
    						for(int y = tempRow-1; y<tempRow+2;y++) {
    							for(int x = tempCol-1; x<tempCol+2; x++) {
    								if((x <= 9)&&(y <= 9)&&(x > - 1)&&(y > -1)) {
    									if(current[y][x].getAppearance() == '*') {
    										neighbours++;
    										if(neighbours == 4) {
    											changes[tempRow][tempCol].setAppearance('*');
    											critterLoc[critterCount] = new Critter(tempRow,tempCol,'*');
    											critterCount++;
    										} else if(neighbours == 5) {
    											changes[tempRow][tempCol].setAppearance(' ');
    											critterLoc[critterCount] = new Critter(tempRow,tempCol,' ');
    											critterCount--;
    										} 
    									}
    								}
    							}
    						}	
    						neighbours = 0;
    					} 
    				}
    			}
    		}
    	}
    }
    public void prosperousDeaths(int index) {
    	int r;
    	int c;
    	int row,col;
    	int neighbours = 0;
    	if((critterLoc[index].getAppearance() == '*')) {
    		row = critterLoc[index].getRow();
    		col = critterLoc[index].getCol();
    		
    		for(r = row-1; r<row+2; r++) {
        		for(c = col-1; c<col+2; c++) {
        			if((r <= 9)&&(c <= 9)&&(r > - 1)&&(c > -1)) {
        				if((current[r][c].getAppearance() == '*')) {
        					neighbours++;
        				}
        			}
        		}
        	}
    		neighbours--;//Deletes when it counts itself
    		if((neighbours < 1)&&(neighbours > 4)) {
    			changes[row][col].setAppearance(' ');
    			critterLoc[index].setAppearance(' ');
    		}
    	}
    }
}
