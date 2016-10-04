public class Morris {
	
	//variables
	private String[] LAYOUT;
	private Board board;
	private Point point;
	private ConsoleIO io;
	private Character input;
	private Character pawnId;
	private Character newLocation;
	private int count;
	private String line;
	private boolean condition;
	private String chars;

	//Constructor
	public Morris(String[] LAYOUT, Board board) {
		//Store the given values
		this.LAYOUT = LAYOUT;
		this.board = board;
		//Create new IO reference for writing and reading console text
		io = new ConsoleIO();
		
		//The variable that checks if winning condition has been met
		condition = true;
		//Initalize board
		board.printBoard(LAYOUT);
		//Game starts now
		System.out.println("*** Fase 1 van het spel begint nu ***");
	}
	
	//Play the game!
	public void Play(Player firstPlayer, Player secondPlayer){
		
		//Check if username is test
		if(firstPlayer.getName().toUpperCase().equals("TEST")){
			placeTestPawns(firstPlayer);
			placeTestPawns(secondPlayer);
		}
		
		
		//Loop till condition has been met
		while(condition){
			showBoard(firstPlayer, secondPlayer);
			//Check conditions
			conditionCheck(firstPlayer, secondPlayer);
			//User input
			playerTurn(firstPlayer, secondPlayer);


			showBoard(firstPlayer, secondPlayer);
			//Check conditions
			conditionCheck(secondPlayer, firstPlayer);
			//User input
			playerTurn(secondPlayer, firstPlayer);
			
		}
		
		//Show board after game
		board.printBoard(LAYOUT);
	}
	
	//Here winning and phase condition checks will be done
	public void conditionCheck(Player player, Player opponent){
		//Check total pawns
		if(player.getPawns() == 0){
			player.setPhase(2);
		}

		//Check if a player has 3 or less pawns on the board
		if(getPlayerPawns(player) <= 3 && player.getPawns() == 0){
			player.setPhase(3);
		}
		
		//Check if player has lost
		if(getPlayerPawns(player) <= 2 && player.getPawns() == 0){
			//lost game
			System.out.println(player.getName()+ " heeft verloren!");
			condition = false;
		}
				
	}
	
	//Show board on console
	public void showBoard(Player player1, Player player2){
		//Check if user is computer
		if(
				player1.isComputer() &&
				player2.isComputer()
		) {
		
		} else {
			//show board
			board.printBoard(LAYOUT);
		}
	}
	
	//Execute player turn
	public void playerTurn(Player player, Player opponent){
		
		//if condition is true
		if(condition){
		
			//Check what phase the player is in
			switch(player.getPhase()){
				case 1:
					placePawn(player, opponent);
					break;
				case 2:
					movePawn(player, opponent);
					break;
				case 3:
					jumpPawn(player, opponent);
					break;
			}
		
		}
	}
	
	//Place the pawn because for the test
	public void placeTestPawns(Player player){
		
		//Check if player color is white or black
		if(getColor(player.getColor()) == Point.BLACK){
			//String with keys
			chars = "CDGIJNSVX";			
		} else {
			//String with keys
			chars = "ABFHKPQUW";
		}
		
		for(int i = 0; i < chars.length(); i++){
			//place pawn
			board.replacePawn(chars.charAt(i), getColor(player.getColor()));
			
			//Reduce pawn by one
			player.setPawns(player.getPawns() - 1);
		}
	}
	
	//Place the pawn on the board
	public void placePawn(Player user, Player opponent) {
		//Check if computer
		if(user.isComputer()){
			
			//Call computer function and get key
			input = ((Computer) user).getPoint();
			io.writeOutput("De computer heeft nog " + user.getPawns() + " pionnen over en de pion geplaatst op: " + input.toString());
			
		} else {
			
			//Ask player to place pawn
			io.writeOutput(user.getName() + ", je hebt " + user.getPawns() + " pionnen over.");
			io.writeOutput(user.getName() + ", geef het punt waar je een pion wilt zetten: ");
				
			//Read user input
			line = io.readInput().toUpperCase();
			
			//Try to get the character
			try {
				input = line.charAt(0);
			} catch(Exception e){
				input = '1';
			}
				
			//Check input
			if(!io.checkInput(input)){
				//call function again
				io.writeOutput("Je hebt een verkeerde letter of niets in gevuld. Probeer het nog eens.");
				placePawn(user, opponent);
				return;
			}
		}
		
		//check if the chosen point is free
		if(board.getBoard().get(input).getPoint() == Point.FREE){
			//place pawn
			board.replacePawn(input, getColor(user.getColor()));
			
			//Reduce pawn by one
			user.setPawns(user.getPawns() - 1);
			
			//Check for "molentje"
			checkIfMill(user, opponent, input);
		} else {
			//Let user know that it's already filled
			io.writeOutput("Deze plek is al bezet!");
			placePawn(user, opponent);
			return;
		}
	}
	
	//Move the pawn on the board
	public void movePawn(Player user, Player opponent){
		
		//Check if computer
		if(user.isComputer()){
			//Get the character from computer
			Character c = ( (Computer) user).getPoint();
			
			//Check if the point chosen is actually the computer's pawn
			if(board.comparePoints(c, getColor(user.getColor()))){
				pawnId = c;
			} else {
				movePawn(user, opponent);
				return;
			}
			
			//Call computer function
			newLocation = ((Computer) user).getPoint();
			
			//Check if valid new connection and if the new location is valid and not filed
			if(!Application.areConnected(pawnId, newLocation) || !board.comparePoints(newLocation, point.FREE)){
				movePawn(user, opponent);
				return;
			}
			
			io.writeOutput(user.getName() + " heeft pion op " + pawnId + " verplaatst naar " + newLocation);
			
		} else {
		
			//Ask player to move pawn
			io.writeOutput(user.getName() + ", het is jouw beurt. Verplaats je pionnen als volgt: `pl`. In dit voorbeelt beweegt de pion op P naar L");
			
			//Get the characters the user has given and check if its not empty
			line = io.readInput().toUpperCase();
			
			if(line.length() > 1 && line.length() < 3){
				pawnId = line.charAt(0);
				newLocation = line.charAt(1);
			} else {
				//Call function again
				io.writeOutput("Je hebt verkeerde waardes ingevuld, probeer het opnieuw.");
				movePawn(user, opponent);
				return;
			}
		}
		
		//Check if given locations are valid
		if(io.checkInput(pawnId) && io.checkInput(newLocation) && io.checkInput(pawnId) && io.checkInput(newLocation)){
			
			//Check if given spots are valid
			if(
				board.comparePoints(newLocation, point.FREE) && 
				board.comparePoints(pawnId, getColor(user.getColor())) && 
				Application.areConnected(pawnId, newLocation)
			){
				board.replacePawn(pawnId, point.FREE);
				board.replacePawn(newLocation, getColor(user.getColor()));
				
				//Check for "molentje"
				checkIfMill(user, opponent, newLocation);
			} else {
				//Inform the user and let them try a new move
				io.writeOutput("Geen geldige zet, probeer het opnieuw");
				movePawn(user, opponent);
				return;
			}
			
		} else {
			io.writeOutput("Geen geldige karakter gebruikt!");
			movePawn(user, opponent);
			return;
		}

	}
	
	//Jump the pawn on the born
	public void jumpPawn(Player user, Player opponent){
		
		//Check if computer
		if(user.isComputer()){
			
			//Get random point
			Character key = null;
			for(char c = 'A' ; c <= 'X' ; c++){
				//Check
				if(board.comparePoints(c, getColor(user.getColor()))){
					key = c;
					break;
				}
			}
			
			//Call computer function
			input = ((Computer) user).getPoint();
			
			//Check if point is free
			if(board.getBoard().get(input).getPoint() != Point.FREE){
				jumpPawn(user, opponent);
				return;
			}
			
			io.writeOutput(user.getName() + " heeft pion op " + board.getBoard().get(key) + " verplaatst naar " + input);
		} else {
			
			io.writeOutput(user.getName() + ", Je kunt nu met je pionnen springen! Bijvoorbeeld: `po` beweegt de pion op P naar O");
			
			//Get the characters the user has given and check if its not empty
			line = io.readInput().toUpperCase();
			//Check if valid character
			if(!line.contains("error")){
				//Save characters
				pawnId = line.charAt(0);
				newLocation = line.charAt(1);
			}
			
		}
		
		
		//Check if given locations are valid
		if(io.checkInput(pawnId) && io.checkInput(newLocation) && io.checkInput(pawnId) && io.checkInput(newLocation)){
			
			//Check if given spots are valid
			if(
				board.comparePoints(newLocation, point.FREE) && 
				board.comparePoints(pawnId, getColor(user.getColor()))
			){
				board.getBoard().replace(pawnId, point = point.FREE);
				board.getBoard().replace(newLocation, point = getColor(user.getColor()));
				
				checkIfMill(user, opponent, newLocation);
			} else {
				//Inform the user and let them try a new move
				io.writeOutput("Geen geldige zet, probeer het opnieuw");
				movePawn(user, opponent);
				return;
			}
			
		} else {
			io.writeOutput("Geen geldige karakter gebruikt!");
			movePawn(user, opponent);
			return;
		}
	}
	
	//Get all the pawns of the player
	public int getPlayerPawns(Player player){
		//Reset count
		count = 0;
		//Loop through all points
		for(char c = 'A' ; c <= 'X' ; c++){
			//Check
			if(board.comparePoints(c, getColor(player.getColor()))){
				count++;
			}
		}
		
		return count;
	}
	
	//Check if there are any mills completed
	public void checkIfMill(Player user, Player opponent, Character key){
		
		//Loop through all mills
		for(String line : Application.MILLS) {
			
			//Check if the new point is in the mill
			if(
					line.charAt(0) == key ||
					line.charAt(1) == key ||
					line.charAt(2) == key
			){
				
				//Check for each mill if they are owned by the same person
				if(
					board.comparePoints(line.charAt(0), getColor(user.getColor())) &&
					board.comparePoints(line.charAt(1), getColor(user.getColor())) &&
					board.comparePoints(line.charAt(2), getColor(user.getColor()))
				){
					//Let user remove pawn
					io.writeOutput("Je hebt een molentje! Je mag een pion van je tegenstander verwijderen.");
					removePawn(user, opponent);
				}
				
			}
			
		}
		
	}
	
	//Let the user remove a pawn from the opponent
	public void removePawn(Player user, Player opponent){
		
		//Check if computer
		if(user.isComputer()){
			
			//Call computer function
			input = ((Computer) user).getPoint();
			
			//Check if point is not free or the user's own pawn
			if(!board.comparePoints(input, getColor(user.getColor()))){
				removePawn(user, opponent);
				return;
			}
			
			io.writeOutput("De computer je pion op " + input.toString() + " gepakt");
			
		} else {
		
			//Ask user to remove pawn
			io.writeOutput("Geef de plek met de pion die je wil verwijderen");
			
			//read de gegeven info
			line = io.readInput().toUpperCase();
			
			//Try to get the first character
			try {
				input = line.charAt(0);
			} catch(Exception e){
				input = '1';
			}
		
			//Check input
			if(!io.checkInput(input)){
				//call function again
				io.writeOutput("Je hebt een verkeerde letter of niets in gevuld. Probeer het nog eens.");
				removePawn(user, opponent);
				return;
			}
		}
		
		
		
		if(board.comparePoints(input, getColor(user.getColor()))){
			board.getBoard().replace(input, point = point.FREE);
		} else {
			//call function again
			io.writeOutput("Dit is geen geldige pion om te verwijderen");
			removePawn(user, opponent);
			return;
		}
	}
	
	//Check what color the user is and return the value in enum
	public Point getColor(int userColor){
		
		switch(userColor){
			case 0:
				return Point.FREE;
			case 1:
				return Point.WHITE;
			case 2:
				return Point.BLACK;
		}
		
		return Point.FREE;
	}

}
