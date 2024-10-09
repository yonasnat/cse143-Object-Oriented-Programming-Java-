// Nathnael Yonas
// CSE143
// 04/27/23
// Assignment #4
// TA: Sophie
// This class runs a game of Hangman that cheats.

import java.util.*;
public class HangmanManager {
	
   //pattern of word
	private String pattern;
   //dictionary of possible words
	private TreeSet<String> wordPossibilities;
   //letters guessed
	private TreeSet<Character> letterGuess;
	private int guessLeft;
	private int number;
	
	// pre- If length is less than 1 or max is less than 1, then throws IllegalArgumentException.
	// post- This method uses the three values passed to initialize the state of the game.
	public HangmanManager(Collection<String> dictionary, int length, int max) {
		if (max < 0 || length < 1) {
			throw new IllegalArgumentException();
		}
		wordPossibilities = new TreeSet<String>();
		letterGuess = new TreeSet<Character>();
		guessLeft = max;
		for (String dicWord: dictionary) {
			if (dicWord.length() == length) {
				wordPossibilities.add(dicWord);
			}
		}
		pattern = "";
		for (int i = 0; i < length; i++) {
			pattern = pattern + "- ";
		}
	}
	
	// pre-
	// post- Returns the set letterGuess to find the current set of letters that have been guessed.
	public Set<Character> guesses() {
		return letterGuess;
	}
	
	// pre-
	// post- Returns the set wordPossibilities to get access to the current set of words
	// being used by the hangaman.
	public Set<String> words() {
		return wordPossibilities;
	}
	
	// pre-
	// post- Returns the number of guesses left in the game.
	public int guessesLeft() {
		return guessLeft;
	}
	
	// pre- If  wordPossibilities is empty, IllegalStateException is thrown.
	// post- return the pattern so its shown for the hangman game.
	public String pattern() {
		if (wordPossibilities.isEmpty()) {
			throw new IllegalStateException();
		}
		return pattern;
	}
	
	// pre- If guessLeft is less than 1 or if wordPossibilities is empty, throws IllegalStateException
	// If wordPossibilities is not empty and the character picked has already been chosen, throws IllegalArgumentException.
	// post- adds the guess to a set and makes new set of words.
	public int record(char guess) {
		if (guessLeft < 1 || wordPossibilities.isEmpty()) {
			throw new IllegalStateException();
		}
		if (!wordPossibilities.isEmpty() && letterGuess.contains(guess)) {
			throw new IllegalArgumentException();
		}
		letterGuess.add(guess);
		TreeMap<String, TreeSet<String>> familyWord = new TreeMap<>();
		for (String word: wordPossibilities) {
			TreeSet<String> hold = new TreeSet<>();
			String updatePattern = makePattern(word, guess);
			if (!familyWord.containsKey(updatePattern)) {
				familyWord.put(updatePattern, hold);
			}
			familyWord.get(updatePattern).add(word);
		}
		rightPattern(familyWord, guess);
		number = guessLetter(pattern, guess);
		return number;
	}
	
	// pre-
	// post- returns max occurence of guessed letter in the word.
	private int guessLetter(String letter, char guess) {
		int numb = 0;
		for (int i = 0; i < letter.length(); i++) {
			if (letter.charAt(i) == guess) {
				numb++;
			}
		}
		if (numb == 0) {
			guessLeft--;
		}
		return numb;
	}
	
	// pre-
	// post- if map isn't empty, it updates wordPossibilities to consist of only specific words
	private void rightPattern(TreeMap<String, TreeSet<String>> familyWord, char guess) {
		int maximum = 0;
		if (!familyWord.isEmpty()) {
			for (String updatePattern : familyWord.keySet()) {
				if (familyWord.get(updatePattern).size() > maximum) {
					wordPossibilities.clear();
					wordPossibilities.addAll(familyWord.get(updatePattern));
					pattern = updatePattern;
					maximum = familyWord.get(updatePattern).size();
				}
			}
		}
	}
	
	// pre-
	// post- makes a new pattern after every word input
	// includes dashes for unidentified letters
	private String makePattern(String word, char guess) {
		int spot = 0;
		String patt = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != guess) {
				patt = patt + pattern.substring(spot, spot + 2);
			} else {
				patt += guess + " ";
			}
			spot = spot + 2;
		}
		return patt;
	}
}
