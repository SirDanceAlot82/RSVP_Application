/* ConfigurationView.java */
package view;

import java.io.File;
import java.io.IOException;

import controller.ConfigurationController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.TextFileReader;
import model.Words;

/**
 * This class works as the initial view, the ConfigurationView. Here, the user
 * enters their text of choice and initial reading settings.
 * 
 * @author Erik Lewis Åkerman
 * @since 2018-11-19
 */
public class ConfigurationView extends VBox {

	private TextField selectFileTextField;
	private Spinner<Integer> spinner;
	private ConfigurationController controller;

	/**
	 * Initializes the VBox-layout, stacking three rows of components on top of each
	 * other. The first row of components contains a field and button for selecting
	 * a file with words. Second row contains a spinner for selecting wpm speed and
	 * last row contains the button to start switch view to RSVPView.
	 */
	public void initializeView() {
		// Creates three rows (3 HBoxes)
		HBox selectFileHBox = getSelectFileHBox();
		HBox selectWpmHBox = getSelectWpmHBox();
		HBox buttonBox = getStartButtonBox();

		// Adds the created rows to the VBox-layout
		getChildren().add(selectFileHBox);
		getChildren().add(selectWpmHBox);
		getChildren().add(buttonBox);

		// Adds "invisible" borders
		setPadding(new Insets(20));
		setSpacing(10); // Adds 10 px spacing in-between each row (= child)
	}

	/**
	 * Initializes a form with three components: one Label, one TextField, and one
	 * Button. The user is able to select a text file containing words.
	 */
	private HBox getSelectFileHBox() {
		// Initializes a Label
		Label label = new Label("Text file (txt):");
		label.setPrefWidth(200); // sätt bredd på label

		// Initializes a TextField
		selectFileTextField = new TextField();
		selectFileTextField.setPrefWidth(400); // sätt bredd på textfältet
		selectFileTextField.setPromptText("Default file (otherwise enter file path)"); // här sätter vi en hjälptext

		// Initializes a Button and adds an event handler
		Button button = new Button("Select file");
		button.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				String filepath = openFileChooser();
				if (filepath != null) {
					// Updates textfield with selected file's file path
					selectFileTextField.setText(filepath);
				}
			}
		});

		// Adds the created components to the HBox-layout
		HBox hbox = new HBox(10);
		hbox.getChildren().add(label);
		hbox.getChildren().add(selectFileTextField);
		hbox.getChildren().add(button);

		return hbox;
	}

	/**
	 * This method handles the user's choice of text to be read with RSVP.
	 */
	private String openFileChooser() {
		// Creates a Filechooser which allows the user to choose a text
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select text file");

		// Stores the selected file
		File selectedFile = fileChooser.showOpenDialog(getScene().getWindow()); // i vårt fall eftersom vi inte har
																				// stage

		// Checks whether a file has been chosen
		if (selectedFile != null) {
			try {
				return selectedFile.getCanonicalPath(); // filens absoluta sökväg
			} catch (IOException e) {
				// Do nothing...
			}
		}
		return null; // If no file has been found
	}

	/**
	 * This method initializes an HBox with three components: two labels and a
	 * spinner.
	 */
	private HBox getSelectWpmHBox() {
		// Initializes a Label
		Label label = new Label("Select read speed:");
		label.setPrefWidth(200);

		// Creates an integer spinner
		spinner = new Spinner<>(50, 700, 300, 50); // min, max, initial value, step size

		// Initializes a Label
		Label wpmLabel = new Label("Words per minute (wpm)");

		// Adds the created components to the HBox-layout
		HBox hbox = new HBox(10);
		hbox.getChildren().add(label);
		hbox.getChildren().add(spinner);
		hbox.getChildren().add(wpmLabel);

		return hbox;
	}

	/**
	 * This method initializes an HBox containing a button.
	 */
	private HBox getStartButtonBox() {
		// Initializes a Button with an EventHandler. When the
		// button is clicked the onStartButtonClick() triggers.
		Button startBtn = new Button("Start");
		startBtn.setPrefSize(200, 40);
		startBtn.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				onStartButtonClick();
			}
		});

		// Adds the start button to the HBox-layout
		HBox hbox = new HBox();
		hbox.getChildren().add(startBtn);
		hbox.setPadding(new Insets(200, 0, 0, 0));
		hbox.setAlignment(Pos.CENTER);

		return hbox;
	}

	/**
	 * This method handels the event which occurs whenever the start button is
	 * clicked. When the words have been extracted from the selected file and the
	 * spinner's value has been stored the controller changes the view from
	 * ConfigurationView to RSVPView.
	 */
	private void onStartButtonClick() {
		String[] words = readWordsFromTextFile();

		// Gets current value of the spinner
		int spinnerWpm = spinner.getValue();

		// Switches view
		controller.switchView(words, spinnerWpm);
	}

	/**
	 * This method reads the String from the selectedFilePath, reads the selected
	 * file, divides the file content to an StringArray and returns the StringArray.
	 */
	private String[] readWordsFromTextFile() {
		// Reads first text field
		String selectedFilePath = selectFileTextField.getText();
		if (selectedFilePath == null || selectedFilePath.equals("")) {
			selectedFilePath = "src/files/natgeo.txt";
		}

		// Reads text file and returns its content
		File file = new File(selectedFilePath);
		TextFileReader reader = new TextFileReader(file);
		String content = reader.readFile();

		// Divides the file content to an array of strings (words)
		Words words = new Words(content);
		String[] arrayOfWords = words.getWords();
		return arrayOfWords;
	}

	/**
	 * A set-method used by the ConfigurationController in order for the
	 * ConfigurationView to know about the controller.
	 */
	public void setController(ConfigurationController controller) {
		this.controller = controller;
	}
}