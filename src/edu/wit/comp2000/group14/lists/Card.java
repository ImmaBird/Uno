package edu.wit.comp2000.group14.lists;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Gina
 */
public class Card extends ImageView{
	private String color = "";
	private boolean isBlue=false;
	private boolean isGreen=false;
	private boolean isRed=false;
	private boolean isYellow=false;
	private boolean isWild=false;
	private int cardValue;
	
	public static void main(String[] args) {
		Card card = new Card("Wild",14);
		System.out.println(card.getColor());
		System.out.println(card.getCardValue());
		/*
		Card green1 = new Card("green", 1);
		System.out.println(green1.toString());
		Card red09 = new Card ("red", 9);
		System.out.println(red09.toString());
		System.out.println(green1.compareTo(red09));
		*/
	}
	
	/**
	 * Second Constructor for the Card that we used the most, take in a String for the 
	 * color and an integer for the cards value (with a note to the side about how we used
	 * the values) and uses the classes setter methods to set the values for the object
	 */
	public Card(String color, int value) { 		//if card is wild, color is entered as "wild"
		setCardValue(value);					//cards have values 0-9, draw two=10, reverse=11,
		if(value == 14 || value == 13) {		//skip=12, wild=13, wild draw four=14
			isWild = true;						
		}
		setColor(color);
		String imgName = "";					
		if(value >= 10 && value <= 14) {
			String type = "";
			if(value == 10) {
				type = "Uno"+this.getColor()+"12";
			} else if(value == 11) {
				type = "Uno"+this.getColor()+"11";
			} else if(value == 12) {
				type = "Uno"+this.getColor()+"10";
			} else if(value == 13) {
				type = "UnoWild";
			} else if(value == 14) {
				type = "UnoDraw4";
			}
			imgName = ""+type;
		} else {
			imgName = "Uno"+this.getColor()+value;
		}
		Image cardPic = new Image(getClass().getResource("Uno/"+imgName+".png").toExternalForm());
		setImage(cardPic);
		setFitWidth(120);
		setFitHeight(180);
	}
	
	public String getSelectedColor() {
		return color;
	}
	
	public void SelectColor(String color) {
		if(isWild) {
			this.color = color;
		}
	}
	
	/**
	 * Specific setter method that sets the Card's Value with the value that is being
	 * passed in
	 */
	public void setCardValue(int cardValue){
		this.cardValue=cardValue;
	}
	
	/**
	 * Getter method that returns the value of the cardValue variable
	 */
	public int getCardValue(){
		return cardValue;
	}
	
	/**
	 * Setter method that uses a string being passed in, ignoring caps, to set the boolean value
	 * of the variable corresponding the string equal to true
	 * for example "Blue" corresponds to isBlue, isBlue is set to true and the
	 * variable color is set to Blue
	 */
	public void setColor(String color){
		if(color.equalsIgnoreCase("blue")){		
			setIsBlue(true);
			this.color="Blue";
		}
		else if(color.equalsIgnoreCase("green")){
			setIsGreen(true);
			this.color="Green";
		}
		else if(color.equalsIgnoreCase("red")){
			setIsRed(true);
			this.color="Red";
		}
		else if(color.equalsIgnoreCase("yellow")){
			setIsYellow(true);
			this.color="Yellow";
		}
		else if(color.equalsIgnoreCase("wild")){
			setIsBlue(true);
			setIsGreen(true);
			setIsRed(true);
			setIsYellow(true);
			this.color="Wild";
		}
		else{
			System.out.println("Invalid Color");
		}
	}
	
	/**
	 * Getter method, checks to makes sure one of the values for the variables has been set
	 * to ensure that the color of the card has been set, then returning the color
	 * If it has not been set, the user is notified
	 */
	public String getColor(){
		return color;
	}
	
	/**
	 * Setter method to set the value of the Blue boolean value
	 */
	public void setIsBlue(boolean isBlue){
		this.isBlue=isBlue;
	}
	
	/**
	 * Getter method to get the value of the Blue boolean value
	 */
	public boolean isBlue(){
		return isBlue;
	}

	/**
	 * Setter method to set the value of the Green boolean value
	 */
	public void setIsGreen(boolean isGreen){
		this.isGreen=isGreen;
	}
	
	/**
	 * Getter method to get the value of the Green boolean value
	 */
	public boolean isGreen(){
		return isGreen;
	}
	
	/**
	 * Setter method to set the value of the Red boolean value
	 */
	public void setIsRed(boolean isRed){
		this.isRed=isRed;
	}
	
	/**
	 * Getter method to get the value of the Red boolean value
	 */
	public boolean isRed(){
		return isRed;
	}
	
	/**
	 * Setter method to set the value of the Yellow boolean value
	 */
	public void setIsYellow(boolean isYellow){
		this.isYellow=isYellow;
	}
	
	/**
	 * Getter method to Get the value of the Yellow boolean value
	 */
	public boolean isYellow(){
		return isYellow;
	}
	
	/**
	 * Getter method to get the value of the Wild boolean value, which is set as the others are set
	 */
	public boolean isWild(){
		return isWild;
	}
	
	/**
	 * Compare to method, returning cards for sorting purposes
	 * Cards valued by color - Blue, Green, Red, Yellow
	 * Then valued by card Value, action cards being worth their numeric values as well
	 * returning 0 if the two cards are equal, -1 if the card being passed in is greater,
	 * and 1 if the card being passed in is less
	 */
	public int compareTo(Card card){
		if (this.isWild() && card.isWild()){
			if(this.getCardValue()>card.getCardValue()){
				return 1;
			}
			else if(this.getCardValue()<card.getCardValue()){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if (this.isBlue() && card.isBlue()){
			if(this.getCardValue()>card.getCardValue()){
				return 1;
			}
			else if(this.getCardValue()<card.getCardValue()){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if (this.isGreen() && card.isGreen()){
			if(this.getCardValue()>card.getCardValue()){
				return 1;
			}
			else if(this.getCardValue()<card.getCardValue()){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if (this.isRed() && card.isRed()){
			if(this.getCardValue()>card.getCardValue()){
				return 1;
			}
			else if(this.getCardValue()<card.getCardValue()){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if (this.isYellow() && card.isYellow()){
			if(this.getCardValue()>card.getCardValue()){
				return 1;
			}
			else if(this.getCardValue()<card.getCardValue()){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if (this.isWild() && !card.isWild()){
			return 1;
		}
		else if (!this.isWild() && card.isWild()){
			return -1;
		}
		else if (this.isBlue && !card.isBlue()){
			return 1;
		}
		else if (!this.isBlue && card.isBlue()){
			return -1;
		}
		else if (this.isGreen && !card.isGreen()){
			return 1;
		}
		else if (!this.isGreen() && card.isGreen()){
			return -1;
		}
		else if (this.isRed && !card.isRed()){
			return 1;
		}
		else if (!this.isRed() && card.isRed()){
			return -1;
		}
		else if (this.isYellow && !card.isYellow()){
			return 1;
		}
		else if (!this.isYellow() && card.isYellow()){
			return -1;
		}
		else{
			return -100;
		}
	}
	
	/**
	 * toString method to print out the cards values
	 */
	public String toString(){
		 if (cardValue>=0 && cardValue<=9){
			 return "[Card] \nColor: " + getColor() + "\nValue:" + cardValue;
		 }
		 else if (cardValue==10){
			 return "[Card] \nColor: " + getColor() + "\nValue: Draw Two";
		 }
		 else if (cardValue==11){
			 return "[Card] \nColor: " + getColor() + "\nValue: Reverse";
		 }
		 else if (cardValue==12){
			 return "[Card] \nColor: " + getColor() + "\nValue: Draw Value";
		 }
		 else if(cardValue==13){
			 return "[Card] \nColor: " + getColor() + "\nValue: Draw Four";
		 }
		 else {
			 return "[Card] \nColor: " + getColor() + "\nValue: Wild";
		 }
	 }
}
