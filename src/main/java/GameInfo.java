import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

//This class implements the Serializable
public class GameInfo implements Serializable {
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int playerNum;
	int playerScore;
	String Mode;
	String nickName;
	String game;
	String error;
	String reset;
	boolean playAgain,gameOver; 
	String winner;
	
	//Initialize the all fields 
	GameInfo(int count)
	{
		this.playerNum=count;
		this.playerScore=0;
		this.Mode="";
		this.nickName="";
		this.game="b b b b b b b b b";
		this.error="";
		this.playAgain= false;
		this.gameOver=false;
		reset = " ";
		this.winner="none";
		
	}
	
	//TopScores: ArrayList contains the score of the player name with supporting class: Top3Player
	ArrayList<Top3Player> topScores= new ArrayList<Top3Player>();
	
	//sorting function for ArrayList: sort the ArrayList based on the player score.
	ArrayList<Top3Player> topThree( ArrayList<Top3Player> topScores)
	{
		ArrayList<Top3Player>OnlyThree= new ArrayList<Top3Player>();
		Collections.sort(topScores, (e,f)->{
			return f.score-e.score;
		});
		for (int i=0;i<3;i++)
		{
			OnlyThree.add(topScores.get(i));
		}
		return OnlyThree;  //return the arrayList of top three player
	}
	

}