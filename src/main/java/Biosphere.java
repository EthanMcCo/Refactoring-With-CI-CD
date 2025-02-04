package src.main.java;
/* As needed students can write additional methods and add attributes */

public class Biosphere
{
    //Constant declarations and attributes by James Tam, don't change.
    public static final int ROWS = 10;
    public static final int COLS = 10;
    public static final String LINE = "  - - - - - - - - - -";
    public static final String HORIZONTAL_COUNT = "  0 1 2 3 4 5 6 7 8 9 ";
    Taminator tam = new Taminator();
    public Critter [][] current; 						   //state where we look to apply birth/death rules and apply them to changes[][]
    public Critter [][] changes = new Critter[ROWS][COLS];  //state where all the changes are applied INTO
    public Critter [][] prev = new Critter[ROWS][COLS];	   //the old version of current state
    public Critter [] critterLoc= new Critter[100];
    int critterCount = 0;
    int turnSinceBirth = 0;
    boolean gameRunning = true;
    int tamX;
    int tamY;
    public boolean tamExists = false;
    public boolean regularRules = true;
    //Original code written by James Tam, don't modify
    public Biosphere(Critter [][] aWorld)
    {
        current = aWorld;
		initArrays();
    }

    public Biosphere() { }

	//initializes prev array and changes array to be empty spaces
	private void initArrays(){
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				this.changes[i][j] = new Critter();
				this.prev[i][j] = new Critter();
			}
		}
	}

	//will combine multiple functions to run the next turn after birth/deaths applied
	//also displays these changes to sysout
	public void runTurn(){
		//display the headers for each state in sysout
		displayHeaders();
		
		//update the locations for the critterLoc[] array
		updateLocations();

		//apply the births/deaths 
		birthsAndDeaths();

		//print out the updated states to sys out and repeat
		printWorlds();

		//update the arrays to store up-to-date data
		copyArray(prev, current);
		copyArray(current, changes);
	}
	public void displayHeaders(){
        int iCounter = 0;
        System.out.println("\tPrevious\t     Second State\t      TAMINATOR!!");
        System.out.print(" ");
        for(int i = 0 ;i <ROWS; i++ ) {
        	System.out.print(" "+i);
        	if((i==9)&&(iCounter !=2)) {
        		i=-1;
        		System.out.print("\t");
        		System.out.print(" ");
        		iCounter++;
        	}
        }
        System.out.println();
	}
	//iterates through the critterLoc[] array and applies birth/deaths accordingly
	public void birthsAndDeaths(){
		copyArray(changes,current);	//before making changes to changes[][] ensure it starts at the same point as current[][]
		for (int i = 0; i < critterCount; i++) {
			births(i);
			deaths(i);			
		}
		//will also apply the taminator changes
		if(tamExists) tam.searchPerim(tamX, tamY, changes);	//kills cells in the radius around taminator then relocates taminator
	}

	public void printWorlds(){
		int stateCounter = 0;	//tracks what state we're printing to sysout
		for(int r = 0; r<ROWS;r++) {
			for(int i = 0; i<3; i++) {
				System.out.print(LINE);
				System.out.print("\t");
			}
			System.out.println();
			System.out.print(r);
			for(int c = 0; c<COLS;c++) {
				if (stateCounter == 0) {
					System.out.print("|"+current[r][c]);
				} else {
					System.out.print("|" +changes[r][c]);
				}
						
				if ((c==9)&&(stateCounter !=2)) {
					System.out.print("|\t");
					System.out.print(r);
					c=-1;
					stateCounter++;
				}
			}
			stateCounter = 0;
			System.out.println("|");
		}
		System.out.println(LINE+"\t"+LINE+"\t"+LINE);
	}

	//this should be the next big refactor
    public Critter copyCritter(char letter) {
    	Critter C = null;
    	Critter T = null;
    	if((letter == ' ')||(letter == '*')) {
			C = new Critter(letter);
			return C;
		}else {
			T = new Taminator(letter);
			return T;
		}
    }
    public void copyArray(Critter[][] getCopy, Critter[][] setCopy) {//first value is going to be the copy and the second array is going to be the thing that's recieved the copy
    	for(int r = 0; r<ROWS; r++) {
    		for (int c = 0; c<COLS; c++) {
    			getCopy[r][c] = copyCritter(setCopy[r][c].getAppearance());
    		}
    	}
    }  

	//count neighbors
	//function returns the number of neighbors of a cell given the x/y coordinate
	public int numNeighbors(int x, int y, Critter[][] world){
		int neighbors = 0;
		for (int r = x - 1; r < x + 2; r++){
			for (int c = y - 1; c < y + 2; c++){
				if((r < 0) || (r >= ROWS) || (c < 0) || (c >= COLS)) continue; //skips over values outside of our world
				if(r == x && c == y) continue; //skips over itself
				if (world[r][c].getAppearance() == '*') neighbors++;
			}	
		}
		return neighbors;
	}
    
// Rather than iterating through all 100 squares then subsequently search all 8 squares around that to spot stars to see if that space 
// matches the birth condition I decided it made more sense to just iterate through the 8 surrounding squares of all spaces next to a star.
// if there's no star even near the spot you're checking for birth conditions why even check it just wastes memory/time.
    public void births(int index) {
    	int row,col;
    	int neighbours = 0; 
		row = critterLoc[index].getRow(); //looks for a space around the critter at that index
		col = critterLoc[index].getCol();
		for(int r = row-1; r<row+2;r++) {
			for(int c = col-1; c<col+2;c++ ) {
				if((r <= 9)&&(c <= 9)&&(r > - 1)&&(c > -1)) {//ensuring r/c is in the world
					if(changes[r][c].getAppearance() == ' ') {//makes sure it only checks the perimeter for the birth condition if it's a space
						neighbours = numNeighbors(r, c, current);
						if(neighbours == 3) changes[r][c].setAppearance('*');	//when there are 3 neighbours we spawn a critter
						neighbours = 0;
					} 
				}
			}
		}
    }
    public void deaths(int index) {
    	int row,col;
    	int neighbours = 0;
    	if((critterLoc[index].getAppearance() == '*')) {
    		row = critterLoc[index].getRow();
    		col = critterLoc[index].getCol();

			neighbours = numNeighbors(row, col, current);

    		if((neighbours !=2) && (neighbours !=3)) {
    			changes[row][col].setAppearance(' ');
    			critterLoc[index].setAppearance(' ');		//will keep this line in for testing purposes
    		}
    	}
    }
	//searches through the world and updates an array that tracks critters
    public void updateLocations() {
    	int r,c;
    	critterCount = 0;
    	for(r=0;r<ROWS;r++) {
    		for(c=0;c<COLS;c++) {
    			if(current[r][c].getAppearance() == '*') {
					critterLoc[critterCount] = new Critter(r,c,'*');
					critterCount++;
				} else if (current[r][c].getAppearance() == 'T'){
					tamX = r;
					tamY = c;
					tamExists = true;
				}
    		}
    	}
    }
    
    //Original code written by James Tam, don't modify
    // getCurrent() used by    
    public Critter [][] getCurrent() 
    {
        return(current);
    }
    public Critter [][] getChanges(){
    	return(changes);
    }
}
