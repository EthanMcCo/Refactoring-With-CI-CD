package src.test.java;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import src.main.java.Biosphere;
import src.main.java.Critter;

public class BirthsTest {
    private Biosphere bio;  
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
        world[0][0].setAppearance('*');
        world[1][0].setAppearance('*');
        world[0][1].setAppearance('*');
        bio = new Biosphere(world);
        bio.updateLocations();  //sets the values of location tracking array
    }

    //feeds the world array position [1][1] and see if it will spawn a critter in that empty space
    @Test
    public void basicBirth(){
        bio.births(0, 3);  //hardcodes 0 as the index since this will be the [0][0] critter which should spawn critter in [1][1]
        //births applies changes to "second" state
        char newBorn = bio.changes[1][1].getAppearance();    //gets the appearance of [1][1] after births function applied
        if(newBorn != '*'){
            fail("A critter was not born in the correct spot");
        }
    }
    
    //tests if critters can be born on the edge of the world
    @Test
    public void edgeCaseBirth() {
        //setting up world to be:
        //**
        // *
        world[1][1] = new Critter('*');
        world[0][1].setAppearance(' ');
        try {
            bio.births(0,3);  //should spawn critter at [0][1]
        } catch (IndexOutOfBoundsException e) {
            fail("Index out of bounds on edge case birth");
        }
        char newBorn = bio.changes[0][1].getAppearance();
        if(newBorn != '*') fail("Was supposed to spawn critter at [0][1]");
    }

    //tests to ensure there are no extra births when there are 2 neighbours
    @Test
    public void twoNeighboursBirth(){
        //setting up world to be:
        //**
        // *
        world[1][1] = new Critter('*');
        world[0][1].setAppearance(' ');
        bio.births(1, 3); //checks the perimeter of [1][0] for birth conditions
        char newBorn = bio.changes[2][1].getAppearance(); //makes sure the cell with 2 neighbours remains dead
        if(newBorn == '*') fail("Spawned critter when there should be none");  
    }

    //testing if a critter is spawned when there is only 1 neighbour
    @Test
    public void oneNeighbourBirth(){
        bio.births(1, 3); //checks the perimeter of [1][0] for birth conditions
        char newBorn = bio.changes[2][1].getAppearance(); //makes sure the cell with 1 neighbours remains dead
        if(newBorn == '*') fail("Spawned critter when there should be none");  
    }

    //testing if a critter will be spawned when there are more than 3 neighbors
    @Test
    public void manyNeighboursBirth(){
        //giving world appearance:
        //**
        //*
        //* 
        //then checking if a critter will spawn in [1][1]
        world[0][2].setAppearance('*');
        bio.births(0, 3);
        char newBorn = bio.changes[1][1].getAppearance();
        if(newBorn != ' ') fail("Birthed a critter that has 4 neighbours");
    }

}
