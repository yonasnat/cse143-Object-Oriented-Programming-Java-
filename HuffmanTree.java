// Nathnael Yonas
// CSE143
// 03/10/22
// TA: Rinav
// Assignment #8
// Using huffman coding, this program decodes or encodes a compressed file.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   // stores root node
   private HuffmanNode overallRoot;
   
   // pre-
   // post- constructs initial Huffman tree using the passed in array of frequencies 
   // the amount of occurrences of a character is followed by count[i]
   public HuffmanTree(int[] count) { 
      Queue<HuffmanNode> priorityQ = new PriorityQueue<>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            priorityQ.add(new HuffmanNode(i, count[i]));
         }
      }
      priorityQ.add(new HuffmanNode(count.length, 1));
      while (priorityQ.size() > 1) {
         HuffmanNode charOne = priorityQ.remove();
         HuffmanNode charTwo = priorityQ.remove();
         HuffmanNode root = new HuffmanNode(-1, charOne.freqCount + charTwo.freqCount, charOne, charTwo);
         priorityQ.add(root);
      }
      overallRoot = priorityQ.remove();
   }
   
   // pre-
   // post- writes tree to given output stream  
      public void write(PrintStream output) {
      helpWrite(output, "", overallRoot);
   }
   
   // pre- 
   // post- prints leafs path with 0 or 1 and the leaf to the output
   private void helpWrite(PrintStream output, String huffCode, HuffmanNode node) {
      if (node.right == null && node.left == null) {
         output.println(node.characterVal);
         output.println(huffCode);
      } else if (node != null) {
         helpWrite(output, huffCode + "0", node.left);
         helpWrite(output, huffCode + "1", node.right);
      }
   }
   
   // pre- 
   // post- reconstruct tree from file
   public HuffmanTree(Scanner input) {
      while(input.hasNextLine()) {
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = helpHuffTree(n, code, overallRoot);
      }
   }
   
   // pre-
   // post- makes path of the tree with 0 or 1 then returns node from the code.
   private HuffmanNode helpHuffTree(int charValue, String huffCode, HuffmanNode node) {
      if (node == null) {
         node = new HuffmanNode(-1);
      }
      if (huffCode.length() == 0) {
         return new HuffmanNode(charValue);
      }
      if (huffCode.charAt(0) == '1') {
         node.right = helpHuffTree(charValue, huffCode.substring(1), node.right);
      } else {
         node.left = helpHuffTree(charValue, huffCode.substring(1), node.left);
      }
      return node;
   }
   
   // pre-
   // post- reads individual bits from BitInputStream and writes 
   // corresponding to PrintStream.
   // stops when there's a character with the same value as eof 
   public void decode(BitInputStream input, PrintStream output, int eof) {
      while (input.readBit() != -1) {
        int bitFile = input.readBit();
        bitFile = helpDecode(eof, bitFile, overallRoot, input, output);
      }
   }
   
   // pre-
   // post- reads individual bits from BitInputStream and writes 
   // corresponding to PrintStream.
   // stops when there's a character with the same value as eof 
   private int helpDecode(int eof, int bitFile, HuffmanNode overallRoot, BitInputStream input, PrintStream output) {
      if (overallRoot.left == null && overallRoot.right == null && overallRoot.characterVal != eof) {
         output.write(overallRoot.characterVal);
         return bitFile;
      } else if (bitFile == 0 && overallRoot.characterVal != eof) {
         return helpDecode(eof, input.readBit(), overallRoot.left, input, output);
      } else if (overallRoot.characterVal != eof) {
         return helpDecode(eof, input.readBit(), overallRoot.right, input, output);
      }
      return -1;
   }
}