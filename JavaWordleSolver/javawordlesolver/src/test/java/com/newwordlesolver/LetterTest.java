package com.newwordlesolver;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Unit test for Letter class
 * 
 * @author Cameron Smith
 * @version 2022.11.02
 */
public class LetterTest {

    /**
     * Tests for the getLetter() method.
     */
    @Test
    public void testGetLetter() {
        Letter testLetter = new Letter("a", "red");
        assertTrue(testLetter.getLetter().equals("a"));
        testLetter.setLetter("b");
        assertTrue(testLetter.getLetter().equals("b"));
    }

    /**
     * Tests for the setLetter() method.
     */
    @Test
    public void testSetLetter(){
        Letter testLetter = new Letter("a", "red");
        testLetter.setLetter("b");
        assertTrue(testLetter.getLetter().equals("b"));
    }

    /**
     * Tests for the getColor() method.
     */
    @Test
    public void testGetColor() {
        Letter testLetter = new Letter("a", "red");
        assertTrue(testLetter.getColor().equals("red"));
        testLetter.setColor("blue");
        assertTrue(testLetter.getColor().equals("blue"));
    }

    /**
     * Tests for the setColor() method.
     */
    @Test
    public void testSetColor() {
        Letter testLetter = new Letter("a", "red");
        testLetter.setColor("blue");
        assertTrue(testLetter.getColor().equals("blue"));
    }

    /**
     * Tests for the getCantPositions() method.
     */
    @Test
    public void testGetCantPositions() {
        // ArrayList<Integer> testList = new ArrayList<>();
        // testList.add(1);
        // testList.add(2);
        // testList.add(3);
        // Letter testLetter = new Letter("a", testList);
        // assertTrue(testLetter.getCantPositions().equals(testList));

        // ArrayList<Integer> testList2 = new ArrayList<>();
        // for (int i = 1; i <= 100; i++) {
        //     testList2.add(i);
        // }
        // Letter testLetter2 = new Letter("b", testList2);
        // ArrayList<Integer> compareList = testLetter2.getCantPositions();
        // assertTrue(compareList.equals(testList2));
    }

    /**
     * Tests for the setCantPositions() method.
     */
    // @Test
    // public void testAddCantPositions(){
    //     ArrayList<Integer> testList = new ArrayList<>();
    //     testList.add(1);
    //     testList.add(2);
    //     testList.add(3);
    //     Letter testLetter = new Letter("a", testList);
    //     testLetter.addCantPositions(4);
    //     testList.add(4);
    //     assertTrue(testLetter.getCantPositions().equals(testList));
    // }
}
