/* Words.java */
package model;

/**
 * This class contains two methods: one constructor for taking a string and
 * another method for splitting the words up into a StringArray.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class Words {

	private String textReturn;

	/**
	 * This constructor takes a String.
	 * 
	 * @param textReturn a string that contains the text.
	 */
	public Words(String textReturn) {
		this.textReturn = textReturn;
	}

	/**
	 * This method splits textReturn into words contained in a StringArray.
	 * 
	 * @return a string array where each index contains a word
	 */
	public String[] getWords() {
		String[] choppedText = textReturn.split(" ");
		return choppedText;
	}
}