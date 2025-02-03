package src.test.java;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import src.main.java.Biosphere;
import src.main.java.Critter;

public class DeathsTest {
    private Biosphere bio;  
    private Critter [][] world = new Critter[Biosphere.ROWS][Biosphere.COLS];

    //when doing death testing we have to reference the critterLoc[] array rather than the second[][]
    //state array because second state initializes to be empty which we cannot change without changing the program
    //this is desired functionality.

    /*
    update locations updates critterLoc[] by counting rows down first then moving right

    world after running updateLocations:
    **
    *
    *

    critterLoc[] index for the critter at [0][1] will actually be in position critterLoc[3] since it counts everything in 
    row 0 first
    */

    //a method that will help with testing initializes an array to just have empty critters
	public void initArray(Critter[][] array){
		for (int i = 0; i < Biosphere.ROWS; i++) {
			for (int j = 0; j < Biosphere.COLS; j++) {
				array[i][j] = new Critter();
			}
		}
	}

    @Before
    public void setup(){
        initArray(world);   //initializes the world to be empty critters
        //creates below structure in the top left
        //**
        //**
        //*
        world[0][0] = new Critter('*');
        world[1][0] = new Critter('*');
        world[0][1] = new Critter('*');
        world[1][1] = new Critter('*');
        world[0][2] = new Critter('*');
        bio = new Biosphere(world);
        bio.updateLocations();  //sets the values of location tracking array
    }

    @Test
    public void basicDeath(){
        bio.deaths(4);  //hardcodes 0 as the index since this will be the [0][0] critter which should spawn critter in [1][1]
        //births applies changes to "second" state
        char deadCritter = bio.critterLoc[4].getAppearance();    //gets the appearance of [1][1] after births function applied
        if(deadCritter != ' '){
            fail("A critter was supposed to die at [1][1]");
        }
    }

    //tests if being on the edge of the world will throw an index out of bounds exception
    @Test
    public void edgeCaseDeath(){
        try {
            bio.deaths(1);  //makes the critter at [0][1] check it's death condition} catch (IndexOutOfBoundsException e) {
        } catch(IndexOutOfBoundsException e) {
            fail("Threw an index out of bounds exception");
        }
        char deadCritter = bio.critterLoc[1].getAppearance();
        if (deadCritter != ' ') fail("A critter was supposed to die at [0][1]");
    }

    //tests to see if a cell will die with 2 neighbours
    @Test
    public void twoNeighboursDeath(){
        bio.deaths(2); //checks the death condition of cell at [0][2]
        char deadCritter = bio.critterLoc[2].getAppearance();
        if(deadCritter == ' ') fail("Critter at [0][2] was not supposed to die with 2 neighbours");
    }

    //tests to see if a cell will die with 1 neighbour
    @Test
    public void oneNeighbourDeath(){
        /*
        World in this test testing if death on [0][3]:
        **
        **
        *
        *

        */
        world[0][3].setAppearance('*');
        bio.updateLocations();
        bio.deaths(3); //checks the death condition of cell at [0][3]
        char deadCritter = bio.critterLoc[3].getAppearance();
        if(deadCritter != ' ') fail("Critter at [0][3] was supposed to die with 1 neighbour");
    }  
    
}
