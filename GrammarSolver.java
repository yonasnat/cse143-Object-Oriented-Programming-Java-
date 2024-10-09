// Nathnael Yonas
// 05/04/23
// CSE143
// TA: Sophie
// Assignment #5
// This program reads a file and randomly generates elements
// of grammar in BNF

import java.util.*;
public class GrammarSolver {

	// non-terminal that holds a list of strings that are arrays
	private SortedMap<String, List<String[]>> inventoryMap = new TreeMap<>();
	
	//pre- Throws an illegal argument exception if list of strings is empty
	//post- stores in a way that could later help generate parts of the grammar
	public GrammarSolver(List<String> grammar) {
		if (grammar.isEmpty()){
			throw new IllegalArgumentException();
		}
		for (String word : grammar) {
			List<String[]> terminal = new ArrayList<>();
			String[] divide = word.split("::=");
			String terminalN = divide[0];
			String[] gramRules = divide[1].split("[|]");
			for (int i = 0; i < gramRules.length; i++) {
				gramRules[i] = gramRules[i].trim();
				terminal.add(gramRules[i].split("[ \t]+"));
			}
			inventoryMap.put(terminalN, terminal);
		}
	}
	
	//pre-
	//post- returns true if the symbol is nonterminal else it returns false
	public boolean grammarContains(String symbol) {
		if (inventoryMap.containsKey(symbol)){
			return true;
		}else{
			return false;
		}
	}
	
	//pre- throws IllegalArgumentException if times is less than 0
	//or if the grammar doesn't have the symbol
	//post- randomly generates number of occurrences of symbol then returns
	// the result
	public String[] generate(String symbol, int times) {
		if (times < 0 || grammarContains(symbol) == false) {
			throw new IllegalArgumentException();
		}
		String[] holdsValue = new String[times];
		for (int i = 0; i < times; i++) {
			holdsValue[i] = randomsGenerate(symbol);
		}
		return holdsValue;
	}
	
	//pre- if the symbol is not nonterminal, returns symbol
	//post- randomly generates symbol occurrence and returns random nonterminal strings
	private String randomsGenerate(String symbol){
		if (grammarContains(symbol) == false){
			return symbol;
		}
		Random rand = new Random();
		String str = "";
		List<String[]> tempValues = new ArrayList<>();
		tempValues.addAll(inventoryMap.get(symbol));
		int randomNumber = rand.nextInt(tempValues.size());
		String[] values = tempValues.get(randomNumber);
		for (int i = 0; i < values.length; i++){
			str += randomsGenerate(values[i]) + " ";
		}
		return str.trim();
	}
	
	//pre-
	//post- returns a string representation of nonterminal symbols
	public String getSymbols() {
		return inventoryMap.keySet().toString();
	}
}


