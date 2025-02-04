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
    public void next() {
    	updateLocations();
    	copyArray(changes,current);
    	for(int i = 0; i<critterCount; i++) {
    		births(i, 4);
    		deaths(i, 1, 4);
    	}
		displayHeaders();
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
}
