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
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
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
    @FXML
    private Label lblRepeatPasswordError;

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
    
    private Signable signable;

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

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

    /*
    The fields Full name (txtFullName), Username (txtUsername), Mail (txtMail), Password (txtPassword) and Repeat password (txtRepeatPassword) are enabled.

    The Back (btnBack) and Register (btnRegister) buttons are enabled.

    The window is not resizable.
     */
    /**
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
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getStage().show();

    }

    /*
        The Full name field (txtFullName) is focused.
     */
    private void handleWindowShowing(WindowEvent event) {
        txtFullName.requestFocus();

    }


    /*
    Check that the Full Name field (txtFullName) is no longer than 255 characters (checkNoLonger255()).
    If it is not correct (FieldTooLongException()), an error label (lblFullNameError) is shown and the register button(btnRegister is disabled.

    Check that the Full name (txtFullName) has at least 1 blank (checkWhiteSpace())
    If it is not correct (FullNameException()), an error label (lblFullNameError) is shown and the register button(btnRegister is disabled.
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void fullNameTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            check255(txtFullName.getText(), lblFullNameError);
            checkWhiteSpace(txtFullName.getText(), lblFullNameError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblFullNameError, ex);

        } catch (FullNameException ex) {
            errorLabel(lblFullNameError, ex);
        }
    }

    /*
    Check that the Username field (txtUsername) is no longer than 255 characters (checkNoLonger255()).
    If it is not correct (FieldTooLongException()), an error label (lblUsernameError) is shown and the register button(btnRegister is disabled.
    When the error is corrected the register button(btnRegister) is enabled.
     */
    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void usernameTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            check255(txtUsername.getText(), lblUsernameError);
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
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void mailTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            check255(txtMail.getText(), lblMailError);
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
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void passwordTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            check255(txtPassword.getText(), lblPasswordError);
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
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void repeatPasswordTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            check255(txtRepeatPassword.getText(), lblRepeatPasswordError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblRepeatPasswordError, ex);

        }
    }

    private void errorLabel(Label lbl, Exception ex) {
        lbl.setText(ex.getMessage());
        lbl.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
        logger.warning(ex.getMessage());
    }

    private void check255(String text, Label lbl) throws FieldTooLongException {
        if (text.length() > 5) {
            btnRegister.setDisable(true);
            throw new FieldTooLongException();
        } else {
            btnRegister.setDisable(false);
            lbl.setText("");
        }
    }

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
     *
     * @param event
     */
    private void handleButtonRegister(ActionEvent event) {
        if (checkEmptyFields()) {
            try {
                checkPasswords();
                txtPassword.setText("");
                txtRepeatPassword.setText("");
                txtPassword.requestFocus();
                User user = addUser();
                signable.signUp(user);

            } catch (RepeatPasswordException ex) {
                errorLabel(lblPasswordError, ex);
                errorLabel(lblRepeatPasswordError, ex);
                logger.warning(ex.getMessage());
            }catch(ExistUserException ex){
                logger.warning(ex.getMessage());
            } catch (ConnectionErrorException ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*
        try{
         addUser();
        
        }catch(ExistUserException ex){
            Alert alert = new Alert(ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText("Oops, try signing in with the user you enter.");
            alert.showAndWait();
            logger.warning(ex.getMessage());
        }
         */
    }

    private void handleButtonBack(ActionEvent event) {
        try {
            getStage().close();
        } catch (Exception ex) {
            lblFullNameError.setText(ex.getMessage());
            logger.warning(ex.getMessage());
        }
    }

    /*
    Check that the Full Name field (txtFullName), Username field (txtUsername), Mail field (txtMail), Password field (txtPassword) and Repeat Password field (txtRepeatPassword) fields are not empty.
    If they are empty (EmptyFieldException()), an error label is shown for each field (lblFullNameError, lblUsernameError, lblMailError, lblPasswordError, lblRepeatPasswordError).
     */
    private boolean checkEmptyFields() {
        boolean check = true;
        if (txtRepeatPassword.getText().isEmpty()) {
            try {
                check = false;
                txtRepeatPassword.requestFocus();
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                errorLabel(lblRepeatPasswordError, ex);
                logger.warning(ex.getMessage());
            }
        }
        if (txtPassword.getText().isEmpty()) {
            try {
                check = false;
                txtPassword.requestFocus();
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                errorLabel(lblPasswordError, ex);
                logger.warning(ex.getMessage());
            }
        }
        if (txtMail.getText().isEmpty()) {
            try {
                check = false;
                txtMail.requestFocus();
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                errorLabel(lblMailError, ex);
                logger.warning(ex.getMessage());
            }
        }
        if (txtUsername.getText().isEmpty()) {
            try {
                check = false;
                txtUsername.requestFocus();
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                errorLabel(lblUsernameError, ex);
                logger.warning(ex.getMessage());
            }
        }
        if (txtFullName.getText().isEmpty()) {
            try {
                check = false;
                txtFullName.requestFocus();
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                errorLabel(lblFullNameError, ex);
                logger.warning(ex.getMessage());
            }
        }
        return check;
    }

    /*
    Validate that the password entered in the Repeat Password field (txtRepeatPassword) is the same as the one entered in the Password field (txtPassword).
    If it is not the same (RepeatPasswordException()), an error label (lblRepeatPasswordError) is shown and the two password fields are deleted.
     */

    /**
     *
     * @throws RepeatPasswordException
     */
    private void checkPasswords() throws RepeatPasswordException {
        if (!txtPassword.getText().trim().equals(txtRepeatPassword.getText().trim())) {
            throw new RepeatPasswordException();
        }
    }

    private User addUser() {

        User user = new User();
        user.setFullName(txtFullName.getText());
        user.setLogin(txtUsername.getText());
        user.setEmail(txtMail.getText());
        user.setPassword(new String(txtPassword.getText()));
        return user;
    }

}
