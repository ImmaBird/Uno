package uno;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.HBox;

public class Pile extends HBox {
	private ArrayList<Card> cards = new ArrayList<>();

	public Pile() {
	}

	public void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < cards.size(); i++) {
			cards.add(cards.remove(rand.nextInt(cards.size() - 1)));
		}
	}

	public void sort() {
		cards.sort(null);
	}

	public Card removeCard(int position) {
		return cards.remove(position);
	}

	public Card removeCard() {
		return cards.remove(cards.size() - 1);
	}

	public boolean removeCard(Card card) {
		return cards.remove(card);
	}

	public void addTop(Card card) {
		cards.add(card);
	}

	public void addBottom(Card card) {
		cards.add(0, card);
	}

	public int getSize() {
		return cards.size();
	}

	public String toString() {
		String output = "Cards:\n";
		for (int i = 0; i < cards.size(); i++) {
			if (i % 5 == 0) {
				output += cards.get(i) + "\n";
			} else {
				output += cards.get(i) + ", ";
			}
		}
		return output;
	}
}
