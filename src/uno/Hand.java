package uno;

/**
 * 
 * @author borgonovog
 */
public class Hand extends Pile {
	/**
	 * default constructor
	 */
	public Hand() {
		super();
	}

	/**
	 * gets top card of deck add to hand remove it from deck
	 */
	public void drawCard(Deck deck) {
		Card drawnCard = deck.removeCard();
		this.getChildren().add(drawnCard);
		this.addCard(drawnCard);
	}
	
	public void drawCard(Card drawnCard) {
		this.getChildren().add(drawnCard);
		this.addCard(drawnCard);
	}

	public void playCard(Card playedCard) {
		this.removeCard(playedCard);
		this.getChildren().remove(playedCard);
	}
}
