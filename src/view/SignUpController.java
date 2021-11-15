/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dataModel.Signable;
import dataModel.User;
import exceptions.ConnectionErrorException;
import exceptions.EmptyFieldsException;
import exceptions.ExistUserException;
import exceptions.FieldTooLongException;
import exceptions.FullNameException;
import exceptions.RepeatPasswordException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.SignableFactory;

/**
 * This is the controller of the Sign Up window
 *
 * @author Alain Lozano, Ilia Consuegra
 */
public class SignUpController {

    private Stage stage;

//Getters and Setters
    /**
     * Gets the stage
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage
     *
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Signable signable;

    private final static Logger logger = Logger.getLogger(SignUpController.class.getName());

    @FXML
    private TextField txtFullName;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblFullNameError;
    @FXML
    private ImageView imgCerebro;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField txtRepeatPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtMail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblMailError;
    @FXML
    private Label lblPasswordError;
    /*@FXML
    private Label lblPassword2Error;*/
    @FXML
    private Label lblRepeatPasswordError;

    private SignInWindowController signin;

    /*
    The fields Full name (txtFullName), Username (txtUsername), Mail (txtMail), Password (txtPassword) and Repeat password (txtRepeatPassword) are enabled.

    The Back (btnBack) and Register (btnRegister) buttons are enabled.

    The window is not resizable.
     */
    /**
     * The method that starts the Sign Up window
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        getStage().setScene(scene);
        getStage().setTitle("Sign Up Window");
        getStage().setResizable(false);

        stage.setOnShowing(this::handleWindowShowing);
        txtFullName.textProperty().addListener(this::fullNameTextChanged);
        txtUsername.textProperty().addListener(this::usernameTextChanged);
        txtMail.textProperty().addListener(this::mailTextChanged);
        txtPassword.textProperty().addListener(this::passwordTextChanged);
        txtRepeatPassword.textProperty().addListener(this::repeatPasswordTextChanged);
        btnBack.setOnAction(this::handleButtonBack);
        btnRegister.setOnAction(this::handleButtonRegister);

        SignableFactory signableFactory = new SignableFactory();
        try {
            signable = signableFactory.getSignable();
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }

        getStage().showAndWait();

    }

    // The Full name field (txtFullName) is focused.
    /**
     * Method that focus the text field txtFullName when the window is opened.
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        txtFullName.requestFocus();

        lblFullNameError.setVisible(false);
        lblMailError.setVisible(false);
        lblPasswordError.setVisible(false);
        lblRepeatPasswordError.setVisible(false);
        lblUsernameError.setVisible(false);

    }

    /*
    Check that the Full Name field (txtFullName) is no longer than 255 characters (checkNoLonger255()).
    If it is not correct (FieldTooLongException()), an error label (lblFullNameError) is shown and the register button(btnRegister is disabled.
     */
    /**
     * Method that checks the the length of the field
     *
     * @param observable
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    private void fullNameTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(txtFullName.getText(), lblFullNameError);
            }
        } catch (FieldTooLongException ex) {
            errorLabel(lblFullNameError, ex);
        }
    }

    /*

    Check that the Full name (txtFullName) has at least 1 blank (checkWhiteSpace())
    If it is not correct (FullNameException()), an error label (lblFullNameError) is shown and the register button(btnRegister is disabled.
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     * Method that checks the Full Name has at least one blank
     *
     * @param observable
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    /*
    Check that the Username field (txtUsername) is no longer than 255 characters (checkNoLonger255()).
    If it is not correct (FieldTooLongException()), an error label (lblUsernameError) is shown and the register button(btnRegister is disabled.
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     * Method that checks the the length of the field
     *
     * @param observable
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    private void usernameTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(txtUsername.getText(), lblUsernameError);
            }
        } catch (FieldTooLongException ex) {
            errorLabel(lblUsernameError, ex);
        }
    }

    /*
    Check that the Mail field (txtMail) is no longer than 255 characters (checkNoLonger255().
    If it is not correct (FieldTooLongException()), an error label (lblMailError) is shown and the register button(btnRegister is disabled.
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     * Method that checks the the length of the field
     *
     * @param observable
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    private void mailTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(txtMail.getText(), lblMailError);
            }
        } catch (FieldTooLongException ex) {
            errorLabel(lblMailError, ex);

        }
    }

    /*
    Check that the Password field (txtPassword) is no longer than 255 characters (checkNoLonger255()).
    If it is not correct (FieldTooLongException()), an error label (lblPasswordError) is shown and the register button(btnRegister is disabled.
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     * Method that checks the the length of the field
     *
     * @param observable
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    private void passwordTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(new String(txtPassword.getText()), lblPasswordError);
            }
        } catch (FieldTooLongException ex) {
            errorLabel(lblPasswordError, ex);

        }
    }

    /*
    Check that the Repeat Password field (txtRepeatPassword) is no longer than 255 characters (checkNoLonger255()).
    If it is not correct (FieldTooLongException()), an error label (lblRepeatPasswordError) is shown and the register button(btnRegister) is disabled. 
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     * Method that checks the the length of the field
     *
     * @param observable
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    private void repeatPasswordTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(new String(txtRepeatPassword.getText()), lblRepeatPasswordError);
            }
        } catch (FieldTooLongException ex) {
            errorLabel(lblRepeatPasswordError, ex);

        }
    }

    /**
     * The method that put the label Visible with the error message in red
     *
     * @param lbl The label that is shown
     * @param ex The error of the exception
     */
    private void errorLabel(Label lbl, Exception ex) {
        lbl.setVisible(true);
        lbl.setText(ex.getMessage());
        lbl.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
        logger.severe(ex.getMessage());
    }

    /**
     * Method that recieves the text field and checks its no longer than 255
     * characters
     *
     * @param text The text that is received
     * @param lbl The label that is shown
     * @throws FieldTooLongException The exception if the text field length is
     * higher than 255
     */
    private void check255(String text, Label lbl) throws FieldTooLongException {
        if (text.length() > 255) {
            btnRegister.setDisable(true);
            throw new FieldTooLongException();
        } else {
            btnRegister.setDisable(false);
            lbl.setVisible(false);
        }
    }

    /**
     * Method that checks if the text has at leat one blank
     *
     * @param text The text that is received
     * @param lblError The label that is shown
     * @throws FullNameException The exception if the text does not have at
     * least one blank
     */
    private void checkWhiteSpace(String text, Label lblError) throws FullNameException {
        if (!text.trim().contains(" ")) {
            btnRegister.setDisable(true);
            throw new FullNameException();
        } else {
            btnRegister.setDisable(false);
        }

    }

    /*
    Validate that there is not already a user with the same value in the Username field (txtUsername), sending the User to the database through the signIn() method.
    If it exists (ExistUserException()), an alert (alertExistUser) is displayed and when it is closed, all the fields are deleted.
    If it does not exist, a user is added to the database through the signUp() method, with the data entered and the signIn window is open sending the username and the password.
     */
    /**
     * The method that handles the register button and catch diferent Exceptions
     *
     * @param event the event that manages the Register button
     */
    private void handleButtonRegister(ActionEvent event) {

        try {
            /*
                Check that the Full Name field (txtFullName), Username field (txtUsername), Mail field (txtMail), Password field (txtPassword) and Repeat Password field (txtRepeatPassword) fields are not empty.
                If they are empty (EmptyFieldException()), an error label is shown for each field (lblFullNameError, lblUsernameError, lblMailError, lblPasswordError, lblRepeatPasswordError).
             */
            try {
                if (txtFullName.getText().trim().isEmpty()) {
                    txtFullName.requestFocus();
                    throw new EmptyFieldsException();
                }
            } catch (EmptyFieldsException ex) {
                errorLabel(lblFullNameError, ex);
                throw new EmptyFieldsException();
            }
            try {
                if (txtUsername.getText().trim().isEmpty()) {
                    txtUsername.requestFocus();
                    throw new EmptyFieldsException();
                }
            } catch (EmptyFieldsException ex) {
                errorLabel(lblUsernameError, ex);
                throw new EmptyFieldsException();
            }
            try {
                if (txtMail.getText().trim().isEmpty()) {
                    txtMail.requestFocus();
                    throw new EmptyFieldsException();
                }
            } catch (EmptyFieldsException ex) {
                errorLabel(lblMailError, ex);
                throw new EmptyFieldsException();
            }
            try {
                if (txtPassword.getText().trim().isEmpty()) {
                    txtPassword.requestFocus();
                    throw new EmptyFieldsException();
                }
            } catch (EmptyFieldsException ex) {
                errorLabel(lblPasswordError, ex);
                throw new EmptyFieldsException();
            }
            try {
                if (txtRepeatPassword.getText().trim().isEmpty()) {
                    txtRepeatPassword.requestFocus();
                    throw new EmptyFieldsException();
                }
            } catch (EmptyFieldsException ex) {
                errorLabel(lblRepeatPasswordError, ex);
                throw new EmptyFieldsException();
            }

            //Check that the full name has at least 1 blank
            checkWhiteSpace(txtFullName.getText(), lblFullNameError);
            //Check that the two passwords match
            checkPasswords();

            //If there are any exceptions the user is added
            User user = addUser();
            signable.signUp(user);
            Alert alertUserAddedCorrectly = new Alert(AlertType.INFORMATION);
            alertUserAddedCorrectly.setTitle("SIGN UP");
            alertUserAddedCorrectly.setHeaderText("User added correctly");
            alertUserAddedCorrectly.showAndWait();

            getStage().close();
            openSignInWindow(user);

        } catch (EmptyFieldsException ex) {
            logger.severe(ex.getMessage());
        } catch (FullNameException ex) {
            errorLabel(lblFullNameError, ex);
            txtFullName.requestFocus();
        } catch (RepeatPasswordException ex) {
            errorLabel(lblPasswordError, ex);
            errorLabel(lblRepeatPasswordError, ex);
            txtPassword.requestFocus();
        } catch (ExistUserException | ConnectionErrorException ex) {
            alertExceptionMethod(ex);
        } catch (Exception ex) {
            alertExceptionMethod(ex);
        }

    }

    /**
     * The method that opens the Sign In window and sends the user
     *
     * @param user The user that is send to the Sign In window
     */
    private void openSignInWindow(User user) {
        try {
            //Opens the Welcome window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInWindow.fxml"));
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            SignInWindowController signInWindowController = ((SignInWindowController) loader.getController());

            signInWindowController.setStage(stage);

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            signInWindowController.initStage(root);
            signInWindowController.initWhenSignUp(user);

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method to handle the Back button that closes the Sing Up window and opens
     * the Sign In window
     *
     * @param event the event that manages the Back button
     */
    private void handleButtonBack(ActionEvent event) {
        try {
            getStage().close();
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInWindow.fxml"));
            Parent root = (Parent) loader.load();

            SignInWindowController controller = (SignInWindowController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root);
            */

        } catch (Exception ex) {
            lblFullNameError.setText(ex.getMessage());
            logger.severe(ex.getMessage());
        }
    }


    /*
    Validate that the password entered in the Repeat Password field (txtRepeatPassword) is the same as the one entered in the Password field (txtPassword).
    If it is not the same (RepeatPasswordException()), an error label (lblRepeatPasswordError) is shown.
     */
    /**
     * Method that checks if the password fields match
     *
     * @throws RepeatPasswordException Is thrown in case the passwords do not
     * match
     */
    private void checkPasswords() throws RepeatPasswordException {
        if (!new String(txtPassword.getText()).trim().equals(new String(txtRepeatPassword.getText()).trim())) {
            throw new RepeatPasswordException();
        }
    }

    /**
     * The method that gets all the user information from the fields
     *
     * @return user The user that is collected
     */
    private User addUser() {

        User user = new User();
        user.setFullName(txtFullName.getText());
        user.setLogin(txtUsername.getText());
        user.setEmail(txtMail.getText());
        user.setPassword(new String(txtPassword.getText()));
        return user;
    }

    private void alertExceptionMethod(Exception ex) {
        Alert alertUserAlreadyExists = new Alert(AlertType.INFORMATION);
        alertUserAlreadyExists.setTitle("SIGN UP");
        alertUserAlreadyExists.setHeaderText(ex.getMessage());
        alertUserAlreadyExists.show();
        logger.severe(ex.getMessage());
    }

}
