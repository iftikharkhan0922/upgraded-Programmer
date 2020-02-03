/*
 * A program to read a file for words separated by line
 * and play hang man game with it.
 */

package com.Iftikhar;

/*
 * @ author Iftikhar Khan
 * Last modified on July 7, 2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Create a Scanner instance for user input.
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        String play;                                            // Loop condition.
        ArrayList<String> words = new ArrayList<String>();      // ArrayList to hold the words.


        // read words into Array list words.
        readWords(words);

        // Print words to check file input.
        //printList(words);

        do {
            // pick random word.
            char [] playWord = randomWord(words);

            // replace word with asterisk.
            char [] asterisks = new char[playWord.length];
            fillAsterisks(asterisks);

            int missed = 0;
            do {
                // Prompt the user to guess one letter
                char guess = getGuess(asterisks);

                // Check if is letter is correct
                if (!isCorrectGuess(playWord, asterisks, guess))
                    missed++;

            } while (!isWordFinish(asterisks));

            // Print results
            print(playWord, missed);

            // Ask the user whether to continue to play with another word
            System.out.println("Do you want to guess another word? Enter y or n>");
            play = input.next();

        } while (play.charAt(0) == 'y' || play.charAt(0) == 'Y');

    } // End main()

    private static void readWords(ArrayList<String> words)  {

        // Create a File object to read the file and read file using while loop.
        File wordsFile = new File("hangman.txt");   // A file that holds a list of words.
        try (Scanner readWords = new Scanner(wordsFile)) {
            while(readWords.hasNext()) {
                try {
                    words.add(readWords.next());
                } catch (IllegalArgumentException e) {
                    System.out.println("Check input file...\n"
                                        + "Words should be separated by spaces. "
                                        + "No special characters.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!..." + e);
        } // End of try-catch block.
    } // End readWords().

    private static char [] randomWord(ArrayList<String> words) {
        // Randomly pick a word from the array list.
        String word = words.get((int) (Math.random() * words.size()));
        return word.toCharArray();
    } // End randomWords().

    private static void fillAsterisks(char [] asterisks) {
        // replace each character with an asterisk.
        for (int i = 0; i < asterisks.length; i++) {
            asterisks[i] = '*';
        }
    } // End printAsterisks().

    /** checkGuess tests if the users guess was correct */
    public static boolean isCorrectGuess(char[] word, char[] blanks, char guess) {
        boolean correct = false;
        int message = 2;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == guess) {
                correct = true;
                if (blanks[i] == guess)
                    message = 1;
                else {
                    blanks[i] = guess; // the actual letter is then displayed.
                    message = 0;
                }
            }
        }
        if (message > 0)
            print(message, guess);
        return correct;
    }

    /** isWordFinish */
    public static boolean isWordFinish(char[] blanks) {
        for (char e: blanks) {
            if (e == '*')
                return false;
        }
        return true;
    } // End isWordFinish().

    /** print overloaded */
    public static void print(char[] word, int missed) {
        System.out.print("The word is ");
        System.out.print(word);
        System.out.println(" You missed " + missed + " time");
    } // End print().

    /** print overloaded */
    public static void print(int m, char guess) {
        System.out.print("\t" + guess);
        switch (m) {
            case 1 : System.out.println(" is already in the word"); break;
            case 2 : System.out.println(" is not in the word");
        }
    } // End print().

    /** getGuess prompts the user to guess one letter at a time */
    public static char getGuess(char[] asterisks){
        Scanner input = new Scanner(System.in);
        System.out.print("(Guess) Enter a letter in word ");
        System.out.print(asterisks);
        System.out.print(" > ");
        String g = input.next();
        return g.charAt(0);
    } // End getGuess().

} // End Main Class.
