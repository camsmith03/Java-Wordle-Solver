package newwordleproject;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * WordleGuess class stores the vital algorithms
 * that access the word file and factor out words
 * that would not work
 * 
 * @author Cameron Smith (camerons03)
 * @version 2022.02.22
 */
public class WordleGuess {
  String [] currentWord = {"0", "0", "0", "0", "0"};
  private ArrayList<Letter> letters;
  private ArrayList<String> wordList;
  private ArrayList<String> newWordList;
  private ArrayList<String> cantLetters;
  private ArrayList<Letter> yellowLetters;
  
  /**
   * Constructor that initializes ArrayLists
   * and turns the word file into an ArrayList
   * 
   * @param letters
   *            stores the letters of type Letter
   *            with their color attributes
   */
  public WordleGuess(ArrayList<Letter> letters) {
    this.letters = letters;
    cantLetters = new ArrayList<>();
    wordList = new ArrayList<>();
    newWordList = new ArrayList<>();
    yellowLetters = new ArrayList<>();
    try {
      File oldWords = new File("fiveletterwords.txt");
      Scanner scan = new Scanner(oldWords);
      while (scan.hasNextLine()) {
        wordList.add(scan.nextLine());
      }
      scan.close();
    }// end try 
    catch (FileNotFoundException e) {
      System.out.println("Can't find the file!");
      e.printStackTrace();
    }//end catch
  }// end constructor
  
  /**
   * adds the 'gray' letters to the cantLetters ArrayList
   * adds the 'green' letters to currentWord array
   * adds the 'yellow' letters to the yellowLetters ArrayList
   */
  public void narrower() {
    for (int i = 0; i < letters.size(); i++) {
      if (letters.get(i).getColor().equals("gray")) {
        cantLetters.add(letters.get(i).getLetter());
      } else if (letters.get(i).getColor().equals("green")) {
        currentWord[i] = letters.get(i).getLetter();
      } else if (letters.get(i).getColor().equals("yellow")){
        yellowLetters.add(new Letter(letters.get(i).getLetter(), i));
      }
    }// end for
  }// end method
  
  /**
   * removes all words from the word list that contain any
   * gray letters.
   */
  public void factorGrays() {
    boolean remove = false;
    for (int i = 0; i < wordList.size(); i++) {
      for (int j = 0; j < cantLetters.size(); j++) {
        if (wordList.get(i).contains(cantLetters.get(j))) {
          remove = true;
        } 
      }// end for
      if (!remove) {
        newWordList.add(wordList.get(i));
      } else {
        remove = false;
      }
    }// end for
  }// end method
  
  /**
   * removes all of the words that do not have the green letter
   * in its corresponding position
   */
  public void factorGreens() {
    ArrayList<String> tempList = new ArrayList<>();
    boolean remove = false;
    for (int i = 0; i < newWordList.size(); i++) {
      for (int j = 0; j < currentWord.length; j++) {
        if (!currentWord[j].equals("0")) {
          if (!newWordList.get(i).substring(j, j+1)
            .equals(currentWord[j])) {
            remove = true;
          }
        }
      }// end for
      if (!remove) {
        tempList.add(newWordList.get(i));
      } else {
        remove = false;
      }
    }// end for
    newWordList = tempList;
  }
  
  /**
   * removes all words that are in the yellow position with
   * the yellow letter. Also removes all words that do not 
   * contain the yellow letter.
   */
  public void factorYellows() {
    int yPosition;
    ArrayList<String> tempList = new ArrayList<>();
    boolean remove = false;
    for (int i = 0; i < newWordList.size(); i++) {
      for (int j = 0; j < yellowLetters.size(); j++) {
        yPosition = yellowLetters.get(j).getPositon();
        if (newWordList.get(i).contains(yellowLetters.get(j).
          getLetter())){
          if (yPosition != 5) {
            if (newWordList.get(i).substring(yPosition, yPosition + 1)
              .equals(yellowLetters.get(j).getLetter())) {
              remove = true;
            }
          } else {
            if (newWordList.get(i).substring(yPosition)
              .equals(yellowLetters.get(j).getLetter())) {
              remove = true;
            }
          }
          
        } else {
          remove = true;
        }
      }// end for
      if (!remove) {
        tempList.add(newWordList.get(i));
      } else {
        remove = false;
      }
    }// end for
    newWordList = tempList;
  }// end method
  
  /**
   * Getter for newWordList
   * 
   * @return newWordList
   *             new list that contains possible
   *             words
   */
  public ArrayList<String> getWordList(){
    return newWordList;
  }// end method
}// end class
