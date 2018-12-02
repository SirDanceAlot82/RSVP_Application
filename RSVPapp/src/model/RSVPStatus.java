/* RSVPStatus.java */
package model;

/**
 * This class works as a model-class. It is responsible for keeping track of the
 * data, storing the latest input and keeping track of which word is in each
 * label in RSVPView.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class RSVPStatus {

	private int wpm;
	private String leftWord;
	private String centerWord;
	private String rightWord;
	private String[] words;

	public int getCurrentWpm() {
		return wpm;
	}

	public void setCurrentWpm(int wpm) {
		this.wpm = wpm;
	}

	public String getLeftWord() {
		return leftWord;
	}

	public void setLeftWord(String leftWord) {
		this.leftWord = leftWord;
	}

	public String getCenterWord() {
		return centerWord;
	}

	public void setCenterWord(String centerWord) {
		this.centerWord = centerWord;
	}

	public String getRightWord() {
		return rightWord;
	}

	public void setRightWord(String rightWord) {
		this.rightWord = rightWord;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}
}