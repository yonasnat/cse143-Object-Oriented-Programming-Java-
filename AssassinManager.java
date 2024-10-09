// Nathnael Yonas
// CSE143
// 04/20/23
// TA: Sophie Lin Robertson
// Assignment 3
// The program plays a game called Assassin.

import java.util.*;

public class AssassinManager {
	
	//list of currently alive
	private AssassinNode alive;
	//list of those dead
	private AssassinNode dead;
	
	// pre- if list is empty, throw IllegalArgumentException
	// post- builds a kill ring adding names from the list parameter in the same order
	public AssassinManager(List<String> names) {
		if (names.isEmpty() || names == null) {
			throw new IllegalArgumentException();
		}
		for (int i = names.size() - 1; i >= 0; i--) {
			this.alive = new AssassinNode(names.get(i), this.alive);
		}
	}
	
	// pre-
	// post- names of the people in the kill ring are being printed line by line
	public void printKillRing() {
		AssassinNode ringName = alive;
		if (ringName != null && ringName.next == null) {
			System.out.println("    " + ringName.name + " is stalking " + ringName.name);
		}
		while (ringName.next != null) {
			System.out.println("    " + ringName.name + " is stalking " + ringName.next.name);
			ringName = ringName.next;
		}
	}
	
	// pre-
	// post- names of the people in the grave yard are printed line by line
	public void printGraveyard() {
		AssassinNode graveName = dead;
		while (graveName != null) {
			System.out.println("    " + graveName.name + " was killed by " + graveName.killer);
			graveName = graveName.next;
		}
	}
	
	// pre-
	// post- if killring contains the string passed in, it returns true. Else, it returns false.
	public boolean killRingContains(String name) {
		AssassinNode ringName = alive;
		while (ringName != null) {
			if (ringName.name.equalsIgnoreCase(name)) {
				return true;
			}
			ringName = ringName.next;
		}
		return false;
	}
	
	// pre-
	// post- if the graveyard contains the string passed in, it returns true. Else, it returns false.
	public boolean graveyardContains(String name) {
		AssassinNode graveName = dead;
		while (graveName != null) {
			if (graveName.name.equalsIgnoreCase(name)) {
				return true;
			}
			graveName = graveName.next;
		}
		return false;
	}
	
	// pre-
	// post- if game is over, it returns true. Else, it returns false.
	public boolean gameOver() {
		return alive.next == null;
	}
	
	// pre-
	// post- if game is over, it retunrs winner of the game. Else, it returns null.
	public String winner() {
		if (gameOver()) {
			return alive.name;
		} else {
			return null;
		}
	}
	
	// pre- throws IllegalArgumentException if string passed in is not part of kill ring or if the game is over.
	// post- records killing of the person then moves that person from the kill ring to the grave yard.
	public void kill(String name) {
	// // 	// if (!killRingContains(name)) {
// // // 			throw new IllegalArgumentException();
// // // 		}
// // // 		if (gameOver()) {
// // // 			throw new IllegalStateException();
// // // 		}
 		AssassinNode current = alive;
 		AssassinNode before = dead;
 		//checks if name is in front, which would mean killer is at the end
 		if (current.name.equalsIgnoreCase(name)) {
 			while (current.next != null) {
 				current = current.next;
 			}
 			before.killer = current.name;
 			alive = alive.next;
 		} else {
 			while (!current.next.name.equalsIgnoreCase(name)) {
 				current = current.next;
 			}
          //gets killer and removes person who died
 			current.next.killer = current.name;
			before = current.next;
 			current.next = current.next.next;
 		}
 		//put killed person in dead list
 		if (dead == null) {
 			dead = before;
 			dead.next = null;
 		} else {
 			before.next = dead;
 			dead = before;
 		}
   }
}
 