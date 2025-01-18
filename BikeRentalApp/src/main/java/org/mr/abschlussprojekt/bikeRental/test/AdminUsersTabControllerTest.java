package org.mr.abschlussprojekt.bikeRental.test;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mr.abschlussprojekt.bikeRental.Main;
import org.mr.abschlussprojekt.bikeRental.database.services.UserService;
import org.mr.abschlussprojekt.bikeRental.gui.AdminUsersTabController;
import org.mr.abschlussprojekt.bikeRental.logic.ChangeSceneManager;
import org.mr.abschlussprojekt.bikeRental.model.User;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class AdminUsersTabControllerTest extends ApplicationTest {

    UserService testService;
    AdminUsersTabController adminController;

    @Before
    public void setUpClass() throws Exception
    {
        ApplicationTest.launch(Main.class);

    }

    @Override
    public void start(Stage stage) throws IOException
    {
        ChangeSceneManager.getInstance().switchToAdminDashboard();
        //stage.show();
    }

    @Before
    public void setupForTests()
    {


        testService = new UserService();


        adminController = new AdminUsersTabController();

        adminController.setUserService(testService);
        adminController.setUserNameField(new TextField());
        adminController.setUserPhoneField(new TextField());
        adminController.setUserEmailField(new TextField());
        adminController.setUserPasswordField(new TextField());


        adminController.activateAddButton();
        adminController.setCancelButton(new Button());
        adminController.setTipText(new Label());
    }

    @Test
    public void testAdminUserDelete()//tests for admin deleting a user
    {

        Platform.runLater(()->{

            //Test Case 1
            int userTestId = testService.registerUserAndGetId("deletetest1","0911112233","deletiontest@gmail.com","sifapsfafaf");
            User testUser = testService.getUserById(userTestId);

            adminController.activateListOfUsers();
            adminController.setUserClickedOk(true);
            adminController.deleteUser(testUser);

            Assert.assertNull(testService.getUserById(userTestId));
            System.out.println("Admin user deletion - Test Case 1 Finished");
        });

        Platform.runLater(()->{
            //Test Case 2
            int userTestId2 = testService.registerUserAndGetId("deletetest2","093431111","deletionMax@gmail.com","afoajfiasfa");
            User testUser2 = testService.getUserById(userTestId2);

            adminController.activateListOfUsers();
            adminController.setUserClickedOk(true);
            adminController.deleteUser(testUser2);

            Assert.assertNull(testService.getUserById(userTestId2));
            System.out.println("Admin user deletion - Test Case 2 Finished");
        });

        Platform.runLater(()->{
            //Test Case 3
            int userTestId3 = testService.registerUserAndGetId("deletetest3","0978889977","deletionkilla@gmail.com","saspifjapsfi");
            User testUser3 = testService.getUserById(userTestId3);

            adminController.activateListOfUsers();
            adminController.setUserClickedOk(true);
            adminController.deleteUser(testUser3);

            Assert.assertNull(testService.getUserById(userTestId3));
            System.out.println("Admin user deletion - Test Case 3 Finished");
        });

        Platform.runLater(()->{
            //Test Case 4
            int userTestId4 = testService.registerUserAndGetId("deletetest4","0913334455","deletionmidd@gmail.com","asfasfagagwwfqf");
            User testUser4 = testService.getUserById(userTestId4);

            adminController.activateListOfUsers();
            adminController.setUserClickedOk(true);
            adminController.deleteUser(testUser4);

            Assert.assertNull(testService.getUserById(userTestId4));
            System.out.println("Admin user deletion - Test Case 4 Finished");
        });
    }

    @Test
    public void testAdminUserCreate()//tests for admin creating a user
    {
        Platform.runLater(() -> {

            //New users are created and deleted soon after in order not to overcrowd the database

            //Test Case 1
            String testUserName = "john wayne";
            String testUserPhone = "07788881415";
            String testUserEmail = "waynethejohn@gmail.com";
            String testUserPassword = "12345";

            adminController.setUserNameFieldValue(testUserName);
            adminController.setUserPhoneFieldValue(testUserPhone);
            adminController.setUserEmailFieldValue(testUserEmail);
            adminController.setUserPasswordFieldValue(testUserPassword);

            adminController.createNewUser();

            User testCreatedUser = testService.getUserById(adminController.registeredUser);

            Assert.assertEquals(testUserName, testCreatedUser.getUserName());
            Assert.assertEquals(testUserPhone, testCreatedUser.getUserPhone());
            Assert.assertEquals(testUserEmail, testCreatedUser.getUserEmail());
            Assert.assertEquals(testUserPassword, testService.getCurrentPassword(adminController.registeredUser));

            testService.deleteUser(adminController.registeredUser);
            System.out.println("Admin user creation - Test Case 1 Finished");
        });

        Platform.runLater(()->{
            //Test Case 2
            String testUserName2 = "Leon Kennedy";
            String testUserPhone2 = "0712234455";
            String testUserEmail2 = "theEvilResident@gmail.com";
            String testUserPassword2 = "hastaluego";

            adminController.setUserNameFieldValue(testUserName2);
            adminController.setUserPhoneFieldValue(testUserPhone2);
            adminController.setUserEmailFieldValue(testUserEmail2);
            adminController.setUserPasswordFieldValue(testUserPassword2);

            adminController.createNewUser();

            User testCreatedUser2 = testService.getUserById(adminController.registeredUser);

            Assert.assertEquals(testUserName2, testCreatedUser2.getUserName());
            Assert.assertEquals(testUserPhone2, testCreatedUser2.getUserPhone());
            Assert.assertEquals(testUserEmail2, testCreatedUser2.getUserEmail());
            Assert.assertEquals(testUserPassword2, testService.getCurrentPassword(adminController.registeredUser));

            testService.deleteUser(adminController.registeredUser);
            System.out.println("Admin user creation - Test Case 2 Finished");
        });

    }

    @Test
    public void testAdminEditUser()
    {
        Platform.runLater(()->{

            //Test case 1
            final int testUserID = testService.registerUserAndGetId("testedit1","0695551144","edittesting@gmail.com","fffgggg");
            User testUser = testService.getUserById(testUserID);

            String testUserName = "jackTheRipper";
            String testUserPhone = "0884442211";
            String testUserEmail = "ripperjack@gmail.com";
            String testUserPassword = "1985321";

            adminController.activateListOfUsers();
            adminController.editUser(testUser);
            adminController.setUserNameFieldValue(testUserName);
            adminController.setUserPhoneFieldValue(testUserPhone);
            adminController.setUserEmailFieldValue(testUserEmail);
            adminController.setUserPasswordFieldValue(testUserPassword);
            adminController.updateUser();

            User resultUser = testService.getUserById(testUserID);
            Assert.assertEquals(testUserName, resultUser.getUserName());
            Assert.assertEquals(testUserPhone, resultUser.getUserPhone());
            Assert.assertEquals(testUserEmail, resultUser.getUserEmail());
            Assert.assertEquals(testUserPassword, testService.getCurrentPassword(testUserID));

            //cleanup
            testService.deleteUser(testUserID);
            System.out.println("Admin user edit - Test Case 1 Finished");
        });
    }


}