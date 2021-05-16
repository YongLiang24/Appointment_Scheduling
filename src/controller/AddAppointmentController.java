/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.yong.dao_implement.AppointDAOImplement;
import com.yong.dao_implement.ContactDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.utility.AlertConfirmation;
import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Appointment;
import model.User;
import model.Contact;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class AddAppointmentController implements Initializable {
    @FXML private DatePicker DatePickerSelect;
    //@FXML private ChoiceBox<Integer> StartHour;  
    @FXML private ComboBox<Integer> StartHour;
    @FXML private ComboBox<Integer> StartMinute;
    @FXML private ComboBox<Integer> EndHour;  
    @FXML private ComboBox<Integer> EndMinute;
    
    @FXML private TextField Title;
    @FXML private TextField Description;
    @FXML private TextField Location;
    @FXML private TextField Type;
    @FXML private ComboBox<Contact> ContactCombo;
    @FXML private ComboBox<Customer> CustomerCombo;
    @FXML private Label StringStartTime;
    @FXML private Label StringEndTime;
    @FXML private Label APTWarningMsg;

    
    
    private User loggedUser;
    private ObservableList<Contact> contactList;
    private ObservableList<Customer> customerList;
    private ObservableList<Appointment> appointmentList;
    @FXML private AnchorPane AddAppointment_AnchorPane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Integer> StartHourList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);      
        StartHour.setItems(StartHourList);
        ObservableList<Integer> StartMinuteList = FXCollections.observableArrayList(0,5,10,15,20,25,30,35,40,45,50,55);      
        StartMinute.setItems(StartMinuteList);
        
        ObservableList<Integer> EndHourList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);      
        EndHour.setItems(EndHourList);
        ObservableList<Integer> EndMinuteList = FXCollections.observableArrayList(0,5,10,15,20,25,30,35,40,45,50,55);      
        EndMinute.setItems(EndMinuteList);
        
        loggedUser = LoginFXMLController.loggedUserObj; //get the current logged in user.     
        contactList = getContactList(); //get all contacts to an observableList.
        customerList = getCustomerList();//get all customers to an observableList.
        appointmentList = getAppointmentList();//get all appointments.
        
        ContactCombo.setItems(contactList);
        CustomerCombo.setItems(customerList);
        
     
    }    
    
    @FXML
    void createAppointmentBtn(ActionEvent event) { 
        if(checkEmptyFields()){
            String title = Title.getText();
            String description = Description.getText();
            String location = Location.getText();
            String type = Type.getText();
            int userID = loggedUser.getUser_ID();
            try{
                ZonedDateTime startDateTime =convertAppointmentTime(DatePickerSelect, StartHour.getSelectionModel().getSelectedItem(), StartMinute.getSelectionModel().getSelectedItem());
                ZonedDateTime endDateTime =convertAppointmentTime(DatePickerSelect, EndHour.getSelectionModel().getSelectedItem(), EndMinute.getSelectionModel().getSelectedItem());
                int customerID = CustomerCombo.getSelectionModel().getSelectedItem().getCustomer_ID();
                int contactID = ContactCombo.getSelectionModel().getSelectedItem().getContact_ID();
                
                
                
             }
            catch(NullPointerException e){
                APTWarningMsg.setText("Message: Please complete all boxes before submitting.");
            }
        }else{APTWarningMsg.setText("Message: Please complete all fields before submitting.");}
       
    }

    @FXML
    void goBackBtn(ActionEvent event) throws IOException {
        //call the switchStage utility method
        String viewFilePath ="/view/Appointment.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event);
    }
    
    @FXML
    void selectStartHour(ActionEvent event){  
        AlertConfirmation alert = new AlertConfirmation();
        try{
            timeFormatter(StartHour, StartMinute, StringStartTime); //a customer hour minute formatter for the create appointment.  
            if(!validateEndHour()){
                String alertMessage ="Starting hour must be earlier than or the same as the ending hour.";       
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");            
            }
            if(!checkHourSelect()){
                String alertMessage ="Please select a time within our office hours.(EST 8:00AM to 10:00PM)";       
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
        }
        catch(NullPointerException e){
        }
    }

    @FXML
    void selectStartMinute(ActionEvent event){
        
        timeFormatter(StartHour, StartMinute, StringStartTime); //a customer hour minute formatter for the create appointment.
        try{
            AlertConfirmation alert = new AlertConfirmation(); 
            if(!validateEndMinute()){
                String alertMessage ="Starting minute must be earlier than or the same as the ending minute.";       
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
        }catch(NullPointerException e){
            
        }
    }
    
    @FXML
    void selectEndHour(ActionEvent event){
        AlertConfirmation alert = new AlertConfirmation(); 
        timeFormatter(EndHour, EndMinute, StringEndTime); //a customer hour minute formatter for the create appointment.
        try{ 
            if(!validateEndHour()){
            String alertMessage ="Starting hour must be earlier than or the same as the ending hour.";       
            alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
            if(!checkHourSelect()){
                String alertMessage ="Please select a time within our office hours.(EST 8:00AM to 10:00PM)";         
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
        }
        catch(NullPointerException e){
        }
    }

    @FXML
    void selectEndMinute(ActionEvent event){
        timeFormatter(EndHour, EndMinute, StringEndTime); //a customer hour minute formatter for the create appointment. 
        try{
            AlertConfirmation alert = new AlertConfirmation(); 
            if(!validateEndMinute()){
                String alertMessage ="Starting minute must be earlier than or the same as the ending minute.";       
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
        }catch(NullPointerException e){
            
        }
    }
    
    private void timeFormatter(ComboBox<Integer> hour, ComboBox <Integer>min, Label timeTxt)throws NullPointerException{
        if(min.getSelectionModel().getSelectedItem() != null && hour.getSelectionModel().getSelectedItem() != null){
            if(min.getSelectionModel().getSelectedItem()<10){
                timeTxt.setText(String.valueOf("["+hour.getSelectionModel().getSelectedItem())+" : 0"+String.valueOf(min.getSelectionModel().getSelectedItem())+"]");
            }else{
                timeTxt.setText(String.valueOf("["+hour.getSelectionModel().getSelectedItem())+" : "+String.valueOf(min.getSelectionModel().getSelectedItem())+"]");
            } 
        }
    }
    
    private boolean validateEndHour(){
        try{
        if(StartHour.getSelectionModel().getSelectedItem() > EndHour.getSelectionModel().getSelectedItem()){
            return false;
        }
        }catch(NullPointerException e){}
      return true;
    }
    
    private boolean validateEndMinute(){
        try{
        if(StartHour.getSelectionModel().getSelectedItem() == EndHour.getSelectionModel().getSelectedItem() && StartMinute.getSelectionModel().getSelectedItem() >= EndMinute.getSelectionModel().getSelectedItem()){         
            return false;
        }
        }catch(NullPointerException e){}
        return true;
    }
    
    ObservableList<Contact> getContactList(){
        ContactDAOImplement contactObj = new ContactDAOImplement();
        return contactObj.getAllContacts();     
    }
    
    ObservableList<Customer> getCustomerList(){
        CustomerDAOImplement customerObj = new CustomerDAOImplement();
        return customerObj.getAllCustomers();
    }
    
    ObservableList<Appointment> getAppointmentList(){
        AppointDAOImplement appointObj = new AppointDAOImplement();
        return appointObj.getAllAppointments();
    }

    ZonedDateTime convertAppointmentTime(DatePicker datePicker, int hour, int minute){          
        LocalDate getDate = datePicker.getValue();
        LocalTime getTime =  LocalTime.of(hour, minute);
        LocalDateTime getDateTime = LocalDateTime.of(getDate, getTime);            
        ZoneId zoneEST = ZoneId.of("America/New_York");
        ZonedDateTime ESTDateTime = ZonedDateTime.of(getDateTime, zoneEST);
        //ZoneId zoneUTC = ZoneId.of("UTC"); 
        //ZonedDateTime UTCTime = ZonedDateTime.ofInstant(ESTDateTime.toInstant(), zoneUTC); 
        //System.out.println("EST: "+ESTDateTime+" UTC: "+UTCTime);
        return ESTDateTime;
    }
    
    private boolean checkEmptyFields(){
        return !(Title.getText().isEmpty() || Description.getText().isEmpty() || Location.getText().isEmpty() || Type.getText().isEmpty());
    }
    
    boolean checkHourSelect(){
         return !(StartHour.getSelectionModel().getSelectedItem()<8 || StartHour.getSelectionModel().getSelectedItem() >20 || EndHour.getSelectionModel().getSelectedItem()<8 || EndHour.getSelectionModel().getSelectedItem() >20);
    }
    
    boolean checkTimeOverLap(ObservableList<Appointment> aptList, ZonedDateTime start, ZonedDateTime end){
        ZoneId zoneEST = ZoneId.of("America/New_York");
        ZoneId zoneUTC = ZoneId.of("UTC");
        for(Appointment apt: aptList){
            //set start/end timestamp with zone UTC
            Instant startDateTime = apt.getStart().toInstant();
            ZonedDateTime startUTC = startDateTime.atZone(zoneUTC);
            Instant endDateTime = apt.getEnd().toInstant();
            ZonedDateTime endUTC = endDateTime.atZone(zoneUTC);
            //convert UTC to EST timezone
            ZonedDateTime startEST = ZonedDateTime.ofInstant(startUTC.toInstant(), zoneEST);
            ZonedDateTime endEST = ZonedDateTime.ofInstant(endUTC.toInstant(), zoneEST);
            //check valid time slot
            if(start.isAfter(startEST) && start.isBefore(endEST)){ 
                return false;
            }
            else if(start.isEqual(startEST) || start.isEqual(endEST)){
                return false;
            }
            else if(end.isAfter(startEST) && end.isBefore(endEST)){
                return false;
            }
            else if(end.isEqual(startEST) || end.isEqual(endEST)){
                return false;
            }
            else if(start.isBefore(startEST) && end.isAfter(endEST)){
                return false;
            }     
        }
        return true;
    }
    
}
