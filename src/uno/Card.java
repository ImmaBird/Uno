package uno;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {
	public enum Color {
		red, green, blue, yellow, none
	}

	private Color color = Color.red;
	private int value = 0;

	public Card(Color color, int value) {
		this.color = color;
		this.value = value;

		Image cardImage = new Image(getClass().getResource(this.getImagePath()).toExternalForm());
		setImage(cardImage);
		setFitWidth(120);
		setFitHeight(180);
	}

	private String getImagePath() {
		return "imgs/" + this + ".png";
	}

	public Color getColor() {
		return this.color;
	}

	public int getValue() {
		return this.value;
	}

	public boolean isValidMove(Card card) {
		return this.color.equals(card.getColor()) || this.value == card.getValue();
	}

	public String toString() {
		return this.color.name() + this.value;
	}
}
