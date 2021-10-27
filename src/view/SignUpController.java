/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import exceptions.EmptyFieldsException;
import exceptions.FieldTooLongException;
import exceptions.FullNameException;
import static java.awt.Color.red;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2dam
 */
public class SignUpController extends Application {
    
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
    private Button btnBack ;
    
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
    
    @Override
    public void start(Stage stage) {
        try {
            this.setStage(stage);
            /*
            Button btn = new Button();
            btn.setText("Say 'Hello World'");
            btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            System.out.println("Hello World!");
            }
            });
            
            
            StackPane root = new StackPane();
            root.getChildren().add(btn);
            
            Scene scene = new Scene(root, 300, 250);
            
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
            */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));
            
            
            Parent root = (Parent)loader.load();
            
            SignUpController controller = ((SignUpController)loader.getController());

            controller.setStage(stage);
            controller.initStage(root);
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void initStage(Parent root){
        Scene scene = new Scene(root);

        getStage().setScene(scene);
        getStage().setTitle("Sign Up Window");
        getStage().setResizable(false);

        getStage().setOnShowing(this::handleWindowShowing);
        txtFullName.textProperty().addListener(this::fullNameTextChanged);
        txtUsername.textProperty().addListener(this::usernameTextChanged);
        txtMail.textProperty().addListener(this::mailTextChanged);
        txtPassword.textProperty().addListener(this::passwordTextChanged);
        txtRepeatPassword.textProperty().addListener(this::repeatPasswordTextChanged);
        btnBack.setOnAction(this::handleButton);
        
        getStage().show();
    
    }
    
    private void handleButton(ActionEvent event){
        try {
            getStage().close();
        } catch (Exception ex) {
            lblFullNameError.setText(ex.getMessage());
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleWindowShowing(WindowEvent event){
        txtFullName.requestFocus();
        
    }
    
    private void fullNameTextChanged(ObservableValue observable, Object oldValue, Object newValue){
        
        try {
            check255(txtFullName.getText(), lblFullNameError);
            checkWhiteSpace(txtFullName.getText(), lblFullNameError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblFullNameError, ex);
            
        } catch (FullNameException ex) {
            errorLabel(lblFullNameError, ex);
        }
    }
   
    private void usernameTextChanged(ObservableValue observable, Object oldValue, Object newValue){
        
        try {
            check255(txtUsername.getText(), lblUsernameError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblUsernameError, ex);
        }
    }
    
    private void mailTextChanged(ObservableValue observable, Object oldValue, Object newValue){
        
        try {
            check255(txtMail.getText(), lblMailError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblMailError, ex);
            
        }
    }
    
    private void passwordTextChanged(ObservableValue observable, Object oldValue, Object newValue){
        
        try {
            check255(txtPassword.getText(), lblPasswordError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblPasswordError, ex);
            
        }
    }
    
    private void repeatPasswordTextChanged(ObservableValue observable, Object oldValue, Object newValue){
        
        try {
            check255(txtRepeatPassword.getText(), lblRepeatPasswordError);
        } catch (FieldTooLongException ex) {
            errorLabel(lblRepeatPasswordError, ex);
            
        }
    }
    
    
    
     private void errorLabel(Label lbl, Exception ex){
        lbl.setText(ex.getMessage());
        lbl.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
        logger.info(ex.getMessage());
        logger.warning(ex.getMessage());
    }

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        launch(args);
    }


    private void check255(String text, Label lbl) throws FieldTooLongException{
        if(text.length() > 255){
            throw new FieldTooLongException();  
        }else{
            lbl.setText("");
        }
    }

    private void checkWhiteSpace(String text, Label lblError) throws FullNameException{
        if(!text.trim().contains(" ")){
            throw new FullNameException();
        }

    }

   

    
    
}
