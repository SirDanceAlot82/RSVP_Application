/* ConfigurationController.java */
package controller;

import view.ConfigurationView;
import view.RSVPApplication;

/**
 * This class is responsible for initializing the ConfigurationView and calling
 * RSVPApplication whenever its Scene needs to switch view.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class ConfigurationController {

	private RSVPApplication rsvpApplication;
	private ConfigurationView view;

	public ConfigurationController(RSVPApplication rsvpApplication, ConfigurationView view) {
		this.rsvpApplication = rsvpApplication;
		this.view = view;
	}

	/**
	 * This method initializes the ConfigurationView meanwhile letting the view know
	 * about itself, in order for the view to be able to let the controller know
	 * when to switch view.
	 */
	public void initializeView() {
		view.setController(this);
		view.initializeView();
	}

	/**
	 * This method switches view by calling the rsvpApplication object in order for
	 * the app to display the RSVP view.
	 * 
	 * @param words array of strings
	 * @param wpm   speed in words per minute
	 */
	public void switchView(String[] words, int wpm) {
		rsvpApplication.switchToRSVPMode(words, wpm);
	}
}