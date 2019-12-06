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
	boolean playAgain;
	class top3Player
	{
		int score;
		String playerName;
		
		top3Player(int scr, String nam)
		{
			this.score=scr;
			this.playerName=nam;
		}
		
	}
	GameInfo(int count)
	{
		this.playerNum=count;
		this.playerScore=0;
		this.Mode="";
		this.nickName="";
		this.game="b b b b b b b b b";
		this.error="";
		this.playAgain=false;
	}
	ArrayList<top3Player> topScores= new ArrayList<top3Player>();
	
	ArrayList<top3Player> topThree( ArrayList<top3Player> topScores)
	{
		ArrayList<top3Player>OnlyThree= new ArrayList<top3Player>();
		Collections.sort(topScores, (e,f)->{
			return e.score-f.score;
		});
		for (int i=0;i<3;i++)
		{
			OnlyThree.add(topScores.get(i));
		}
		return OnlyThree;
		
	}
	

}
