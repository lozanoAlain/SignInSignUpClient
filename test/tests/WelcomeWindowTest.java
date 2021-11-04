package tests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import org.testfx.matcher.base.WindowMatchers;
import view.WelcomeWindowController;

/**
 * This class tests the Welcome window
 * @author Matteo Fernández
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WelcomeWindowTest extends ApplicationTest {

    /**
     * Starts the test with the Welcome window controller
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomeWindowController.fxml"));
        Parent root = (Parent) loader.load();

        WelcomeWindowController welcomeWindowController = (loader.getController());
        welcomeWindowController.setStage(stage);
     //   welcomeWindowController.initStage(root,);
    }

    /**
     * Test the init stage of the window
     *
     */
    @Test
    public void testA_initStage() {
        verifyThat("#btnExit", isEnabled());
        verifyThat("#btnLogOut", isEnabled());
    }

    /**
     * Veryfy that when the bot clicks on btnLogOut, the Sign Up Window is
     * showing
     */
    @Test
    public void testB_logOutStage() {
        clickOn("#btnLogOut");
        verifyThat(window("Sign Up Window"), WindowMatchers.isShowing());
    }

}
