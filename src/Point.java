public enum Point {
	FREE, BLACK, WHITE;
	
	//Variables
	private Point p = this;
	
	//Get the enum value
	Point getPoint(){
		return p;
	}
	
	//Set the enum value
	void setPoint(Point point){
		this.p = point;
	}
}
