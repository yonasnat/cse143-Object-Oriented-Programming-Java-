// Nathnael Yonas
// 4/4/23
// cse143
// TA: Sophie Lin Robertson
// Assignment #1
// This program counts characters in a string without it caring about the case.

public class LetterInventory {
   private static final int ALPHABET = 26;
   
   //list of count of characters
   private int[] elementData;
   
   //size of inventory
   private int size;
   
   // pre: 
   // post: Makes an inventory of all the letters in the given string
   // and it ignores anything that isn't a letter.
   public LetterInventory(String data) {
      elementData = new int[ALPHABET];
      data = data.toLowerCase();
      for(int i = 0; i < data.length(); i++) {
         char letter = data.charAt(i);
         if (letter >= 'a' && letter <= 'z') {
            size++;
            elementData[letter - 'a']++;
         }
      }
   }
   
   //pre: If there is a nonalphabetic character, it will throw an IllegalArgumentException.
   //post: It returns the amount of letters in the parameter are inside the inventory.
   public int get(char letter) {
      char lower = Character.toLowerCase(letter);
      if (lower < 'a' || lower > 'z') {
         throw new IllegalArgumentException();
      }
      return elementData[lower - 'a'];
   }
   
   // pre: throws IllegalArgumentException if value is less than 0 
   // or anything other than a letter is passed
   // post: sets the count of the inventory
   public void set(char letter, int value) {
      char lower = Character.toLowerCase(letter);
      if (lower < 'a' || lower > 'z' || value < 0) {
         throw new IllegalArgumentException();
      }
      size -= elementData[lower - 'a'];
      elementData[lower - 'a'] = value;
      size += value;
   }
   
   // pre:
   // post: returns sum of all counts in inventory
   public int size() {
      return size;
   }
   
   // pre:
   // post: it returns true if inventory is Empty and false if it's not
   public boolean isEmpty() {
      return size == 0;
   }
   
   // pre:
   // post: returns a string that represents the inventory
   public String toString() {
      String bracket = "[";
      for (int i = 0; i < ALPHABET; i++) {
         for (int k = 0; k < elementData[i]; k++) {
            bracket += (char) (i + 'a');
         }
      }
      return bracket + "]";
   }
   
   // Pre:  takes another letterinventory as a parameter
   // Post: constructs and returns new object of letterinventory that is the sum of both
   public LetterInventory add(LetterInventory other) {
      LetterInventory equals = new LetterInventory("");
      for (int i = 0; i < ALPHABET; i++) {
         equals.elementData[i] = this.elementData[i] + other.elementData[i];
      }
      equals.size = this.size + other.size;
      return equals;
   }
   
   // Pre:  takes another letterinventory as a parameter
   // Post: constructs and returns new object of letterinventory that is the result of subtracting
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory difference = new LetterInventory("");
      for (int i = 0; i < ALPHABET; i++) {
         difference.elementData[i] = this.elementData[i] - other.elementData[i];
         if (difference.elementData[i] < 0) {
            return null;
         }
         difference.size += difference.elementData[i];
      }
      return difference;
   }
}
