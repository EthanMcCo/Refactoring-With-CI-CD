# copyCritter() Dead Code - SHA: 1fffee97

## Affected Files/methods
- Biosphere.java with the copyArray() and copyCritter() methods

## Code Smell/What needed improvement
This class used a method that had dead logic. The method copyCritter() would update appearance when that method was already contained within the Critter class.

## Code Before Changes
![][image1]

## Code After Changes
![][image2]

## Refactoring Method Applied
To achieve this refactor I simply used an already existing function for setting the appearance of an existing critter and removed the useless code.

## How it was Tested
To test these changes I ran the runTurn() method in some JUnit test cases which applied the copyArray() method and all test cases passed without introducing any new behaviours.

## How Structure Improved
The clarity of copyArray() was improved since now the logic for changing the appearance is contained within copyArray().

## How does it enable future changes
This change likely does not enable future changes due to its simplicity.

---

# Births/Deaths Duplicate Code - SHA: 05c20e6a

## Affected Files/methods
- Biosphere.java
- births() and deaths() methods

## Code Smell/What needed improvement
The code smell that called for this change was duplicated code between the births() and deaths() methods.

## Code Before Changes
### Biosphere.java births() method:
![][image3]

### And in the deaths() method:
![][image4]

## Code After Changes
I extracted these changes into a method named numNeighbours() that counts the number of neighbours in the perimeter of a certain x/y coordinate and returns the count.

![][image5]

### In births():
![][image6]

### In Deaths():
![][image7]

## Refactoring Method Applied
I noticed that in the births() and deaths() methods there was similar loop logic to count how many neighbours were in proximity to a given x/y coordinate. To solve this problem I used the extract method approach and pulled out the logic that was the same. Then in place of those for loops I simply made a call to the newly built numNeighbors() function.

## How it was Tested
To test these changes I made JUnit test cases that directly tested edge cases of the world. Namely if the new neighbours function would throw index out of bounds at the edge of the world, or if the neighbours function would report the correct number of neighbours if new neighbours are birthed/die. I also went and tested the refactored birth/deaths methods to ensure that they still behave as intended with similar situations.

## How Structure Improved
After completing this refactor it made the code a lot more clear what was actually happening and the methods are a lot more modifiable. Before you would have to track down the logic of four for loops just to get a grasp of what was going on, but now that logic is contained in a separate method.

## How does it enable future changes
Changes made from this refactor led into other changes as it made me identify that there was a substantial amount of dead code being contained in the birth/death functions that would be later removed.

## Additional information
The SHA number for this refactor is 05c20e6a and the changes were made in the Births/deaths Duplicate code branch.

---

# Births Dead Code - SHA: 34a609e5

## Affected Files/methods
- Biosphere.java
- Critters.java
- Births()

## Code Smell/What needed improvement
When looking at updateLocations() in the biosphere class I noticed that row/col was updated for each critter so creating new critters was not important anymore. This realization made the fact I was creating new critters to birth them useless when I could just take advantage of the setAppearance() method.

## Code Before Changes
![][image8]
![][image9]

## Code After Changes
![][image10]

## Refactoring Method Applied
To facilitate this refactor I used the remove approach to remove the dead code.

## How it was Tested
I ran the JUnit tests pertaining to the birth() method in the biosphere class and noticed that all test cases passed. I also created new test cases that ensured newly born critters wouldnt change the spawn conditions so that bornRecent boolean was not needed.

## How Structure Improved
By reducing the number of lines in an already very large method this significantly improved clarity of what was going on.

## How does it enable future changes
The simplification of structure allowed a future change to make births() allow for different rulesets through parameterization. This would allow for duplicate code to be further removed.

## Additional information
This change was contained in the Dead-Code-Critters branch.

---

# Terminator feature envy - SHA: 9f51730b

## Affected Files/methods
- Taminator.java
- searchPerim()
- searchLocation()

## Code Smell/What needed improvement
The Taminator class had one of my world state 2D arrays in it however this did not make sense since this is supposed to just be an extension of the Critter class as a specialized critter. The specialized Critter should not know about the entire world.

## Code Before Changes
![][image11]

## Code After Changes
![][image12]

In addition to this I also adjusted the functions that the taminator uses with this array to take it as a parameter instead so it can be called from the biosphere class through a Tamintor object.

![][image13]

## Refactoring Method Applied
To facilitate this refactor I used the move method and brought the 2D array over to the Biosphere class where it belongs. In addition to this I adjusted the parameters of the two helper functions for the Taminator class to take a 2D array instead of needing that array within the class.

## How it was Tested
To test that these changes did not change functionality I created JUnit test cases that called the searchPerim function and searchLocation functions for coverage. Then after isolating these functions I tested to see if they indeed removed critters within the perimeter of the Taminator as well as observed if the Taminator would be moved to a new location after.

## How Structure Improved
The change of location of the third, Taminator, state made it so each world's state was contained in one world data class, Biosphere.java. Now if someone is trying to make changes to the functionality of each of these states they don't have to try and locate where this third state was declared.

## How does it enable future changes
This refactor aided in a future change where I realized that having this third state array actually wasnt needed at all. This realization wouldnt have been easy to spot had I left it contained in a separate class. I would have continued using the 3 state approach.

## Additional information
The changes were made in the shotgun surgery branch before merging into main.

---

# GUI Constants refactor - SHA: a614b4d0

## Affected Files/methods
- Gamepanel.java
- drawFirstState()
- drawSecondState()
- drawThirdState()

## Code Smell/What needed improvement
The code smell that this fit under the most was comments. Each of the constants that decided window shape/size were either duplicated logic or unclear. Sometimes second_window would be drawing things in the third state.

## Code Before Changes
![][image14]

## Code After Changes
In this code there was also duplicated logic since each window was a box storing width/height was not important. To solve this code smell I used the rename approach. The result of the renamed constants:

![][image15]

It can be observed that the large window constant is now 700. This is actually the same size as the previous window size because in the old version of the code there was a floating number that would subtract 100.

![][image16]

## Refactoring Method Applied
To solve the comments' bad smell I used the rename approach to rename the poorly labeled constants. I also removed some of the dead code that didn't need to be there like length/height.

## How it was Tested
Because this is a GUI component testing with JUnit was not possible so I ran the program and ensured that the changes to the constants reflected what the GUI used to be.

## How Structure Improved
After making these changes it's a lot more clear on how you can modify the window size from modifying these constants.

## How does it enable future changes
By making these changes to the GUI constants it helped substantially when removing duplicate code between all of the drawState() methods.

## Additional information
The changes to this file were made in the GUI-Duplicate-Code branch.

---

# GUI Duplicate code - SHA: 373d88f2

## Affected Files/methods
- GamePanel.java
- drawFirstState()
- drawSecondState()
- drawThirdState()

## Code Smell/What needed improvement
The code smell for this change was duplicate code. There was three states that were being drawn with only minor changes to their x/y coordinates and window size.

## Code Before Changes
This is an example of one of the methods before any changes they all had the same function calls to the graphics library but would input slightly different coordinates.

![][image17]

## Code After Changes
Now the code after the refactor takes x/y/window size and grid size into account then also takes what array we are drawing from as parameters.

![][image18]

## Refactoring Method Applied
To facilitate this change I used the extract method approach. I isolated what parts of the code referenced the x/y coordinates and used those as parameters. I then isolated what parts of the program referenced the size of the box/grid and made respective parameters for those. The final step was ensuring that each window would be drawing from the correct 2D world array so I have that passed in as a parameter too. After isolating these constants between each of the three state functions I extracted the logic to draw the windows and passed in the parameters for each graphics function call.

## How it was Tested
To test that this was producing the same results I called my drawWindow function without deleting the old drawState() methods and I ran the program each time I would draw 1 of the 3 windows. With each window there were no changes. I also provided multiple input files that would test the edge cases and did not run into any problems.

## How Structure Improved
This significantly improved the structure of the codebase since now if you want to modify some element of the GUI you can do so from the call to drawWindow()

## How does it enable future changes
If someone wanted to reposition the windows and change their size this would be made easy by just adjusting 2-4 values whereas before they would have had to look through the state functions and interpret what was going on with the x/y calculation.

## Additional information
The changes to this file were made in the GUI-Duplicate-Code branch.

---

# Long Method Game Runner - SHA: a67970f4

## Affected Files/methods
- Biosphere.java 
- GamePanel.java 
- display()/worldBody() methods in biosphere.java

## Code Smell/What needed improvement
The code smell that called for this refactor was the long method code smell. The logic that would build and run the world was mixed up between two functions display() and worldBody() where display() would call worldBody(). This chain of calls made it unclear how the program was progressing and following along with each of the steps had me going all over the place. Because these calls were chained it was basically one long method that did more than it should have.

## Code Before Changes
![][image19]
![][image20]

## Code After Changes
![][image21]

## Refactoring Method Applied
The refactoring method that was applied was breaking the functionality of the two functions display() and worldBody() down into smaller sub methods. After breaking the two methods down they were contained in a single runTurn() method that would run exactly one turn each time it was called.

## How it was Tested
To test the changes made I ran the already existing JUnit tests and also observed the graphical output to ensure that there were no changes in either cases. There was not logical changes going on here, simply just reorganizing code.

## How Structure Improved
The structure improved because you don't have one long function both drawing the world to sysout and updating each of the critter locations. Instead we have a runTurn() method that has each of these functionalities broken down into smaller sub methods. Now if you want to modify the functionality of a method it's much easier to facilitate this change.

## How does it enable future changes
The refactorization led into the change of duplicate code between prosperous biosphere and the regular with respect to the birth/death functions. For the most part the code between these two classes for births/deaths was the same with just some slight rule adjustments for the neighbours. Now with the methods broken down we can modify these rulesets much easier.

## Additional information
The changes were made in the temporary-field-copyArray branch.

---

# Duplicate Code Prosperous Births/Deaths - SHA: 3f05a4a3

## Affected Files/methods
- Biosphere.java
- ProsperousBiosphere.java
- births() and deaths()

## Code Smell/What needed improvement
In biosphere and prosperous biosphere classes the birth and death functions logically identical except for one if statement that was the condition for how many neighbours there needed to be to spawn/kill a critter.

## Code Before Changes
![][image22]
![][image23]

## Code After Changes
![][image24]
![][image25]

## Refactoring Method Applied
To make the refactorization necessary I used the extract method to pull the method from the prosperous biosphere up to its super class parent Biosphere.java. After doing this I made a parameter in the births() and deaths() method that would allow each class to pass what ruleset it wants to work with. This way prosperous biosphere doesnt have to redefine it's the birth/death condition functions.

## How it was Tested
To test this I created JUnit test cases for the prosperous biosphere to ensure that the 2D arrays were storing the correct data and doing the proper changes. After all the test cases passed I felt confident that this change was working as intended.

## How Structure Improved
With the removal of the dead code it's a lot more clear what the prosperous biosphere was doing and it was simply just a different ruleset.

## How does it enable future changes
Because the only real difference between the prosperous biosphere and regular one is it's ruleset there really isnt much need for Prosperous biosphere to be a subclass of biosphere. This class is now exhibiting traits of feature envy and could very well be an inline class or just a constant at the top with prosperous rules. Prosperous biosphere doesnt really need to exist anymore.

## Additional information
The changes for this refactor were made in the duplicate-code-biosphere/prospBiosphere branch.

[image1]: images/extracted_code_image_1.png
[image2]: images/extracted_code_image_2.png
[image3]: images/extracted_code_image_3.png
[image4]: images/extracted_code_image_4.png
[image5]: images/extracted_code_image_5.png
[image6]: images/extracted_code_image_6.png
[image7]: images/extracted_code_image_7.png
[image8]: images/extracted_code_image_8.png
[image9]: images/extracted_code_image_9.png
[image10]: images/extracted_code_image_10.png
[image11]: images/extracted_code_image_11.png
[image12]: images/extracted_code_image_12.png
[image13]: images/extracted_code_image_13.png
[image14]: images/extracted_code_image_14.png
[image15]: images/extracted_code_image_15.png
[image16]: images/extracted_code_image_16.png
[image17]: images/extracted_code_image_17.png
[image18]: images/extracted_code_image_18.png
[image19]: images/extracted_code_image_19.png
[image20]: images/extracted_code_image_20.png
[image21]: images/extracted_code_image_21.png
[image22]: images/extracted_code_image_22.png
[image23]: images/extracted_code_image_23.png
[image24]: images/extracted_code_image_24.png
[image25]: images/extracted_code_image_25.png
