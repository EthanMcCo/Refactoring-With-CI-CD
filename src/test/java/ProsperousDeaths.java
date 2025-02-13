

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


public class ProsperousDeaths {
    private ProsperousBiosphere bio;  
    private Critter [][] world = new Critter[Biosphere.ROWS][Biosphere.COLS];

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
        //**
        world[0][0].setAppearance('*');
        world[1][0].setAppearance('*');
        world[0][1].setAppearance('*');
        world[1][1].setAppearance('*');
        world[0][2].setAppearance('*');
        world[1][2].setAppearance('*');
        bio = new ProsperousBiosphere(world);
        bio.updateLocations();  //sets the values of location tracking array
    }

    @Test
    public void basicDeath(){
        bio.deaths(4, 1, 4);  //hardcodes 0 as the index since this will be the [0][0] critter which should spawn critter in [1][1]
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
            bio.deaths(1, 1, 4);  //makes the critter at [0][1] check it's death condition
        } catch(IndexOutOfBoundsException e) {
            fail("Threw an index out of bounds exception");
        }
        char deadCritter = bio.critterLoc[1].getAppearance();
        if (deadCritter != ' ') fail("A critter was supposed to die at [0][1]");
    }

    //tests to see if a cell will die with 2 neighbours
    @Test
    public void twoNeighboursDeath(){
        bio.deaths(2, 1, 4); //checks the death condition of cell at [0][2]
        char deadCritter = bio.critterLoc[2].getAppearance();
        if(deadCritter == ' ') fail("Critter at [0][2] was not supposed to die with 2 neighbours");
    }

    //tests to see if a cell will die with 1 neighbour
    @Test
    public void noNeighbourDeath(){
        /*
        World in this test testing if death on [0][3]:
        **
        **
        **
        
        *

        */
        world[0][4].setAppearance('*');
        bio.updateLocations();
        bio.deaths(3, 1, 4); //checks the death condition of cell at [0][3]
        char deadCritter = bio.critterLoc[3].getAppearance();
        if(deadCritter != ' ') fail("Critter at [0][4] was supposed to die with no neighbour");
    }  

    //testing if a second round of births/deaths things will work properly
    /*
        World:
        ****
        **    
        ****

    */
    @Test
    public void twoRoundsDeaths(){
        world[2][0].setAppearance('*');
        world[3][0].setAppearance('*');
        world[2][2].setAppearance('*');
        world[3][3].setAppearance('*');
        bio.updateLocations();
        testingRunTurn(); //applies all births/deaths for every critter
        /*new world after changes should be:
        
         World:
        ****
           *   
        ****

        */
        testingRunTurn(); //runs a second round
        /*new world after changes should be:
        
         World:
        ****
        *  *   
        ****

        */
        bio.updateLocations();
        if(bio.critterLoc[1].getAppearance() != '*') fail("Was supposed to spawn critter at [0][1] 2 rounds of death went wrong");
    }

    //runs the next turn without waiting for sysout input so a test case can be run
    public void testingRunTurn(){
        bio.updateLocations();
        bio.copyArray(bio.changes, bio.current);
        for(int i = 0; i<bio.critterCount; i++) {
    		bio.births(i, 4);
    		bio.deaths(i, 1, 4);
    	}

        bio.copyArray(bio.prev, bio.current);
		bio.copyArray(bio.current, bio.changes);
    }
    
}
