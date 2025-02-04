package src.main.java;
/* As needed students can write additional methods and add attributes */

public class Critter
{
    //Class attributes by James Tam. Students do not make any changes to these 3 but
    //additional attributes may be added.
    public static final char DEFAULT_APPEARANCE = '*';
    public static final char EMPTY = ' ';
    private char appearance;  
    private int row,col;
    //Start of modifiable code written by James Tam
    //Critter(), Critter(char), getAppearance(), setAppearance(char).
    //Students can make any changes.
    public Critter ()
    {
	setAppearance(EMPTY);
    }

    public Critter(char ch)
    {
	setAppearance(ch);
    }
    public Critter(int newRow, int newCol, char newAppearance) {
    	row = newRow;
    	col = newCol;
    	appearance = newAppearance;
    }
    
    public char getAppearance()
    {
	return appearance;
    } 

    public void setAppearance(char newAppearance)
    {
        appearance = newAppearance;
    } 
    //End of modifiable code written by James Tam

    
    public void setRow(int newRow) {
    	this.row = newRow;
    }
    public void setCol(int newCol) {
    	this.col = newCol;
    }
    public int getRow() {//needs to return stored row
    	return(row);
    }
    public int getCol() {
    	return(col);
    }
    //Code written by James Tam. Students: do not modify
    public String toString()
    {
	String s = "" + appearance;
        return(s);
    }
}

