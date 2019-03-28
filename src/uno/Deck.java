package uno;

import java.util.ArrayList;

/**
 * 
 * @author borgonovog deck is just a pile, but with an order
 */
public class Deck extends Pile {

	/**
	 * default constructor
	 */
	public Deck() {
		String[] color = { "red", "blue", "green", "yellow", "none"};
		for (int i = 0; i < color.length; i++) {
			this.addCard(new Card(color[i], 0));
			this.addCard(new Card("wild", 13));
			this.addCard(new Card("wild", 14));
			for (int j = 1; j < 13; j++) {
				this.addCard(new Card(color[i], j));
				this.addCard(new Card(color[i], j));
			}
		}
		this.shuffle();
	}
	
	public Deck(ArrayList<Card> deck) {
		for(Card aCard : deck) {
			this.addCard(aCard);
		}
	}

	public void deal(Hand hand) {
		for (int j = 0; j <= 7; j++) {
			hand.addCard(this.removeCard());
		}
	}
}
