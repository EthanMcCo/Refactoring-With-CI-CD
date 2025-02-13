
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


public class TaminatorTest {
    private Biosphere bio;
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
        bio = new Biosphere(world);
        bio.updateLocations();  //sets the values of location tracking array
    }

    //tests if the Taminator will kill critters in it's perimeter
     //creates below structure in the top left
    //**
    //**
    //*T
    @Test
    public void basicDeaths(){
        world[1][2].setAppearance('T');
        bio.updateLocations();

        //runs one cycle of the world
        bio.runTurn();

        if(bio.current[0][1].getAppearance() == '*') fail("Critter at 0 1 was supposed to die.");
        if(bio.current[1][1].getAppearance() == '*') fail("Critter at 1 1 was supposed to die.");
        if(bio.current[0][2].getAppearance() == '*') fail("Critter at 0 2 was supposed to die.");
        
    }

    //tests if the Taminator will kill critters in it's perimeter
     //creates below structure in the top left
    //**
    //**
    //T*
    @Test
    public void edgeDeaths(){
        world[0][2].setAppearance('T');
        bio.updateLocations();

        //runs one cycle of the world
        bio.runTurn();

        if(bio.current[0][1].getAppearance() == '*') fail("Critter at 0 1 was supposed to die.");
        if(bio.current[1][1].getAppearance() == '*') fail("Critter at 1 1 was supposed to die.");
        if(bio.current[1][2].getAppearance() == '*') fail("Critter at 0 2 was supposed to die.");
        
    }

    //tests if the Taminator will change positions after killing things in it's perimeter
     //creates below structure in the top left
    //**
    //**
    //T*
    @Test
    public void changePositions(){
        world[0][2].setAppearance('T');
        bio.updateLocations();

        //runs one cycle of the world
        bio.runTurn();

        if(bio.current[0][1].getAppearance() == '*') fail("Critter at 0 1 was supposed to die.");
        if(bio.current[1][1].getAppearance() == '*') fail("Critter at 1 1 was supposed to die.");
        if(bio.current[1][2].getAppearance() == '*') fail("Critter at 0 2 was supposed to die.");
        if(bio.current[0][2].getAppearance() == 'T') fail("Taminator was supposed to move after killing perimeter");
    }

    //program can not handle multiple taminators will only work with 1 taminator the other one is forgotten
     //creates below structure in the top left
    //*T
    //**
    //T*
    @Test
    public void multipleTams(){
        world[0][2].setAppearance('T');
        world[1][0].setAppearance('T');
        
        bio.updateLocations();

        //runs one cycle of the world
        bio.runTurn();

        if(bio.current[0][1].getAppearance() == '*') fail("Critter at 0 1 was supposed to die.");
        if(bio.current[1][1].getAppearance() == '*') fail("Critter at 1 1 was supposed to die.");
        if(bio.current[1][2].getAppearance() != '*') fail("Critter at 1 2 cannot die since only one Tam is processed");
        if(bio.current[0][0].getAppearance() == '*') fail("Critter at 0 0 was supposed to die.");
        
        if(bio.current[0][2].getAppearance() != 'T') fail("Taminator cannot move since this is the second observed taminator");
        if(bio.current[1][0].getAppearance() == 'T') fail("Taminator was supposed to move after killing perimeter");
        
    }
    
    
}