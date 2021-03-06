import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to read in a state of a tic tac toe board. It creates a MinMax object and passes the state to it. What returns is a list 
 * of possible moves for the player X that have been given min/max values by the method findMoves. The moves that can result in a win or a 
 * tie for X are printed out with the method printBestMoves()
 * 
 * @author Mark Hallenbeck
 *
 * Copyright© 2014, Mark Hallenbeck, All Rights Reservered.
 *
 */
public class AI_MinMax {
	
	private String[] init_board;
	
	private ArrayList<Node> movesList;
	
	AI_MinMax(GameInfo gameinfo)
	{
		String previous= gameinfo.game;
		init_board = getBoard(gameinfo.game);
		System.out.print("checkpoint 3");
		
		if(init_board.length != 9)
		{
			System.out.println("You have entered an invalid state for tic tac toe, exiting......");
			System.exit(-1);
		}
		
		
			MinMax sendIn_InitState = new MinMax(init_board);
			movesList = sendIn_InitState.findMoves();
		
		
			 
			
		
		if(gameinfo.playAgain )
		{
			gameinfo.playAgain=false;	
			gameinfo.game="b b b b b b b b b";
		}
		else if(movesList.size()==0)
		{
			
			
			if(gameinfo.gameOver)
			{
				
				gameinfo.gameOver=false;
				gameinfo.game=previous;
				//gameinfo.game="b b b b b b b b b";
			}
			else
				gameinfo.game=previous;
			System.out.print("check4");
			
		}
		else
		{
			if(gameinfo.Mode.equals("Easy"))
			{
				gameinfo.game=sendIn_InitState.easyMode(movesList);
			}
			else if(gameinfo.Mode.equals("Medium"))
			{
				gameinfo.game=sendIn_InitState.mediumMode(movesList);
			}
			else if(gameinfo.Mode.equals("Expert"))
			{
				gameinfo.game=sendIn_InitState.expertMode(movesList);
			}
		}
		
		
		
	 //printBestMoves();
	}
	
	/**
	 * reads in a string from user and parses the individual letters into a string array
	 * @return String[]
	 */
	private String[] getBoard(String info)
	{
			String puzzle;
			String[] puzzleParsed;
			String delim = "[ ]+";
			
			//give input message
			System.out.println("Enter a string to represent the board state:");

			puzzle = info;					//scan in string
			
			puzzleParsed = puzzle.split(delim);
			  	  						//close scanner
			
			return puzzleParsed;
			
	}
	
	/**
	 * goes through a node list and prints out the moves with the best result for player X
	 * checks the min/max function of each state and only recomends a path that leads to a win or tie
	 */
	private void printBestMoves()
	{
		System.out.print("\n\nThe moves list is: < ");
		
		for(int x = 0; x < movesList.size(); x++)
		{
			Node temp = movesList.get(x);
			
			if(temp.getMinMax() == 10 || temp.getMinMax() == 0)
			{
				System.out.print(temp.getMovedTo() + " ");
			}
		}
		
		System.out.print(">");
	}
}
