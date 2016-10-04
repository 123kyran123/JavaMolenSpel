import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleIO {
	private BufferedReader br;
	
	// The constructor constructs our
	// BufferedReader object:
	public ConsoleIO() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public String readInput() {
		// Give returnstring a temp value so it 
		// resembles an error when nothing was read:
		String returnString = "ERROR";
		try {
			// Try to read a line of text:
			returnString = br.readLine();
			
		}
		catch(Exception e){
			// If the read didn't work, put something in the console:
			writeOutput("Iets ging fout: " + e.getMessage());
		}
		
		return returnString;
	}
	
	public boolean checkInput(Character input){
		//Check if the given text is correct
		return input != 0 && Character.isLetter(input) && input > 'A' && input < 'X';
	}
	
	//Write the message given
	public void writeOutput(String message){
		System.out.println(message);
	}
}