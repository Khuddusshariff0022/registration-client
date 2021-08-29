
package registrationtest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import io.mosip.registration.dto.mastersync.GenericDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testfx.api.FxRobot;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import registrationtest.controls.Alerts;
import registrationtest.utility.ExtentReportUtil;
import registrationtest.utility.PropertiesUtil;
import registrationtest.utility.WaitsUtil;
import java.util.Optional;

public class LoginPage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    FxRobot robot;
    Stage applicationPrimaryStage;
    Scene scene;
    Node node;

    TextField userIdTextField;
    String userId = "#userId";

    TextField passwordTextField;
    String password = "#password";

    String loginScreen = "#loginScreen";
    String homeSelectionMenu = "#homeSelectionMenu";
    String logout = "#logout";
    String submit = "#submit1";
    //String success = "#context";
    String success = "Success";
    String exit = "#exit";
    String appLanguage = "#appLanguage";

    WaitsUtil waitsUtil;
    Alerts alerts;

    public LoginPage(FxRobot robot, Stage applicationPrimaryStage, Scene scene) {
        logger.info("LoginPage Constructor");
        this.robot = robot;
        this.applicationPrimaryStage = applicationPrimaryStage;
        this.scene = scene;
        waitsUtil = new WaitsUtil(robot);
        alerts = new Alerts(robot);
    }

    public LoginPage(FxRobot robot) {
        logger.info("LoginPage Constructor");

        this.robot = robot;
        waitsUtil = new WaitsUtil(robot);
        waitsUtil.clickNodeAssert(loginScreen);
    }

    public void selectAppLang() {
        String[] listLang = null;
        try {
            listLang = PropertiesUtil.getKeyValue("appLanguage").split("@@");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String str = listLang[2];
        // ComboBoxUtil.user_selects_combo_item(appLanguage,str);

//					GenericDto dto=new GenericDto();
//					
//					dto.setCode(listLang[0]);
//					dto.setLangCode(listLang[1]);
//					dto.setName(listLang[2]);
//					
//
        try {
            Platform.runLater(new Runnable() {
                public void run() {

                    ComboBox comboBox = waitsUtil.lookupById(appLanguage);

                    // comboBox.getSelectionModel().select(dto);
                    Optional<GenericDto> op = comboBox.getItems().stream()
                            .filter(i -> ((GenericDto) i).getName().equalsIgnoreCase(str)).findFirst();
                    if (op.isEmpty())
                        comboBox.getSelectionModel().selectFirst();
                    else
                        comboBox.getSelectionModel().select(op.get());

                    try {
                        Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("ComboItemTimeWait")));
                    } catch (Exception e) {

                        logger.error("", e);
                    }
                }
            });
        } catch (Exception e) {

            logger.error("", e);
        }
    }

    public String getUserId() {
        logger.info("getUserId");

        return userIdTextField.getText();
    }

    public void setUserId(String userIdText) {
        logger.info("setUserId" + userIdText);

        try {

            Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("wait6")));

            userIdTextField = waitsUtil.lookupByIdTextField(userId, robot);

            assertNotNull(userIdTextField, "userIdTextField not present");

            // robot.clickOn(userIdTextField);

            userIdTextField.clear();

            userIdTextField.setText(userIdText);

            System.out.println("User id Entered ");
            waitsUtil.clickNodeAssert("#sub1");
            assertEquals(userIdText, userIdTextField.getText(), "User id is not as same as entered");

        } catch (Exception e) {
            logger.error("", e);
        }

    }

    /**
     * Verify HomePage after password enter
     * 
     * @param pwd
     * @return
     */

    public HomePage setPassword(String pwd) {

        logger.info("setPassword");

        try {

            passwordTextField = waitsUtil.lookupByIdTextField(password, robot);

            assertNotNull(passwordTextField, "passwordTextField Not Present");

            passwordTextField.setText(pwd);

            waitsUtil.clickNodeAssert("#sub2");

        } catch (Exception e) {
            logger.error("", e);
        }
        return new HomePage(robot);

    }

    /**
     * verifyAuthentication after password enter
     * 
     * @param pwd
     * @return
     * @throws InterruptedException
     */

    public boolean verifyAuthentication(String pwd, Stage applicationPrimaryStage) {
        boolean flag = false;
        try {
            passwordTextField = waitsUtil.lookupByIdTextField(password, robot);

            assertNotNull(passwordTextField, "passwordTextField Not Present");

            passwordTextField.setText(pwd);
            
            // if home else fail or check the #context

            waitsUtil.clickNodeAssert("#sub2");

            Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("SyncWait")));
            waitsUtil.clickNodeAssert(success);

            flag = true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return flag;

    }
    

    public boolean verifyLoginAndHome(String pwd, Stage applicationPrimaryStage) {
        boolean flag = false;
        try {
            passwordTextField = waitsUtil.lookupByIdTextField(password, robot);

            assertNotNull(passwordTextField, "passwordTextField Not Present");

            passwordTextField.setText(pwd);
            
            // if home else fail or check the #context

            waitsUtil.clickNodeAssert("#sub2");

            Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("SyncWait")));
            waitsUtil.clickNodeAssert("#homeImgView");

            flag = true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return flag;

    }

    public void loadLoginScene(Stage applicationPrimaryStage) {
        logger.info("In Login test Loaded");

        try {
            // alerts.clickAlertCancel();

            Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("Loginwait")));

            waitsUtil.clickNodeAssert(loginScreen);

            scene = applicationPrimaryStage.getScene();
            node = scene.lookup(loginScreen);
            while (node.isDisable()) {
                Thread.sleep(Long.parseLong(PropertiesUtil.getKeyValue("ComboItemTimeWait")));

                System.out.println("Disable login screen waiting to get it on");
            }
            assertNotNull(node, "Login Page is not shown");

            ExtentReportUtil.test1.info("Successfully Screen Loaded");

        } catch (Exception e) {
            logger.error("", e);
        }

    }

    public void logout() {
        /**
         * Click Menu Logout
         */
        waitsUtil.clickNodeAssert(homeSelectionMenu);

        waitsUtil.clickNodeAssert(logout);

    }

}
