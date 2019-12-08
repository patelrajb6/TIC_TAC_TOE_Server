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

        		connections.getItems().clear();
        		
        		
        		
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
		
		Label port= new Label("Enter Port: ");
		sPortNum = new TextField("5555");				//for the entry of desired port with default 5555
		sPortNum.setTranslateX(sPortNum.getLayoutX()-300);
		
		startBtn = new Button("Start Game");			//button which starts the server and the game
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
		 	gridPane.add(sPortNum, 1,1 );
		 	gridPane.add(startBtn, 0, 2);  
		 	gridPane.setBackground(background);

		
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
