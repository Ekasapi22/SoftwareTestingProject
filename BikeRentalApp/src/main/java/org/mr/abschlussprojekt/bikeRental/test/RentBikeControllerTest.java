package org.mr.abschlussprojekt.bikeRental.test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mr.abschlussprojekt.bikeRental.Main;
import org.mr.abschlussprojekt.bikeRental.database.services.RentService;
import org.mr.abschlussprojekt.bikeRental.database.services.UserService;
import org.mr.abschlussprojekt.bikeRental.gui.RentBikeController;
import org.mr.abschlussprojekt.bikeRental.logic.ChangeSceneManager;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.mr.abschlussprojekt.bikeRental.model.Bike;




/* these are for mockito*/
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;


public class RentBikeControllerTest extends ApplicationTest {

    private RentBikeController controller;
    /*private DatePicker testStartDatePicker; //keeping these for now
    private DatePicker testEndDatePicker;
    private TextField testPricefield;
    private Button testButton;
    private Label testErrorLabel;
    private Label testTotalPriceLabel;
    */


    @Before
    public void setUpClass() throws Exception
    {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        //ChangeSceneManager.getInstance().switchToUserHome();
        stage.show();
    }


    @Before
    public void setupCalculatePrice()
    {
        //the calculatepricemethod needs these components to work
        DatePicker startdatePicker = new DatePicker();
        DatePicker enddatePicker = new DatePicker();
        TextField priceField = new TextField();
        Button testbutton = new Button();
        Label testErrorLabel = new Label("test");
        Label testtotalPriceLabel = new Label("test");

        controller = new RentBikeController();
        controller.setStartDatePicker(startdatePicker);
        controller.setEndDatePicker(enddatePicker);
        controller.setPriceField(priceField);
        controller.setConfirmRentalButton(testbutton);
        controller.setTotalPriceLabel(testErrorLabel);
        controller.setRentErrorLabel(testtotalPriceLabel);
    }

    //the method selected here is the "calculatePrice" method
    //Here's the boundary value test for it
    @Test
    public void calculatePriceNormalBoundaryValueTest()
    {
        //This testing method will focus on some nominal variables and variables close to bounds
        Assert.assertNotNull(controller); //to check that the controller has been made properly

        //Test case 1
        controller.setStartDate(LocalDate.of(2025,2,2));
        controller.setEndDate(LocalDate.of(2025,2,4));
        controller.setPrice("100");
        Assert.assertEquals(300,(int)controller.calculatePrice());

        //Test case 2
        controller.setStartDate(LocalDate.of(2025,2,3));
        controller.setEndDate(LocalDate.of(2025,2,7));
        controller.setPrice("200");
        Assert.assertEquals(1000,(int)controller.calculatePrice());

        //Test case 3
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.now());
        controller.setPrice("50");
        Assert.assertEquals(50,(int)controller.calculatePrice());

        //Test case 4
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.now());
        controller.setPrice("1");
        Assert.assertEquals(1,(int)controller.calculatePrice());

        //Test case 5
        controller.setStartDate(LocalDate.of(2025,3,3));
        controller.setEndDate(LocalDate.of(2025,4,3));
        controller.setPrice("1");
        Assert.assertEquals(32,(int)controller.calculatePrice());

        //Test case 6
        controller.setStartDate(LocalDate.of(2025,2,5));
        controller.setEndDate(LocalDate.of(2025,2,10));
        controller.setPrice("2");
        Assert.assertEquals(12,(int)controller.calculatePrice());

        //Test case 7
        controller.setStartDate(LocalDate.of(2025,5,2));
        controller.setEndDate(LocalDate.of(2025,10,2));
        controller.setPrice("50");
        Assert.assertEquals(7700,(int)controller.calculatePrice());

        //Test case 8
        controller.setStartDate(LocalDate.of(2025,5,2));
        controller.setEndDate(LocalDate.of(2025,10,2));
        controller.setPrice("2");
        Assert.assertEquals(308,(int)controller.calculatePrice());

    }

    @Test
    public void calculatePriceRobustExtremeValueTest()
    {   //this testing method will focus on values out of bounds and some extreme cases
        Assert.assertNotNull(controller);

        //Test case 1
        controller.setStartDate(LocalDate.of(2025,7,8));
        controller.setEndDate(LocalDate.of(2030,5,9));
        controller.setPrice("1300");
        Assert.assertEquals(2297100,(int)controller.calculatePrice());

        //Test case 2
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.of(2049,5,7));
        controller.setPrice("18000");
        int durationOfExpected = (int)Duration.between( LocalDateTime.now(), LocalDateTime.of( LocalDate.of(2049,5,7), LocalTime.now())).toDays() + 1; //LocalDateTime was used instead
        int priceExpected = durationOfExpected * 18000;
        Assert.assertEquals(priceExpected,(int)controller.calculatePrice());

        //Test case 3
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.now());
        controller.setPrice("0");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());


        //Test case 4
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.now());
        controller.setPrice("-89999");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

        //Test case 5
        controller.setStartDate(LocalDate.of(2025,7,8));
        controller.setEndDate(LocalDate.of(2022,7,8));
        controller.setPrice("77");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        //Test case 6
        controller.setStartDate(LocalDate.of(2025,5,3));
        controller.setEndDate(LocalDate.of(2025,4,3));
        controller.setPrice("77");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        //Test case 7
        controller.setStartDate(LocalDate.of(2025,7,9));
        controller.setEndDate(LocalDate.of(2025,7,8));
        controller.setPrice("77");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

    }

    //The equivalence class methods have been listed below
    @Test
    public void calculatePriceBetweenDays()
    {
        Assert.assertNotNull(controller);

        //Test case 1 & 2 - Dates are equal
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.now());
        controller.setPrice("500");
        Assert.assertEquals(500,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,2,3));
        controller.setEndDate(LocalDate.of(2025,2,3));
        controller.setPrice("700");
        Assert.assertEquals(700,(int)controller.calculatePrice());

        //Test case 3 & 4 - Dates are at the first week of a month, price between several days
        controller.setStartDate(LocalDate.of(2025, 7, 1 ));
        controller.setEndDate(LocalDate.of(2025,7,5));
        controller.setPrice("200");
        Assert.assertEquals(1000,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,5,3));
        controller.setEndDate(LocalDate.of(2025,5,5));
        controller.setPrice("300");
        Assert.assertEquals(900,(int)controller.calculatePrice());

        //Test case 5 & 6 - Dates are at the last week of a month
        controller.setStartDate(LocalDate.of(2025,5,25));
        controller.setEndDate(LocalDate.of(2025,5,30));
        controller.setPrice("300");
        Assert.assertEquals(1800,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,2,22));
        controller.setEndDate(LocalDate.of(2025,2,28));
        controller.setPrice("300");
        Assert.assertEquals(2100,(int)controller.calculatePrice());

        //Test case 7 & 8 - Dates are between months
        controller.setStartDate(LocalDate.of(2025,4,29));
        controller.setEndDate(LocalDate.of(2025,5,3));
        controller.setPrice("400");
        Assert.assertEquals(2000,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,2,28));
        controller.setEndDate(LocalDate.of(2025,3,5));
        controller.setPrice("500");
        Assert.assertEquals(3000,(int)controller.calculatePrice());

    }

    @Test
    public void calculatePriceBetweenMonths()
    {
        Assert.assertNotNull(controller);

        //Test case 1 & 2 - Month has 30 days
        controller.setStartDate(LocalDate.of(2025,4,20));
        controller.setEndDate(LocalDate.of(2025,5,20));
        controller.setPrice("500");
        Assert.assertEquals(500*31,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,6,17));
        controller.setEndDate(LocalDate.of(2025,7,17));
        controller.setPrice("700");
        Assert.assertEquals(700*31,(int)controller.calculatePrice());

        //Test case 3 & 4 - Month has 31 days
        controller.setStartDate(LocalDate.of(2025,7,10));
        controller.setEndDate(LocalDate.of(2025,8,10));
        controller.setPrice("1000");
        Assert.assertEquals(1000*32,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,10,27));
        controller.setEndDate(LocalDate.of(2025,11,27));
        controller.setPrice("800");
        Assert.assertEquals(800*32,(int)controller.calculatePrice());

        //Test case 5 - Month is February, non-leap year
        controller.setStartDate(LocalDate.of(2025,2,28));
        controller.setEndDate(LocalDate.of(2025,3,28));
        controller.setPrice("500");
        Assert.assertEquals(500*29,(int)controller.calculatePrice());

        //Test case 6 - Month is February, leap year
        controller.setStartDate(LocalDate.of(2028,2,29));
        controller.setEndDate(LocalDate.of(2028,3,29));
        controller.setPrice("100");
        Assert.assertEquals(100*30,(int)controller.calculatePrice());

        //Test case 7 & 8 - Prices between several months
        controller.setStartDate(LocalDate.of(2025,7,25));
        controller.setEndDate(LocalDate.of(2025,10,25));
        controller.setPrice("500");
        Assert.assertEquals(500*(31+31+30+1),(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,11,9));
        controller.setEndDate(LocalDate.of(2026,4,9));
        controller.setPrice("800");
        Assert.assertEquals(800*(30+31+31+28+31+1),(int)controller.calculatePrice());
    }

    @Test
    public void calculatePriceBetweenYears()
    {
        //Test case 1 & 2 - Price between a non-leap year
        controller.setStartDate(LocalDate.of(2025,3,22));
        controller.setEndDate(LocalDate.of(2026,3,22));
        controller.setPrice("700");
        Assert.assertEquals(700 * 366,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2029,8,17));
        controller.setEndDate(LocalDate.of(2030,8,17));
        controller.setPrice("400");
        Assert.assertEquals(400 * 366,(int)controller.calculatePrice());

        //Test case 3 & 4 - Price between leap years
        controller.setStartDate(LocalDate.of(2028,4,21));
        controller.setEndDate(LocalDate.of(2029,4,22));
        controller.setPrice("800");
        Assert.assertEquals(800 * 367,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2032,2,29));
        controller.setEndDate(LocalDate.of(2033,3,1));
        controller.setPrice("700");
        Assert.assertEquals(700 * 367,(int)controller.calculatePrice());

        //Test case 5 & 6 - Price between several years
        controller.setStartDate(LocalDate.of(2025,3,22));
        controller.setEndDate(LocalDate.of(2027,3,22));
        controller.setPrice("700");
        Assert.assertEquals(700 * (365+365+1),(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2028,5,27));
        controller.setEndDate(LocalDate.of(2033,5,28));
        controller.setPrice("1300");
        Assert.assertEquals(1300 * (366+365+365+365+366+1),(int)controller.calculatePrice());
    }

    //This method tests the cases when the start date is before the end date
    //a few test cases for this were done above, but it wil be tested more precisely here
    @Test
    public void calculatePriceStartBeforeEnd()
    {
        //Test case 1 & 2 - The day is off
        controller.setStartDate(LocalDate.of(2025,2,28));
        controller.setEndDate(LocalDate.of(2025,2,27));
        controller.setPrice("700");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.of(2025,4,25));
        controller.setEndDate(LocalDate.of(2025,4,11));
        controller.setPrice("700");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        //Test case 3 & 4 - The month is off
        controller.setStartDate(LocalDate.of(2025,7,28));
        controller.setEndDate(LocalDate.of(2025,6,29));
        controller.setPrice("700");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.of(2025,8,15));
        controller.setEndDate(LocalDate.of(2025,3,20));
        controller.setPrice("700");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        //Test case 5 & 6 - The year is off
        controller.setStartDate(LocalDate.of(2025,5,14));
        controller.setEndDate(LocalDate.of(2024,5,14));
        controller.setPrice("700");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.of(2025,3,11));
        controller.setEndDate(LocalDate.of(2020,9,15));
        controller.setPrice("700");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());
    }

    @Test
    public void calculatePriceIntegerTest()
    {
        //Test case 1 & 2 & 3
        controller.setStartDate(LocalDate.of(2025,2,17));
        controller.setEndDate(LocalDate.of(2025,2,27));
        controller.setPrice("73");
        controller.calculatePrice();
        Assert.assertEquals(73 * 11,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,4,22));
        controller.setEndDate(LocalDate.of(2025,5,30));
        controller.setPrice("351");
        controller.calculatePrice();
        Assert.assertEquals(351 * 39,(int)controller.calculatePrice());

        controller.setStartDate(LocalDate.of(2025,3,10));
        controller.setEndDate(LocalDate.of(2025,3,23));
        controller.setPrice("1252");
        controller.calculatePrice();
        Assert.assertEquals(1252 * 14,(int)controller.calculatePrice());

    }

    @Test
    public void calculatePriceDoubleTest()
    {
        //Test case 1 & 2 & 3
        controller.setStartDate(LocalDate.of(2025,7,11));
        controller.setEndDate(LocalDate.of(2025,7,25));
        controller.setPrice("25.7");
        controller.calculatePrice();
        Assert.assertEquals(25.7 * 15,controller.calculatePrice(),0.1);

        controller.setStartDate(LocalDate.of(2025,1,3));
        controller.setEndDate(LocalDate.of(2025,1,19));
        controller.setPrice("134.19");
        controller.calculatePrice();
        Assert.assertEquals(134.19 * 17,controller.calculatePrice(),0.1);

        controller.setStartDate(LocalDate.of(2025,8,7));
        controller.setEndDate(LocalDate.of(2025,9,25));
        controller.setPrice("2517.378");
        controller.calculatePrice();
        Assert.assertEquals(2517.378 * 50,controller.calculatePrice(),0.1);

    }

    @Test
    public void calculatePriceNegativeOrZero()
    {
        //Test case 1 & 2 - Price is Negative
        controller.setStartDate(LocalDate.of(2025,4,19));
        controller.setEndDate(LocalDate.of(2025,4,30));
        controller.setPrice("-889");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.of(2025,8,29));
        controller.setPrice("-1534.199");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

        //Test case 3 & 4 - Price is zero
        controller.setStartDate(LocalDate.of(2025,8,19));
        controller.setEndDate(LocalDate.of(2025,11,8));
        controller.setPrice("0");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.of(2025,3,15));
        controller.setEndDate(LocalDate.of(2025,5,7));
        controller.setPrice("0");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

    }

    @Test
    public void CalculatePriceCoverageTesting()
    {
        //Test case 1 & 2 - Normal price calculations section
        controller.setStartDate(LocalDate.of(2025,2,15));
        controller.setEndDate(LocalDate.of(2025,2,19));
        controller.setPrice("135.7");
        Assert.assertEquals(135.7 * 5,controller.calculatePrice(),0.1);

        controller.setStartDate(LocalDate.of(2025,3,29));
        controller.setEndDate(LocalDate.of(2025,4,15));
        controller.setPrice("250");
        Assert.assertEquals(250 * 18,(int)controller.calculatePrice());

        //Test case 3 - StartDate is null
        LocalDate nullStart = null;
        controller.setStartDate(nullStart);
        controller.setEndDate(LocalDate.now());
        controller.setPrice("135.7");
        controller.calculatePrice();
        Assert.assertEquals("Please select both start and end dates.",controller.getRentErrorLabel());

        //Test case 4 - EndDate is null
        LocalDate nullEnd = null;
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(nullEnd);
        controller.setPrice("135.7");
        controller.calculatePrice();
        Assert.assertEquals("Please select both start and end dates.",controller.getRentErrorLabel());

        //Test case 5 - Both dates are null
        LocalDate nullStartTwo = null;
        LocalDate nullEndTwo = null;
        controller.setStartDate(nullStartTwo);
        controller.setEndDate(nullEndTwo);
        controller.setPrice("135.7");
        controller.calculatePrice();
        Assert.assertEquals("Please select both start and end dates.",controller.getRentErrorLabel());

        //Test case 6 - End Date is before Start date section
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.of(2024,11,1));
        controller.setPrice("135.7");
        controller.calculatePrice();
        Assert.assertEquals("Please ensure that the end date is not before the start date.",controller.getRentErrorLabel());

        //Test case 7 & 8 - Price is of invalid value
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.of(2025,3,19));
        controller.setPrice("0");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.of(2025,4,3));
        controller.setEndDate(LocalDate.of(2025,5,15));
        controller.setPrice("-3314.759");
        controller.calculatePrice();
        Assert.assertEquals("Bike is of Invalid price, the transaction cannot be completed.",controller.getRentErrorLabel());

        //Test case 9 & 10 - Price is of wrong format or not a number section
        controller.setStartDate(LocalDate.now());
        controller.setEndDate(LocalDate.of(2025,3,19));
        controller.setPrice("24.44.33");
        controller.calculatePrice();
        Assert.assertEquals("Error in parsing the daily price multiple points", controller.getRentErrorLabel());

        controller.setStartDate(LocalDate.of(2025,4,3));
        controller.setEndDate(LocalDate.of(2025,5,15));
        String input = "abcdefg";
        controller.setPrice(input);
        controller.calculatePrice();
        Assert.assertEquals("Error in parsing the daily price For input string: " + "\"" + input + "\"" , controller.getRentErrorLabel());
    }

    //The above methods focus on the method's behavior rather than implementation

}