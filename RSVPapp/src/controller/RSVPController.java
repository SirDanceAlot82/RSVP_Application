/* RSVPController.java */
package controller;

import model.OnWordChangeListener;
import model.RSVPStatus;
import model.WordChanger;
import view.RSVPApplication;
import view.RSVPView;

/**
 * This class is responsible for initializing the RSVPView and for handling the
 * logic behind the textflow logic (starting, pausing, and changing speed). It
 * is also responsible for notifying RSVPApplication when
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-20
 */
public class RSVPController {

	private RSVPView view;
	private RSVPStatus rsvpStatus;
	private WordChanger wordChanger;

	private RSVPApplication rsvpApplication;

	public RSVPController(RSVPView view, RSVPStatus rsvpStatus) {
		this.view = view;
		this.rsvpStatus = rsvpStatus;
	}

	/**
	 * This method initialises the RSVPView meanwhile letting the view know about
	 * itself, in order for the view to be able to let the controller know when to
	 * switch view.
	 */
	public void initializeView() {
		view.setController(this);
		view.initializeView();
	}

	/**
	 * This method gets the current reading speed, measured in words per minute.
	 * 
	 * @return current reading speed
	 */
	public int getCurrentWpm() {
		return rsvpStatus.getCurrentWpm();
	}

	/**
	 * This method controls if the start or pause image should be displayed.
	 * 
	 * @return the path to the image
	 */
	public String getStartPauseImagePath() {
		String imagePath = "/files/start.png";

		// if the wordChanger object is NOT null AND words are changing the
		// path i set to the pause image
		if (wordChanger != null && wordChanger.isChangingWords()) {
			imagePath = "/files/pause.png";
		}
		return imagePath;
	}

	/**
	 * This method gets the word that is stored in the leftWord label in RSVPView.
	 * 
	 * @return word that has been read
	 */
	public String getLeftWord() {
		return rsvpStatus.getLeftWord();
	}

	/**
	 * This method gets the word that is stored in the centerWord label in RSVPView.
	 * 
	 * @return word that is currently being read
	 */
	public String getCenterWord() {
		return rsvpStatus.getCenterWord();
	}

	/**
	 * This method gets the word that is stored in the rightWord label in RSVPView.
	 * 
	 * @return word that is going to be read
	 */
	public String getRightWord() {
		return rsvpStatus.getRightWord();
	}

	/**
	 * This method moves the text to the right if the left button has been clicked.
	 */
	public void onLeftButtonClicked() {
		// Check whether the wordChanger has already been initialized
		if (wordChanger == null) {
			initializeWordChanger();
		}
		wordChanger.changeWordManually(WordChanger.RIGHT);
	}

	/**
	 * This method creates a WordChanger object and initializes it with a string
	 * array with words and a reading speed. It also defines what happens when a
	 * word is changed, meaning the controller will update the view when that
	 * happens.
	 */
	private void initializeWordChanger() {
		wordChanger = new WordChanger(rsvpStatus.getCurrentWpm());
		wordChanger.setWords(rsvpStatus.getWords());

		wordChanger.setOnWordChangeListener(new OnWordChangeListener() {
			@Override
			public void onWordChange(String previousWord, String currentWord, String nextWord) {
				// Update view
				view.showWords(previousWord, currentWord, nextWord);

				double part = wordChanger.getCurrentIndex() + 1;
				double whole = rsvpStatus.getWords().length;
				int completePercentage = (int) ((part / whole) * 100);
				view.updateCompleteBarLabel(completePercentage);
			}

			@Override
			public void onWordChangeComplete() {
				view.updateStartPauseButton(getStartPauseImagePath());
			}
		});
	}

	/**
	 * This method moves the text to the left if the right button has been clicked.
	 */
	public void onRightButtonClicked() {
		// Check whether the wordChanger has already been intitialized
		if (wordChanger == null) {
			initializeWordChanger();
		}
		wordChanger.changeWordManually(WordChanger.LEFT);
	}

	/**
	 * This method decreases the speed of the textflow by 50 wpm if the "-50 wpm"
	 * button is clicked.
	 */
	public void onDecreaseButtonClicked() {
		// Check whether the wordChanger has already been intitialized
		if (wordChanger == null) {
			initializeWordChanger();
		}

		int currentWpm = rsvpStatus.getCurrentWpm();
		// Only decrease wpm if current wpm is greater than 50 (eg 100)
		if (currentWpm > 50) {
			currentWpm -= 50;
			rsvpStatus.setCurrentWpm(currentWpm);
			wordChanger.setWordsPerMinute(currentWpm);
		}

		// Update view
		view.updateCurrentWpmLabel(currentWpm);
	}

	/**
	 * This method handles the logic when the start/pause button has been clicked.
	 * It either starts or pauses the wordChanger (textflow) and updates the
	 * button's image.
	 */
	public void onStartPauseButtonClicked() {
		// Check whether the wordChanger has already been initialized
		if (wordChanger == null) {
			initializeWordChanger();
		}

		// Check whether the wordChanger is paused
		if (!wordChanger.isChangingWords()) {
			wordChanger.start();
		} else {
			wordChanger.pause();
		}

		// Update the start-pause-button's image
		view.updateStartPauseButton(getStartPauseImagePath());
	}

	/**
	 * This method increases the speed of the textflow by 50 wpm if the "+50 wpm"
	 * button is clicked.
	 */
	public void onIncreaseButtonClicked() {
		if (wordChanger == null) {
			initializeWordChanger();
		}

		int currentWpm = rsvpStatus.getCurrentWpm();

		if (currentWpm < 700) {
			currentWpm += 50;
			rsvpStatus.setCurrentWpm(currentWpm);
			wordChanger.setWordsPerMinute(currentWpm);
		}

		view.updateCurrentWpmLabel(currentWpm);
	}

	/**
	 * This method stops the textflow.
	 */
	public void stopRunningTimer() {
		if (wordChanger != null) {
			wordChanger.stopChangingWords();
		}
	}

	/**
	 * This method stops the flow of words and returns to the configuration view.
	 */
	public void onHomeButtonClicked() {
		stopRunningTimer();
		rsvpApplication.switchToConfigurationMode();
	}

	/**
	 * This method stores an instance of the RSVPApplication in order for the
	 * controller let RSVPApplication know whenever the Home-button has been
	 * pressed.
	 * 
	 * @param rsvpApplication responsible for eg switching views
	 */
	public void setRSVPApplication(RSVPApplication rsvpApplication) {
		this.rsvpApplication = rsvpApplication;
	}
}