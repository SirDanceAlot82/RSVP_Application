/* RSVPApplication.java */
package view;

import controller.ConfigurationController;
import controller.RSVPController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.RSVPStatus;

/**
 * This class is the main file for starting an RSVP session. The
 * class is able to switch between two views: a view for configuring
 * an RSVP session and another view for playing the configured 
 * RSVP session.
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class RSVPApplication extends Application {

	private Stage stage;
	private double width;
	private double height;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
	    this.stage = stage;
	    
	    switchToConfigurationMode();
        stage.show();
    }

	/**
	 * This method is called in the start-method and works
	 * as the initial view. It is also called from the RSVPController
	 * when the "home" button in RSVPView is clicked.
	 */
	public void switchToConfigurationMode() {
        ConfigurationView view = new ConfigurationView();     
	    
	    // Creates controller object and initializes view
	    ConfigurationController controller = new ConfigurationController(this, view);
        controller.initializeView();    
        
        // Initalizes scene with view and css rules for it to follow
        width = 800; height = 400; 
        Scene scene = new Scene(view, width, height); 
		scene.getStylesheets().add("files/stylesheet.css");
        
        stage.setScene(scene);    
    }

	/** 
	 * This method is called by the ConfigurationController when a speed 
	 * and a file with words have been selected.
	 * @param words a string array with words
	 * @param wpm speed in words per minute
	 */
	public void switchToRSVPMode(String[] words, int wpm) {
	    RSVPView view = new RSVPView(); 
        
        // Creates and initializes RSVPStatus with words and wpm
        RSVPStatus rsvpStatus = new RSVPStatus();
        rsvpStatus.setWords(words);
        rsvpStatus.setCurrentWpm(wpm);
        
        // Creates controller and lets the controller know the view, 
        // rsvpStatus, and RSVPApplication
	    RSVPController controller = new RSVPController(view, rsvpStatus);      
	    controller.setRSVPApplication(this);
	    controller.initializeView(); // Initializes the RSVPView
	    
	    Scene scene = new Scene(view, width, height);
	    scene.getStylesheets().add("files/stylesheet.css");
        
	    stage.setScene(scene);
	    
	    // Specifies what happens when app is closed
    	stage.setOnCloseRequest((event) -> {
	        controller.stopRunningTimer(); // Lambda expression, EventHandler<WindowEvent>
		});
	}
}
