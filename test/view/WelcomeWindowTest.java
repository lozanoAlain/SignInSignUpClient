package view;

import clientApplication.ClientApplication;
import java.util.concurrent.TimeoutException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import org.testfx.matcher.base.WindowMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * This class tests the Welcome window
 *
 * @author Matteo Fernández
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WelcomeWindowTest extends ApplicationTest {

    /**
     * Starts the test with the Welcome window controller.
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }

    /**
     * Test the init stage of the Welcome window.
     *
     */
    @Test
    public void testA_initStage() {
        doubleClickOn("#txtUsername");
        write("matteo");
        doubleClickOn("#txtPassword");
        write("abcd*1234");
        clickOn("#btnLogin");
        verifyThat("#btnExit", isEnabled());
        verifyThat("#btnLogOut", isEnabled());
    }

    /**
     * Veryfy that when the window opens, the txtWelcome has the greeting.
     */
    @Test
    public void testB_greeting() {

        verifyThat("#txtWelcome", hasText("Hello matteo"));
    }

    /**
     * Veryfy that when the bot clicks on btnLogOut, the Sign Up Window is
     * showing.
     */
    @Test
    public void testC_logOutStage() {

        doubleClickOn("#btnLogOut");
        clickOn("Aceptar");
        verifyThat(window("Sign In Window"), WindowMatchers.isShowing());
    }

}
