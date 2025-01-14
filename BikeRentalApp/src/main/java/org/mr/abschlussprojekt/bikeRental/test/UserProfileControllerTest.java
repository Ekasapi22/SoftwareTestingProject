package org.mr.abschlussprojekt.bikeRental.test;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mr.abschlussprojekt.bikeRental.database.services.UserService;
import org.mr.abschlussprojekt.bikeRental.gui.UserProfileController;
import org.mr.abschlussprojekt.bikeRental.logic.ChangeSceneManager;
import org.mr.abschlussprojekt.bikeRental.model.User;
import org.mr.abschlussprojekt.bikeRental.setting.AppTexts;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


//these methods will focus on integration testing

public class UserProfileControllerTest extends ApplicationTest{

    private UserProfileController profileController;
    private UserService testService;

    /*@Before
    public void setUpClass() throws Exception
    {
        ApplicationTest.launch(Main.class);
    }*/

    @Override
    public void start(Stage stage) throws IOException
    {
        ChangeSceneManager.getInstance().switchToUserHome();
        //stage.show();
    }


    @Before
    public void setupForPasswordChangeTesting()
    {
        //setting up the controller and the user service
        testService = new UserService();
        profileController = new UserProfileController();
        profileController.setUserService(testService);

        //setting up the controller for the password change tsting
        profileController.setAltPasswordField(new PasswordField());
        profileController.setNewPasswordField(new PasswordField());
        profileController.setConfirmPasswordField(new PasswordField());
        profileController.setCancelChangePassButton(new Button());
        profileController.setChangePasswordButton(new Button());
        profileController.setErrorLabel(new Label());

        //setting up the controller for the user infor change tsting
        profileController.setUserNameField(new TextField());
        profileController.setUserPhoneField(new TextField());
        profileController.setEditButton(new Button());
        profileController.setCancelEditButton(new Button());
        profileController.setUserEmailField(new TextField());
    }



    @Test
    public void ChangePasswordIntegrationTest() //the main focus of this method will be to check if the password has been successfully saved in the database after it has been changed
    {
       Assert.assertNotNull(profileController);

       //Platform.runLater was used because alertManager needs to be run in the JavaFX thread
       //Final variables are also required for it

       Platform.runLater(() -> {

           final String currentTestPassword = "motorstormtest";
           final int currentTestUserId = 10;

           profileController.setCurrentUserId(currentTestUserId); //it will be tested for user with id number 10;
           profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
           profileController.setNewPasswordFieldText(String.valueOf(currentTestPassword));
           profileController.changePasswordHandler();

           Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
           Assert.assertEquals(String.valueOf(currentTestPassword),testService.getCurrentPassword(currentTestUserId));

       });

        Platform.runLater(() -> {

            final String currentTestPassword = "apocalypsetest";
            final int currentTestUserId = 14;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setNewPasswordFieldText(String.valueOf(currentTestPassword));
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(String.valueOf(currentTestPassword),testService.getCurrentPassword(currentTestUserId));
        });

        Platform.runLater(() -> {

            final String currentTestPassword = "lunartecgrendel";
            final int currentTestUserId = 15;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setNewPasswordFieldText(String.valueOf(currentTestPassword));
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(String.valueOf(currentTestPassword),testService.getCurrentPassword(currentTestUserId));
        });

        Platform.runLater(() -> {

            final String currentTestPassword = "beowulfmaster123";
            final int currentTestUserId = 23;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setNewPasswordFieldText(currentTestPassword);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword,testService.getCurrentPassword(currentTestUserId));
        });

        Platform.runLater(() -> {

            final String currentTestPassword = "group935enjoyer";
            final int currentTestUserId = 17;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setNewPasswordFieldText(currentTestPassword);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword,testService.getCurrentPassword(currentTestUserId));
        });

        Platform.runLater(() -> {

            final String currentTestPassword = "jackalhunter3341";
            final int currentTestUserId = 11;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setNewPasswordFieldText(currentTestPassword);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword,testService.getCurrentPassword(currentTestUserId));
        });

        Platform.runLater(() -> {

            final String currentTestPassword = "199543200";
            final int currentTestUserId = 4;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setNewPasswordFieldText(String.valueOf(currentTestPassword));
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(String.valueOf(currentTestPassword),testService.getCurrentPassword(currentTestUserId));
        });

    }

    @Test
    public void ChangeUserInfoIntegrationTest() //this method will focus on testing if the user's new username and phone number have been successfuly saved in the database
    {
        Assert.assertNotNull(profileController);

        Platform.runLater(() -> {

            final String currentTestUsername = "robotBoy07";
            final String currentTestPhone = "0692334711";
            final int currentTestUserId = 4;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername);
            profileController.setPhoneNumberTextValue(currentTestPhone);
            profileController.editInfoHandler();

            User testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername, testUser.getUserName());
            Assert.assertEquals(currentTestPhone, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);


            final String currentTestUsername2 = "LilacHunter1998";
            final String currentTestPhone2 = "0711112233";


            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername2);
            profileController.setPhoneNumberTextValue(currentTestPhone2);
            profileController.editInfoHandler();

            testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername2, testUser.getUserName());
            Assert.assertEquals(currentTestPhone2, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
        });

        Platform.runLater(() -> {

            final String currentTestUsername = "Marina Willson";
            final String currentTestPhone = "06723442345";
            final int currentTestUserId = 20;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername);
            profileController.setPhoneNumberTextValue(currentTestPhone);
            profileController.editInfoHandler();

            User testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername, testUser.getUserName());
            Assert.assertEquals(currentTestPhone, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);


            final String currentTestUsername2 = "TreasureHunter19";
            final String currentTestPhone2 = "0699998877";


            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername2);
            profileController.setPhoneNumberTextValue(currentTestPhone2);
            profileController.editInfoHandler();

            testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername2, testUser.getUserName());
            Assert.assertEquals(currentTestPhone2, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
        });

        Platform.runLater(() -> {

            final String currentTestUsername = "Monika Leohart";
            final String currentTestPhone = "0681992121";
            final int currentTestUserId = 11;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername);
            profileController.setPhoneNumberTextValue(currentTestPhone);
            profileController.editInfoHandler();

            User testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername, testUser.getUserName());
            Assert.assertEquals(currentTestPhone, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);


            final String currentTestUsername2 = "Monika DreamCaster99";
            final String currentTestPhone2 = "0623331471";


            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername2);
            profileController.setPhoneNumberTextValue(currentTestPhone2);
            profileController.editInfoHandler();

            testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername2, testUser.getUserName());
            Assert.assertEquals(currentTestPhone2, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
        });

        Platform.runLater(() -> {

            final String currentTestUsername = "DonyaThink";
            final String currentTestPhone = "0684431915";
            final int currentTestUserId = 22;

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId));
            profileController.setUserNameTextValue(currentTestUsername);
            profileController.setPhoneNumberTextValue(currentTestPhone);
            profileController.editInfoHandler();

            User testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername, testUser.getUserName());
            Assert.assertEquals(currentTestPhone, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
        });

    }

}