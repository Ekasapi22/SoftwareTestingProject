package org.mr.abschlussprojekt.bikeRental.gui;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.mr.abschlussprojekt.bikeRental.database.services.DatabaseService;
import org.mr.abschlussprojekt.bikeRental.database.services.UserService;
import org.mr.abschlussprojekt.bikeRental.logic.AlertManager;
import org.mr.abschlussprojekt.bikeRental.logic.CurrentUser;
import org.mr.abschlussprojekt.bikeRental.logic.Validator;
import org.mr.abschlussprojekt.bikeRental.model.User;
import org.mr.abschlussprojekt.bikeRental.setting.AppTexts;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller class for the User Profile UI. This class manages the user's information,
 */
public class UserProfileController implements Initializable {

    /*
    Attributes---------------------------------------------------------------*/

    private String resultString;
    private boolean userClickedOk = false;

    private UserService userService;

    // Current user's ID for personalized actions
    int currentUserId = CurrentUser.getInstance().getUserId();


    /*
   FXML UI Elements---------------------------------------------------------*/
    @FXML
    private Label userIdLabel;
    @FXML
    private Button deleteAccountButton;

    @FXML
    private Label editUserTipText;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userPhoneField;
    @FXML
    private TextField userEmailField;
    @FXML
    private Label validErrorLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelEditButton;


    @FXML
    private Label changePassTipText;
    @FXML
    private PasswordField altPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Button cancelChangePassButton;
    @FXML
    private Button changePasswordButton;


    /*
    FXML-Initialisierungsmethod---------------------------------------------------------*/

    /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded.
     * It attempts to connect to the database and load the user's information.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.userService = new UserService();
        DatabaseService databaseService = new DatabaseService();

        // button states are disabled until validation passes
        editButton.setDisable(true);
        cancelEditButton.setDisable(true);
        changePasswordButton.setDisable(true);
        cancelChangePassButton.setDisable(true);

        // Add listener to password fields to enable/disable buttons based on input
        addPasswordFieldsListener();

        // Display the current user's ID
        userIdLabel.setText(String.valueOf(currentUserId));

        // Check database connection and display user information if connected
        boolean isDbConnected = databaseService.isDatabaseConnected();
        if (isDbConnected){
            try {
                showUserInfos();
            } catch (Exception e) {
                System.out.println(AppTexts.ERROR_DURING_INITIALIZATION + e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    /*
    JavaFX-EventHandler---------------------------------------------------------*/

    // Validates the username input field and updates the validErrorLabel with a message if validation fails.
    @FXML
    void nameFieldValidation() {
        String upUsernameInserted = userNameField.getText();
        if (!Validator.isNameValid(upUsernameInserted)) {
            validErrorLabel.setText(AppTexts.VALID_USERNAME_4_15_CHARACTERS);
        } else {
            validErrorLabel.setText("");
        }
    }


    //Validates the phone number input field and updates the validErrorLabel with a message if validation fails.
    @FXML
    void phoneFieldValidation() {
        String upPhoneInserted = userPhoneField.getText();
        if (!Validator.isPhoneValid(upPhoneInserted)){
           validErrorLabel.setText(AppTexts.PHONE_NUMBER_WITH_AT_LEAST_11_DIGITS);
        }else {
            validErrorLabel.setText("");
        }

    }

    /**
     * Handles the action of editing user information. Updates the user's name and phone number in the database.
     */
    @FXML
    public void editInfoHandler() {

        // Retrieve the new name and phone number entered by the user
        String newName = userNameField.getText();
        String newPhone = userPhoneField.getText();

        try {
            // Attempt to update the user's information in the profile using the UserDao class
            userService.updateUserInfoInProfile(currentUserId, newName,newPhone);

            // Show a confirmation alert to the user indicating the update was successful
            AlertManager.getInstance().showInformationAlert(AppTexts.UPDATE_SUCCESSFUL,null, AppTexts.SUCCESSFULLY_UPDATED_CONTENT);
            resultString = AppTexts.SUCCESSFULLY_UPDATED_CONTENT;

            // Refresh the displayed user information to reflect the changes made
            showUserInfos();
            // Disable the "Edit" and "Cancel" buttons as the update has been successfully completed
            editButton.setDisable(true);
            cancelEditButton.setDisable(true);

        } catch (Exception e) {
            AlertManager.getInstance().showErrorAlert(AppTexts.UPDATE_FAILED, AppTexts.PROBLEM_UPDATING_USER_INFORMATION_TRY_AGAIN);
            System.out.println("Error in update user info: " + e.getMessage());
            resultString = "Error in update user info: " + e.getMessage();
        }

    }

    /**
     * Cancels the edit action and refreshes the form to show current user information.
     */
    @FXML
    void cancelEditHandler() {
        showUserInfos();
    }


    /**
     * Handles the deletion of the current user's account.
     * Prompts the user with a confirmation dialog before proceeding with the deletion.
     * If the user confirms, it attempts to delete the user account and provides feedback.
     */
    @FXML
    public void deleteAccountHandler() {

        if(!userClickedOk)
        {
            Optional<ButtonType> result = AlertManager.getInstance().showConfirmationAlert(AppTexts.DELETE_USER, null, AppTexts.WANT_TO_DELETE_THIS_USER);


            // Check if the user confirmed the deletion.
            if (result.isPresent() && result.get() == ButtonType.OK){
                //to make sure the other tests will be consistent and wait for a button click


                try{
                    // Attempt to delete the user account using the userService.
                    userService.deleteUser(currentUserId);
                    // Show an informational alert to indicate successful deletion.
                    AlertManager.getInstance().showInformationAlert(AppTexts.DELETION_SUCCESSFUL, null , AppTexts.BEEN_SUCCESSFULLY_DELETED);

                } catch (RuntimeException e){
                    // If the deletion fails, show an error alert with details.
                    AlertManager.getInstance().showErrorAlert(AppTexts.DELETION_FAILED, AppTexts.PROBLEM_DELETING_THE_USER_TRY_AGAIN);
                }
            }
        }else
        {
            userClickedOk = false;

            try{
                // Attempt to delete the user account using the userService.
                userService.deleteUser(currentUserId);
                // Show an informational alert to indicate successful deletion.
                AlertManager.getInstance().showInformationAlert(AppTexts.DELETION_SUCCESSFUL, null , AppTexts.BEEN_SUCCESSFULLY_DELETED);
                resultString = AppTexts.BEEN_SUCCESSFULLY_DELETED;

            } catch (RuntimeException e){
                // If the deletion fails, show an error alert with details.
                AlertManager.getInstance().showErrorAlert(AppTexts.DELETION_FAILED, AppTexts.PROBLEM_DELETING_THE_USER_TRY_AGAIN);
                resultString = AppTexts.PROBLEM_DELETING_THE_USER_TRY_AGAIN;
            }
        }



    }



    private void clearPasswordFields() {

        altPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
        errorLabel.setText("");
    }

    /**
     * Handles the change password action triggered by the user.
     * It verifies the old password, checks the new password's strength, and updates the password accordingly.
     */
    @FXML
    public void changePasswordHandler() {
        // Retrieve user input for old and new passwords.
        String oldPasswordInput = altPasswordField.getText();
        String newPassword = newPasswordField.getText();

        try {
            // Get the current password for the user from the database.
            String currentPassword = userService.getCurrentPassword(currentUserId);

            // Validate the strength of the new password.
            if (!Validator.isPasswordStrong(newPassword)) {
                AlertManager.getInstance().showErrorAlert(AppTexts.VALIDATION_ERROR, AppTexts.NEW_PASSWORD_VALIDATION_FAILED);
                resultString = AppTexts.NEW_PASSWORD_VALIDATION_FAILED;
                return;
            }

            // Check if the old password input matches the current password.
            if (!currentPassword.equals(oldPasswordInput)) {
                AlertManager.getInstance().showErrorAlert(AppTexts.INCORRECT_OLD_PASSWORD, AppTexts.THE_OLD_PASSWORD_YOU_ENTERED_IS_INCORRECT);
                resultString = AppTexts.THE_OLD_PASSWORD_YOU_ENTERED_IS_INCORRECT;
                return;
            }
            // Change the user's password to the new password.
            userService.changeUserPassword(currentUserId, newPassword);
            // Display a success alert.
            AlertManager.getInstance().showInformationAlert(AppTexts.PASSWORD_CHANGE, null, AppTexts.PASSWORD_SUCCESSFULLY_CHANGED);
            resultString = AppTexts.PASSWORD_SUCCESSFULLY_CHANGED;
            clearPasswordFields(); // Clear the password input fields.

            // Disable the change password and cancel buttons post-success.
            changePasswordButton.setDisable(true);
            cancelChangePassButton.setDisable(true);

        } catch (Exception e) {
            // Display an error alert if the password change fails.
            AlertManager.getInstance().showErrorAlert(AppTexts.PASSWORD_CHANGE_ERROR, AppTexts.FAILED_TO_CHANGE_PASSWORD);
            resultString = e.getMessage();
        }

    }


    @FXML
    void cancelChangePassHandler() {
        clearPasswordFields();
        changePasswordButton.setDisable(true);
        cancelChangePassButton.setDisable(true);

    }

     /*
    Help Methods----------------------------------------------------------------*/

    /**
     *
     *  Sets up change listeners for the username and phone number text fields.
     * @param user user The current {@link User} instance whose information is displayed on the form.
     *  *             This is used to compare the current form values against the original values.
     */
    private void setChangeListeners(User user) {

        // Add a listener to the username field. When the text changes, call updateButtonStates
        userNameField.textProperty().addListener(((observableValue, oldValue, newValue) -> updateButtonStates(user)));

        // Add a similar listener to the phone field. This also calls updateButtonStates on text changes
        userPhoneField.textProperty().addListener(((observableValue, oldValue, newValue) -> updateButtonStates(user)));

    }

    /**
     * Adds change listeners to the password fields to trigger validation and button state updates.
     */
    private void addPasswordFieldsListener() {
        // Listener for changes in the current password field.
        altPasswordField.textProperty().addListener((observable, oldValue, newValue) -> updateBtnStateInChangePassword());

        // Listener for changes in the new password field, triggering validation of the new password.
        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateNewPassword());

        // Listener for changes in the confirm new password field, triggering validation of password confirmation.
        confirmNewPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateNewPasswordConfirmation());
    }


    /**
     * Validates the new password for strength and matching with the confirmation password.
     * Updates the UI with error messages if validation fails.
     */
    private void validateNewPassword() {
        // Validate the strength of the new password.
        boolean isNewPasswordValid = Validator.isPasswordStrong(newPasswordField.getText());
        // Check if the new password and its confirmation match.
        boolean arePasswordsMatching = newPasswordField.getText().equals(confirmNewPasswordField.getText());

        // Update error messages based on validation results.
        if (!isNewPasswordValid) {
            errorLabel.setText(AppTexts.PASSWORD_IS_NOT_LONG_ENOUGH);
        } else if (!arePasswordsMatching && !confirmNewPasswordField.getText().isEmpty()) {
            // Überprüfen Sie, ob der Benutzer begonnen hat, das Bestätigungsfeld auszufüllen, bevor diese Meldung angezeigt wird
            errorLabel.setText(AppTexts.NEW_PASSWORDS_DO_NOT_MATCH);
        } else {
            errorLabel.setText("");
        }

        updateBtnStateInChangePassword(); // Update the state of the change password button based on validation.
    }

    /**
     * Validates that the new password and its confirmation match.
     * Updates the UI with an error message if the passwords do not match.
     */
    private void validateNewPasswordConfirmation() {

        // Check if the new password and its confirmation match.
        boolean arePasswordsMatching = newPasswordField.getText().equals(confirmNewPasswordField.getText());
        if (!arePasswordsMatching && !newPasswordField.getText().isEmpty()) {
            // Überprüfen Sie, ob der Benutzer das neue Passwortfeld bereits ausgefüllt hat, bevor diese Meldung angezeigt wird
            errorLabel.setText(AppTexts.NEW_PASSWORDS_DO_NOT_MATCH);
        } else {
            errorLabel.setText("");
        }

        updateBtnStateInChangePassword(); // Update the state of the change password button based on validation.
    }


    /**
     * Updates the enabled/disabled state of the change password and cancel buttons.
     * The change password button is enabled only if all conditions are met.
     */
    private void updateBtnStateInChangePassword() {
        // Check if any of the password fields are filled.
        boolean isAnyFieldFilled = !altPasswordField.getText().isEmpty() ||
                                   !newPasswordField.getText().isEmpty() ||
                                   !confirmNewPasswordField.getText().isEmpty();

        // Validate the new password for strength and matching with the confirmation.
        boolean isNewPasswordValid = Validator.isPasswordStrong(newPasswordField.getText());
        boolean arePasswordsMatching = newPasswordField.getText().equals(confirmNewPasswordField.getText());

        // Enable or disable buttons based on the validation results.
        changePasswordButton.setDisable(!(isAnyFieldFilled && isNewPasswordValid && arePasswordsMatching));
        cancelChangePassButton.setDisable(!isAnyFieldFilled);
    }


    /**
     * Updates the states of the edit and cancel buttons based on changes in the user's information.
     * The edit button is enabled only if the new data entered is valid and different from the current data.
     * The cancel button is enabled if any changes have been made to the user's information.
     *
     * @param user The current user object containing the original user information for comparison.
     */
    private void updateButtonStates(User user) {
        String newUserName = userNameField.getText();
        String newUserPhone = userPhoneField.getText();

        boolean isNewDatenValid = Validator.isNameValid(newUserName) && Validator.isPhoneValid(newUserPhone);
        boolean isUserNameChanged = !newUserName.equals(user.getUserName());
        boolean isUserPhoneChanged = !newUserPhone.equals(user.getUserPhone());

        // A change is considered to have occurred if either the username or phone number has changed.
        boolean hasChange = isUserNameChanged || isUserPhoneChanged;

        // Enable the edit button if the new data is valid and any changes have been made.
        editButton.setDisable(!(isNewDatenValid && hasChange));
        // Enable the cancel edit button if any changes have been made to the user's information.
        cancelEditButton.setDisable(!hasChange);
    }


    /**
     * Retrieves and displays the current user's information on the profile form.
     */
    private void showUserInfos() {

        try {
            // Fetch the current user's information from the database using the user ID
            User user = userService.getUserById(currentUserId );

            // Check if the user data is successfully fetched
            if (user != null){

                // Update the form fields with the fetched user information
                userNameField.setText(user.getUserName());
                userPhoneField.setText(user.getUserPhone());
                userEmailField.setText(user.getUserEmail());

                // Make the email field non-editable to prevent changes
                userEmailField.setEditable(false);

                // Set up change listeners on the userName and userPhone fields to handle user input
                setChangeListeners(user);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setUserService(UserService newUserService)
    {
        this.userService = newUserService;
    }

    public void setAltPasswordField(PasswordField altPasswordField) {
        this.altPasswordField = altPasswordField;
    }

    public void setNewPasswordField(PasswordField newPasswordField) {
        this.newPasswordField = newPasswordField;
    }

    public void setCancelChangePassButton(Button cancelChangePassButton) {
        this.cancelChangePassButton = cancelChangePassButton;
    }

    public void setChangePasswordButton(Button changePasswordButton) {
        this.changePasswordButton = changePasswordButton;
    }

    public void setCurrentUserId(int newUserId)
    {
        this.currentUserId = newUserId;
    }

    public void setAltPasswordFieldText(String text)
    {
        this.altPasswordField.setText(text);
    }

    public void setNewPasswordFieldText(String text)
    {
        this.newPasswordField.setText(text);
    }

    public void setConfirmPasswordField(PasswordField newPasswordField )
    {
        this.confirmNewPasswordField = newPasswordField;
    }

    public void setErrorLabel(Label newlabel)
    {
        this.errorLabel = newlabel;
    }

    public String getResultString()
    {
        return this.resultString;
    }

    public void setUserNameField(TextField newField)
    {
        this.userNameField = newField;
    }

    public void setUserPhoneField(TextField userPhoneField) {
        this.userPhoneField = userPhoneField;
    }

    public void setEditButton(Button newButton)
    {
        this.editButton = newButton;
    }

    public void setCancelEditButton(Button newButton)
    {
        this.cancelEditButton = newButton;
    }

    public void setUserNameTextValue(String username)
    {
        this.userNameField.setText(username);
    }

    public void setPhoneNumberTextValue(String phone)
    {
        this.userPhoneField.setText(phone);
    }

    public void setUserEmailField(TextField newField)
    {
        this.userEmailField = newField;
    }

    public void setUserClickedOk(boolean b)
    {
        this.userClickedOk = b;
    }



}


