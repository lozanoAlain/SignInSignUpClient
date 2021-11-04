package view;

import dataModel.Signable;
import dataModel.User;
import exceptions.ConnectionErrorException;
import exceptions.EmptyFieldsException;
import exceptions.FieldTooLongException;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotExistException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import static javafx.fxml.FXMLLoader.load;
import static javafx.fxml.FXMLLoader.load;
import static javafx.fxml.FXMLLoader.load;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.SignableFactory;

/**
 * This is the controller of the Sign In window
 *
 * @author Matteo Fernández
 */
public class SignInWindowController {

    @FXML
    private Stage stage;

    private Signable signable;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblPasswordError;

    @FXML
    private Label lblUsernameError;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Hyperlink hlkHere;

    private static final Logger logger = Logger.getLogger("package.class");

    //Getters and Setters
    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializates the main stage.
     *
     * @param root
     */
    public void initStage(Parent root) {
        //The login button (btnLogin) is enabled.
        //The username (txtUsername) and password (txtPassword) fields are enabled.
        //The here hyperlink (hlkHere) is enabled.
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
        //The window title
        stage.setTitle("Sign In Window");

        //The window (SignInWindow) is not resizable.
        stage.setResizable(false);

        //The username (txtUsername) field is focused.
        stage.setOnShowing(this::handleOnWindow);

        //The error labels (lblUsernameError and lblPasswordError) are not visible.
        lblPasswordError.setVisible(false);
        lblUsernameError.setVisible(false);

        //some tooltips to help the user
        btnLogin.setTooltip(new Tooltip("Click to log in"));
        hlkHere.setTooltip(new Tooltip("Click to go to the Sign up window and register"));

        SignableFactory signableFactory = new SignableFactory();
        try {
            signable = signableFactory.getSignable();
        } catch (Exception ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Shows stage
        stage.show();
        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Showing stage");
    }

    /**
     * Handels the login button, checks the username and password and shows
     * multiple errors if doesnt match the cryteria.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleBtnLoginPressed(ActionEvent event) throws IOException {
        try {
            //Check username and shows user errors
            checkIsNotEmpty(txtUsername, lblUsernameError);
            checkNoLonger255(txtUsername, lblUsernameError);
            //Check password and shows password error
            checkIsNotEmpty(txtPassword, lblPasswordError);
            checkNoLonger255(txtPassword, lblPasswordError);
            //Check that the user exist or not
            checkUserExist(txtUsername, txtPassword);
        } catch (Exception ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the hyperlink field.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void hlkHerePressed(ActionEvent event) throws IOException {
        //Opens the Sign Up window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpWindow.fxml"));

        //Creates a new stage
        Stage stageSignUp = new Stage();
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        SignUpController signUpController = ((SignUpController) loader.getController());

        //Set the stage that we already created to the sign up controller
        signUpController.setStage(stageSignUp);

        //Opening application as modal
        stageSignUp.initModality(Modality.APPLICATION_MODAL);
        stageSignUp.initOwner(
                ((Node) event.getSource()).getScene().getWindow());

        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
        signUpController.initStage(root);
    }

    /**
     * It comes after having pressed the Register button, in the Sign up Window
     *
     */
    public void initWhenSignUp() {
        //Creates a new user object
        User user = new User();
        //  user=comoSeLlameLaFuncionDeEllosQueMeTraeElUser(user);
        if (user.getLogin().isEmpty()) {
            //viene de btnBack
            txtUsername.setText("");
            txtPassword.setText("");
        }
        //Fields are completed with the information brought in from the Sign Up window.
        txtUsername.setText(user.getLogin());
        txtPassword.setText(user.getPassword());
        //The login button (btnLogin) is focused.
        stage.setOnShowing(this::handleOnWindowSignUp);
    }

    /**
     * Checks the field is no longer than 255 characters
     *
     * @param text
     * @param lblError
     * @exception FieldTooLongException
     */
    private void checkNoLonger255(TextField text, Label lblError) {
        // If the field is longer than 255, an error label is shown.
        if (text.getLength() > 255) {
            try {
                lblError.setVisible(true);
                throw new FieldTooLongException();
            } catch (FieldTooLongException ex) {
                //The error label is shown
                lblError.setText(ex.getMessage());
                logger.warning(ex.getMessage());
            }
        }
    }

    /**
     * Check that the field is not empty
     *
     * @param text
     * @param lblError
     * @exception EmptyFieldsException
     */
    private void checkIsNotEmpty(TextField text, Label lblError) {
        // If the fields are empty, an error label is shown.
        if (text.getText().trim().isEmpty()) {
            try {
                lblError.setVisible(true);
                throw new EmptyFieldsException();

            } catch (EmptyFieldsException ex) {
                //The error label is shown
                lblError.setText(ex.getMessage());
                logger.warning(ex.getMessage());
            }
        }
    }

    /**
     *
     * @param txtUsername
     * @param txtPassword
     * @exception UserNotExistException
     */
    private void checkUserExist(TextField txtUsername, PasswordField txtPassword) throws Exception {
        try {
            User user = new User();
            user.setLogin(txtUsername.getText());
            user.setPassword(txtPassword.getText());
            signable.signIn(user);
            openWelcomeWindow();

        } catch (UserNotExistException ex) {
            //shows an alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("User does not exist");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Ooops, there was an error! Try to write a valid username.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
            lblPasswordError.setDisable(true);
            lblUsernameError.setDisable(true);
        } catch (ConnectionErrorException ex) {
            //shows an alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Conection error");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Ooops, there was an error! Impossible to conect to the server.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
    }

    /**
     * Focus the username field.
     *
     * @param event
     */
    public void handleOnWindow(WindowEvent event) {
        //The username (txtUsername) field is focused.
        txtUsername.requestFocus();
    }

    /**
     * Focus the login button.
     *
     * @param event
     */
    public void handleOnWindowSignUp(WindowEvent event) {
        //The login button (btnLogin) is focused.
        btnLogin.isFocused();
    }

    private void openWelcomeWindow() {
        try {
            //Opens the Welcome window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeWindowController.fxml"));
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            WelcomeWindowController welcomeWindowController = ((WelcomeWindowController) loader.getController());

            welcomeWindowController.setStage(stage);

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            welcomeWindowController.initStage(root, txtUsername.getText());

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
