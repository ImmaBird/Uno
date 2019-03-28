package uno;

public class Deck extends Pile {

	public Deck() {
		Card.Color[] colors = Card.Color.values();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j <= 12; j++) {
				if (j <= 9) {
					this.addTop(new Card(colors[i], j));
				} else {
					this.addTop(new Card(colors[i], j));
					this.addTop(new Card(colors[i], j));
				}
			}
			this.addTop(new Card(colors[4], 13));
			this.addTop(new Card(colors[4], 14));
		}
		this.shuffle();
	}

	public void deal(Hand hand) {
		for (int j = 0; j < 7; j++) {
			hand.addTop(this.removeCard());
		}
	}
}
