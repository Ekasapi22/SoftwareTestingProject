package org.mr.abschlussprojekt.bikeRental.test;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mr.abschlussprojekt.bikeRental.Main;
import org.mr.abschlussprojekt.bikeRental.database.services.BikeService;
import org.mr.abschlussprojekt.bikeRental.database.services.StationService;
import org.mr.abschlussprojekt.bikeRental.gui.AdminBikesTabController;
import org.mr.abschlussprojekt.bikeRental.logic.ChangeSceneManager;
import org.mr.abschlussprojekt.bikeRental.model.Bike;
import org.mr.abschlussprojekt.bikeRental.model.BikeStatus;
import org.mr.abschlussprojekt.bikeRental.model.BikeType;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class AdminBikesTabControllerTest extends ApplicationTest {

    private AdminBikesTabController adminController;

    private BikeService bikeService;

    private StationService stationService;



    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);

    }

    @Override
    public void start(Stage stage) throws IOException {
        ChangeSceneManager.getInstance().switchToAdminDashboard();

    }

    @Before
    public void setUpForTests()
    {
         adminController = new AdminBikesTabController();
         bikeService = new BikeService();
         stationService = new StationService();

         adminController.setBikeService(bikeService);
         adminController.setStationService(stationService);

         adminController.setAddButton(new Button());
         adminController.setCancelButton(new Button());
         adminController.setUpdateButton(new Button());
         adminController.setTipText(new Label());
         adminController.setErrorLabel(new Label());
         adminController.setBikeModelField(new TextField());
         adminController.setBikePriceField(new TextField());
         adminController.setBikeStatusComboBox(new ComboBox<>());
         adminController.setBikeTypeComboBox(new ComboBox<>());
         adminController.setStationComboBox(new ComboBox<>());
    }

    @Test
    public void testDeleteBike() //this method tests if a bike gets deleted in the database
    {
        Platform.runLater(()->{

            int testBikeId = bikeService.addNewBikeAndGetId("TestModelDeletion", BikeType.E_BIKE, 188.9, 1, BikeStatus.AVAILABLE );

            Bike deletionBike = bikeService.getBikeById(testBikeId);

            adminController.activateListOfBikes();
            adminController.actuallyClickedOk();
            adminController.deleteBike(deletionBike);

            Assert.assertNull(bikeService.getBikeById(testBikeId));
            System.out.println("Bike Deletion - Test Case 1 finished");

        });
    }

    @Test
    public void testCreateBike()//this method tests if the bike is created in the database with the appropriate user data entered
    {
        String testModel = "Lunar3";
        String testPrice = "127.3";
        BikeStatus testStatus = BikeStatus.MAINTENANCE;
        BikeType testType = BikeType.MOUNTAIN;
        int testStationId = 1;

        adminController.setBikeModelTextFieldVal(testModel);
        adminController.setBikePriceFieldVal(testPrice);
        adminController.setBikeStatusVal(testStatus);
        adminController.setBikeTypeVal(testType);
        adminController.setStationValById(testStationId);
        adminController.createNewBike();

        Bike testBike = bikeService.getBikeById(adminController.currentRegisteredBike);

        Assert.assertNotNull(testBike);
        Assert.assertEquals(testModel,testBike.getBikeModel());
        Assert.assertEquals(Double.parseDouble(testPrice),testBike.getBikePrice(),1);
        Assert.assertEquals(testStatus,testBike.getBikeStatus());
        Assert.assertEquals(testType,testBike.getBikeType());
        Assert.assertEquals(testStationId, stationService.getStationIdByLocation(testBike.getBikeLocation()));

        //cleanup
        bikeService.deleteBikeFromDB(testBike.getBikeId());
        System.out.println("Bike Creation - Test Case 1 finished");

    }

    @Test
    public void testEditBike()
    {

        Platform.runLater(()->{

            String testModel = "Atlas Earthquake";
            String testPrice = "500.91";
            BikeStatus testStatus = BikeStatus.MAINTENANCE;
            BikeType testType = BikeType.MOUNTAIN;
            int testStationId = 1;
            int testBikeID = bikeService.addNewBikeAndGetId("TestEditBike", BikeType.CARGO, 150.0, 3, BikeStatus.AVAILABLE);

            Bike testBike = bikeService.getBikeById(testBikeID);

            adminController.activateListOfBikes();
            adminController.populateFieldsForEditing(testBike);
            adminController.setBikeModelTextFieldVal(testModel);
            adminController.setBikePriceFieldVal(testPrice);
            adminController.setBikeStatusVal(testStatus);
            adminController.setBikeTypeVal(testType);
            adminController.setStationValById(testStationId);
            adminController.updateBikeHandler(new ActionEvent());

            Bike resultBike = bikeService.getBikeById(testBikeID);
            Assert.assertNotNull(resultBike);
            Assert.assertEquals(testModel, resultBike.getBikeModel());
            Assert.assertEquals(Double.parseDouble(testPrice),resultBike.getBikePrice(),1);
            Assert.assertEquals(testStatus, resultBike.getBikeStatus());
            Assert.assertEquals(testType,resultBike.getBikeType());
            Assert.assertEquals(testStationId, stationService.getStationIdByLocation(resultBike.getBikeLocation()));

            //cleanup
            bikeService.deleteBikeFromDB(resultBike.getBikeId());
            System.out.println("Bike Editing - Test case 1 Finished");

        });



    }

}


