package com.newwordlesolver;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Unit test for WordleGuess Class
 * 
 * @author Cameron Smith
 * @version 2022.11.03
 */
public class WordleGuessTest {

    /**
     * Testing of the narrower() method. Making sure that the 
     * fields are properly updated.
     */
    @Test
    public void testNarrower() {
        ArrayList<Letter> testWord1 = new ArrayList<>();
        testWord1.add(new Letter("e", "gray"));
        testWord1.add(new Letter("a", "yellow"));
        testWord1.add(new Letter("r", "gray"));
        testWord1.add(new Letter("t", "gray"));
        testWord1.add(new Letter("h", "gray"));

        WordleGuess testingEval = new WordleGuess(testWord1);
        assertEquals(testWord1, testingEval.getLetters());

        testingEval.narrower();

        // Checking for yellow
        ArrayList<Letter> testYellow = new ArrayList<>();
        ArrayList<Integer> testYellowPos = new ArrayList<>();
        testYellowPos.add(1);
        testYellow.add(new Letter("a", testYellowPos));
        
        assertEquals(testYellow.get(0).getLetter(), 
                testingEval.getYellowLetters().get(0).getLetter());


        // Checking for gray
        ArrayList<String> testCantLetters = new ArrayList<>();
        testCantLetters.add("e");
        testCantLetters.add("r");
        testCantLetters.add("t");
        testCantLetters.add("h");
        for (int i = 0; i < testCantLetters.size(); i++) {
            assertEquals(testCantLetters.get(i), 
                    testingEval.getCantLetters().get(i));
        }

        testYellow.clear();
        testYellowPos.clear();
        testCantLetters.clear();

        ArrayList<Letter> testWord2 = new ArrayList<>();
        testWord2.add(new Letter("a", "green"));
        testWord2.add(new Letter("b", "green"));
        testWord2.add(new Letter("c", "green"));
        testWord2.add(new Letter("d", "green"));
        testWord2.add(new Letter("e", "green"));

        testingEval = new WordleGuess(testWord2);

        testingEval.narrower();

        // Checking for green
        String[] testGreen = {"a", "b", "c", "d", "e"};
        
        for (int i = 0; i < testGreen.length; i++) {
            assertEquals(testGreen[i], 
                    testingEval.getCurrentWord()[i]);
        }
    }

    /**
     * Testing of the factorGrays() method. Making sure that the 
     * word list is updated upon calling the method.
     */
    @Test
    public void testFactorGrays() {
        ArrayList<Letter> testWord1 = new ArrayList<>();
        testWord1.add(new Letter("e", "gray"));
        testWord1.add(new Letter("a", "gray"));
        testWord1.add(new Letter("i", "gray"));
        testWord1.add(new Letter("o", "gray"));
        testWord1.add(new Letter("u", "gray"));

        WordleGuess testingEval = new WordleGuess(testWord1);
        assertEquals(testWord1, testingEval.getLetters());
        
        testingEval.narrower();
        testingEval.factorGrays();

        String[] grayWords = {"gypsy", "myths", "hymns", "shyly", "nymph", 
            "myrrh", "dryly", "slyly", "lymph", "wryly", "pygmy", "lynch", 
            "cysts", "glyph", "flyby", "crypt", "synch", "tryst", "psych", 
            "pssst", "sylph", "syncs", "pffft", "gyppy"};
        
        for (int i = 0; i < grayWords.length; i++) {
            assertEquals(grayWords[i], testingEval.getWordList().get(i));
        }
    }

    /**
     * Testing of the factorGreens() method. Making sure word 
     * list is updated to include only words that have letters
     * in the green position. 
     */
    @Test
    public void testFactorGreens() {        
        ArrayList<Letter> testWord1 = new ArrayList<>();
        testWord1.add(new Letter("e", "green"));
        testWord1.add(new Letter("m", "gray"));
        testWord1.add(new Letter("m", "gray"));
        testWord1.add(new Letter("m", "gray"));
        testWord1.add(new Letter("n", "green"));

        String [] greenWords = {"eaten", "elfin", "excon"};

        WordleGuess testingEval = new WordleGuess(testWord1);
        assertEquals(testWord1, testingEval.getLetters());

        testingEval.narrower();
        testingEval.factorGreens();

        for (int i = 0; i < greenWords.length; i++) {
            assertEquals(greenWords[i], testingEval.getWordList().get(i));
        }
    }

    /**
     * Testing of the factorYellows() method. Making sure the 
     * word list is updated to include only the words that have 
     * the yellow letter in them, and to ensure that the yellow 
     * letter is not in the location that it should not be in.
     */
    @Test 
    public void testFactorYellows() {
        ArrayList<Letter> testWord1 = new ArrayList<>();
        testWord1.add(new Letter("e", "yellow"));
        testWord1.add(new Letter("c", "yellow"));
        testWord1.add(new Letter("s", "yellow"));
        testWord1.add(new Letter("a", "yellow"));
        testWord1.add(new Letter("z", "gray"));
        
        WordleGuess testingEval = new WordleGuess(testWord1);
        assertEquals(testWord1, testingEval.getLetters());

        testingEval.narrower();
        testingEval.factorYellows();


        String[] yellowWords = {"space", "cause", "faces", "cakes", "races", 
            "chase", "caves", "sauce", "cease", "cages", "cares", "paces", 
            "laces", "cafes", "capes", "canes", "maces"};

        for (int i = 0; i < yellowWords.length; i++) {
            assertEquals(yellowWords[i], testingEval.getWordList().get(i));
        }
        

    }
}
