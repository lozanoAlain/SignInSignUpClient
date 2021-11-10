package clientApplication;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.SignInWindowController;

/**
 * Main class from the client application
 * @author Matteo Fernández
 */
public class ClientApplication extends Application {

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInWindow.fxml"));
        
        Parent root = (Parent) loader.load();
        SignInWindowController signInWindowController = (loader.getController());
        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
        
        //The primary stage is not resizable.
        primaryStage.setResizable(Boolean.FALSE);
        
        signInWindowController.setStage(primaryStage);
        signInWindowController.initStage(root);
    }

    /**
     * Our main method of the client application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
