import java.util.Random;

public class Computer extends Player {
	//Variables
	private Random r;
	private String characs = "ABCDEFGHIJKLMNOPQRSTUVWX";
	
	//Constructer
	Computer(String name) {
		super(name);
	}
	
	//Returns character for the key of the board points
	public Character getPoint(){
		//Returns random character
		r = new Random();
		return characs.charAt(r.nextInt(characs.length()));
	}
}
