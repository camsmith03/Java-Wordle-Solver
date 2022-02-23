package newwordleproject;

/**
 * Letter class used to create letter objects containing 
 * a character and color. Also used to set yellow letter
 * positions.
 * 
 * @author Cameron Smith (camerons03)
 * @version 2022.02.22
 */
public class Letter {
  private String letter;
  private String color;
  private int position;
  
  /**
   * Constructor for the letter type
   * 
   * @param letter
   *            character letter of type 
   *            string
   * @param color
   *            color of the letter
   */
  public Letter(String letter, String color) {
    this.letter = letter;
    this.color = color;
  }
  
  /**
   * Constructor for the yellow letters
   * 
   * @param letter
   *            character letter of type
   *            string
   * @param position
   *            position of the yellow 
   *            letter in the word
   */
  public Letter(String letter, int position) {
    this.letter = letter;
    this.position = position;
  }
  
  /**
   * Getter for the letter field
   * 
   * @return letter
   */
  public String getLetter() {
    return letter;
  }
  
  /**
   * Setter for the letter field
   * Not used by program
   * 
   * @param letter
   *            character letter of type
   *            string
   */
  public void setLetter(String letter) {
    this.letter = letter;
  }
  
  /**
   * Getter for the color field
   * 
   * @return color
   */
  public String getColor() {
    return color;
  }
  
  /**
   * Setter for the color field
   * Not used by program
   * 
   * @param color
   *            color of the letter
   */
  public void setColor(String color) {
    this.color = color;
  }
  
  /**
   * Getter for the position field
   * 
   * @return position
   */
  public int getPositon() {
    return position;
  }
  
  /**
   * Setter for the position field
   * 
   * @param position
   *            position of the yellow
   *            letter in the word
   */
  public void setPostion(int position) {
    this.position = position;
  }
}// end class
