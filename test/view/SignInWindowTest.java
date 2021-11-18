package view;

import clientApplication.ClientApplication;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * This class tests the Sign In window
 *
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
     *
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
    public void testAA_initStage() {
        verifyThat("#btnLogin", isEnabled());
        verifyThat("#txtUsername", isEnabled());
        verifyThat("#txtPassword", isEnabled());
        verifyThat("#hlkHere", isEnabled());
        verifyThat("#lblUsernameError", isInvisible());
        verifyThat("#lblPasswordError", isInvisible());
    }

    /**
     * Test the Hyperlink
     *
     */
    @Test
    public void testX_HyperlinkHlkHere() {
        clickOn("#hlkHere");
        verifyThat(window("Sign Up Window"), WindowMatchers.isShowing());
        clickOn("#btnBack");
    }

    /**
     * Test what happens when the password is too long
     */
    @Test
    public void testV_PasswordTooLong() {
        doubleClickOn("#txtPassword");
        write(OVERSIZED_TEXT);
        verifyThat("#lblPasswordError", hasText("The field is too long (255 character max)."));
        verifyThat("#btnLogin", isDisabled());
    }

    /**
     * Test what happens when the user is too long
     */
    @Test
    public void testN_UserTooLong() {
        doubleClickOn("#txtUsername");
        write(OVERSIZED_TEXT);
        verifyThat("#lblUsernameError", hasText("The field is too long (255 character max)."));
        verifyThat("#btnLogin", isDisabled());
    }

    /**
     * Test what happens when the user is empty
     */
    @Test
    public void testM_UserIsEmpty() {
        doubleClickOn("#txtUsername");
        write("matteo");
        doubleClickOn("#txtUsername");
        write(" ");
        verifyThat("#lblUsernameError", hasText("The field cannot be empty."));
    }

    /**
     * Test what happens when the password is empty
     */
    @Test
    public void testF_PasswordIsEmpty() {
        doubleClickOn("#txtPassword");
        write(" ");
        verifyThat("#lblPasswordError", hasText("The field cannot be empty."));
    }

    /**
     * Test what happens when the user exist
     */
    @Test
    public void testA_UserExist() {
        doubleClickOn("#txtUsername");
        write("matteo");
        doubleClickOn("#txtPassword");
        write("abcd*1234");
        clickOn("#btnLogin");
        verifyThat(window("Welcome window"), WindowMatchers.isShowing());
        clickOn("#btnLogOut");
        clickOn("Aceptar");
    }

    /**
     * Test what happens when the user does not exit
     */
    @Test
    public void testB_UserNotExist() {
        doubleClickOn("#txtUsername");
        write("matteosss");
        doubleClickOn("#txtPassword");
        write("abcd*1234");
        clickOn("#btnLogin");
        verifyThat("The user does not exist.", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Test what happens when the password does not match.
     */
    @Test
    public void testC_PasswordDoesNotMatch() {
        doubleClickOn("#txtUsername");
        write("matteo");
        doubleClickOn("#txtPassword");
        write("abcd");
        clickOn("#btnLogin");
        verifyThat("The password is incorrect.", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Test what happens when the server is off and the conexion doesnt work
     *
     */
    //TEST ONLY WITHOUT THE SERVER RUNNING
    @Test
    public void testD_Connection_Error() {
        doubleClickOn("#txtUsername");
        write("matteo");
        doubleClickOn("#txtPassword");
        write("abcd*1234");
        clickOn("#btnLogin");
        verifyThat("It was implossible to connect to the server.", isVisible());
        clickOn("Aceptar");
    }
}
