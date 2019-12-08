import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TicTacToe extends Application {

	Server svr;		//server to start
	Text play1,play2, play1choice,play2choice,winner;								    //texts
	Button rockBtn, spockBtn, paperBtn,scissorBtn, lizardBtn, playAgainBtn, quitBtn;	//buttons
	Button startBtn, backBtn, scloseBtn;
	TextField sPortNum;
	GameInfo game;						    //gameInfo reference
	HashMap<String, Scene> sceneMap;		//hashMap to keep the scenes
	ListView <String> gameSummary;
	ListView<String> connections;
	SplitPane oneThirdSplit;
	
	Server createServer(){												//function when called creates the server 
        return new Server(sPortNum.getText(),data->{
        	Platform.runLater(()->{
        		String delim = "[ ]+";
        		connections.getItems().clear();
        		for(int i=0;i<data.topScores.size();i++)
        		{
        			connections.getItems().add("player: "+i+" connected");
        		}
        		if(data.playAgain)
        		{
        			gameSummary.getItems().add("player: "+data.playerNum+" is playing again");
        			data.playAgain=false;
        		}
        			
        		if(!data.game.equals("b b b b b b b b b") )
        		{
        			String[] temp= data.game.split(delim);
            		gameSummary.getItems().add("player: "+data.playerNum+" vs Server       Mode: "+ data.Mode);

            		gameSummary.getItems().add(temp[0]+ " "+temp[1]+ " "+temp[2]);
            		gameSummary.getItems().add(temp[3]+ " "+temp[4]+ " "+temp[5]);
            		gameSummary.getItems().add(temp[6]+ " "+temp[7]+ " "+temp[8]);
            		if(!data.winner.equals("none"))
            		{
            			gameSummary.getItems().add(data.winner);
            		}
        		}
        		
        		//checking if both have answer
        		
        	});
        });
                
	}

	Scene sStartScene(Stage primaryStage)			//Scene where the game starts
	{
		GridPane gridPane= new GridPane();		//using gridPane
		DropShadow ds = new DropShadow();		//dropping shadow on the title
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		
		BackgroundFill background_fill = new BackgroundFill(Color.rgb(200, 200, 0), CornerRadii.EMPTY, Insets.EMPTY); // color setting
		// create Background 
		Background background = new Background(background_fill); 

		Text top = new Text ("TIC TAC TOE                                     ");		//title
		top.setFont(Font.font ("Verdana", 20));							//font
		top.setFill(Color.RED);											//color
		top.setEffect(ds);
		top.setStyle(
				"-fx-font-size: 20px;"
				+"-fx-font-weight: bold;");
		
		Label port= new Label("Enter Port: ");
		port.setStyle(
				"-fx-font-size: 20px;"
				+"-fx-font-weight: bold;");
		
		sPortNum = new TextField("5555");				//for the entry of desired port with default 5555
		sPortNum.setTranslateX(sPortNum.getLayoutX()-300);
		sPortNum.setStyle(
				"-fx-font-size: 15px;"
				+"-fx-font-weight: bold;");
		
		startBtn = new Button("Start Game");			//button which starts the server and the game
		startBtn.setStyle(
				"-fx-background-color: \n" + 
				"        rgba(0,0,0,0.08),\n" + 
				"        linear-gradient(#5a61af, #51536d),\n" + 
				"        linear-gradient(#e4fbff 0%,#cee6fb 10%, #a5d3fb 50%, #88c6fb 51%, #d5faff 100%);\n" + 
				"    -fx-background-insets: 0 0 -1 0,0,1;\n" + 
				"    -fx-background-radius: 5,5,4;\n" + 
				"    -fx-padding: 10 30 10 30;\n" + 
				"    -fx-text-fill: #242d35;\n" + 
				"    -fx-font-size: 14px;"
				);
		startBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(sSecondScene());		//moves to the second scene
				svr= createServer();						//creates the server
			}
		});
			gridPane.setMinSize(400, 200);
			gridPane.setPadding(new Insets(10, 10, 10, 10));
		 	gridPane.setVgap(5); 
		 	gridPane.setHgap(5);       
	      
	        //Setting the Grid alignment 
		 	gridPane.setAlignment(Pos.CENTER); 
		 	gridPane.add(top, 0, 0); 
		 	gridPane.add(port, 0, 1); 
		 	gridPane.add(sPortNum, 2,1 );
		 	gridPane.add(startBtn, 0, 2);  
		 	gridPane.setStyle("-fx-background-image: url(\"image1.jpeg\");"
					  +  "-fx-background-size: 700.0 800.0;"
				      + "-fx-background-position: center top; "
				);
		
		 	
		 	
		return new Scene(gridPane,600,600);
		
	}
	
	Scene sSecondScene()								//creating the gui for the  server to keep track of whats happening
	{
		//BackgroundFill background_fill = new BackgroundFill(Color.rgb(100, 200, 10), CornerRadii.EMPTY, Insets.EMPTY);
		oneThirdSplit= new SplitPane();
		gameSummary= new ListView<String>();
		connections= new ListView<String>();
		BorderPane ConnectionPane= new BorderPane();
		ConnectionPane.setTop(new Text("Players Connected"));
		ConnectionPane.setCenter(connections);
		gameSummary.setBackground(new Background(new BackgroundFill(Color.rgb(100, 255, 200), CornerRadii.EMPTY, Insets.EMPTY)));
	
		//GamePane: BorderPane for SetTop, Set Center 
		BorderPane gamePane =new BorderPane();
		gamePane.setTop(new Text("Summary of Games"));
		gamePane.setCenter(gameSummary);
		
		
		//Split the list for the Players Status and what they have played
		oneThirdSplit.getItems().add(ConnectionPane);
		oneThirdSplit.getItems().add(gamePane);
		oneThirdSplit.setDividerPositions(0.3);
		
		return new Scene(oneThirdSplit,600,600);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	   launch(args);
	}                        
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("TIC TAC TOE!!!");
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("start", sStartScene(primaryStage));
		sceneMap.put("second", sSecondScene());
		
		
		primaryStage.setScene(sceneMap.get("start"));
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.out.print("Server is closed");
                System.exit(0);
                
            }
        });
	}
	
	
	
	

}
