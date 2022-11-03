package com.newwordlesolver;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * WordleGuess class stores the vital algorithms that access the 
 * word file and factor out words that would not work.
 * 
 * @author Cameron Smith
 * @version 2022.02.22
 */
public class WordleGuess {
  private String [] currentWord = {"0", "0", "0", "0", "0"};
  private ArrayList<Letter> letters;
  private ArrayList<String> wordList;
  private ArrayList<String> cantLetters;
  private ArrayList<Letter> yellowLetters;
  
  /**
   * Constructor that initializes ArrayLists and turns the word, 
   * file into an ArrayList. Only runs on the first instance.
   * 
   * @param letters stores the letters of type Letter with their, 
   *                color attributes.
   * @throws        FileNotFoundException 
   */
  public WordleGuess(ArrayList<Letter> letters) {
    this.letters = letters;
    cantLetters = new ArrayList<>();
    wordList = new ArrayList<>();
    yellowLetters = new ArrayList<>(); 
    try {
      File oldWords = new File("fiveletterwords.txt");
      Scanner scan = new Scanner(oldWords);
      while (scan.hasNextLine()) {
        wordList.add(scan.nextLine());
      }
      scan.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error finding file");
    }//end try/catch
  }// end constructor
  
  /**
   * Construction that runs on all instances except the first 
   * instance.
   * 
   * @param letters      stores the letters of type Letter with 
   *                     their, color attributes.
   * @param prevWordList stores the previous word list.
   */
  public WordleGuess(ArrayList<Letter> letters, 
      ArrayList<String> prevWordList) {
    this.letters = letters;
    cantLetters = new ArrayList<>();
    wordList = prevWordList;
    yellowLetters = new ArrayList<>(); 
  }// end constructor
  
  /**
   * adds the 'gray' letters to the cantLetters ArrayList
   * adds the 'green' letters to currentWord array
   * adds the 'yellow' letters to the yellowLetters ArrayList
   */
  public void narrower() {
    boolean dupYellow = false; 
    ArrayList<Integer> tempYellowPOS = new ArrayList<>();
    for (int i = 0; i < letters.size(); i++) {
        if (letters.get(i).getColor().equals("gray")) {
            cantLetters.add(letters.get(i).getLetter());
        } else if (letters.get(i).getColor().equals("green")) {
            currentWord[i] = letters.get(i).getLetter();
        } else if (letters.get(i).getColor().equals("yellow")){
            for (int j = 0; j < yellowLetters.size(); j++){
                if (yellowLetters.get(j).getLetter().equals(
                        letters.get(i).getLetter())){
                    yellowLetters.get(j).addCantPositions(i);       
                    dupYellow = true;
                }
            }// end for
            if (!dupYellow) {
                tempYellowPOS.add(i);
                yellowLetters.add(new Letter(letters.get(i).
                        getLetter(), tempYellowPOS));
                tempYellowPOS.clear();
            } else {
                dupYellow = false;
            }
        }
    }// end for
}// end method
  
  /**
   * Removes all words from the word list that contain any gray 
   * letters.
   */
  public void factorGrays() {
    boolean remove = false; // boolean used to remove unwanted words
    ArrayList<String> tempList = new ArrayList<>(); // temporary list
    for (int i = 0; i < wordList.size(); i++) {
      for (int j = 0; j < cantLetters.size(); j++) {
        if (wordList.get(i).contains(cantLetters.get(j))) {
          remove = true;
        } 
      }// end for
      if (!remove) {
        tempList.add(wordList.get(i));
      } else {
        remove = false;
      }
    }// end for
    wordList = tempList; // replace current word list with tempList
  }// end method
  
  /**
   * removes all of the words that do not have the green letter
   * in its corresponding position.
   */
  public void factorGreens() {
    boolean remove = false; // boolean used to remove unwanted words
    ArrayList<String> tempList = new ArrayList<>(); // temporary list
    for (int i = 0; i < wordList.size(); i++) {
      for (int j = 0; j < currentWord.length; j++) {
        if (!currentWord[j].equals("0")) {
          if (!wordList.get(i).substring(j, j+1)
            .equals(currentWord[j])) {
            remove = true;
          }
        }
      }// end for
      if (!remove) {
        tempList.add(wordList.get(i));
      } else {
        remove = false;
      }
    }// end for
    wordList = tempList; // replace current word list with tempList
  }
  
  /**
   * removes all words that are in the yellow position with the 
   * yellow letter. Also removes all words that do not contain 
   * the yellow letter.
   */
  public void factorYellows() {
    int yPosition; // variable set to position of the yellow letter
    boolean remove = false; // boolean used to remove unwanted words
    ArrayList<String> tempList = new ArrayList<>(); // temporary list
    for (int i = 0; i < wordList.size(); i++) {
      for (int j = 0; j < yellowLetters.size(); j++) {
        for (int k = 0; k < yellowLetters.get(j).getCantPositions()
            .size(); k++) {
          yPosition = yellowLetters.get(j).getCantPositions().get(k);
          if (wordList.get(i).contains(yellowLetters.get(j)
              .getLetter())) {
            if (yPosition != 5) {
              if (wordList.get(i).substring(yPosition, yPosition + 1)
                  .equals(yellowLetters.get(j).getLetter())) {
                remove = true;
              }
            } else {
              if (wordList.get(i).substring(yPosition)
                  .equals(yellowLetters.get(j).getLetter())) {
                remove = true;
              }
            }
          }
        }// end for
      }// end for
      if (!remove) {
        tempList.add(wordList.get(i));
      } else {
        remove = false;
      }
    }// end for
    wordList = tempList; // replace current word list with tempList
    // not working
  }// end method
  
  /**
   * Getter for newWordList.
   * 
   * @return wordList with potential words.
   */
  public ArrayList<String> getWordList() {
    return wordList;
  }// end method
}// end class
