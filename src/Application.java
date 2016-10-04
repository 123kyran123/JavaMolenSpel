import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Application extends BoardGeometry {
	
	//Variables
	private ConsoleIO io;
	private Board board;
	private List<Player> players;
	private List<Integer> userColor;
	private Player player;
	private Player player1;
	private Player player2;
	private String user;
	private Random rand = new Random();
	
	//Contructor
	public Application() {
		//Create new IO reference for writing and reading console text
		io = new ConsoleIO();
	}
	
	//Start the game
	public void start(){
		//Crate board
		board = new Board();
		
		//Intro messages
		System.out.println("Welkom bij het aloude Molenspel!");
		System.out.println();

		//create array of players
		players = new ArrayList<Player>();
		//get user given name and create players
		System.out.println("Geef naam van speler 1 (of een C voor een computer):");
		user = io.readInput();
		createPlayer(user);
		System.out.println("Geef naam van speler 2 (of een C voor een computer):");
		user = io.readInput();
		createPlayer(user);

		//Check if first user is test
		if(players.get(0).getName().toUpperCase().equals("TEST")){
			//TEST MODE
			//Set player 1 to white
			players.get(0).setColor(1);
			io.writeOutput(players.get(0).getName() + " is wit");
			player1 = players.get(0);
			//Set player 2 to black
			players.get(1).setColor(2);
			io.writeOutput(players.get(1).getName() + " is zwart");
			player2 = players.get(1);
			
		} else {
			
			//Randomize color for players
			userColor = new ArrayList<Integer>();
			userColor.add(1);
			userColor.add(2);
			Collections.shuffle(userColor);
			
			//Set color to the players
			setPlayer(0);
			setPlayer(1);
			
		}
		
		//Create Morris class
		Morris morris = new Morris(LAYOUT, board);
		//loop through game		
		morris.Play(player1, player2);
		//End game text and stuff
		endGame();
	}
	
	public void endGame(){
		//When game is over
			io.writeOutput("Wilt u nog een keer spelen? j/n");
			//Get response from user
			String response = io.readInput();
			//Check what response is
			if(response.contains("j")){
				//Restart game
				start();
				return;
			} else if(response.contains("n")){
				//Do nothing
				return;
			}
			
			//when user has given incorrect awnser
			io.writeOutput("Verkeerd antwoord gegeven. Probeer het nog eens: ");
			endGame();
			return;
	}
	
	//Create player
	public void createPlayer(String name){	
		//Check if needs to be a computer
		if(name.toUpperCase().equals("C") || name.toUpperCase().equals("COMPUTER")){
			//Create computer
			player = new Computer(user);
		} else {
			//Create player
			player = new Player(user);
		}
		
		//Create the player
		players.add(player);
	}
	
	//Get the player and set the color
	public void setPlayer(int player){
		//Set the color
		players.get(player).setColor(userColor.get(player));
		//Show what player is what color
		io.writeOutput(players.get(player).getName() + " is " + getColorInString(players.get(player).isColor()));

		//Store what player starts
		switch(userColor.get(player)){
			case 1:
				player1 = players.get(player);
				break;
			case 2:
				player2 = players.get(player);
				break;
		}
	}
	
	//Get the color from the player in a string
	public String getColorInString(int color){
		//Give color to user
		switch(color){
			case 1:
				return "Wit";
			case 2:
				return "Zwart";
			default: 
				return "Error";
		}
	}
}
