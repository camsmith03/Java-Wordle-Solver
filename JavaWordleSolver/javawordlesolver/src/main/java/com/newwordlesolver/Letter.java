package com.newwordlesolver;
import java.util.ArrayList;

/**
 * Letter class used to create letter objects containing a 
 * character and color. Also used to set yellow letter positions.
 * 
 * @author Cameron Smith
 * @version 2022.02.22
 */
public class Letter {
  private String letter;
  private String color;
  private ArrayList<Integer> cantPositions;
  
  /**
   * Constructor for the letter type.
   * 
   * @param letter character letter of type string.
   * @param color  color of the letter.
   */
  public Letter(String letter, String color) {
    this.letter = letter;
    this.color = color;
  }
  
  /**
   * Constructor for the yellow letters.
   * 
   * @param letter   character letter of type string.
   * @param position position of the yellow letter in the word.
   */
  public Letter(String letter, ArrayList<Integer> cantPositions) {
    this.letter = letter;
    this.cantPositions = cantPositions;
  }
  
  /**
   * Getter for the letter field.
   * 
   * @return the letter field.
   */
  public String getLetter() {
    return letter;
  }
  
  /**
   * Setter for the letter field.
   * 
   * @param letter letter to be set.
   */
  public void setLetter(String letter) {
    this.letter = letter;
  }
  
  /**
   * Getter for the color field.
   * 
   * @return the color field.
   */
  public String getColor() {
    return color;
  }
  
  /**
   * Setter for the color field.
   * 
   * @param color color of the letter.
   */
  public void setColor(String color) {
    this.color = color;
  }
  
  /**
   * Getter for the position field.
   * 
   * @return positions of the yellow letters.
   */
  public ArrayList<Integer> getCantPositions() {
    return cantPositions;
  }
  
  /**
   * Setter for the position field.
   * 
   * @param position index of the yellow letter in the word.
   */
  public void addCantPositions(int position) {
    cantPositions.add(position);
  }

  /**
   * Equals method for the letter class. Used to override the
   * default equals method used by the contains method.
   * 
   * @return true if the letters are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (this.getClass().equals(obj.getClass())){
      Letter newObj = (Letter) obj;
      if (this.letter.equals(newObj.getLetter())) {
        return true;
      }
    }
    return false;
  }
}// end class
