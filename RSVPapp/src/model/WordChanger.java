/* WordChanger.java */
package model;

import java.util.Timer;

/**
 * This class handles the logic behind the process of continuously switching
 * between two words with a certain speed, measured in words per minute. This is
 * done by using Timer and a TimerTask (WordsManager).
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class WordChanger {

	// Variables specfifying how the array with words should be traversed
	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	private String[] words;
	private int wordsPerMinute;
	private int currentIndex;
	private Timer timer;

	private WordsManager wordsManager;
	private OnWordChangeListener onWordChangeListener;

	/**
	 * This constructor setting the speed of changing words.
	 * 
	 * @param wordsPerMinute speed in words per minute
	 */
	public WordChanger(int wordsPerMinute) {
		this.wordsPerMinute = wordsPerMinute;
	}

	/**
	 * This method sets the string as an Array.
	 * 
	 * @param words text as a stringArray
	 */
	public void setWords(String[] words) {
		this.words = words;
	}

	/**
	 * This method sets the speed and if this class is already switching words when
	 * this method is called, the Timer restarts with the updated speed.
	 * 
	 * @param wordsPerMinute speed in words per minute
	 */
	public void setWordsPerMinute(int wordsPerMinute) {
		this.wordsPerMinute = wordsPerMinute;

		// Checks if class is already switching words
		if (isChangingWords()) {
			start();
		}
	}

	/**
	 * This method returns whether wordsManager (TimerTask) is paused.
	 * 
	 * @return true if wordsManager is not paused
	 */
	public boolean isChangingWords() {
		if (wordsManager == null || wordsManager.isPaused()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method checks to see if a task is running or not. If the a task already
	 * is running, the task will be stopped and then started with the method
	 * startChangingWords().
	 */
	public void start() {
		// Check whether task is running or not
		if (timer == null) {
			startChangingWords();
		}
		// When paused or when a new wpm is set
		else {
			stopChangingWords();
			startChangingWords();
		}
	}

	/**
	 * This method starts changing words by creating a Timer object and a belonging
	 * TimerTask (WordsManager).
	 */
	private void startChangingWords() {
		// Initializes and sets a wordsManager with the current index.
		wordsManager = new WordsManager(words);
		wordsManager.setStartIndex(currentIndex);

		// Adds a listener to the WordsManager
		wordsManager.setWordsManagerListener(new WordsManagerListener() {
			// This method is called from within wordsManager whenever a
			// counter has been incremented
			@Override
			public void onIndexChange(int newIndex) {
				currentIndex = newIndex;
				changeWord(newIndex);
			}

			// This method is called from within wordsManager when the
			// textflow is complete.
			@Override
			public void onComplete() {
				stopChangingWords();
			}
		});

		// Converts wpm to ms
		int millisecondsPerWord = convertToMillisecondsPerWord(wordsPerMinute);

		// Creates and starts timer
		timer = new Timer();
		timer.schedule(wordsManager, 0, millisecondsPerWord);
	}

	/**
	 * This method stops the textflow by setting the wordsManager to its default
	 * setting (null). This method also first terminates the timer and then and
	 * removes all cancelled tasks.
	 */
	public void stopChangingWords() {
		wordsManager = null;
		if (timer != null) {
			timer.cancel(); // Terminates timer and discards scheduled tasks
			timer.purge(); // Removes all cancelled tasks
			timer = null;
		}
	}

	/**
	 * This method takes words per minute(wpm) and calculates milliseconds per word.
	 * 
	 * @param wordsPerMinute an integer with number of words per minute
	 * @return milliseconds per word
	 */
	private int convertToMillisecondsPerWord(int wordsPerMinute) {
		double wordsPerMillisecond = wordsPerMinute / 60.0 / 1000.0;
		System.out.println(wordsPerMillisecond + " words/ms");

		int millisecondsPerWord = (int) (1.0 / wordsPerMillisecond);
		System.out.println(millisecondsPerWord + " ms/word");
		return millisecondsPerWord;
	}

	/**
	 * This method pauses the wordsManager task. The timer will still be running but
	 * textflow will not.
	 */
	public void pause() {
		wordsManager.pause();
	}

	/**
	 * This method changes the textflow by one index in either left or right
	 * direction.
	 * 
	 * @param direction how the textflow should be run
	 */
	public void changeWordManually(int direction) {
		if (direction == LEFT) {
			if (currentIndex < words.length - 1) {
				currentIndex += 1;
			}
		} else if (direction == RIGHT) {
			if (currentIndex > 0) {
				currentIndex -= 1;
			}
		}
		changeWord(currentIndex);
	}

	/**
	 * This method changes the word presented in the three labels in RSVPView:
	 * previousWords, currentWord and nextWord.
	 * 
	 * @param newIndex the index corresponding to the word that is presented in
	 *                 currentWord
	 */
	private void changeWord(int newIndex) {
		// The word that is presented in label previousWord in RSVPView
		String previousWord;
		if (newIndex == 0) {
			previousWord = null;
		} else {
			previousWord = words[newIndex - 1];
		}

		// The word that is presented in label currentWord in RSVPView
		String currentWord = words[newIndex];

		// The word that is presented in label nextWord in RSVPView
		String nextWord;
		if (newIndex >= words.length - 1) {
			nextWord = null;
		} else {
			nextWord = words[newIndex + 1];
		}

		// Let the listener object know that a word has been changed
		onWordChangeListener.onWordChange(previousWord, currentWord, nextWord);
	}

	/**
	 * This method is used to set a listener object which is used to report whenever
	 * a word has been changed.
	 * 
	 * @param onWordChangeListener listener object used when changing words
	 */
	public void setOnWordChangeListener(OnWordChangeListener onWordChangeListener) {
		this.onWordChangeListener = onWordChangeListener;
	}

	/**
	 * This method returns the index corresponding to the word presented in the
	 * label currentWord in RSVPView.
	 * 
	 * @return the current index
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
}