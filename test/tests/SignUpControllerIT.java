/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import clientApplication.ClientApplication;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
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
    public void testB_TextLongerThan255() {
        txtFullName = lookup("#txtFullName").query();
        lblFullNameError = lookup("#lblFullNameError").query();
        textLongerThan255(txtFullName, lblFullNameError);
        txtUsername = lookup("#txtUsername").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        textLongerThan255(txtUsername, lblUsernameError);
        txtMail = lookup("#txtMail").query();
        lblMailError = lookup("#lblMailError").query();
        textLongerThan255(txtMail, lblMailError);
        txtPassword = lookup("#txtPassword").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        textLongerThan255(txtPassword, lblPasswordError);
        txtRepeatPassword = lookup("#txtRepeatPassword").query();
        lblRepeatPasswordError = lookup("#lblRepeatPasswordError").query();
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

}
