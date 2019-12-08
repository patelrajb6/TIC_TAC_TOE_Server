import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class GameInfo implements Serializable {
	/**
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
		
	}
	ArrayList<Top3Player> topScores= new ArrayList<Top3Player>();
	
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
		return OnlyThree;
		
	}
	

}