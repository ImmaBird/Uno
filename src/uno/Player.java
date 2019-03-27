package uno;

import java.io.PrintWriter;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/* James Coleman
 * A player in the Uno Game
 * 
 * 
 * 
 */
public class Player extends VBox{
	private String myName;
	private Hand myCards;
	private int score;
	private Card topCard;
	private boolean myTurn;
	private Deck deck;
	private PrintWriter write;
	
	//if card is wild, color is entered as "wild"
	//cards have values 0-9, draw two=10, reverse=11,
	//skip=12, wild=13, wild draw four=14
	
	public Player(String name) {
		myTurn = false;
		myName = name;
		myCards = new Hand();
		this.getChildren().addAll(new Button(),myCards);
	}
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	public Deck getDeck() {
		return this.deck;
	}
	public void setMyTurn() {
		myTurn = true;
		
		int value = topCard.getCardValue();
		Platform.runLater(new Runnable() {
			public void run() {
				if(value == 10) {
					// Draw 2
					addCard(deck);
					addCard(deck);
				} else if(value == 14) {
					// Wild Draw 4
					addCard(deck);
					addCard(deck);
					addCard(deck);
					addCard(deck);
				}
			}
		});
	}
	public void setTopCard(Card topCard) {
		if(topCard != null && myTurn) {
			deck.addCard(0, topCard);
		}
		this.getChildren().remove(0);
		this.topCard = topCard;
		this.getChildren().add(0, topCard);
	}
	public Card getTopCard() {
		return topCard;
	}
	//Gets player's name
	public String getName() {
		return myName;
	}
	//Returns the player's cards
	public Hand getCards() {
		return myCards;
	}
	public void setCardHandler(Card card) {
		card.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Card card = (Card) event.getSource();
				if(isLegal(card)) {
					myTurn = false;
					myCards.playCard(card);
					card.setOnMouseClicked(null);
					setTopCard(card);
					
					if(card.isWild()) {
						int x = (int) event.getX();
						int y = (int) event.getY();
						System.out.println("X: " + x + " Y: " + y);
						boolean top = false;
						boolean right = false;
						if(x > card.getFitWidth()/2) {
							right = true;
						}
						if(y < card.getFitHeight()/2) {
							top = true;
						}
						if(top && right) {
							card.SelectColor("Blue");
						} else if(top && !right) {
							card.SelectColor("Red");
						} else if(!top && !right) {
							card.SelectColor("Yellow");
						} else if(!top && right) {
							card.SelectColor("Green");
						}
						System.out.println(card.getSelectedColor() + "Setting");
					}
					
					// Send deck
					String sendDeck = "";
					while(deck.length() > 0) {
						Card tempCard = deck.removeCard(0);
						sendDeck = sendDeck + tempCard.getColor() + "," + tempCard.getCardValue() + ":";
					}
					write.println(sendDeck);
					write.println(topCard.getColor() + "," + topCard.getCardValue());
					write.println(myCards.length());
				}
			}
		});
	}
	//Adds a card to the current player's hand.
	public void addCard(Card card) {
		setCardHandler(card);
		myCards.drawCard(card);
	}
	private boolean isLegal(Card card) {
		System.out.println(topCard.getSelectedColor() + "Checking Card:" + card.getColor());
		return myTurn && (
				card.isWild() ||
				topCard.getColor().equals(card.getColor()) ||
				topCard.getCardValue() == card.getCardValue() ||
				topCard.isWild() && topCard.getSelectedColor().equals("Wild") ||
				topCard.isWild() && topCard.getSelectedColor().equals(card.getColor())
				);
	}
	public void setWriter(PrintWriter write) {
		this.write = write;
	}
	//Adds a card to the current player's hand.
	public void addCard(Deck deck) {
		Card card = deck.removeCard();
		setCardHandler(card);
		myCards.drawCard(card);
	}
	//Get player's score
	public int getScore() {
		return score;
	}
	//String representation of the player
	public String toString() {
		return myName;
	}
	//Clear the player's hand
	public void clearCards() {
		myCards = null;
		myCards = new Hand();
	}
	/*checks if the player has UNO
	public boolean hasUno(){
		if(myCards.size() == 1){
			return true;
		} else 
			return false;
	}
	 */
}

