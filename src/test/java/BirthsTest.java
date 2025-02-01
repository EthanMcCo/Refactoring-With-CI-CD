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
        world[0][0] = new Critter('*');
        world[1][0] = new Critter('*');
        world[0][1] = new Critter('*');
        bio = new Biosphere(world);
    }

    //feeds the world array position [1][1] and see if it will spawn a critter in that empty space
    @Test
    public void basicBirth(){
        bio.updateLocations();  //sets the values of location tracking array
        bio.births(0);  //hardcodes 0 as the index since this will be the [0][0] critter which should spawn critter in [1][1]
        //births applies changes to "second" state
        char newBorn = bio.second[1][1].getAppearance();    //gets the appearance of [1][1] after births function applied
        if(newBorn != '*'){
            fail("A critter was not born in the correct spot");
        }
    }
}
