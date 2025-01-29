import java.util.Random;

/* As needed students can write additional methods and add attributes */

public class Taminator extends Critter
{
    //Class attribute by James Tam. Students do not make any changes to it but
    //additional attributes may be added.
    public static final char DEFAULT_APPEARANCE = 'T';
    Random rand = new Random();
 
    public Critter[][] thirdState = new Critter[10][10];
    
    public Taminator()
    {
	super(DEFAULT_APPEARANCE);
    }

    public Taminator(char newAppearance)
    {
	super(newAppearance);
    }
    public void inputArray(Critter[][] array) {
    	Biosphere bio = new Biosphere();
    	bio.copyArray(thirdState, array);
    }
    public void searchPerim(int tx, int ty, boolean exists) {
    	int r,c,x,y;
    	int tamX =tx;
    	int tamY = ty;
    	boolean tamExists = exists;
    	for(r = tamX-1; r<tamX+2;r++) {
    		for(c = tamY-1; c<tamY+2; c++) {
    			if((r <= 9)&&(c <= 9)&&(r > - 1)&&(c > -1)) {
    				if(thirdState[r][c].getAppearance()== '*') {
						thirdState[r][c].setAppearance(EMPTY);
					}
    			}
    		}
    	}
    	if (tamExists) {
    		searchLocation(tamX,tamY);
    	}
    }
    public void searchLocation(int tx, int ty) {
    	int x = rand.nextInt(10);
    	int y = rand.nextInt(10);
    	int tamX = tx;
    	int tamY = ty;		
    	if (thirdState[x][y].getAppearance() == ' ') {
    		thirdState[x][y].setAppearance('T');
    		thirdState[tamX][tamY].setAppearance(EMPTY);
    	} else {
    		searchLocation(tamX,tamY);
    	}
    }
}







