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
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author Alain Lozano, Ilia Consuegra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerIT extends ApplicationTest {

    private TextField txtFullName;
    private TextField txtUsername;
    private TextField txtMail;
    private TextField txtPassword;
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

    @Before
    public void initializeLookUp() {
        txtFullName = lookup("#txtFullName").query();
        txtUsername = lookup("#txtUsername").query();
        txtMail = lookup("#txtMail").query();
        txtPassword = lookup("#txtPassword").query();
        txtRepeatPassword = lookup("#txtRepeatPassword").query();

        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        lblMailError = lookup("#lblMailError").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        lblRepeatPasswordError = lookup("#lblRepeatPasswordError").query();

    }

    public SignUpControllerIT() {
    }

    @Test
    public void testA_InitialState() {

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
        clickOn(txtUsername);
        write("aitor");
        clickOn(txtMail);
        write("aitor@gmail.com");
        clickOn(txtPassword);
        write("abcd*1234");
        clickOn(txtRepeatPassword);
        write("abcd*1234");
        clickOn("#btnRegister");
        verifyThat("User added correctly", isVisible());     
    }

    @Test
    public void testC_SignUpErrorServer() {
        press(KeyCode.ENTER).release(KeyCode.ENTER); 
        clickOn(txtFullName);
        write("Aitor Ruiz De Gauna");
        clickOn(txtUsername);
        write("aitor");
        clickOn(txtMail);
        write("aitor@gmail.com");
        clickOn(txtPassword);
        write("abcd*1234");
        clickOn(txtRepeatPassword);
        write("abcd*1234");
        clickOn("#btnRegister");
        verifyThat("It was implossible to connect to the server.", isVisible());

    }

    @Test
    public void testD_SignUpErrorUser() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        cleanAll();
        clickOn(txtFullName);
        write("Alain Lozano");
        clickOn(txtUsername);
        write("alain");
        clickOn(txtMail);
        write("alain@gmail.com");
        clickOn(txtPassword);
        write("abcd*1234");
        clickOn(txtRepeatPassword);
        write("abcd*1234");
        clickOn("#btnRegister");
        verifyThat("The user already exist.", isVisible());

    }
    @Ignore
    @Test
    public void testE_TextLongerThan255() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        cleanAll();
        textLongerThan255(txtFullName, lblFullNameError);
        textLongerThan255(txtUsername, lblUsernameError);
        textLongerThan255(txtMail, lblMailError);
        textLongerThan255(txtPassword, lblPasswordError);
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

    }
    
    @Ignore
    @Test
    public void testF_BlankSpacesError() {
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        cleanAll();
        clickOn(txtFullName);
        write("AAA");
        clickOn(txtUsername);
        verifyThat("#btnRegister", isDisabled());
        verifyThat(lblFullNameError, hasText("The full name is incomplete."));
    }
    
    @Ignore
    @Test
    public void testG_BlankSpacesCorrect() {
        doubleClickOn(txtFullName);
        eraseText(1);
        write("AA A");
        clickOn(txtUsername);
        verifyThat("#btnRegister", isEnabled());
        verifyThat(lblFullNameError, hasText(""));

    }
    
    @Ignore
    @Test
    public void testH_EmptyField() {
        clickOn(txtFullName);
        write("AA A");
        clickOn("#btnRegister");
        verifyThat(lblUsernameError, hasText("The field cannot be empty."));
        verifyThat(lblMailError, hasText("The field cannot be empty."));
        verifyThat(lblPasswordError, hasText("The field cannot be empty."));
        verifyThat(lblRepeatPasswordError, hasText("The field cannot be empty."));

    }
    
    @Ignore
    @Test
    public void testI_PasswordCorrect() {
        clickOn(txtFullName);
        write("AA A");
        clickOn(txtUsername);
        write("AAA");
        clickOn(txtMail);
        write("AAA");
        clickOn(txtPassword);
        write("AAA");
        clickOn(txtRepeatPassword);
        write("BBB");
        clickOn("#btnRegister");
        verifyThat(lblPasswordError, hasText("The passwords don´t match."));
        verifyThat(lblRepeatPasswordError, hasText("The passwords don´t match."));
        verifyThat(txtPassword, isFocused());

    }
    
    @Ignore
    @Test
    public void testJ_EmailCorrect() {
        cleanAll();
        clickOn(txtFullName);
        write("AA A");
        clickOn(txtUsername);
        write("AAA");
        clickOn(txtMail);
        write("AAA");
        clickOn(txtPassword);
        write("AAA");
        clickOn(txtRepeatPassword);
        write("AAA");
        clickOn("#btnRegister");
        verifyThat(lblMailError, hasText("The mail is written incorrectly"));

    }

    private void clean(TextField txt) {
        int textLength = txt.getLength();
        clickOn(txt);
        eraseText(textLength);
    }

    private void cleanAll() {
        //Delete data

        clean(txtFullName);
        clean(txtUsername);
        clean(txtMail);
        clean(txtPassword);
        clean(txtRepeatPassword);

    }

}
