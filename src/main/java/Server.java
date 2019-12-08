import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;





public class Server{
	int count = 1;	// will count the number of games played
	ObjectOutputStream out;		//output stream of the server
	ObjectInputStream in;			//input of the stream
	int portNum;		//getting the port number
	
	ArrayList<ClientThread>players= new ArrayList<ClientThread>(); // players will be there
	ArrayList<Top3Player> AlltopScores= new ArrayList<Top3Player>();
	ArrayList<GameInfo> playerInfo= new ArrayList<GameInfo>();
	
	ServerThread server;
	private Consumer<GameInfo> callback;

	Server(String port,Consumer<GameInfo> call){
		try {
		   portNum = Integer.parseInt(port) ;		//error checking and converting the portnum
		}catch(Exception e){}
		
		server = new ServerThread(portNum);
		callback=call;
		server.start();
	}
	
	class ServerThread extends Thread{		//creating the server thread
		int port;
		
		ServerThread(int num)
		{
			this.port = num;
		}
		
		public void run() {		//the thread executes the following thread method
			
			try(ServerSocket mysocket = new ServerSocket(port);){		//creates new socket for the clients to connect to
		    System.out.println("Server is waiting for a client!");
			
		    while(true) {				//can only have 2 clients
		    	
		    	
		    	ClientThread client= new ClientThread(mysocket.accept(), count);
				players.add(client);
				System.out.println("Check 1");
				Top3Player temp= new Top3Player(0,"player"+(count-1));
				AlltopScores.add(temp);
				playerInfo.add(new GameInfo(count-1));
				System.out.println(AlltopScores.size());
				System.out.println("Check 2");
				//print for the debugging 
				System.out.println("client has connected to server: " + "client #" + count);
				
				//start the client 
				client.start();
				count++;
				
			    }
			}                                                      //end of try
				catch(Exception e) {
					
				}
			}//end of while
		
	}
	
	class ClientThread extends Thread{			//the thread on which each client will run
		Socket connection;
		int count;
		GameInfo init;
		
		AI_MinMax minmaxGame ;
		ObjectInputStream in;			//their input and output streams for comm.
		ObjectOutputStream out;
		ClientThread(Socket s, int count)		
		{
			
			this.connection=s;		
			this.count=count;
			this.init= new GameInfo(count-1);
			
			
		}

		void updateClient(GameInfo game)		//updating the clients accordingly
		 {
			try
			 {
				for(Top3Player p: AlltopScores)
					System.out.print(p.playerName+"; "+p.score+ "----  ");
				System.out.println();
				ClientThread temp;
				for(int i=0;i<players.size();i++)
				{
					temp= players.get(i);
					System.out.println(i+":::::: "+ game.playerNum);
					if(i==game.playerNum)
					{
						game.topScores.clear();
						game.topScores.addAll(AlltopScores);
						temp.out.writeObject(game);
					}
					else
					{
						System.out.println("ggggggggggggggggggggg");
						playerInfo.get(i).topScores.clear();
						playerInfo.get(i).topScores.addAll(AlltopScores);
						temp.out.writeObject(playerInfo.get(i));
					}
					
				}
				
					

			 }
			 catch(Exception e) {}
 
		 }
		
		
		public void run(){
			
			try {
						//clear the current client list
				out = new ObjectOutputStream(connection.getOutputStream());		//opens the streams
				in = new ObjectInputStream(connection.getInputStream());
				
				
				
				updateClient(init);
				
				
			}
			catch(Exception e) {
				System.out.println("Streams not open");
			}
			
		while(true)
		{
			try {
				  
				synchronized(this)
				{
					GameInfo clientInfo=(GameInfo)in.readObject();		//reading from the incoming data
					//temp= clientInfo.topScores.get(clientInfo.playerNum);
					Top3Player temp2= new Top3Player(clientInfo.playerScore,clientInfo.nickName);
					AlltopScores.set(clientInfo.playerNum, temp2);
					playerInfo.set(clientInfo.playerNum, clientInfo);
					for(GameInfo p: playerInfo)
						System.out.print(p.playerNum+"; "+p.playerScore+ "=======");
					System.out.println();
					for(Top3Player p: AlltopScores)
						System.out.print(p.playerName+"; "+p.score+ "----  ");
					System.out.println();
				    this.minmaxGame= new AI_MinMax(clientInfo);
				    System.out.println("checkpoint 2");
			    	callback.accept(clientInfo);		//displaying and using the info send for gui	
			    	updateClient(clientInfo);
				}
			    
				connection.setTcpNoDelay(true);	
			}
			
			catch(Exception e) {				//if the client closes the connection
				GameInfo error= new GameInfo(count-1);
		
				callback.accept(error);
		    	
		    	break;
			}
		}
			
	}	
}
}