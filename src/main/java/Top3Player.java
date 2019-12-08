import java.io.Serializable;

public class Top3Player  implements Serializable {

	int score;
	String playerName;
	
	Top3Player(int scr, String nam)
	{
		this.score=scr;
		this.playerName=nam;
	}
	
}
