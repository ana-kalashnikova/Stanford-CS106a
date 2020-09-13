/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class HangmanLexicon {

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		BufferedReader rd = openFile();
		readFile(rd);
		return lexicon.size();
	}
	
	private BufferedReader openFile() {
		BufferedReader rd = null;
		while(rd == null) {
			try {
				rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			}catch(IOException ex) {
				throw new ErrorException(ex);
			}
		}
		return rd;
	}
	private void readFile(BufferedReader rd) {
		while (true) {
			try {
				String word = rd.readLine();
				if (word == null) break;
				lexicon.add(word);
			}catch(IOException ex) {
				throw new ErrorException(ex);
			}
		}
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lexicon.get(index);
	}
	
	private ArrayList<String> lexicon = new ArrayList<String>();
}
