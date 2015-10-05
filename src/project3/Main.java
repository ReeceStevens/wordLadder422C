/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Reece Stevens
 * rgs835
 * <16340>
 * <Stephen Tran>
 * <set896>
 * <16340>
 * Slip days used: <0>
 * Fall 2015
 */


package project3;
import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String QUIT = "/quit"; // to compare to the /quit close command
		
		while (true) { // continues until /quit
			String input = kb.next(); // prompts user input
			if (QUIT.compareTo(input) == 0) { break; } // close scanner; terminate
			// any other /command print: invalid command /command and also /quit command
			if (input.charAt(0) == '/') {
					System.out.println("invalid command " + input);
					continue;
			}
			
			// a <N>-rung word ladder exists between <start> and <finish> with N being size() - 2
			// (word ladder size does not include start and end)
			else {	
				String inputEnd = kb.next(); // now we have the two words...
				if (getWordLadder(input, inputEnd).size() == 0){ // no word ladder can be found between <start> and <finish>.
					System.out.println("no word ladder can be found between " + input + " and " + inputEnd + ".");
				} else {
					ArrayList<String> myList = getWordLadder(input, inputEnd);
					System.out.println("a " + (myList.size()-2) + "-rung word ladder exists between " + input + " and " + inputEnd);
					// size() - 2 because the word ladder size does not count start and end but the ArrayList will print and count them
					for (String s : myList) { 
						System.out.println(s);
					}
				}
			}
	
		}
		kb.close(); // close after command /quit
		return;
		
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

    /*
     * findConnections(input, dictionary)
     *
     * Finds all valid words that are a one-letter
     * permutation from the input and returns them
     * in an ArrayList.
     *
     * @param   input   The word to be permutated
     * @param   dictionary  The list of all valid words
     *
     * @return  All one-letter permuations of input into valid words
     */
	public static ArrayList<String> findConnections(String input, Set<String> dictionary) {
		ArrayList<String> connections = new ArrayList<String>();
		for (String s : dictionary) {
			// If we haven't already visited it and it's valid, add it to connections
			if (diffByOne(input, s))  { connections.add(s);}
		}
		return connections;
	}


    /*
     * wordLadder(start,end,dictionary)
     *
     * A recursive function that finds a word ladder between
     * start and end, based on the valid words in dictionary.
     *
     * @param   start   The beginning of the word ladder
     * @param   end     The end of the word ladder
     * @param   dictionary  The list of all valid words
     *
     */
	public static ArrayList<String> wordLadder(String start, String end, Set<String> dictionary) {
		ArrayList<String> connections = findConnections(start, dictionary);

		// Base Case 1: Word is directly connected to end.
		if (connections.contains(end)) {
			ArrayList<String> final_path = new ArrayList<String>();
			final_path.add(end);
			final_path.add(start);
			return final_path;
		}

		// Base Case 2: We can't find any path to result.
		if (connections.isEmpty()) { 
				return null; 
		}

        // Mark nodes as visited by removing them from the list
        // of valid words.
		dictionary.removeAll(connections);

		// General Case:
        // Pick the shortest word ladder of this words's connections
        // and continue it by appending start.
		int min_len = dictionary.size();
		String min_string = null;
		ArrayList<String> min_path = new ArrayList<String>();
		min_path = null;
		for (String s : connections) {

			ArrayList<String> path = wordLadder(s,end,dictionary);
	        // If this is a path to the word, check how long it is.	
			if (path != null) {	
				if (path.size() < min_len) {
					min_path = path;
					min_len = path.size();
					min_string = s;
				}
			}
			else { continue; }
		}

        // If no paths were found, signal back with null
		if (min_path == null) { return null; }

        // Otherwise, add the smallest route back so as to 
        // allow doubling back on its trail.
		dictionary.add(min_string);
		min_path.add(start);
		return min_path;
	}

    /*
     * getWordLadder(start,end)
     *
     * A function that determines the word ladder between start and end. 
     * Prepares the dictionary and produces a final output array.
     *
     * @param   start   The word at the start of the ladder
     * @param   end     the word at the end of the ladder
     *
     * @return  An ArrayList containing the word ladder, ordered from first to last.
     */
	public static ArrayList<String> getWordLadder(String start, String end) {
		ArrayList<String> ret_val = new ArrayList<String>();	

		// Catch if words are not of same length
		if (start.length() != end.length()) { return ret_val; }	
		Set<String> dict = makeDictionary();

		
		start = start.toUpperCase();
		end = end.toUpperCase();

		ArrayList<String> result = wordLadder(start, end, dict); 
		if (result == null) { return ret_val; }
		
		// Catch if starting and ending word are the same
		if (start.equals(end)) { return ret_val; }
		ArrayList<String> flipped_result = new ArrayList<String>();
		flipped_result.add(start.toLowerCase());
		for (int i = result.size()-2; i >= 0; i -= 1) { // print ArrayList in lower case
			flipped_result.add(result.get(i).toLowerCase());	
		}
		return flipped_result;
	}
	
    /*
     * makeDictionary()
     *
     * Imports words from a text file and stores them as a 
     * HashSet.
     *
     * @return  Set to contain the words in the dictionary.
     *
     */
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
