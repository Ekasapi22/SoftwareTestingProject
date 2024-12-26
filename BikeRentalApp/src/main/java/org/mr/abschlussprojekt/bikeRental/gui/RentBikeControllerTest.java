package org.mr.abschlussprojekt.bikeRental.gui;

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
import org.testfx.framework.junit.ApplicationTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.mr.abschlussprojekt.bikeRental.model.Bike;




/* these are for mockito
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
*/

public class RentBikeControllerTest extends ApplicationTest {

    private RentBikeController controller;
    /*private DatePicker testStartDatePicker; //keeping these for now
    private DatePicker testEndDatePicker;
    private TextField testPricefield;
    private Button testButton;
    private Label testErrorLabel;
    private Label testTotalPriceLabel;
    */


    @Override
    public void start(Stage stage)
    {
        stage.setScene(new Scene(new StackPane(), 100, 100));
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

}