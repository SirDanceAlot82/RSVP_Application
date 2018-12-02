/* OnWordChangeListener.java */
package model;

/**
 * This interface acts as a listener used by WordChanger to notify RSVPView
 * whenever a word has changed or whenever the textflow has been completed.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-20
 */
public interface OnWordChangeListener {
	void onWordChange(String previousWord, String currentWord, String nextWord);

	void onWordChangeComplete();
}