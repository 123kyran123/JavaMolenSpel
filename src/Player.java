
public class Player {
	
	//variables
	private String name;
	private int color;
	private int pawns;
	private int phase;
	
	//Get player name
	public String getName() {
		return name;
	}
	
	//Get player color
	public int isColor() {
		return color;
	}

	//Get player color
	public int getColor(){
		return color;
	}
	
	//Set player color
	public void setColor(int color) {
		this.color = color;
	}

	//Get player phase it is in
	public int getPhase() {
		return phase;
	}

	//Set the phase the player is in
	public void setPhase(int phase) {
		this.phase = phase;
	}

	//Get the amount of pawn the player has
	public int getPawns() {
		return pawns;
	}
	
	//Set the amount of pawns the player has
	public void setPawns(int pawns) {
		this.pawns = pawns;
	}
	
	//Constructor
	public Player(String name) {
		//Set name
		this.name = name;	
		//Set pawns
		pawns = 9;
		//Set phase
		phase = 1;
		
	}
	
	//Check if user is computer
	public boolean isComputer(){
		if(name.toUpperCase().equals("C") || name.toUpperCase().equals("COMPUTER")){
			return true;
		}
		return false;
	}

}
