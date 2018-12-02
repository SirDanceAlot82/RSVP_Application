/* WordsManagerListener.java */
package model;

/**
 * This interface acts as a listener used by WordsManager to notify WordChanger
 * whenever the counter in the TimerTask's run method changes or whenever the
 * counter has exceeded the length of a given array of words.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-20
 */
public interface WordsManagerListener {
	void onIndexChange(int newIndex);

	void onComplete();
}