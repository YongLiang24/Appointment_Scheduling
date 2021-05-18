package controller;

import com.yong.dao_implement.AppointDAOImplement;
import com.yong.dao_implement.ContactDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.utility.AlertConfirmation;
import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
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
    @FXML private Label APTWarningMsg;
    
    private User loggedUser;
    private ObservableList<Contact> contactList;
    private ObservableList<Customer> customerList;
    private ObservableList<Appointment> appointmentList;
    @FXML private AnchorPane AddAppointment_AnchorPane;
    
//    @FXML private Label ESTTime;
//    @FXML private Label SysTime;
    
    @FXML private Label HourFrom;
    @FXML private Label HourTo;
    AlertConfirmation alert = new AlertConfirmation();
    
    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb RB
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
        
        HourFrom.setText(String.valueOf(8+getZoneOffsetHour()));
        HourTo.setText(String.valueOf(20+getZoneOffsetHour()));
        
//        SysTime.setText("Local Time: "+ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));
//        ESTTime.setText("EST   Time: "+ZonedDateTime.now(ZoneId.of("America/New_York")).truncatedTo(ChronoUnit.SECONDS));
    }    
    
    @FXML
    void createAppointmentBtn(ActionEvent event) throws IOException { 
        if(checkEmptyFields()){
            String title = Title.getText();
            String description = Description.getText();
            String location = Location.getText();
            String type = Type.getText();
            String userName = loggedUser.getUsername();
            int userID = loggedUser.getUser_ID();
            
            try{
                LocalDateTime startTime =convertAppointmentTime(DatePickerSelect, StartHour.getSelectionModel().getSelectedItem(), StartMinute.getSelectionModel().getSelectedItem());
                LocalDateTime endTime =convertAppointmentTime(DatePickerSelect, EndHour.getSelectionModel().getSelectedItem(), EndMinute.getSelectionModel().getSelectedItem());

                int customerID = CustomerCombo.getSelectionModel().getSelectedItem().getCustomer_ID();
                int contactID = ContactCombo.getSelectionModel().getSelectedItem().getContact_ID();
              
                appointmentList = getAppointmentList();//get all appointments.
                if(checkEmptyFields() && checkHourSelect() && checkTimeOverLap(appointmentList, startTime, endTime) && validateEndHour() && validateEndMinute()){
                   AppointDAOImplement createApt = new AppointDAOImplement();
                   int createResult =  createApt.createAppointment(title, description, location, type, startTime, endTime, userName, customerID, userID, contactID);
                   System.out.println("created result: "+createResult);
                   String alertMessage ="Appointment has successfully created!";       
                    
                   Optional<ButtonType> buttonType = alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
                   if(buttonType.get() == ButtonType.OK){
                        goBackBtn(event);
                   }                   
                }else{
                    String alertMessage ="An error has occurred, please check all fields, date and time."; 
                    alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
                }         
             }
            catch(NullPointerException e){
                APTWarningMsg.setText("Message: Please complete all fields and validate date & time before submitting.");
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
    void selectStartMinute(ActionEvent event){  
        try{
            if(!validateEndMinute()){
                String alertMessage ="Starting minute must be earlier than or the same as the ending minute.";       
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
        }catch(NullPointerException e){
            
        }
    }
    
    @FXML
    void selectEndHour(ActionEvent event){ 
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
        try{
            if(!validateEndMinute()){
                String alertMessage ="Starting minute must be earlier than or the same as the ending minute.";       
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "information");
            }
        }catch(NullPointerException e){
            
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
            APTWarningMsg.setText("Message: Ending Minute must be later than the starting minute.");
            return false;
        }
        }catch(NullPointerException e){}
        return true;
    }
    

    LocalDateTime convertAppointmentTime(DatePicker datePicker, int hour, int minute){          
        LocalDate getDate = datePicker.getValue();
        LocalTime getTime =  LocalTime.of(hour, minute);
        LocalDateTime getDateTime = LocalDateTime.of(getDate, getTime);  
        return getDateTime;
    }
    
    private boolean checkEmptyFields(){
        return !(Title.getText().isEmpty() || Description.getText().isEmpty() || Location.getText().isEmpty() || Type.getText().isEmpty());
    }
    
    boolean checkHourSelect(){
         return !(StartHour.getSelectionModel().getSelectedItem()<(8+getZoneOffsetHour()) || StartHour.getSelectionModel().getSelectedItem() >(20+getZoneOffsetHour()) || EndHour.getSelectionModel().getSelectedItem()<(8+getZoneOffsetHour()) || EndHour.getSelectionModel().getSelectedItem() >(20+getZoneOffsetHour()));
    }
    
    boolean checkTimeOverLap(ObservableList<Appointment> aptList, LocalDateTime start, LocalDateTime end){
        String alertMessage ="The selected time is overlapping with another appointment. Please select another time.";
        for(Appointment apt: aptList){
            LocalDateTime storedStart = apt.getStart().toLocalDateTime();
            LocalDateTime storedEnd = apt.getEnd().toLocalDateTime();
            System.out.println("DB to local: "+storedStart);
            if(start.isEqual(storedStart) || start.isEqual(storedEnd) || end.isEqual(storedStart) || end.isEqual(storedEnd)){
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "warning");
                return false;
            }else if((start.isAfter(storedStart)&& start.isBefore(storedEnd)) || (end.isAfter(storedStart)&& end.isBefore(storedEnd))){
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "warning");
                return false;
            }else if(start.isBefore(storedStart) && end.isAfter(storedEnd)){
                alert.alertConfirmation(AddAppointment_AnchorPane, alertMessage, "warning");
                return false;
            }
        }
        return true;
    }
    
    /** This method dynamically calculates the hour difference from a systemDefault zone to EST office hour. 
     @return the hour difference.*/
    int getZoneOffsetHour(){
        ZonedDateTime sysZoneTime = ZonedDateTime.now();
        ZoneId zoneEST = ZoneId.of("America/New_York");
        ZonedDateTime ESTZoneTime = sysZoneTime.withZoneSameInstant(zoneEST);   
        return (sysZoneTime.getOffset().getTotalSeconds() - ESTZoneTime.getOffset().getTotalSeconds())/3600;
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
}
