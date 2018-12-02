/* TextFileReader.java */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This klass handles reading a textfile and storing the text as a string.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class TextFileReader {

	private File file;
	private Scanner scan; // Obs. Default value for objects are null

	/**
	 * This constructor "takes" the file and then calls the initializeScanner
	 * method.
	 * 
	 * @param file a textfile chosen by the user
	 */
	public TextFileReader(File file) {
		this.file = file;
		initializeScanner();
	}

	/**
	 * This method initializes the scanner object based on the selected file.
	 */
	private void initializeScanner() {
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scans the text and stores and returns the text as a string.
	 * 
	 * @return a string containing the file's content
	 */
	public String readFile() {
		String result = "";
		String originalText = null;

		// Traverse the file until stop
		while (scan.hasNextLine()) {
			originalText = scan.nextLine(); // Stores line in originalText
			result = result + originalText + " "; // Adds originalText to result
		}
		scan.close(); // Closes scanner

		return result;
	}
}