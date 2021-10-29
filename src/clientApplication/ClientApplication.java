/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.SignInWindowController;

/**
 *
 * @author Matteo Fernández
 */
public class ClientApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInWindow.fxml"));
        Parent root = (Parent) loader.load();
        SignInWindowController signInWindowController = (loader.getController());
        primaryStage.setResizable(Boolean.FALSE);
        signInWindowController.setStage(primaryStage);
        signInWindowController.initStage(root);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
