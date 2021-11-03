package tests;

import clientApplication.ClientApplication;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * This class tests the Sign In window
 * @author Matteo Fernández
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInWindowTest extends ApplicationTest {

    private static final String OVERSIZED_TEXT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    /**
     * Starts the test with the client application
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        new ClientApplication().start(stage);
    }

    /**
     * Test the init stage of the window
     *
     */
    @Test
    public void testA_initStage() {
        verifyThat("#btnLogin", isEnabled());
        verifyThat("#txtUsername", isEnabled());
        verifyThat("#txtPassword", isEnabled());
        verifyThat("#txtUsername", isFocused());
        verifyThat("#hlkHere", isEnabled());
        verifyThat("#lblUsernameError", isInvisible());
        verifyThat("#lblPasswordError", isInvisible());
    }

    /**
     * Test the Hyperlink
     *
     */
    @Test
    public void testB_HyperlinkHlkHere() {
        clickOn("#hlkHere");
        verifyThat(window("Sign Up Window"), WindowMatchers.isShowing());
    }

    /**
     * Test what happens when the password is too long
     */
    @Test
    public void testC_PasswordTooLong() {
        doubleClickOn("#txtPassword");
        write(OVERSIZED_TEXT);
        clickOn("#btnLogin");
        verifyThat("#lblPasswordError", isVisible());
        verifyThat("#lblPasswordError", hasText("The field is too long (255 character max)."));
    }

    /**
     * Test what happens when the user is too long
     */
    @Test
    public void testD_UserTooLong() {
        doubleClickOn("#txtUsername");
        write(OVERSIZED_TEXT);
        clickOn("#btnLogin");
        verifyThat("#lblUsernameError", hasText("The field is too long (255 character max)."));
    }

    /**
     * Test what happens when the user is empty
     */
    @Test
    public void testE_UserIsEmpty() {
        doubleClickOn("#txtUsername");
        write("");
        clickOn("#btnLogin");
        verifyThat("#lblUsernameError", hasText("The field cannot be empty."));
    }
    
     /**
     * Test what happens when the password is empty
     */
    @Test
    public void testF_PasswordIsEmpty() {
        doubleClickOn("#txtPassword");
        write("");
        clickOn("#btnLogin");
        verifyThat("#lblPasswordError", hasText("The field cannot be empty."));
    }

}
