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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

        txtUsername.textProperty().addListener(this::txtUsernameChanged255);
        txtUsername.textProperty().addListener(this::txtUsernameEmpty);
        txtPassword.textProperty().addListener(this::txtPasswordChanged255);
        txtPassword.textProperty().addListener(this::txtPasswordEmpty);
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
     * Check that the Username field (txtUsername) is no longer than 255
     * characters (checkNoLonger255()). If it is not correct
     * (FieldTooLongException), an error label (lblUsernameError) is shown.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void txtUsernameChanged255(ObservableValue observable, String oldValue, String newValue) {
        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                checkNoLonger255(txtUsername, lblUsernameError);
            }
        } catch (FieldTooLongException ex) {
            //The error label is shown
            btnLogin.setDisable(true);
            lblUsernameError.setVisible(true);
            errorLabel(lblUsernameError, ex);
            logger.warning(ex.getMessage());
        }
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void txtUsernameEmpty(ObservableValue observable, String oldValue, String newValue) {

        if (!newValue.equalsIgnoreCase(oldValue)) {
            checkEmptyFields(txtUsername, lblUsernameError);
        }
    }

    /**
     * Check that the Password field (txtPassword) is no longer than 255
     * characters (checkNoLonger255()).
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void txtPasswordChanged255(ObservableValue observable, String oldValue, String newValue) {
        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                checkNoLonger255(txtPassword, lblPasswordError);
            }
        } catch (FieldTooLongException ex) {
            //The error label is shown   
            btnLogin.setDisable(true);
            lblPasswordError.setVisible(true);
            errorLabel(lblPasswordError, ex);
            logger.warning(ex.getMessage());
        }
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void txtPasswordEmpty(ObservableValue observable, String oldValue, String newValue) {

        if (!newValue.equalsIgnoreCase(oldValue)) {
            checkEmptyFields(txtPassword, lblPasswordError); //The error label is shown
        }
    }

    /**
     * Handels the login button, checks the username and password and shows
     * multiple errors if doesnt match the cryteria. If the User exists, the
     * Welcome window (WelcomeWindow) is opened as modal. And the Sign In window
     * (SignInWindow) is closed.
     *
     * @param event
     */
    @FXML
    public void handleBtnLoginPressed(ActionEvent event) {

        try {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Log in button pressd.");

            checkEmptyFields(txtUsername, lblUsernameError);
            checkEmptyFields(txtPassword, lblPasswordError);

            //Check that the user exist or not
            if (lblUsernameError.isVisible() || lblPasswordError.isVisible()) {
                throw new EmptyFieldsException();
            } else if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                throw new EmptyFieldsException();
            } else {
                checkUserExist(txtUsername, txtPassword);
            }
        } catch (ConnectionErrorException ex) {
            //shows an alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Conection error");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Ooops, there was an error! Impossible to conect to the server.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
        } catch (EmptyFieldsException ex) {
            logger.severe(ex.getMessage());
        }

    }

    /**
     * Handles the hyperlink field.
     *
     * @param event
     */
    @FXML
    public void hlkHerePressed(ActionEvent event) {
        try {
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

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * Check that the Username field (txtUsername) or Password field
     * (txtPassword) are no longer than 255 characters
     * (checkNoLonger255(TextField, Label)). If the field is longer
     * (FieldTooLongException), an error label (lblUsernameError) and
     * (lblPasswordError) is shown.
     *
     * @param text
     * @param lblError
     * @exception FieldTooLongException
     */
    private void checkNoLonger255(TextField text, Label lblError) throws FieldTooLongException {
        // If the field is longer than 255, an error label is shown.
        if (text.getLength() > 5) {

            //An error label is shown.
            lblError.setVisible(true);
            throw new FieldTooLongException();
        } else {
            lblError.setVisible(false);
        }
    }

    /**
     * Validate that the password entered in the Password field (txtPassword) is
     * correct, sending the User to the database through the
     * signable.signIn(user) method. If the password is not correct
     * (IncorrectPasswordException), an error label (lblPasswordError) is
     * displayed to the user.
     *
     * @param txtUsername
     * @param txtPassword
     * @throws ConnectionErrorException
     */
    private void checkUserExist(TextField txtUsername, PasswordField txtPassword) throws ConnectionErrorException {
        User user = null;
        try {
            user = new User();
            user.setLogin(txtUsername.getText());
            user.setPassword(txtPassword.getText());
            //sending the User to the database
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Sending the User to the database...");
            user = signable.signIn(user);
            //Opens the welcome window
            openWelcomeWindow(user);
        } catch (ConnectionErrorException ex) {
            //Shows an alert when the connection is imposible
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Conection error");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Ooops, there was an error! Impossible to conect to the server.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
        } catch (IncorrectPasswordException ex) {
            //Shows an alert when the password of the user does not match
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Password is incorrect");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Ooops, there was an error! Try to write a valid password.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
            lblPasswordError.setVisible(true);
            lblUsernameError.setVisible(false);
            lblPasswordError.setText(ex.getMessage());
        } catch (UserNotExistException ex) {
            //Shows an alert when the user does not exist
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("User does not exist");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Ooops, there was an error! Try to write a valid username.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
            lblPasswordError.setVisible(false);
            lblUsernameError.setVisible(false);
        } catch (Exception ex) {
            throw new ConnectionErrorException();
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

    private void openWelcomeWindow(User user) {
        try {
            //Opens the Welcome window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeWindow.fxml"));
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            WelcomeWindowController welcomeWindowController = ((WelcomeWindowController) loader.getController());

            welcomeWindowController.setStage(stage);

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            welcomeWindowController.initStage(root, user);

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void errorLabel(Label lbl, Exception ex) {
        lbl.setVisible(true);
        lbl.setText(ex.getMessage());
        logger.severe(ex.getMessage());
    }

    /*
   Check that the Username field (txtUsername) or Password field
     * (txtPassword) fields are not empty checkIsNotEmpty(TextField, Label). If
     * the fields are empty (EmptyFieldsException), an error label
     * (lblUsernameError) and (lblPasswordError) is shown
     *  */
    private boolean checkEmptyFields(TextField txt, Label lbl) {
        boolean check = false;
        if (txt.getText().isEmpty()) {
            try {
                check = true;
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                errorLabel(lbl, ex);
                logger.warning(ex.getMessage());
            }
        }
        return check;
    }
}
