/* WordsManager.java */
package model;

import java.util.TimerTask;

/**
 * This class is used by the Timer object in WordChanger. The run method is
 * continuously called at regular intervals. The class is responsible for
 * incrementing a counter variable (i) as long it is not exceeding the length of
 * the array with words.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class WordsManager extends TimerTask {

	private String[] words;
	private int i;
	private boolean isPaused = false;

	private WordsManagerListener wordsManagerListener;

	/**
	 * This constructor sets the string array of words.
	 * 
	 * @param words a string array containing the words from the selected file
	 */
	public WordsManager(String[] words) {
		this.words = words;
	}

	/**
	 * This method is called at regular intervals by the Timer object defined in
	 * WordChanger.
	 */
	@Override
	public void run() {
		// Only executes if-statement if isPaused equals false
		if (!isPaused) {
			// If counter is index out of bounds, the textflow is completed
			if (i >= words.length - 1) {
				wordsManagerListener.onComplete();
			}

			// Let the listener object know that the counter has been changed
			wordsManagerListener.onIndexChange(i);
			i++;
		}
	}

	/**
	 * This method sets the start index, ie the index to start counting from.
	 * 
	 * @param index start index used in the run method
	 */
	public void setStartIndex(int index) {
		i = index;
	}

	/**
	 * This method sets the isPaused variable to false. Next time the run method is
	 * called the run method's if-statement will be executed.
	 */
	public void start() {
		isPaused = false;
	}

	/**
	 * This method sets the isPaused variable to true. Next time the run method is
	 * called the run method's if-statement will NOT be executed.
	 */
	public void pause() {
		isPaused = true;
	}

	/**
	 * This method is used to check if the WordsManager (TimerTask) is paused.
	 * 
	 * @return returns a boolen if timerTask is paused or not
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * This method is used to set a listener object which is used to report whenever
	 * the the class' counter i has been changed in the run method. In this app the
	 * listener is utilized in WordChanger.
	 * 
	 * @param wordsManagerListener listener object used when changing counter i
	 */
	public void setWordsManagerListener(WordsManagerListener wordsManagerListener) {
		this.wordsManagerListener = wordsManagerListener;
	}
}