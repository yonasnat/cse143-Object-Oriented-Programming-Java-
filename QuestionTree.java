// Nathnael Yonas
// CSE 143
// 02/28/21
// TA: Rinva
// Assignment #7
//
// produces a game where the computer tries to guess your object
// by asking questions. 
// computer learns from other guesses and gets smarter to figure out 
// users object.

import java.io.*;
import java.util.*;

public class QuestionTree {
   //root of tree that stores QandA
   private QuestionNode root;
   //records user input
   private Scanner console;
   
   // pre- 
   // post- constructs question tree with one leaf node(object computer)
   public QuestionTree() {
      root = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   // pre- 
   // post- reads input to construct a tree then replaces the current one
   public void read(Scanner input) {
      root = helpRead(input);
   }
   
   // pre-
   // post- helps read the input and construct a tree from a file
   private QuestionNode helpRead(Scanner input) {
      if (input.hasNextLine()){
         String QandA = input.nextLine();
         QuestionNode root = new QuestionNode(input.nextLine());
         if (QandA.startsWith("Q:")) {
            root.yes = helpRead(input);
            root.no = helpRead(input);
         }
      }
      return root;
   }
   
   // pre-
   // post- stores tree to output file 
   public void write(PrintStream output) {
      helpWrite(output, root);
   }
   
   // pre- when PrintStream is null, throws new IllegalArgumentException
   // post- helps store content of tree to a file using printstream to allow 
   // writing to an output file and the root as the Q or A for output
   private void helpWrite(PrintStream output, QuestionNode root) {
      if (output == null) {
         throw new IllegalArgumentException();
      }
      if (root.yes == null || root.no == null) {
         output.println("A:");
         output.println(root.data);
         output.println("Q:");
         output.println(root.data);
         helpWrite(output, root.yes);
         helpWrite(output, root.no);
      } else {
         output.println("Q:");
         output.println(root.data);
         helpWrite(output, root.yes);
         helpWrite(output, root.no);
      }
   }
   
   // pre- 
   // post- asks the user a question, forcing an answer of yes or no;
   // and returns true if the answer was yes, retuns false if it was no
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   // pre-
   // post- Asks y or no questions until they're guess is correct or wrong 
   // makes the tree bigger to add new question and new object
   public void askQuestions() {
      root = askQuestions(root);
   }
   
   // pre-
   // post- with current tree, helps ask y or no questions until they're guess is correct or wrong 
   // makes the tree bigger to add new question and new object
   private QuestionNode askQuestions(QuestionNode root) {
      if (root.yes != null || root.no != null) {
         if (yesTo(root.data)) {
            root.yes = askQuestions(root.yes);
         } else {
            root.no = askQuestions(root.no);
         }
      } else {
         if (yesTo("Would your object happen to be " + root.data + "?")) {
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            QuestionNode userAnswer = new QuestionNode(console.nextLine());
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String userQuestion = console.nextLine();
            
            if (yesTo("And what is the answer for your object?")) {
               root = new QuestionNode(userQuestion, userAnswer, root);
            } else {
               root = new QuestionNode(userQuestion, root, userAnswer);
            }
         }
      }
      return root;
   }
}