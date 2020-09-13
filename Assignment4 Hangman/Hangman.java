/*
 * File: Hangman.java
 * ------------------
 *
 * Assignment #4 Hangman Game
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
    public void run() {
    	canvas.reset();
		println("Welcome to Hangman!");
		//chooses a random word from the lexicon
		int wordIndex = rgen.nextInt(0, lexicon.getWordCount()-1);
		word = lexicon.getWord(wordIndex);
		play(word);	
    }
    private void play(String word) {
		//initialize the word with dashes instead of letters
    	wordWithGuessedLetters = word.replaceAll("*", "-");
		// the game ends if the user guessed all the letters before he reached maximum number of allowed guesses
    	//or when he reached max.
		while (guess != 0 && correct != word.length()) {
			println("The word now looks like this: " + wordWithGuessedLetters);
			canvas.displayWord(wordWithGuessedLetters); //display word on the canvas
			println("You have " + guess + " guesses left.");
			String letter = readLine("Your guess: ");
			String wordWithNewGuessedLetter = userGuess(letter);
			if (!wordWithNewGuessedLetter.equals(wordWithGuessedLetters)) {
				println("That guess is correct.");
				wordWithGuessedLetters = wordWithNewGuessedLetter;
			}else {
				println("There are no " + letter.toUpperCase() + "'s in the word.");
				guess--;
				canvas.noteIncorrectGuess(letter.toUpperCase().charAt(0));
			}
		}
		println(endOfGame());
    }

    // display outcome of the game
	private String endOfGame() {
		if(correct == word.length()) {
			return "You've won!";
		}else {
			return "You've lost!";
		}
	}
	
	private String userGuess(String letter) {
		char ch = letter.toUpperCase().charAt(0);
		String result = wordWithGuessedLetters;
		// loop through the word and see if guessed letter matches any letter in the word,
		// if yes, replace the letter with the guessed one in the result variable, that is displayed to the user
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == ch) {
				result = result.substring(0, i) + ch + result.substring(i+1, word.length());
				correct++;
			}
		}
		return result;
	}
    
    private HangmanLexicon lexicon = new HangmanLexicon();
    private RandomGenerator rgen = RandomGenerator.getInstance();
    private static final int NUM_GUESSES = 8;
    public static int guess = NUM_GUESSES;
    private int correct;
    private String word = "";
    String wordWithGuessedLetters;
    
    private HangmanCanvas canvas;

}
