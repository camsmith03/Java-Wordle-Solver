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
      // Credit to charlesreid1 for the file
      // https://github.com/charlesreid1/five-letter-words/blob/master/sgb-words.txt
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
    for (int i = 0; i < letters.size(); i++) {
        if (letters.get(i).getColor().equals("gray")) {
            Letter tempYellow = new Letter(letters.get(i).getLetter(), 
                "yellow");
            if (yellowLetters.contains(tempYellow)) { 
              // if the gray letter is already a yellow letter
              for (int k = 0; k < yellowLetters.size(); k++) {
                if (yellowLetters.get(k).getLetter().equals(
                    letters.get(i).getLetter())) {
                  yellowLetters.get(k).addCantPositions(i);
                }
              }
            } else {
              cantLetters.add(letters.get(i).getLetter());
            }
        } else if (letters.get(i).getColor().equals("green")) {
          currentWord[i] = letters.get(i).getLetter();
        } else if (letters.get(i).getColor().equals("yellow")){
            if (yellowLetters.contains(letters.get(i))){ 
              // if there is more than one yellow letter
              int index = yellowLetters.indexOf(letters.get(i));
              yellowLetters.get(index).addCantPositions(i);
            } else {
              yellowLetters.add(new Letter(letters.get(i).getLetter()
                  , new ArrayList<Integer>()));
              yellowLetters.get(yellowLetters.size() - 1).addCantPositions(i);
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
    String[] regex = {"^0.{4}$", "^.0.{3}$", "^.{2}0.{2}$", 
        "^.{3}0.$", "^.{4}0$"};
    for (int i = 0; i < wordList.size(); i++) {
      for (int j = 0; j < cantLetters.size(); j++) {
        if (wordList.get(i).contains(cantLetters.get(j))) {
          // Checking to see if the gray letter is already in the
          // current word in the specific index. This factors for
          // if there is a gray letter then a green letter of the
          // same value.
          
          if (!currentWord[wordList.get(i).indexOf(cantLetters.get(j))]
              .equals(cantLetters.get(j))) {
            // if the word in the word list does not have the
            // gray letter in the current word.
            remove = true; 
          } else {
            int greenPOS = wordList.get(i).indexOf(cantLetters.get(j));
            for (String s : regex) {
              if (!s.equals(regex[greenPOS])) {
                // removes all words that have a green and gray 
                // letter in the same word.
                if (wordList.get(i).matches(s
                      .replace("0", cantLetters.get(j)))) {
                  remove = true;
                }
              }
            }

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
    ArrayList<Integer> greenPositions = new ArrayList<>();
    ArrayList<String> removeWords = new ArrayList<>();

    for (int i = 0; i < yellowLetters.size(); i++) {
      ArrayList<Integer> yellowPositions = yellowLetters.get(i)
          .getCantPositions();
      final String letter = yellowLetters.get(i).getLetter();        
      for (int j = 0; j < yellowPositions.size(); j++) {
        final int pos = j;
        // remove every word that has the yellow letter in the 
        // given position while also containing the yellow letter
        wordList.removeIf(s -> s.matches(regex[yellowPositions.get(pos)]
            .replace("0", letter)));
        wordList.removeIf(s -> !s.contains(letter));
      }
      // checking to see if there are any green letters with the
      // yellow ones.
      for (int k = 0; k < currentWord.length; k++) {
        if (currentWord[k].equals(letter)) {
          greenPositions.add(k);
        }
      }
      // Remove words where there is a green letter the same as the yellow
      boolean remove = true;
      if (!greenPositions.isEmpty()){
        for (String word : wordList) {
          for (Integer greenPOS : greenPositions) {
            if (word.substring(0, greenPOS).contains(letter) || 
                word.substring(greenPOS + 1).contains(letter)) {
              remove = false;
            }
          }
          if (remove) {
            removeWords.add(word);
          } else {
            remove = true;
          }
        }
        greenPositions.clear();
      }
    }

    for (String word : removeWords) {
      wordList.remove(word);
    }
    removeWords.clear();
    
    
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
