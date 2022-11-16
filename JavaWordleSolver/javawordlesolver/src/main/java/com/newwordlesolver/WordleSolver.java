package com.newwordlesolver;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Java Wordle Solver, Solves the daily wordle using process of 
 * elimination and a list of known five letter words. 
 * 
 * @author Cameron Smith
 * @version 2022.02.22
 */
public class WordleSolver {
  /**
   * Fields:
   * 
   * letters          Stores the current word submitted by the 
   *                  user as well as updating the colors of the 
   *                  new letters.
   * wordList         Stores the list of words from the word 
   *                  file. Updated as the program runs.
   * prevGreenLetters Stores the green letter indexes of the  
   *                  previous word to prevent user from needing
   *                  to specify the colors of them.
   * wordleGuess      Instance of the WordleGuess class used to
   *                  calculate the potential words.
   * betterWordIndex  In the event the user's first guess is
   *                  entirely gray, this will be used to provide
   *                  a more accurate guess.
   * iterate          Used to keep track of which run the current
   *                  game is on out of 6.
   * scan             Scanner used for user input.
   */
  private static ArrayList<Letter> letters = new ArrayList<>();
  private static ArrayList<String> wordList = new ArrayList<>();
  private static ArrayList<Integer> prevGreenLetters = new ArrayList<>();
  private static WordleGuess wordleGuess;
  private static int betterWordIndex = -1;
  private static int iterate;
  private static Scanner scan;
  
  /**
   * Main method that runs the main menu and receives user data 
   * on whether they want to use the program.
   * 
   * @param args
   */
  public static void main(String[] args) {
    iterate = 1;
    String selection = "";
    scan = new Scanner(System.in);
    boolean exited = false; // when the program comes back to main after
                            // quitting this will be checked
    while(!selection.toLowerCase().equals("q")) {
      mainMenu();
      selection = scan.nextLine().replaceAll(" ", ""); // user selection
      if (selection.toLowerCase().equals("e")) {
        System.out.println("Type 'exit' to quit to menu.");
        System.out.println("Type 'back' if you made a mistake\n");
        inputLetters();
        exited = true;
        if (args.length > 0) {
          selection = "q";
        }
        break;
      } else if (selection.toLowerCase().equals("i")) {
        System.out.println(instructions());
      } else if (!selection.toLowerCase().equals("q") &&
          !selection.toLowerCase().equals("i")){
        System.out.println("Please enter 'e' or 'q'!");
        System.out.println("");
      }
    }
    
    if (!selection.equals("q")) {
      System.out.println("\nWould you like to play again!");
      selection = "";
      while(!selection.toLowerCase().equals("n")) {
        System.out.println("Y/N\n");
        selection = scan.nextLine().replaceAll(" ", "");
        
        if (selection.toLowerCase().equals("y")) {
          wordList.clear();
          letters.clear();
          prevGreenLetters.clear();
          main(null);
          break;
        } else if (selection.toLowerCase().equals("n")) {
          selection = "n";
          exited = false;
        } else {
          System.out.println("Please enter 'y' or 'n'!");
          System.out.println("");
        }
      }
    }
    if (!exited) {
      System.out.println("Thank you for playing!");
    }
  }// end main
  
  /**
   * Method is used to receive letters from user while also 
   * running the WordleGuess class to narrow down the results.
   */
  public static void inputLetters() {
    boolean exit = false;
    String word = "";
    final String[] goodWords = {"salet", "crate", "crane", "soare", "roast"
        , "phony"}; // words known to be great guesses in wordle
    if (iterate == 1) { // user's first guess
      while (word.length() != 5) {
        System.out.println("Please enter a five letter word:\n");
        word = scan.nextLine().replaceAll(" ", "");
        if (word.toLowerCase().equals("exit")) {
          exit = true;
          word = "aaaaa"; // used to exit loop when quitting
          String[] exitArgs = {"exit"}; // used to allow the 
          main(exitArgs);               // play again function
        }
        for (int i = 0; i < word.length(); i++) {
          if (isNumeric(word.substring(i, i + 1))){
            System.out.println("Please enter a word without numbers!");
            break;
          }
        }
        if (word.length() != 5 && !exit) {
          System.out.println("The word must be five letters long\n");
        }   
      }
    } else {
      if (betterWordIndex == -1) {
        word = wordList.get(0);
      } else {
        // if the user's guess had all gray letters it will
        // choose a better guess based on known good words.
        word = goodWords[betterWordIndex];
      }
    }
    betterWordIndex = -1;
    
    String color;
    int colorNum = 1;
    boolean back = false;
    int prevGreenSize = prevGreenLetters.size();
    if (!exit) {
      for (int i = 0; i < word.length(); i++) { // specifying colors of letters
        if (!prevGreenLetters.contains(i) && !exit){ 
          System.out.println("\n(Green - 1, Yellow - 2, Gray - 3)");
          System.out.println("Please enter the number of the color for " 
              + word.substring(i, i + 1).toUpperCase() + ":\n");
          
          color = scan.nextLine().replaceAll(" ", "");

          if (color.toLowerCase().equals("back")) { // user wants to go back
            if (i - 1 < 0 || (prevGreenLetters.contains(i - 1) && 
                  prevGreenLetters.size() == prevGreenSize)) {
              System.out.println("You cannot go back\n");
            } else {
              if (prevGreenLetters.contains(i - 1)) {
                // if the user is going back on a green letter
                prevGreenLetters.remove(prevGreenLetters.size() - 1);
              }
              i = i - 2;         
              back = true;
              letters.remove(letters.size() - 1);            
            }
          } else if (color.toLowerCase().equals("exit")) { // user exits to menu
            wordList.clear();
            letters.clear();
            prevGreenLetters.clear();
            exit = true;
            String[] exitArgs = {"exit"}; // used to allow the 
            main(exitArgs);               // play again function
          }

          if (!back && !exit){
            while (!isNumeric(color)) {
              System.out.println("Please enter a number!\n");
              color = scan.nextLine().replaceAll(" ", "");
              if (color.toLowerCase().equals("exit")) {
                // in the event the user has a typo for exit,
                // this is run to help out
                wordList.clear();
                letters.clear();
                prevGreenLetters.clear();
                color = "1"; // to break look
                String[] exitArgs = {"exit"}; // used to allow the 
                main(exitArgs);               // play again function
              }
            }
            colorNum = Integer.parseInt(color);
            
            while (colorNum < 1 || colorNum > 3) {
              while (!isNumeric(color)) {
                System.out.println("Please enter a number!\n");
                color = scan.nextLine().replaceAll(" ", "");
              }
              colorNum = Integer.parseInt(color);
              
              System.out.println("Please enter a number between 1 and 3!\n");
              color = scan.nextLine().replaceAll(" ", "");

            }

            if (colorNum == 1) {
              prevGreenLetters.add(i);
              color = "green";
            } else if (colorNum == 2) {
              color = "yellow";
            } else {
              color = "gray";
            }

            letters.add(new Letter(word.substring(i, i + 1), color));
          } else {
            back = false;
          }
        } else if (!exit){
          // System to prevent user from needing to enter color 
          // for green letters
          System.out.println("\nGreen letter " + word.substring(i, i + 1)
              .toUpperCase() + " was automatically added\n");
          letters.add(new Letter(word.substring(i, i + 1), "green"));
        }
      }
    }

    if (!exit) { // if the user exited the program this will skip
      if (wordList.isEmpty()) {
        wordleGuess = new WordleGuess(letters);
      } else {
        wordleGuess = new WordleGuess(letters, wordList);
      }
      wordleGuess.narrower();
      wordleGuess.factorGreens();
      wordleGuess.factorGrays();
      wordleGuess.factorYellows();

      wordList = wordleGuess.getWordList();

      if (wordList.size() == 1) { // Program has the correct word
        System.out.println("The word is " + wordList.get(0).toUpperCase());
        iterate = 5;
      } else {
        if (prevGreenLetters.isEmpty() || 
              wordleGuess.getYellowLetters().isEmpty()){
          // If there is only gray letters it will suggest a better
          // word.
          for (int j = 0; j < goodWords.length; j++){
            if (wordList.contains(goodWords[j])){
              System.out.println("\nTry this word: " + goodWords[j]
                  .toUpperCase());
              betterWordIndex = j;
              break;
            }
          }
          if (betterWordIndex == -1) {
            // if there were no better words that fit with the 
            // gray letters it will just suggest the next word.
            System.out.println("\nTry this word: " + wordList.get(0).toUpperCase() 
                + "\n");
          }
        } else {
          System.out.println("\nTry this word: " + wordList.get(0).toUpperCase() 
              + "\n");
          
        }
      } 
    
      
      iterate++;

      if (iterate < 6) {  
        letters.clear();
        inputLetters();
      }
    }
  }// end method
  
  /**
   * Checks to see if a string is a number.
   * 
   * @param  str  user input to see if it is a number
   * @return      true if the string is a number, false otherwise
   */
  public static boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }// end method
  
  /**
   * Checks to see if the user inputted color is one of the three 
   * allowed colors. No longer used.
   * 
   * @param  color sends in empty string to be replaced with, 
   *               user input.
   * @param  scan  sends in scanner object to get user input
   * @return       color based on user selection.
   */
  public static String findColor(String color, Scanner scan) {
    boolean running = true; // while loop checker
    while (running) {
      color = scan.nextLine();
      if (color.toLowerCase().equals("gray") || 
          color.toLowerCase().equals("green") || 
          color.toLowerCase().equals("yellow")) {
        running = false;
      } else {
        System.out.println("That is not a given color!\n");
      }
    }
    return color;
  }// end method

 /**
  * Registers the text to display in a tool tip.   The text 
  * displays when the cursor lingers over the component.
  *
  * @param text  the string to display.  If the text is null, 
  *              the tool tip is turned off for this component.
  */
  /**
   * Method used to store instructions to reduce clutter
   * 
   * @return      instructions for the user
   */
  public static String instructions() {
    return "Hello user, welcome to Wordle Solver \n"+
            "        Created by Cameron Smith\n"+
            "In order to use this solver you must first\n"+
            "guess your own word. We suggest a word with\n"+
            "many vowels such as SALET or CRANE. Then we \n"+
            "will ask you which letters are grayed out and \n"+
            "which letters are not. Then the program will \n"+
            "take over from there. To go back to the main\n"+
            "menu type exit at any time and if you made\n" +
            "a mistake you can go back by typing back.\n" +
            "Enjoy :)\n"+
            "-----------------------------------------------\n";
  }// end method
  
  /**
   * Method containing the main menu screen used to reduce 
   * clutter.
   */
  public static void mainMenu() {
    System.out.println("Welcome to the Wordle Solver!"
      + "\nBy Cameron Smith"
      + "\n-----------------------------"
      + "\nType 'e' to enter"
      + "\nType 'i' for instructions"
      + "\nType 'q' to quit\n");
  }// end method
}// end class
