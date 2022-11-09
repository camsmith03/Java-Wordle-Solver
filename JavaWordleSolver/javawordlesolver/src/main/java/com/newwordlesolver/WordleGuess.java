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
  /**
   * Fields:
   * 
   * currentWord   Stores the current word known by the program.
   *               Zeros are used to represent unknown letters.
   * letters       Stores the five Letter objects of the user 
   *               guessed word.
   * wordList      Stores the list of words from the word file.
   *               Updated as the program runs.
   * cantLetters   List of all the gray letters in the word.
   * yellowLetters Stores the yellow letters and their positions
   *               in the word.
   */
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
            // for (int j = 0; j < yellowLetters.size(); j++){
            //     if (yellowLetters.get(j).getLetter().equals(
            //             letters.get(i).getLetter())){
            //         yellowLetters.get(j).addCantPositions(i);       
            //         dupYellow = true;
            //     }
            // }// end for
            // if (!dupYellow) {
            //     tempYellowPOS.add(i);
            //     yellowLetters.add(new Letter(letters.get(i).
            //             getLetter(), tempYellowPOS));
            //     tempYellowPOS.clear();
            // } else {
            //     dupYellow = false;
            // }
            // TODO: Fix this
            yellowLetters.add(new Letter(letters.get(i).getLetter(), new ArrayList<Integer>()));
            yellowLetters.get(yellowLetters.size() - 1).addCantPositions(i);
            
        }
    }// end for
}// end method
  
  /**
   * Removes all words from the word list that contain any gray 
   * letters.
   */
  public void factorGrays() {
    // TODO: Check to see if it is also a yellow letter
    // in the game if you have two of the same letter and 
    // one is yellow then the other defaults to gray
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
    String[] regex = {"^0.{4}$", "^.0.{3}$", "^.{2}0.{2}$", 
        "^.{3}0.$", "^.{4}0$"};
    for (int i = 0; i < yellowLetters.size(); i++) {
        ArrayList<Integer> yellowPositions = yellowLetters.get(i)
            .getCantPositions();
        final String letter = yellowLetters.get(i).getLetter();
        int size = yellowPositions.size();
        
        for (int j = 0; j < yellowPositions.size(); j++) {
          final int pos = j;
          
          wordList.removeIf(s -> s.matches(regex[yellowPositions.get(pos)]
              .replace("0", letter)));
          wordList.removeIf(s -> !s.contains(letter));
        }
    }
    // wordList = tempList; // replace current word list with tempList
    // REGEX work on this ^e.{4}$ => earth or e....
  }// end method
  
  /**
   * Getter for newWordList.
   * 
   * @return wordList with potential words.
   */
  public ArrayList<String> getWordList() {
    return wordList;
  }// end method
  
  /**
   * Getter for the current word. Used in testing.
   * 
   * @return currentWord array.
   */
  public String[] getCurrentWord() {
    return currentWord;
  }

  /**
   * Getter for the yellow letter. Used in testing.
   * 
   * @return yellowLetters ArrayList.
   */
  public ArrayList<Letter> getYellowLetters() {
    return yellowLetters;
  }

  /**
   * Getter for the gray letters. Used in testing.
   * 
   * @return grayLetters ArrayList.
   */
  public ArrayList<String> getCantLetters() {
    return cantLetters;
  }

  /**
   * Getter for the letters from user inputted word. Used in 
   * testing.
   * 
   * @return letters ArrayList.
   */
  public ArrayList<Letter> getLetters() {
    return letters;
  }
}// end class
