package newwordleproject;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Java Wordle Solver, Solves the daily wordle
 * using process of elimination and a list of 
 * known five letter words
 * 
 * @author Cameron Smith (camerons03)
 * @version 2022.02.22
 */
public class WordleSolver {
  static String selection = "";
  static String secondSelection = "";
  static ArrayList<Letter> letters = new ArrayList<>();
  static WordleGuess wordleGuess;
  static int iterate;
  int num = 0;
  
  /**
   * main method that runs the main menu and 
   * receives user data on whether they want to use
   * the program.
   * 
   * @param args
   */
  public static void main(String[] args) {
    iterate = 1;
    while(!selection.toLowerCase().equals("q")) {
      Scanner scan = new Scanner(System.in);
      mainMenu();
      selection = scan.nextLine().replaceAll(" ", "");
      if (selection.toLowerCase().equals("e")) {
        inputLetters();
        scan.close();
        selection = "q";
      } else if (selection.toLowerCase().equals("i")) {
        System.out.println(instructions());
      }
      else if (!selection.toLowerCase().equals("q") &&
              !selection.toLowerCase().equals("i")){
        System.out.println("Please enter 'e' or 'q'!");
        System.out.println("");
      }
    }
    System.out.println("/nNow you have the word!");
  }// end main
  
  /**
   * Method is used to receive letters from user
   * while also running the WordleGuess class to 
   * narrow down the results.
   */
  public static void inputLetters() {
    Scanner scan = new Scanner(System.in);
    int num = 1;
    String word = "";
    String color = "";
    while(num != 6) {
      System.out.println("Please enter letter #" + num + "\n");
      word = scan.nextLine().replaceAll(" ", "");
      if (word.length() == 1 && isNumeric(word)) {
        System.out.println("What color is the letter? (gray, green, "
          + "or yellow)\n");
        color = findColor(color, scan);
        letters.add(new Letter(word, color));
        num += 1;
      }
      else {
        System.out.println("Please enter a letter!\n");
      }
    }
    wordleGuess = new WordleGuess(letters);
    wordleGuess.narrower();
    wordleGuess.factorGrays();
    wordleGuess.factorGreens();
    wordleGuess.factorYellows();
    System.out.println("These are the words you can use!");
    for (String i : wordleGuess.getWordList()) {
      System.out.println(i);
    }
    if (wordleGuess.getWordList().size() == 1) {
      iterate = 5;
    }
    iterate++;
    if (iterate != 6) {
      System.out.println("Enter your next word and provide the "
        + "info!\n");
      letters.clear();
      inputLetters();
    }
  }// end method
  
  /**
   * checks to see if a string is a number
   * @param str
   *            user input to see if it is a number
   * @return boolean
   */
  public static boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return false;
    } catch (NumberFormatException e) {
      return true;
    }
  }// end method
  
  /**
   * Checks to see if the user inputed color is one of the 
   * three allowed colors
   * 
   * @param color
   *            sends in empty string to be replaced with user
   *            input
   * @param scan
   *            sends in scanner object to get user input
   * @return color
   *            correct user input returned
   */
  public static String findColor(String color, Scanner scan) {
    boolean running = true;
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
   * method used to store instructions to reduce clutter
   * 
   * @return String
   */
  public static String instructions() {
    return "Hello user, welcome to Worldle Solver \n"+
            "        Created by Cameron Smith\n"+
            "In order to use this solver you must first\n"+
            "guess your own word. We suggest a word with\n"+
            "many vowels such as EARTH or OATER. Then we \n"+
            "will ask you which letters are grayed out and \n"+
            "which letters are not. Then the program will \n"+
            "take over from there.\n"+
            "Enjoy :)\n"+
            "-----------------------------------------------\n";
  }// end method
  
  /**
   * Method containing the main menu screen
   * used to reduce clutter
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
