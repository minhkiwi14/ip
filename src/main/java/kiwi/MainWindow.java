package kiwi;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kiwi kiwiChatbot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Eyes.png"));
    private Image kiwiImage = new Image(this.getClass().getResourceAsStream("/images/Kiwi.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Kiwi instance
     */
    public void setKiwi(Kiwi k) {
        kiwiChatbot = k;
        String greeting = kiwiChatbot.getGreeting();
        assert greeting != null;
        dialogContainer.getChildren().add(DialogBox.getKiwiDialog(greeting, kiwiImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kiwiChatbot.getResponse(input);

        assert response != null;

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKiwiDialog(response, kiwiImage)
        );
        userInput.clear();
        if (response.contains("Bye. Hope to see you again soon!")) {
            // Solution below adapted from https://github.com/nus-cs2103-AY2425S2/forum/issues/160
            new Timer(true).schedule(new TimerTask() {
                public void run() {
                    Platform.exit();
                }
            }, 3000);
        }
    }
}

