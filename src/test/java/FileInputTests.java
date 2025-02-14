import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileInputTests {
    
    // Code used from stack overflow https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    // End of cited code from stackoverflow

    //testing that the world will be built given a file
    @Test
    public void basicFile(){
        ByteArrayInputStream fileInput = new ByteArrayInputStream("test1.txt".getBytes());
        System.setIn(fileInput);
        Biosphere bio = new Biosphere(FileInitialization.read()); //reads input

        if(bio.current[0][0].getAppearance() != '*') fail("did not read file properly");
    }

    //testing what happens when the file is not found
    @Test
    public void fileNotFound(){
        String inputs = "badFile.txt\ntest1.txt\n";  
        ByteArrayInputStream fileInput = new ByteArrayInputStream(inputs.getBytes());
        System.setIn(fileInput);
        
        Biosphere bio = new Biosphere(FileInitialization.read());
        
        String location = System.getProperty("user.dir");
        assertTrue(outContent.toString().contains("Input file not in " + location));
    }

}
