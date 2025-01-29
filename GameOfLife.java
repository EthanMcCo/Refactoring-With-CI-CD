import java.util.Scanner;

/* No additional methods and nor attributes to be added. */ 

/*
 	Author: Ethan McCorquodale UCID: 30125353
 	Version 3/20/21: I just outlined methods that I wanted to construct and started figuring out what the design specifications was actually
 					 asking to be done. Created the 3 Critter arrays that correspond to each state that's being displayed. Outlined
 					 birth and death methods that'll be built further later, idea is to iterate through a 1D array similarly to assignment
 					 3. 
 	Version 3/21/21: I spend most of today just figuring out how to print the 3 states in the same row in the console I had a few versions
 					 but they all ended up being incredibly long methods. So I testing out some different approaches and was able to shorten
 					 the method to 20 lines (Since has expanded). I have the output now displaying the current Critter array in all 3 states
 					 that'll eventually get swapped to separate 2D critter arrays that'll show they're respective.
 	Version 3/22/21: This was my first day off in a while so I was finally able to start working on this assignment.  I created two methods
 					 that'll essentially clone my container array of objects current (or whatever two arrays I pass into it) I did this because
 					 I believe I was unintentionally copying the addresses of each Critter object so when I'd object a critters appearance it'd
 					 in all the arrays/states.  I tried to figure out why this was was happening with deep/shallow copies but couldn't quite
 					 figure it out so I did this instead.  The two methods create new Critter objects or Taminator objects depending on the 
 					 character that the current array's appearance holds for that row/column then my createCritter method returns that critter
 					 to be then set to the same row/column in the new array that's a copy. I also got my death/birth methods setup the birth 
 					 method I optimized so it wouldnt check all 100 squares looking for a space then for that's spaces' 8 surrounding squares 
 					 check if they're a *, instead I only checked spaces that were in the perimeter of an already existing * because there's
 					 no sense in checking a square that'll never spawn a critter. At this point program matches Game of Life outputs.
 	Version 3/24/21  Today I focused on getting the Taminator part of the code functional.  I created  a method in the Taminator class that
 					 will take a critter array as a parameter so from the biosphere class I can create a taminator object then pass the 
 					 2D Critter array into it to then call my search perimeter method in the Taminator class that takes the current position
 					 that the Taminator is at and checks everything in it's perimeter if it finds star's in it's perimeter it sets that
 					 appearance of Critter to ' ' then when the program is finished it searches for a location to teleport to and then does
 					 so. This handles the Taminator state.  Now that the Taminator/Births and Deaths states are handled I just need to set 
 					 previous to = old Taminator array at the end so the previous will equal the last appearance of Taminator. From here I 
 					 imported my arrays into the GamePanel class using my copyArray method that I have in biosphere. Now that the arrays are 
 					 in the GamePanel class I just run my methods that print them onto the screen at their respective coordinates.
 					 
 					 I worked really hard on this I hope you enjoy it :) 
 					 -Ethan McCorquodale
 
 */
public class GameOfLife
{
    public static void main (String [] args)
    {
        String ruleSet;
        boolean settingRules = true;
        Scanner in = new Scanner(System.in);
        Biosphere regular = new Biosphere();
        ProsperousBiosphere prosp = new ProsperousBiosphere();
        while(settingRules) {
        	System.out.print("Press 'r' to play with the regular rule set \nPress 'p' to play with the prosperous rule set: ");
            ruleSet = in.nextLine();
        	if((ruleSet.equals("R"))||(ruleSet.equals("r"))) {
        		regular.regularRules = true;
				prosp.gameGoing = false;
        		new GameFrame();
        		settingRules = false;
        	} else if ((ruleSet.equals("p"))||(ruleSet.equals("P"))) {
        		regular.regularRules = false;
        		prosp = new ProsperousBiosphere(FileInitialization.read());
        		settingRules = false;
        	} else {
        		System.out.print("Sorry, '"+ruleSet+ "' is not a valid character.");
        	}
        	while(prosp.gameGoing) {	
        		prosp.runTurn();
        	}
        }
        
       
        
    }
}



