
import java.util.Random;

/* As needed students can write additional methods and add attributes */

public class Taminator extends Critter
{
    //Class attribute by James Tam. Students do not make any changes to it but
    //additional attributes may be added.
    public static final char DEFAULT_APPEARANCE = 'T';
    Random rand = new Random();
    
    public Taminator()
    {
	super(DEFAULT_APPEARANCE);
    }

    public Taminator(char newAppearance)
    {
	super(newAppearance);
    }

	//refactored this method to include an array parameter so we can remove thirdstate[][] from this class and keep things together
	//removed boolean "TamExists" because this function can only run if one does
    public void searchPerim(int tx, int ty, Critter[][] array) {
    	int r,c;
    	int tamX =tx;
    	int tamY = ty;
    	for(r = tamX-1; r<tamX+2;r++) {
    		for(c = tamY-1; c<tamY+2; c++) {
    			if((r <= 9)&&(c <= 9)&&(r > - 1)&&(c > -1)) {
    				if(array[r][c].getAppearance()== '*') {
						array[r][c].setAppearance(EMPTY);
					}
    			}
    		}
    	}
    	newLocation(tamX,tamY, array);
    	
    }
	//finds a new location for the taminator
    public void newLocation(int tx, int ty, Critter[][] array) {
    	int x = rand.nextInt(10);
    	int y = rand.nextInt(10);
    	int tamX = tx;
    	int tamY = ty;		
    	if (array[x][y].getAppearance() == ' ') {
    		array[x][y].setAppearance('T');
    		array[tamX][tamY].setAppearance(EMPTY);
    	} else {
    		newLocation(tamX,tamY, array);
    	}
    }
}







