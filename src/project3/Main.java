/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Reece Stevens
 * rgs835
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2015
 */


package project3;
import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		
		//Scanner kb = new Scanner(System.in);

		// TODO methods to read in words, output ladder
	

		// TESTING ONLY!
		//
		String a1 = "stone";
		String a2 = "atone";
		System.out.println(diffByOne(a1,a2));


		String a = "stone";
		String b = "money";
		ArrayList<String> result = getWordLadder(a.toUpperCase(),b.toUpperCase());	
		if (result == null) { System.out.println("No word ladder exists."); return; }
		int len = result.size();
		for (int i = len-1; i >= 0; i -= 1) {
			System.out.println(result.get(i));
		}
		System.out.println("This word ladder has " + len + " rungs.");

	}

	/*
	 * diffByOne(a,b)
	 * Determine if two strings only differ by one letter.
	 * Assumes that strings are of the same length.
	 *
	 * @param 	a	First string
	 * @param 	b 	Second string
	 *
	 * @return 	true or false
	 */
	public static boolean diffByOne(String a, String b) {
		int len = a.length();
		boolean oneDiff = false;
		// For the length of the word
		for (int i = 0; i < len; i += 1) {
			if (a.charAt(i) != b.charAt(i)) { 
				if (oneDiff) { return false; }
				else { oneDiff = true; }
			}
		}
		if (!oneDiff) { return false; }
		return true;
	}

	public static ArrayList<String> findConnections(String input, Set<String> dictionary) {
		ArrayList<String> connections = new ArrayList<String>();
		for (String s : dictionary) {
			// If we haven't already visited it and it's valid, add it to connections
			if (diffByOne(input, s)) { connections.add(s);}
		}
		return connections;
	}

	public static ArrayList<String> wordLadder(String start, String end, Set<String> dictionary) {

		// Base case: we are already at the result.
		if (start.equals(end)) {
			ArrayList<String> final_path = new ArrayList<String>();
			final_path.add(end);
			return final_path;
		}

		// Other base case: we can't find any path to result.
		ArrayList<String> connections = findConnections(start, dictionary);
		if (connections.isEmpty()) { return null; }

		// All the words we've visited are no longer valid words.
		dictionary.removeAll(connections);

		for (String s : connections) {
			ArrayList<String> path = wordLadder(s,end,dictionary);
			// If the path isn't null, we have a winner!
			if (path != null) {
				path.add(s);
				return path;
			}
			// Otherwise, tell the caller that this path is a dead end.
			else {
				continue;
			}
		}

		return null;
			
	}

	public static ArrayList<String> getWordLadder(String start, String end) {
		
		Set<String> dict = makeDictionary();
		ArrayList<String> path = new ArrayList<String>();
		path.add(start);
		ArrayList<String> visited = new ArrayList<String>();
		visited.add(start);
		dict.remove(start);

		ArrayList<String> result = wordLadder(start, end, dict); // replace this line later with real return
		if (result == null) { System.out.println("No word ladder found."); }
		return result;
	}
	
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
