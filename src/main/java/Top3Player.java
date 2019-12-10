import java.io.Serializable;

//This class implements the Serializable and set the Score and playerName
public class Top3Player implements Serializable {
	int score;
	String playerName;
	
	//Constructor: set the score and name of the player
	Top3Player(int scr, String nam)
	{
		this.score=scr;
		this.playerName=nam;
	}
}