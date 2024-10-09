// Nathnael Yonas
// 02/21/22
// CSE143
// TA: Rinav
// Assignment 6
// Class uses dictionary to find combinations of words with the same letters

import java.util.*;
public class AnagramSolver {
	// remainder of phrase
	private LetterInventory leftPhrase;
	// stores inputs of the client
	private List<String> dictionary;
	// maps word with letter to correspond
	private Map<String, LetterInventory> anagramDictionary = new HashMap<>();
	
	// pre-
	// post- initializes object that uses list as its dictionary
	public AnagramSolver(List<String> list) {
		for (String phrase : list) {
			anagramDictionary.put(phrase, new LetterInventory(phrase));
		}
		dictionary = list;
	}
   
	// pre- throws IllegalArgumentException if max is less than 0
	// post- combinations of the anagram words are printed using the
	// phrase passed in and the max words allowed in the anagram..
	public void print(String s, int max) {
		List<String> wordsAna = new ArrayList<>();
		List<String> qualify = new ArrayList<>();
		if (max < 0) {
			throw new IllegalArgumentException();
		}else{
			leftPhrase = new LetterInventory(s);
			for (String phrase : dictionary) {
				if (leftPhrase.subtract(anagramDictionary.get(phrase)) != null) {
					qualify.add(phrase);
				}
			}
		}
		helperPrint(max, leftPhrase, qualify, wordsAna);
	}
	
	// pre-
	// post- finds anagrams with same letters and prints.
	// Uses the reamining phrases, the list anagrams to hold the final anagram,
	// the list qualify to eliminate unrelevant words out of the dictionary, and
	// uses max as the limit of words in the anagram.
	private void helperPrint(int max, LetterInventory leftPhrase, List<String> qualify, List<String> wordsAna) {
		if (leftPhrase.isEmpty()) {
			System.out.println(wordsAna);
		} else if (max != wordsAna.size() || max == 0) {
			for (String phrase : qualify){
				LetterInventory inventoryNew = leftPhrase.subtract(anagramDictionary.get(phrase));
				if (inventoryNew != null) {
					wordsAna.add(phrase);
					helperPrint(max, inventoryNew, qualify, wordsAna);
					wordsAna.remove(phrase);
				}
			}
		}
	}
}
