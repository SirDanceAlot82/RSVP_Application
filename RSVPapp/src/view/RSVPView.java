/* RSVPView.java */
package view;

import controller.RSVPController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class represents the RSVPView. Here, the user can read and control a
 * text of their choice with RSVP settings.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-20
 */
public class RSVPView extends VBox {

	private Label currentWpmLabel;
	private Label completeBarLabel;
	private Label leftWord;
	private Label centerWord;
	private Label rightWord;
	private Button startPauseButton;

	private RSVPController controller;

	/**
	 * This method initializes the VBox-layout, stacking four rows of components on
	 * top of each other. One row includes a layout containing the textflow.
	 */
	public void initializeView() {
		// Creates 4 rows(4 HBoxes)
		HBox currentWpmBox = getCurrentWpmBox();
		HBox completeBarBox = getCompleteBarBox();
		HBox readBox = getReadBox();
		HBox startPauseButtonBox = getStartPauseButtonBox();

		// Adds rows to the VBox-layout
		getChildren().add(currentWpmBox);
		getChildren().add(completeBarBox);
		getChildren().add(readBox);
		getChildren().add(startPauseButtonBox);

		// Adds "invisible" borders
		setPadding(new Insets(20));
		setSpacing(10); // 10 px between each row (child)

		initializeRSVPMode();
	}

	/**
	 * This methods sets the initial settings for a RSVP session.
	 */
	public void initializeRSVPMode() {
		// Sets the currentWpmLabel and completeBarLabel to initial value
		currentWpmLabel.setText(String.valueOf(controller.getCurrentWpm()));
		completeBarLabel.setText("0%");

		// Sets the path to the start/pause images and sets the image
		ImageView startPauseImageView = new ImageView(controller.getStartPauseImagePath());
		startPauseButton.setGraphic(startPauseImageView);

		leftWord.setText(controller.getLeftWord());
		centerWord.setText(controller.getCenterWord());
		rightWord.setText(controller.getRightWord());
	}

	/**
	 * This method creates the layout responsible for showing the current reading
	 * speed in words per minute and provides the user an option to click on a
	 * button to be redirected towards the initial view (ConfigurationView).
	 * 
	 * @return a HBox-layout
	 */
	private HBox getCurrentWpmBox() {
		currentWpmLabel = new Label();
		currentWpmLabel.setPrefWidth(650);
		Button homeButton = new Button("Home");

		// Adds an event handler
		homeButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				controller.onHomeButtonClicked();
			}
		});

		HBox hbox = new HBox();
		hbox.setSpacing(20);

		hbox.getChildren().add(currentWpmLabel);
		hbox.getChildren().add(homeButton);

		return hbox;
	}

	/**
	 * This method creates the layout that shows how much of the text, in
	 * percentage, has been read.
	 * 
	 * @return a HBox-layout
	 */
	private HBox getCompleteBarBox() {
		completeBarLabel = new Label();
		completeBarLabel.getStyleClass().add("bold-text"); // Sets css class

		Label ofTextReadLabel = new Label("of text read");

		HBox hbox = new HBox(6); // HBox with 6 px spacing between each child
		hbox.getChildren().add(completeBarLabel);
		hbox.getChildren().add(ofTextReadLabel);

		return hbox;
	}

	/**
	 * This method creates the layout showing the textflow, three different boxes
	 * with one word in each and two different buttons to manually control the
	 * textflow.
	 * 
	 * @return a HBox-layout
	 */
	private HBox getReadBox() {
		Button leftButton = new Button();
		leftButton.setPrefSize(25, 25);
		leftButton.setGraphic(new ImageView("/files/arrow_left.png"));

		// Adds an event handler to go backwards one word in the textflow
		leftButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				controller.onLeftButtonClicked();
			}
		});

		leftWord = new Label("One");
		leftWord.setPrefSize(200, 100);
		leftWord.setAlignment(Pos.CENTER);
		leftWord.getStyleClass().add("edge-words"); // Sets css class

		centerWord = new Label("Two");
		centerWord.setPrefSize(200, 100);
		centerWord.setAlignment(Pos.CENTER);
		centerWord.setId("center-word"); // Sets css id for centerWord

		rightWord = new Label("Three");
		rightWord.setPrefSize(200, 100);
		rightWord.setAlignment(Pos.CENTER);
		rightWord.getStyleClass().add("edge-words"); // Sets css class

		Button rightButton = new Button();
		rightButton.setPrefSize(25, 25);
		rightButton.setGraphic(new ImageView("/files/arrow_right.png"));

		// Adds an event handler to go forward one word in the textflow
		rightButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				controller.onRightButtonClicked();
			}
		});

		// Creates layout and adds the labels and buttons
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		hbox.setPadding(new Insets(30, 30, 30, 30));
		hbox.getChildren().add(leftButton);
		hbox.getChildren().add(leftWord);
		hbox.getChildren().add(centerWord);
		hbox.getChildren().add(rightWord);
		hbox.getChildren().add(rightButton);

		hbox.setAlignment(Pos.CENTER);

		return hbox;
	}

	/**
	 * This method creates the layout showing a panel for controlling the speed of
	 * the textflow. The user is able to increase and decrease the reading speed.
	 * The user is also able to pause the textflow.
	 * 
	 * @return a HBox-layout
	 */
	private HBox getStartPauseButtonBox() {
		Button decreaseButton = new Button("-50 wpm");
		decreaseButton.setPrefSize(100, 50);

		// Event handler for decreasing the reading speed
		decreaseButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				controller.onDecreaseButtonClicked();
			}
		});

		startPauseButton = new Button();
		startPauseButton.setPrefSize(100, 50);

		// Event handler for pausing and starting the textflow
		startPauseButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				controller.onStartPauseButtonClicked();
			}
		});

		Button increaseButton = new Button("+50 wpm");
		increaseButton.setPrefSize(100, 50);

		// Event handler for increasing the reading speed
		increaseButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				controller.onIncreaseButtonClicked();
			}
		});

		HBox hbox = new HBox();
		hbox.setSpacing(50); // HBox with 50 px horizontal spacing
		hbox.getChildren().add(decreaseButton);
		hbox.getChildren().add(startPauseButton);
		hbox.getChildren().add(increaseButton);

		hbox.setAlignment(Pos.CENTER);

		return hbox;
	}

	/**
	 * This method makes it possible for the view to know about the controller. The
	 * view can then notify the controller eg whenever a certain button has been
	 * pressed.
	 * 
	 * @param rsvpController controller responsible for the play-start logic
	 */
	public void setController(RSVPController rsvpController) {
		this.controller = rsvpController;
	}

	/**
	 * This method updates the content in each label used to represent the textflow.
	 * 
	 * @param previousWord word that has been read
	 * @param currentWord  word that is being read
	 * @param nextWord     word that is going to be read
	 */
	public void showWords(String previousWord, String currentWord, String nextWord) {
		// Need this because the gui (labels) is modified from a thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				leftWord.setText(previousWord);
				centerWord.setText(currentWord);
				rightWord.setText(nextWord);
			}
		});
	}

	/**
	 * This method updates the wpm label.
	 * 
	 * @param currentWpm an integer containing the current wpm
	 */
	public void updateCurrentWpmLabel(int currentWpm) {
		currentWpmLabel.setText(String.valueOf(currentWpm) + " wpm");
	}

	/**
	 * This method updates the label containing the info about the percentage of
	 * text read. Platform is needed because the label is modified (using
	 * setText-method) from within a thread (Timer and TimerTask in WordChanger).
	 * 
	 * @param completePercentage integer representing the percentage of text read
	 */
	public void updateCompleteBarLabel(int completePercentage) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				completeBarLabel.setText(completePercentage + "%");
			}
		});
	}

	/**
	 * This method updates the image shown on the start/pause button.
	 * 
	 * @param imagePath a string containing the path to the image
	 */
	public void updateStartPauseButton(String imagePath) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ImageView startPauseImageView = new ImageView(imagePath);
				startPauseButton.setGraphic(startPauseImageView);
			}
		});
	}
}