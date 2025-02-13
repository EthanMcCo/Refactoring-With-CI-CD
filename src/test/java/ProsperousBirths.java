
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ProsperousBirths {
    private ProsperousBiosphere bio;  
    private Critter [][] world = new Critter[Biosphere.ROWS][Biosphere.COLS];

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
        //*
        //*  
        world[0][0].setAppearance('*');
        world[1][0].setAppearance('*');
        world[0][1].setAppearance('*');
        world[0][2].setAppearance('*');
        bio = new ProsperousBiosphere(world);
        bio.updateLocations();  //sets the values of location tracking array
    }

    //basic birth in prosp biosphere.  Utilizing births() function in biosphere with prosperous biosphere rules hardcoded
    @Test
    public void basicBirth(){
        bio.births(0, 4); //checks the birth conditions around the first critter
        if(bio.changes[1][1].getAppearance() != '*') fail("Basic birth test failed, was supposed to spawn critter at [1][1]");
    }

    //testing if a critter being born on the edge will cause an out of bounds exception
    //world:
    /*
        **
         *
        * 
     */
    @Test
    public void edgeBirth(){
        world[0][1].setAppearance(' ');
        world[1][1].setAppearance('*');
        bio.updateLocations();
        try {
            bio.births(0, 4);
            if(bio.changes[0][1].getAppearance() != '*') fail("Was supposed to spawn a critter on edge at [0][1]");    
        } catch (IndexOutOfBoundsException e) {
            fail("Spawning a critter on the border caused an out of bounds exception");
        }
    }

    //testing if a critter will be born with only 3 neighbours
     //world:
    /*
        **
        * 
     */
    @Test
    public void threeNeighbourBirth(){
        world[0][2].setAppearance(' ');
        bio.updateLocations();
        bio.births(0, 4);
        if(bio.changes[1][1].getAppearance() == '*') fail("was not supposed to spawn a critter at [1][1] with 3 neighbours");
    }

    //testing if a critter will spawn if there are 5 neighbours
    //world:
    /*
        ***
        *
        * 
     */
    @Test
    public void fiveNeighbourTest(){
        world[2][0].setAppearance('*');
        bio.updateLocations();
        bio.births(0, 4);
        if(bio.changes[1][1].getAppearance() == '*') fail("Was not supposed to spawn a critter at [1][1] with 5 neighbours");
    }

    //testing 2 rounds of births to ensure that newly spawned critters can spawn other critters
    //world:
    /*
         *
        * 
        * **
     */
    @Test
    public void twoRoundsBirths(){
        world[0][0].setAppearance(' ');
        world[2][2].setAppearance('*');
        world[2][3].setAppearance('*');
        bio.updateLocations();
        bio.births(1, 4);   //this should spawn a critter at [1][1] which will make [1][2] have 4 neighbours
        bio.births(1, 4); //should spawn a critter at [1][2]
        if(bio.changes[1][2].getAppearance() != '*') fail("Should have spawned a critter at [1][2] in second round of births");
    }
}
