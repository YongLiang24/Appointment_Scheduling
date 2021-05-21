/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.yong.dao_implement.AppointDAOImplement;
import com.yong.dao_implement.ContactDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.Customer;
import model.Contact;

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
    @FXML private ComboBox<Contact> ContactCombo;
    @FXML private TextField Type;
    @FXML private ComboBox<Customer> CustomerCombo;
    @FXML private Label APTWarningMsg;
    @FXML private ComboBox<Integer> StartHour;
    @FXML private ComboBox<Integer> StartMinute;
    @FXML private ComboBox<Integer> EndHour;
    @FXML private ComboBox<Integer> EndMinute;
    @FXML private Label HourFrom;
    @FXML private Label HourTo;
    ObservableList<Appointment> avoidOwnTimeOverlapList=FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb RB
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Integer> StartHourList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);      
        StartHour.setItems(StartHourList);
        ObservableList<Integer> StartMinuteList = FXCollections.observableArrayList(0,5,10,15,20,25,30,35,40,45,50,55);      
        StartMinute.setItems(StartMinuteList);
        
        loggedUser = LoginFXMLController.loggedUserObj; //get the current logged in user.
        
        ContactCombo.setItems(getContactList());
        CustomerCombo.setItems(getCustomerList());
        
        ObservableList<Integer> EndHourList = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);      
        EndHour.setItems(EndHourList);
        ObservableList<Integer> EndMinuteList = FXCollections.observableArrayList(0,5,10,15,20,25,30,35,40,45,50,55);      
        EndMinute.setItems(EndMinuteList);
        //retrieve the selected appointment object, customer and contact.
        Appointment selectedApt = AppointmentController.selectedAppointment; 
        CustomerDAOImplement getCustomer = new CustomerDAOImplement();
        Customer selectedCustomer =getCustomer.getCustomerByID(selectedApt.getCustomer_ID());
        ContactDAOImplement getContact = new ContactDAOImplement();
        Contact selectedContact = getContact.getContactByID(selectedApt.getContact_ID());
        //set default values
        Title.setText(selectedApt.getTitle());
        Description.setText(selectedApt.getDescription());
        Location.setText(selectedApt.getLocation());
        Type.setText(selectedApt.getType());
        CustomerCombo.setValue(selectedCustomer);
        ContactCombo.setValue(selectedContact);
        //set default date and time
        DatePickerSelect.setValue(selectedApt.getStart().toLocalDateTime().toLocalDate());
        StartHour.setValue(selectedApt.getStart().toLocalDateTime().getHour());
        StartMinute.setValue(selectedApt.getStart().toLocalDateTime().getMinute());
        EndHour.setValue(selectedApt.getEnd().toLocalDateTime().getHour());
        EndMinute.setValue(selectedApt.getEnd().toLocalDateTime().getMinute());  
        HourFrom.setText(String.valueOf(8+getZoneOffsetHour()));
        HourTo.setText(String.valueOf(20+getZoneOffsetHour()));
        
        appointmentList = getAppointmentList();//get all appointments.
        avoidOwnTimeOverlapList = avoidOwnTimeOverlapUpdate(appointmentList);
        
    }    

    @FXML
    private void updateAppointmentBtn(ActionEvent event) throws IOException {
        if(checkEmptyFields()){
            String title = Title.getText();
            String description = Description.getText();
            String location = Location.getText();
            String type = Type.getText();
            String userName = loggedUser.getUsername();
            Appointment selectedApt = AppointmentController.selectedAppointment;
            try{
                LocalDateTime startTime =convertAppointmentTime(DatePickerSelect, StartHour.getSelectionModel().getSelectedItem(), StartMinute.getSelectionModel().getSelectedItem());
                LocalDateTime endTime =convertAppointmentTime(DatePickerSelect, EndHour.getSelectionModel().getSelectedItem(), EndMinute.getSelectionModel().getSelectedItem());

                int customerID = CustomerCombo.getSelectionModel().getSelectedItem().getCustomer_ID();
                int contactID = ContactCombo.getSelectionModel().getSelectedItem().getContact_ID();  
                
                if(checkEmptyFields() && checkHourSelect() && checkTimeOverLap(avoidOwnTimeOverlapList, startTime, endTime, UpdateAppointment_AnchorPane) && validateEndHour() && validateEndMinute()){
                   AppointDAOImplement updateApt = new AppointDAOImplement();
                   //int appointment_id, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, String user, int customer_id, int contact_id
                   int createResult =  updateApt.updateAppointment(selectedApt.getAppointment_ID(), title, description, location, type, startTime, endTime, userName, customerID, contactID);
                   if(createResult ==1){
                    
                       String alertMessage ="Appointment has successfully updated!";        
                       Optional<ButtonType> buttonType = alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
                        if(buttonType.get() == ButtonType.OK){
                            goBackBtn(event);
                        }   
                   }else{
                       String alertMessage ="Connection error. The Appointment was not updated.";
                       Optional<ButtonType> buttonType = alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
                        if(buttonType.get() == ButtonType.OK){
                            goBackBtn(event);
                        }  
                   }
                                   
                }else{
                    String alertMessage ="An error has occurred, please check all fields, date and time."; 
                    alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
                }         
             }
            catch(NullPointerException e){
                APTWarningMsg.setText("Message: Please complete all fields and validate date & time before submitting.");
            }
        }else{APTWarningMsg.setText("Message: Please complete all fields before submitting.");}
    } 
    /** this method calls the goBackBtn method from AddAppointmentController class.
     it takes the user back to the appointment main page.
     @param event event object
     @exception IOException stage switching. */ 
    @FXML
    private void backBtn(ActionEvent event) throws IOException {
        goBackBtn(event);
    }
    
    /** call the method from AddAppointmentController for validation. 
     @param event event object. */
    @FXML
    private void selectStarthour(ActionEvent event) {
        try{
            if(!validateEndHour()){
                String alertMessage ="Starting hour must be earlier than or the same as the ending hour.";       
                alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");            
            }
            if(!checkHourSelect()){
                String alertMessage ="Please select a time within our office hours.(EST 8:00AM to 10:00PM)";       
                alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
            }
        }
        catch(NullPointerException e){
        }
    }

    @FXML
    private void selectStartminute(ActionEvent event) {
        try{
            if(!validateEndMinute()){
                String alertMessage ="Starting minute must be earlier than or the same as the ending minute.";       
                alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
            }
        }catch(NullPointerException e){
            
        }
    }

    @FXML
    private void selectEndhour(ActionEvent event) {
        try{ 
            if(!validateEndHour()){
            String alertMessage ="Starting hour must be earlier than or the same as the ending hour.";       
            alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
            }
            if(!checkHourSelect()){
                String alertMessage ="Please select a time within our office hours.(EST 8:00AM to 10:00PM)";         
                alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
            }
        }
        catch(NullPointerException e){
        }
    }

    @FXML
    private void selectEndminute(ActionEvent event) {
        try{
            if(!validateEndMinute()){
                String alertMessage ="Starting minute must be earlier than or the same as the ending minute.";       
                alert.alertConfirmation(UpdateAppointment_AnchorPane, alertMessage, "information");
            }
        }catch(NullPointerException e){
            
        }
    }
    
    private ObservableList<Appointment> avoidOwnTimeOverlapUpdate(ObservableList <Appointment> aptList){
        ObservableList<Appointment> avoidOwnTimeOverlapList=FXCollections.observableArrayList();
        Appointment selectedApt = AppointmentController.selectedAppointment; 
        for(Appointment apt: aptList){
            if(apt.getAppointment_ID()!= selectedApt.getAppointment_ID()){
                avoidOwnTimeOverlapList.add(apt);
            }
        }
        return avoidOwnTimeOverlapList;
    }
    
}
