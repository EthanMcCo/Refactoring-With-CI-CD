package src.test.java;

import org.junit.Before;
import org.junit.Test;

import src.main.java.Biosphere;
import src.main.java.Critter;

import static org.junit.Assert.*;

public class NeighborTest {
    private Biosphere bio = new Biosphere();  
    private Critter [][] world = new Critter[bio.ROWS][bio.COLS];

    //a method that will help with testing initializes an array to just have empty critters
	public void initArray(Critter[][] array){
		for (int i = 0; i < bio.ROWS; i++) {
			for (int j = 0; j < bio.COLS; j++) {
				array[i][j] = new Critter();
			}
		}
	}

    @Before
    private void setup(){
        initArray(world);   //initializes the world to be empty critters
        //creates below structure in the top left
        //**
        //* 
        world[0][0] = new Critter('*');
        world[1][0] = new Critter('*');
        world[0][1] = new Critter('*');
    }

    @Test
    public void simpleTest(){
        int neighbors = 0;
        neighbors = bio.numNeighbors(1, 1, world);
        assertEquals("Basic Neighbor count wrong", 3, neighbors);
    }
}
