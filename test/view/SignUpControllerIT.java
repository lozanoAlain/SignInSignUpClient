/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import clientApplication.ClientApplication;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author Alain Lozano, Ilia Consuegra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerIT extends ApplicationTest {

    private TextField txtFullName;
    private TextField txtUsernameUp;
    private TextField txtMail;
    private TextField txtPasswordUp;
    private TextField txtRepeatPassword;

    private Label lblFullNameError;
    private Label lblUsernameError;
    private Label lblMailError;
    private Label lblPasswordError;
    private Label lblRepeatPasswordError;

    private static final String OVERSIZED_TEXT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);

    }
     
    /*
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpWindow.fxml"));

        //Creates a new stage
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        SignUpController signUpController = ((SignUpController) loader.getController());

        //Set the stage that we already created to the sign up controller
        signUpController.setStage(stage);
        signUpController.initStage(root);
        
    }
*/
    @Before
    public void initializeLookUp() {
        txtFullName = lookup("#txtFullName").query();
        txtUsernameUp = lookup("#txtUsernameUp").query();
        txtMail = lookup("#txtMail").query();
        txtPasswordUp = lookup("#txtPasswordUp").query();
        txtRepeatPassword = lookup("#txtRepeatPassword").query();

        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameErrorUp").query();
        lblMailError = lookup("#lblMailError").query();
        lblPasswordError = lookup("#lblPasswordErrorUp").query();
        lblRepeatPasswordError = lookup("#lblRepeatPasswordError").query();

    }

    public SignUpControllerIT() {
    }
    
    @Test
    public void testA_InitialState() {

        clickOn("#hlkHere");
        verifyThat("#txtFullName", isEnabled());
        verifyThat("#txtUsername", isEnabled());
        verifyThat("#txtPassword", isEnabled());
        verifyThat("#txtRepeatPassword", isEnabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnRegister", isEnabled());
        verifyThat("#txtFullName", isFocused());

    }

    @Test
    public void testB_SignUpCorrect() {
        clickOn(txtFullName);
        write("Aitor Ruiz De Gauna");
        clickOn(txtUsernameUp);
        write("aitor");
        clickOn(txtMail);
        write("aitor@gmail.com");
        clickOn(txtPasswordUp);
        write("abcd*1234");
        clickOn(txtRepeatPassword);
        write("abcd*1234");
        clickOn("#btnRegister");
        verifyThat("User added correctly", isVisible());
        sleep(1000);

    }
@Ignore
    @Test
    public void testC_SignUpErrorServer() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        cleanAll();
        clickOn("#txtFullName");
        write("Aitor Ruiz De Gauna");
        clickOn("#txtUsernameUp");
        write("aitor");
        clickOn("#txtMail");
        write("aitor@gmail.com");
        clickOn("#txtPasswordUp");
        write("abcd*1234");
        clickOn("#txtRepeatPassword");
        write("abcd*1234");
        clickOn("#btnRegister");
        verifyThat("It was implossible to connect to the server.", isVisible());

    }

    @Test
    public void testD_SignUpErrorUser() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#hlkHere");
        //cleanAll();
        clickOn("#txtFullName");
        write("Alain Lozano");
        clickOn("#txtUsernameUp");
        write("alain");
        clickOn("#txtMail");
        write("alain@gmail.com");
        clickOn("#txtPasswordUp");
        write("abcd*1234");
        clickOn("#txtRepeatPassword");
        write("abcd*1234");
        clickOn("#btnRegister");
        verifyThat("The user already exist.", isVisible());
        sleep(1000);

    }
@Ignore
    @Test
    public void testE_TextLongerThan255() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        cleanAll();
        textLongerThan255(txtFullName, lblFullNameError);
        textLongerThan255(txtUsernameUp, lblUsernameError);
        textLongerThan255(txtMail, lblMailError);
        textLongerThan255(txtPasswordUp, lblPasswordError);
        textLongerThan255(txtRepeatPassword, lblRepeatPasswordError);
    }

    private void textLongerThan255(TextField txt, Label lbl) {
        
        clickOn(txt);
        write(OVERSIZED_TEXT);
        verifyThat("#btnRegister", isDisabled());
        verifyThat(lbl, isEnabled());
        verifyThat(lbl, hasText("The field is too long (255 character max)."));
        doubleClickOn(txt);
        eraseText(1);
        lbl.setVisible(false);

    }
@Ignore
    @Test
    public void testF_BlankSpacesError() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        cleanAll();
        clickOn(txtFullName);
        write("AAA");
        clickOn("#btnRegister");
        verifyThat(lblFullNameError, hasText("The full name is incomplete."));
    }
@Ignore
    @Test
    public void testG_BlankSpacesCorrect() {
        doubleClickOn(txtFullName);
        eraseText(1);
        write("AA A");
        clickOn("#btnRegister");
        verifyThat(lblFullNameError, isInvisible());

    }
@Ignore
    @Test
    public void testH_FullNameEmptyField() {
        clean(txtFullName);
        clickOn("#btnRegister");
        verifyThat(lblFullNameError, hasText("The field cannot be empty."));
        write("AA A");  

    }
   @Ignore
    @Test
    public void testI_UserNameEmptyField(){
        clickOn("#btnRegister");
        verifyThat(lblFullNameError, isInvisible());
        verifyThat(lblUsernameError, isVisible());
        //verifyThat(lblUsernameError, hasText("The field cannot be empty."));
        write("AA");
    }
    @Ignore
    @Test
    public void testJ_MailEmptyField(){
        clickOn("#btnRegister");
        verifyThat(lblUsernameError, isInvisible());
        verifyThat(lblMailError, isVisible());
        verifyThat(lblMailError, hasText("The field cannot be empty."));
        write("AA");
    }
   @Ignore
    @Test
    public void testK_UsPasswordEmptyField(){
        clickOn("#btnRegister");
        verifyThat(lblMailError, isInvisible());
        verifyThat(lblPasswordError, isVisible());
        verifyThat(lblPasswordError, hasText("The field cannot be empty."));
        write("AA");
    }
   @Ignore
    @Test
    public void testM_RepeatPasswordEmptyField(){
        clickOn("#btnRegister");
        verifyThat(lblPasswordError, isInvisible());
        verifyThat(lblRepeatPasswordError, isVisible());
        verifyThat(lblRepeatPasswordError, hasText("The field cannot be empty."));
        write("AA");
    }
@Ignore
    @Test
    public void testN_PasswordCorrect() {
        doubleClickOn(txtRepeatPassword);
        eraseText(1);
        write("BBB");
        clickOn("#btnRegister");
        verifyThat(lblPasswordError, hasText("The passwords don´t match."));
        verifyThat(lblRepeatPasswordError, hasText("The passwords don´t match."));
        verifyThat(txtPasswordUp, isFocused());

    }

    private void clean(TextField txt) {
        int textLength = txt.getLength();
        clickOn(txt);
        eraseText(textLength);
    }

    private void cleanAll() {
        //Delete data

        clean(txtFullName);
        clean(txtUsernameUp);
        clean(txtMail);
        clean(txtPasswordUp);
        clean(txtRepeatPassword);

    }

}
