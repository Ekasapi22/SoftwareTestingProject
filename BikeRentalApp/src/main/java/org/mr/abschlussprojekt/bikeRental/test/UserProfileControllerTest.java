package org.mr.abschlussprojekt.bikeRental.test;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.runner.OrderWith;
import org.mr.abschlussprojekt.bikeRental.Main;
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

    //It is important to note that these methods were tested with existing users
    //changes here will also reflect in the database

    private UserProfileController profileController;
    private UserService testService;

    private final int[] userIds = { 1, 2, 4, 5, 8, 10, 11};
    ;



    @Before
    public void setUpClass() throws Exception
    {
        ApplicationTest.launch(Main.class);
    }

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


        //setting up the controller for the password change testing
        profileController.setAltPasswordField(new PasswordField());
        profileController.setNewPasswordField(new PasswordField());
        profileController.setConfirmPasswordField(new PasswordField());
        profileController.setCancelChangePassButton(new Button());
        profileController.setChangePasswordButton(new Button());
        profileController.setErrorLabel(new Label());

        //setting up the controller for the user info change testing
        profileController.setUserNameField(new TextField());
        profileController.setUserPhoneField(new TextField());
        profileController.setEditButton(new Button());
        profileController.setCancelEditButton(new Button());
        profileController.setUserEmailField(new TextField());


    }

    //Platform.runLater was used because alertManager needs to be run in the JavaFX thread

    @Test
    public void ChangePasswordIntegrationTest() //the main focus of this method will be to check if the password has been successfully saved in the database after it has been changed
    {
       Assert.assertNotNull(profileController);

       Platform.runLater(() -> {

            //Test Case 1
            String currentTestPassword1 = "motorstormtestinggg";
            int currentTestUserId1 = userIds[4];

            profileController.setCurrentUserId(currentTestUserId1); //it will be tested for user with id number 10;
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId1));
            profileController.setNewPasswordFieldText(currentTestPassword1);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword1,testService.getCurrentPassword(currentTestUserId1));
            System.out.println("User password - Test Case 1 Finished");
       });

        Platform.runLater(()->{

            //Test Case 2
            String currentTestPassword2 = "apocalypsetest2";
            int currentTestUserId2 = userIds[3];

            profileController.setCurrentUserId(currentTestUserId2);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId2));
            profileController.setNewPasswordFieldText(currentTestPassword2);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword2,testService.getCurrentPassword(currentTestUserId2));
            System.out.println("User password - Test Case 2 Finished");

       });

        Platform.runLater(()->{

            //Tst Case 3
            String currentTestPassword3 = "lunartecgrendellll";
            int currentTestUserId3 = userIds[2];

            profileController.setCurrentUserId(currentTestUserId3);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId3));
            profileController.setNewPasswordFieldText(currentTestPassword3);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword3,testService.getCurrentPassword(currentTestUserId3));
            System.out.println("User password - Test Case 3 Finished");
       });

        Platform.runLater(()->{

            //Test Case 4
            String currentTestPassword4 = "beowulfmaster12345";
            int currentTestUserId4 = userIds[1];

            profileController.setCurrentUserId(currentTestUserId4);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId4));
            profileController.setNewPasswordFieldText(currentTestPassword4);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword4,testService.getCurrentPassword(currentTestUserId4));
            System.out.println("User password - Test Case 4 Finished");
       });

        Platform.runLater(()->{

            //Test case 5
            String currentTestPassword5 = "group935supreme";
            int currentTestUserId5 = userIds[0];

            profileController.setCurrentUserId(currentTestUserId5);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId5));
            profileController.setNewPasswordFieldText(currentTestPassword5);
            profileController.changePasswordHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            Assert.assertEquals(currentTestPassword5,testService.getCurrentPassword(currentTestUserId5));
            System.out.println("User password - Test Case 5 Finished");
        });
    }

    @Test
    public void ChangeUserInfoIntegrationTest() //this method will focus on testing if the user's new username and phone number have been successfuly saved in the database
    {
        Assert.assertNotNull(profileController);

        Platform.runLater(() -> {
            //Test Case 1
            String currentTestUsername = "robotBoy34";
            String currentTestPhone = "0692334711";
            int currentTestUserId = userIds[0];

            profileController.setCurrentUserId(currentTestUserId);
            profileController.setUserNameTextValue(currentTestUsername);
            profileController.setPhoneNumberTextValue(currentTestPhone);
            profileController.editInfoHandler();

            User testUser = testService.getUserById(currentTestUserId);
            Assert.assertEquals(currentTestUsername, testUser.getUserName());
            Assert.assertEquals(currentTestPhone, testUser.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 1 Finished");
        });

        Platform.runLater(()->{
            //Test Case 2
            String currentTestUsername2 = "LilacGatherer";
            String currentTestPhone2 = "0993334422";
            int currentTestUserId2 = userIds[1];


            profileController.setCurrentUserId(currentTestUserId2);
            profileController.setUserNameTextValue(currentTestUsername2);
            profileController.setPhoneNumberTextValue(currentTestPhone2);
            profileController.editInfoHandler();

            User testUser2 = testService.getUserById(currentTestUserId2);
            Assert.assertEquals(currentTestUsername2, testUser2.getUserName());
            Assert.assertEquals(currentTestPhone2, testUser2.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 2 Finished");
        });

        Platform.runLater(()->{
            //Test Case 3
            String currentTestUsername3 = "Marina Domek";
            String currentTestPhone3 = "06723442345";
            int currentTestUserId3 = userIds[2];

            profileController.setCurrentUserId(currentTestUserId3);
            profileController.setUserNameTextValue(currentTestUsername3);
            profileController.setPhoneNumberTextValue(currentTestPhone3);
            profileController.editInfoHandler();

            User testUser3 = testService.getUserById(currentTestUserId3);
            Assert.assertEquals(currentTestUsername3, testUser3.getUserName());
            Assert.assertEquals(currentTestPhone3, testUser3.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 3 Finished");
        });

        Platform.runLater(()->{
            //Test Case 4
            String currentTestUsername4 = "TreasureHunterPirate";
            String currentTestPhone4 = "0699998877";
            int currentTestUserId4 = userIds[3];

            profileController.setCurrentUserId(currentTestUserId4);
            profileController.setAltPasswordFieldText(testService.getCurrentPassword(currentTestUserId4));
            profileController.setUserNameTextValue(currentTestUsername4);
            profileController.setPhoneNumberTextValue(currentTestPhone4);
            profileController.editInfoHandler();

            User testUser4 = testService.getUserById(currentTestUserId4);
            Assert.assertEquals(currentTestUsername4, testUser4.getUserName());
            Assert.assertEquals(currentTestPhone4, testUser4.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 4 Finished");

        });

        Platform.runLater(()->{
            //Test Case 5
            String currentTestUsername5 = "Monika Livingston";
            String currentTestPhone5 = "0681992121";
            int currentTestUserId5 = userIds[4];

            profileController.setCurrentUserId(currentTestUserId5);
            profileController.setUserNameTextValue(currentTestUsername5);
            profileController.setPhoneNumberTextValue(currentTestPhone5);
            profileController.editInfoHandler();

            User testUser5 = testService.getUserById(currentTestUserId5);
            Assert.assertEquals(currentTestUsername5, testUser5.getUserName());
            Assert.assertEquals(currentTestPhone5, testUser5.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 5 Finished");
        });

        Platform.runLater(()->{

            //Test Case 6
            String currentTestUsername6 = "Richard LionHeart";
            String currentTestPhone6 = "0623331471";
            int currentTestUserId6 = userIds[5];

            profileController.setCurrentUserId(currentTestUserId6);
            profileController.setUserNameTextValue(currentTestUsername6);
            profileController.setPhoneNumberTextValue(currentTestPhone6);
            profileController.editInfoHandler();

            User testUser6 = testService.getUserById(currentTestUserId6);
            Assert.assertEquals(currentTestUsername6, testUser6.getUserName());
            Assert.assertEquals(currentTestPhone6, testUser6.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 6 Finished");
        });

        Platform.runLater(()->{

            //Test Case 7
            String currentTestUsername7 = "Noneed15";
            String currentTestPhone7 = "0694431915";
            int currentTestUserId7 = userIds[6];

            profileController.setCurrentUserId(currentTestUserId7);
            profileController.setUserNameTextValue(currentTestUsername7);
            profileController.setPhoneNumberTextValue(currentTestPhone7);
            profileController.editInfoHandler();

            User testUser7 = testService.getUserById(currentTestUserId7);
            Assert.assertEquals(currentTestUsername7, testUser7.getUserName());
            Assert.assertEquals(currentTestPhone7, testUser7.getUserPhone());
            Assert.assertEquals(profileController.getResultString(), AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            System.out.println("User info - Test Case 7 Finished");

        });

    }

    @Test
    public void userDeletesOwnAccountTest() //this test focuses on the user deleting his own account
    {
        //New users will be registered so that we won't have to use existing users and compromise the information in the database


        Platform.runLater(()->{

            //Test Case 1
            final int currentTestId = testService.registerUserAndGetId("Bruce Wayne","0689993434","richkid99@gmail.com","imsecretlybatman");

            profileController.setCurrentUserId(currentTestId);
            profileController.setUserClickedOk(true);
            profileController.deleteAccountHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.BEEN_SUCCESSFULLY_DELETED);
            Assert.assertNull(testService.getUserById(currentTestId));
            System.out.println("User deletion - Test Case 1 Finished");

        });

        Platform.runLater(()->{

            //Test Case 2
            final int currentTestId2 = testService.registerUserAndGetId("Jay Jonnah Jameson","0688983355","iwwantspiderman@gmail.com","spidermanisamenace");


            profileController.setCurrentUserId(currentTestId2);
            profileController.setUserClickedOk(true);
            profileController.deleteAccountHandler();

            Assert.assertEquals(profileController.getResultString(), AppTexts.BEEN_SUCCESSFULLY_DELETED);
            Assert.assertNull(testService.getUserById(currentTestId2));
            System.out.println("User deletion - Test Case 2 Finished");

        });

    }

}