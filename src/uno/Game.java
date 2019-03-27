package uno;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Starts an online version of the card game UNO.
 * @author Bradley Rawson Jr.
 *
 */
public class Game extends Application {
	
	private Scene mainMenuScene;
	private Scene cardTableScene;
	private Scene optionsScene;
	private Scene rulesScene;
	private Font TITLE_FONT = new Font(32);
	private Font STANDARD_FONT = new Font(16);
	private int WINDOW_WIDTH = 800;
	private int WINDOW_HEIGHT = 600;
	private Player me = new Player(name);
	private static String name;
	private static String ip;
	private static int port;
	private Server server;
	private Label whosTurn = new Label();
	private static int numberOfPlayers;
	private VBox listOfPeople;

	/**
	 * Launches the GUI.
	 * @param args
	 */
	public static void main(String[] args) {
		File options = new File("Options.txt");
		if(options.exists()) {	// Read options file
			try(Scanner sc = new Scanner(options)) {
				sc.next();
				name = sc.next();
				sc.next();
				ip = sc.next();
				sc.next();
				port = sc.nextInt();
				sc.next();
				numberOfPlayers = sc.nextInt();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} else {	// Generate one if it doensn't exist
			try {
				options.createNewFile();
				PrintWriter pr = new PrintWriter(options);
				
				name = "User";
				ip = "localhost";
				port = 25565;
				numberOfPlayers = 4;
				
				pr.println("Name: " + name);
				pr.println("IP: " + ip);
				pr.println("Port: " + port);
				pr.println("Players: " + numberOfPlayers);
				
				
				pr.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		launch(); // Starts the GUI
	}
	
	/**
	 * Creates a button with the given name and handles their action Events.
	 * @return Button
	 */
	private Button createButton(String text) {
		Button button = new Button(text);
		button.setFont(STANDARD_FONT);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				Button button = (Button) node;
				
				switch(button.getText()) {
				case"Join Session":
					stage.setScene(cardTableScene);
					startGame();
					break;
				case"Rules":
					stage.setScene(rulesScene);
					break;
				case"Options":
					stage.setScene(optionsScene);
					break;
				case"Start Session":
					if(server != null) {
						server = null;
					}
					server = new Server(numberOfPlayers);
					break;
				case"Back to Menu":
					if(stage.getScene().equals(optionsScene)) {
						VBox bv1 = (VBox) button.getParent();
						HBox bh = (HBox) bv1.getChildren().get(1);
						VBox bv2 = (VBox) bh.getChildren().get(1);
						name = ((TextField) bv2.getChildren().get(0)).getText();
						ip = ((TextField) bv2.getChildren().get(1)).getText();
						port = Integer.parseInt(((TextField) bv2.getChildren().get(2)).getText());
						numberOfPlayers = Integer.parseInt(((TextField) bv2.getChildren().get(3)).getText());
					}
					stage.setScene(mainMenuScene);
					break;
				case"Draw Card":
					me.addCard(me.getDeck());
					break;
				}
			}
		});
		return button;
	}
	
	/**
	 * Creates the mainMenuScene.
	 */
	private void createMainMenu() {
		Label title = new Label("Uno!");
		title.setFont(TITLE_FONT);
		
		VBox v = new VBox();
		v.getChildren().addAll(title, createButton("Join Session"), createButton("Start Session"), createButton("Options"), createButton("Rules"));
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(10,70,20,70));
		
		Pane pane = new Pane();
		pane.autosize();
		pane.getChildren().add(v);
		
		ScrollPane root = new ScrollPane(pane);
		root.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		mainMenuScene = new Scene(root);
	}
	
	private void createRules() {
		Label title = new Label("Rules");
		title.setFont(TITLE_FONT);
		
		TextArea text = new TextArea();
		try(Scanner sc = new Scanner(new File("Rules.txt"))) {
			String theText = "";
			while(sc.hasNextLine()) {
				theText = theText + "\n" + sc.nextLine();
			}
			text.setText(theText);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		text.setWrapText(true);
		text.setEditable(false);
		
		VBox v = new VBox();
		v.getChildren().addAll(title, text, createButton("Back to Menu"));
		
		Pane pane = new Pane();
		pane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		pane.getChildren().add(v);
		
		ScrollPane root = new ScrollPane(pane);
		root.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		rulesScene = new Scene(root);
	}
	
	/**
	 * 
	 */
	private void createOptions() {
		Label title = new Label("Options");
		title.setFont(TITLE_FONT);
		
		VBox v1 = new VBox();
		VBox v2 = new VBox();
		
		Label l1 = new Label("Name: ");
		l1.setFont(STANDARD_FONT);
		Label l2 = new Label("IP: ");
		l2.setFont(STANDARD_FONT);
		Label l3 = new Label("Port: ");
		l3.setFont(STANDARD_FONT);
		Label l4 = new Label("Players: ");
		l4.setFont(STANDARD_FONT);
		
		v1.getChildren().addAll(l1,l2,l3,l4);
		
		TextField t1 = new TextField();
		TextField t2 = new TextField();
		TextField t3 = new TextField();
		TextField t4 = new TextField();
		
		t1.setText(name);
		t2.setText(ip);
		t3.setText(""+port);
		t4.setText(""+numberOfPlayers);
		
		v2.getChildren().addAll(t1,t2,t3,t4);
		
		HBox h1 = new HBox();
		h1.getChildren().addAll(v1, v2);
		
		VBox v3 = new VBox();
		v3.getChildren().addAll(title, h1, createButton("Back to Menu"));
		
		Pane pane = new Pane();
		pane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		pane.getChildren().add(v3);
		
		ScrollPane root = new ScrollPane(pane);
		root.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		optionsScene = new Scene(root);
	}
	
	/**
	 * Creates the cardTableScene.
	 */
	private void createCardTable() {
		listOfPeople = new VBox();
		listOfPeople.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		whosTurn.setFont(STANDARD_FONT);
		VBox v = new VBox();
		v.getChildren().addAll(whosTurn,me,createButton("Draw Card"));
		
		HBox h = new HBox();
		h.getChildren().addAll(listOfPeople,v);
		
		Pane pane = new Pane();
		pane.getChildren().addAll(h);
		
		ScrollPane scrollPane = new ScrollPane(pane);
		scrollPane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		cardTableScene = new Scene(scrollPane);
	}
	
	private void startGame() {
		new Thread(new Runnable() {
			public void run() {
				try(Socket s = new Socket(ip,port)
				;BufferedReader read = new BufferedReader(new InputStreamReader(s.getInputStream()))
				;PrintWriter write = new PrintWriter(s.getOutputStream(),true)) {
					write.println(name);
					String firstHand = read.readLine();
					
					if(firstHand.equals("Name Taken")) {
						name = name + "(d)"; // d for duplicate
						startGame();
						
					} else {
						String topCard = read.readLine();
						String[] cards = firstHand.split(" ");
						
						String[] tempCard;
						for(int i = 0; i < cards.length; i++) {
							tempCard = cards[i].split(",");
							String color = tempCard[0];
							int value = Integer.parseInt(tempCard[1]);
							try {
								tempCard = topCard.split(",");
							} catch(Exception ex) {
								ex.printStackTrace();
							}
							Card card = new Card(tempCard[0],Integer.parseInt(tempCard[1]));
							
							Platform.runLater(new Runnable() {
								public void run() {							
									me.addCard(new Card(color,value));
									me.setTopCard(card);
								}
							});
						}
						
						try {
							me.setWriter(write);
							while(true) {
								String dataLine = read.readLine();
								String[] data = dataLine.split(" ");
								switch(data[0]) {
								case"YourTurn":
									ArrayList<Card> deckArrayList = new ArrayList<Card>();
									String[] theDeck = read.readLine().split(":");
									for(int i = 0 ; i < theDeck.length-1 ; i++) {
										String[] aCard = theDeck[i].split(",");
										// Array index error
										deckArrayList.add(new Card(aCard[0],Integer.parseInt(aCard[1])));
									}
									me.setDeck(new Deck(deckArrayList));
									me.setMyTurn();
									
									
									Platform.runLater(new Runnable() {
										public void run() {
											whosTurn.setText("Your Turn");
										}
									});
									break;
								case"TopCard":
									tempCard = data[1].split(",");
									Card card = new Card(tempCard[0],Integer.parseInt(tempCard[1]));
									Platform.runLater(new Runnable () {
										public void run() {
											me.setTopCard(card);
										}
									});
									break;
								case"Turn":
									Platform.runLater(new Runnable() {
										public void run() {
											whosTurn.setText(data[1] + "'s Turn");
										}
									});
									break;
								case"People":
									String[] people = data[1].split(",");
									Platform.runLater(new Runnable() {
										public void run() {
											listOfPeople.getChildren().clear();
											for(int i = 0; i < people.length ; i++) {
												Label aLabel = new Label(people[i]);
												aLabel.setFont(TITLE_FONT);
												listOfPeople.getChildren().add(aLabel);
											}
										}
									});
									break;
								}
							}
						} catch(Exception ex) {
							ex.printStackTrace();
						}	
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * Creates the scenes and displays the main menu.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		createMainMenu();
		createCardTable();
		createOptions();
		createRules();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try(PrintWriter pr = new PrintWriter(new File("Options.txt"))) {
					pr.println("Name: " + name);
					pr.println("IP: " + ip);
					pr.println("Port: " + port);
					pr.println("Players: " + numberOfPlayers);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		stage.setTitle("Uno Game");
		stage.setScene(mainMenuScene);
		stage.show();
	}
	
	/**
	 * The server class to be used for the game.
	 */
	private class Server {
		
		private HashMap<String,Client> clients = new HashMap<String,Client>();
		private ArrayList<String> index = new ArrayList<String>();
		private boolean online = true;
		private int whosTurn = 0;
		private boolean direction = true;
		private Deck deck = new Deck();
		private Card topCard = deck.removeCard();
		
		public Server(int numberOfPlayers) {
			new Thread(new Runnable() {
				public void run() {
					try(ServerSocket welcomeSocket = new ServerSocket(port)) {
						while(online) {
							Socket client = welcomeSocket.accept();
							String name;
							
							BufferedReader read = new BufferedReader(new InputStreamReader(client.getInputStream()));
							PrintWriter write = new PrintWriter(client.getOutputStream(),true);
							name = read.readLine();
							if(clients.containsKey(name)) {
								write.println("Name Taken");
							} else {
								clients.put(name, new Client(name,read,write));
								index.add(name);
								Hand hand = new Hand();
								deck.deal(hand);
								String firstHand = "";
								Card aCard;
								while(hand.length() != 0) {
									aCard = hand.removeCard();
									firstHand = firstHand + aCard.getColor() + "," + aCard.getCardValue() + " ";
								}
								write.println(firstHand);
								write.println(topCard.getColor() + "," + topCard.getCardValue());
							}
						}
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					try {
						while(index.size() != numberOfPlayers) {
							Thread.sleep(100);
						}
						
						while(online) {
							Client player = clients.get(index.get(whosTurn));
							player.write.println("YourTurn");
							messageAll("Turn " + player.name);
							
							// Send deck
							String sendDeck = "";
							while(deck.length() > 0) {
								Card tempCard = deck.removeCard(0);
								sendDeck = sendDeck + tempCard.getColor() + "," + tempCard.getCardValue() + ":";
							}
							player.write.println(sendDeck);
							System.out.println("sent deck");
							
							String data = player.read.readLine();
							if(data == null) {
								System.out.println("got a null ---------------------------------------- " + index.get(whosTurn));
								index.remove(whosTurn);
								clients.remove(player.name);
							} else {
								// Get deck
								ArrayList<Card> deckArrayList = new ArrayList<Card>();
								String[] theDeck = data.split(":");
								for(int i = 0 ; i < theDeck.length -1 ; i++) {
									String[] aCard = theDeck[i].split(",");
									deckArrayList.add(new Card(aCard[0],Integer.parseInt(aCard[1])));
								}
								deck = new Deck(deckArrayList);
								String aNewTopCard = player.read.readLine();
								String[] getTopCard = aNewTopCard.split(",");
								topCard = new Card(getTopCard[0],Integer.parseInt(getTopCard[1]));
								messageAll("TopCard " + aNewTopCard);
								
								//if card is wild, color is entered as "wild"
								//cards have values 0-9, draw two=10, reverse=11,
								//skip=12, wild=13, wild draw four=14
								
								if(topCard.getCardValue() == 11) {
									direction = !direction;
								} else if(topCard.getCardValue() == 12) {
									nextTurn();
								}
								player.cards = Integer.parseInt(player.read.readLine());
								String playerList = "People ";
								for(int i = 0 ; i < index.size() ; i++) {
									System.out.println("it ran");
									playerList = playerList + index.get(i) + ":" + clients.get(index.get(i)).cards + ",";
								}
								player = null;
								if(topCard.isWild()) {
									playerList = playerList +"Chosen-Color:" + topCard.getColor() + ",";
								}
								System.out.println(playerList);
								messageAll(playerList);
							}
							nextTurn();
						}
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();
		}
		
		private void nextTurn() {
			if(direction) {
				whosTurn++;
			} else {
				whosTurn--;
			}
			whosTurn = Math.floorMod(whosTurn,index.size());
		}
		
		private void messageAll(String message) {
			Client c;
			try {
				for(int i = 0; i < index.size(); i++) {
					if(i != whosTurn) {
						c = clients.get(index.get(i));
						c.write.println(message);
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		private class Client {
			public String name;
			public BufferedReader read;
			public PrintWriter write;
			public int cards = 7;
			
			public Client(String name,BufferedReader read,PrintWriter write) {
				this.name = name;
				this.read = read;
				this.write = write;
			}
			
		}
	}
	
}
