

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class NeighborTest {
    private Biosphere bio = new Biosphere();  
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

    //tests if looking around the perimeter along the border will throw an out of bounds exception
    @Test
    public void edgeCase(){
        int neighbors = 0;
        try {
            neighbors = bio.numNeighbors(0, 0, world);
        } catch (IndexOutOfBoundsException e) {
            fail("numNeighbors accessed element out of bounds");
        }
        assertEquals("Incorrect count of neighbors on edge case",2, neighbors);
    }

    //testing if we get index out of bounds for a coordinate that is out of bounds itself
    @Test
    public void outOfBounds(){
        int neighbors = 0;
        try {
            neighbors = bio.numNeighbors(-1, -1, world);
        } catch (IndexOutOfBoundsException e) {
            fail("numNeighbors accessed element out of bounds");
        }
        assertEquals("Incorrect count of neighbors on edge case",1, neighbors);
    }
}
