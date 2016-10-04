import java.util.HashMap;

public class Board {
	
	//Variables
	private HashMap<Character, Point> points;
	private Point point;
	private ConsoleIO io;
	private boolean check;
	private Character input;
	private Character pawnId;
	private Character newLocation;
	private int count;
	private String line;

	//Constructor
	public Board() {
		//Create new hashmap with the points on the map
		points = new HashMap<Character, Point>();
		io = new ConsoleIO();
	}

	
	//Create new points when it doesn't exist already
	public void createPoints(char c){
		//Check if point already exists
		if(points.get(c) == null){
			//Create class
			points.put(c, point = Point.FREE);
		}
	}
	
	//Print the board in the console
	public void printBoard(String[] LAYOUT){
		//Loop through strings of the board layout
		for(String line : LAYOUT) {
			//Create character array
			char[] characters =	line.toCharArray();
			//Loop through characters
			for(char c : characters){
				//Check if character is A to Z
				if(Character.isLetter(c)){
					//Create points if necessary
					createPoints(c);
					//Print out points
					System.out.print(getGameChars(points.get(c).getPoint()));
				} else {
					//Print other characters
					System.out.print(c);
				}
			}
			
			//Print the example board right to the empty one
			System.out.print("      ");
			System.out.print(line);
			System.out.println();
		}
	}
	
	//Get the character to show ingame
	public Character getGameChars(Point point){
		//Check what the enum value is
		if(point.getPoint() == point.FREE){
			return '.';
		} else if(point.getPoint() == point.BLACK){
			return 'B';
		} else if(point.getPoint() == point.WHITE) {
			return 'W';
		}
		
		//If none of them apply
		return '.';
	}
	
	//Get the hashmap of points
	public HashMap<Character, Point> getBoard(){
		return points;
	}
	
	//Replace pawn on board
	public void replacePawn(Character c, Point point){
		points.replace(c, point);
	}
	
	//Compare points
	public boolean comparePoints(Character c, Point point2){
		return points.get(c).getPoint() == point2;
	}
}
