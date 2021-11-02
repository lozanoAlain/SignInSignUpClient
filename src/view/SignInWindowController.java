/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dataModel.User;
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

/**
 * This is the controller of the Sign In window
 *
 * @author Matteo Fernández
 */
public class SignInWindowController {

    @FXML
    private Stage stage;

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
        //Check username and shows user errors
        checkIsNotEmpty(txtUsername, lblUsernameError);
        checkNoLonger255(txtUsername, lblUsernameError);
        //Check password and shows password error
        checkIsNotEmpty(txtPassword, lblPasswordError);
        checkNoLonger255(txtPassword, lblPasswordError);
        //Check that the user exist or not
        //  checkUserExist(txtUsername, txtPassword);
    }

    /**
     * Handles the hyperlink field. Opens the sign up window as modal.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void hlkHerePressed(ActionEvent event) throws IOException {
        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Closing Sign in stage.");
        stage.close();

        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing Sign Up stage.");
        //opens the Sign Up window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));

        Parent root = (Parent) loader.load();

        SignUpController controller = ((SignUpController) loader.getController());

        controller.setStage(stage);
        //put it as modal
        stage.initModality(Modality.WINDOW_MODAL);
        controller.initStage(root);
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
    /*    private void checkUserExist(TextField txtUsername, PasswordField txtPassword) {
    if (!signin(txtUsername, txtPassword)) {
    try {
    if (!txtUsername.getText().isEmpty()) {
    //clears the fields
    txtUsername.clear();
    txtPassword.clear();
    //throws the exception
    throw new UserNotExistException();
    }
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
    }
    } else {
    try {
    stage.close();
    //opens the Welcome window
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomeWindow.fxml"));
    Parent root = (Parent) loader.load();
    
    WelcomeWindowController welcomeWindowController = (loader.getController());
    welcomeWindowController.setStage(stage);
    
    //open as modal
    stage.initModality(Modality.WINDOW_MODAL);
    
    welcomeWindowController.initStage(root);
    
    } catch (IOException ex) {
    logger.warning(ex.getMessage());
    }
    }
    }*/
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

    /**
     *
     * @param txtUsername
     * @param txtPassword
     * @return
     * @exception IncorrectPasswordException
     */
    /* private boolean signin(TextField txtUsername, PasswordField txtPassword) {
    User user = new User();
    user.setLogin(txtUsername.getText());
    user.setPassword(txtPassword.getText());
    //llamar a la factoría de Signable para utilizar el metodo signIn(user);
    //cambiar, is empty NO
    if (!user.getPassword().isEmpty()) {
    try {
    lblPasswordError.setVisible(true);
    throw new IncorrectPasswordException();
    
    } catch (IncorrectPasswordException ex) {
    logger.warning(ex.getMessage());
    lblPasswordError.setText(ex.getMessage());
    txtPassword.setText("");
    txtUsername.setText("");
    }
    }
    return false;
    }*/
     }
