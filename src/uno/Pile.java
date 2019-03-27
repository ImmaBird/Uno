package uno;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.HBox;

/**
 * Pile class represents a pile of cards in the UNO game. This class
 * can be used to represent any pile of cards in any card game.
 * 
 * Prof. Rosenberg
 * Application 4 Group 14
 * @author Jacob Marra
 */
public class Pile extends HBox{
	/**
	 * ArrayList cards to hold cards
	 */
	private ArrayList<Card> cards = new ArrayList<>();
	
	/**
	 * int numberOfCards to keep track of how many cards are in the pile
	 */
	private int numberOfCards;
	
	/**
	 * Default constructor initializes numberOfCards to 0
	 */
	public Pile(){
		numberOfCards = 0;
	}
	
	public int length() {
		return numberOfCards;
	}
	
	/**
	 * Shuffles order of cards in the pile
	 */
	public void shuffle(){
		Random rand = new Random();
		for (int i=0; i<cards.size(); i++){
			cards.add(cards.remove(rand.nextInt(cards.size()-1)));
		}
	}
	
	/**
	 * Removes card at specified position from the pile
	 * @param int - position of card to be removed
	 */
	public Card removeCard(int position){
		Card card = cards.remove(position);
		numberOfCards--;
		return card;
	}
	
	/**
	 * Removes specified card from the pile
	 * @param Card - card to be removed
	 */
	public void removeCard(Card card){
		int pos = findPosition(card);
		cards.remove(pos);
		numberOfCards--;
	}
	
	/**
	 * Removes card at the last position in the pile
	 * @return 
	 */
	public Card removeCard(){
		numberOfCards--;
		return cards.remove(cards.size()-1);
	}
	
	/**
	 * Adds card to the end (top) of the pile
	 * @param Card - card to be added
	 */
	public void addCard(Card card){
		cards.add(card);
		numberOfCards++;
	}
	
	/**
	 * Adds card at specified position in the pile
	 * @param int - desired position for new card
	 * @param Card - card to be added to the pile
	 */
	public void addCard(int pos, Card card){
		cards.add(pos, card);;
		numberOfCards++;
	}
	
	/**
	 * Determines whether a card exists in the pile
	 * @param Card - card to check for
	 * @return true if card exists in pile, false if it does not
	 */
	public boolean contains(Card card){
		return cards.contains(card);
	}
	
	/**
	 * Finds the position of a card in the pile
	 * @param Card - card to look for
	 * @return int - position of the card in the pile or -1 if it is not in the pile
	 */
	public int findPosition(Card card){
		if (cards.contains(card)){
			return cards.indexOf(card);
		} else{
			return -1;
		}
	}
	
	/**
	 * Sorts the pile using the Card class compareTo method
	 */
	public void sort(){
		cards.sort(null);
	}
	
	/**
	 * Returns a string displaying the number of cards in the deck and calls the toString of each card
	 */
	public String toString(){
		String output = "Pile contains " + numberOfCards + "cards.\n";
		for (int i = 0; i < cards.size(); i++){
			output += cards.get(i) + "\n";
		}
		return output;
	}
	
	/**
	 * Getter method for numberOfCards
	 * @return int - number of cards in the pile
	 */
	public int getNumberOfCards(){
		return numberOfCards;
	}
	
}


