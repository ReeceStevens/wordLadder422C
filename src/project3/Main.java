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

		String a = "stone";
		String b = "money";
		ArrayList<String> result = getWordLadder(a.toUpperCase(),b.toUpperCase());	
		if (result == null) { System.out.println("No word ladder exists."); return; }
		System.out.println("This word ladder has " + result.size() + " rungs.");

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
			if (diffByOne(input, s))  { connections.add(s);}
		}
		return connections;
	}

	public static ArrayList<String> wordLadder(String start, String end, Set<String> dictionary) {
		ArrayList<String> connections = findConnections(start, dictionary);

		// Base Case 1: Word is directly connected to end.
		if (connections.contains(end)) {
			ArrayList<String> final_path = new ArrayList<String>();
			final_path.add(start);
			return final_path;
		}

		// Base Case 2: We can't find any path to result.
		if (connections.isEmpty()) { 
				return null; 
		}

		dictionary.removeAll(connections);
		// General Case
		int min_len = dictionary.size();
		String min_string = null;
		ArrayList<String> min_path = new ArrayList<String>();
		min_path = null;
		for (String s : connections) {

			ArrayList<String> path = wordLadder(s,end,dictionary);
		
			if (path != null) {	
				if (path.size() < min_len) {
					min_path = path;
					min_len = path.size();
					min_string = s;
				}
			}
			else { continue; }
		}

		// Mark all paths as invalid except the shortest.
		if (min_path == null) { return null; }
		dictionary.add(min_string);
		min_path.add(start);
		return min_path;
	}

	public static ArrayList<String> getWordLadder(String start, String end) {
		
		Set<String> dict = makeDictionary();
		System.out.println("Dictionary is of size " + dict.size() );
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayList<String> path = new ArrayList<String>();
		path.add(start);
		Set<String> visited = new HashSet<String>();
		visited.add(start);

		ArrayList<String> result = wordLadder(start, end, dict); 
		if (result == null) { System.out.println("No word ladder found."); }
		else {
			for (int i = result.size()-1; i >= 0; i -= 1) {
				System.out.println(result.get(i));
			}
		}
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
