package tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import clientApplication.ClientApplication;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author Matteo Fernández
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WelcomeWindowTest extends ApplicationTest {

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
         verifyThat("#btnExit", isEnabled());
         verifyThat("#btnLogOut", isEnabled());
    }

 
  
}
