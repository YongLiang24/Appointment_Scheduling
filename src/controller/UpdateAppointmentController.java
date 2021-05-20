/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.User;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class UpdateAppointmentController extends AddAppointmentController implements Initializable {

    @FXML private AnchorPane UpdateAppointment_AnchorPane;
    @FXML private DatePicker DatePickerSelect;
    @FXML private TextField Title;
    @FXML private TextField Description;
    @FXML private TextField Location;
    @FXML private ComboBox<?> ContactCombo;
    @FXML private TextField Type;
    @FXML private ComboBox<?> CustomerCombo;
    @FXML private Label APTWarningMsg;
    @FXML private ComboBox<?> StartHour;
    @FXML private ComboBox<?> StartMinute;
    @FXML private ComboBox<?> EndHour;
    @FXML private ComboBox<?> EndMinute;
    @FXML private Label HourFrom;
    @FXML private Label HourTo;

    Appointment selectedApt;
    User user;
    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb RB
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //retrieve the selected appointment object.
        selectedApt = AppointmentController.selectedAppointment;
        //retrieve the logged user.
        user =LoginFXMLController.loggedUserObj;
    }    

    @FXML
    private void updateAppointmentBtn(ActionEvent event) {
    }

    @FXML
    private void backBtn(ActionEvent event) {
    }

    @FXML
    private void selectStarthour(ActionEvent event) {
    }

    @FXML
    private void selectStartminute(ActionEvent event) {
    }

    @FXML
    private void selectEndhour(ActionEvent event) {
    }

    @FXML
    private void selectEndminute(ActionEvent event) {
    }
    
}
