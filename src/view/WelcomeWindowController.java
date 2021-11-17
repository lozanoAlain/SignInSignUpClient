package view;

import dataModel.User;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 * FXML Controller class for the welome window
 *
 * @author Matteo Fernández
 */
public class WelcomeWindowController {

    @FXML
    private Stage stage;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogOut;

    @FXML
    private Label txtWelcome;

    private User user;

    //Getters and Setters
    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Get the User
     *
     * @param user
     */
    public void getUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the Sign in window
     *
     * @param root
     * @param user
     */
    public void initStage(Parent root, User user) {
        //Exit button (btnExit) and Log out button (btnLogOut) are enabled.
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Logger.getLogger(WelcomeWindowController.class.getName()).log(Level.INFO, "Initializing stage.");

        //The window title
        stage.setTitle("Welcome window");

        //The window (WelcomeWindow) is not resizable.
        stage.setResizable(false);

        //some tooltips to help the user
        btnExit.setTooltip(new Tooltip("Click to exit"));
        btnLogOut.setTooltip(new Tooltip("Click to log out (You will be redirected to log in window)"));

        //A text (txtWelcome) with the user's name is displayed.
        txtWelcome.setText("Hello " + user.getFullName());

        //Shows stage
        stage.show();
        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Showing stage");
    }

    /**
     * The welcome window (WelcomeWindow) is closed. This handles the event of
     * the exit button. Shows an alert to the user and verifies that they want
     * to exit or not.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleBtnExitPressed(ActionEvent event) throws IOException {
        //An alert is shown to the user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() != ButtonType.OK) {
            //Doesn´t want to exit
            this.btnExit.setText("Cancelling it...");
        } else {
            //They choose to exit
            this.btnExit.setText("Bye, thanks for using our app.");
            stage.close();
        }
        Logger.getLogger(WelcomeWindowController.class.getName()).log(Level.INFO, "Exit button pressed.");
        stage.close();
    }

    /**
     * The welcome window (WelcomeWindow) is closed and returns to the Sign In
     * window (SignInWindow). Handles de Log Out button.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleBtnLogOutPressed(ActionEvent event) throws IOException {
        //Shows an alert to the user and verifies that they want to exit or not.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to log out of the app?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() != ButtonType.OK) {
            //Doesn´t want to exit
            this.btnLogOut.setText("Cancelling it...");
        } else {
            //They choose to log out
            this.btnLogOut.setText("Bye, thanks for using our app.");

            //Goes to the Sign in window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInWindow.fxml"));
            Parent root = (Parent) loader.load();

            SignInWindowController signInWindowController = (loader.getController());
            signInWindowController.setStage(stage);
            signInWindowController.initStage(root);
        }
    }
}
