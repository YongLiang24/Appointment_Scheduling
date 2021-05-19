package controller;

import com.yong.dao_implement.AppointDAOImplement;
import com.yong.dao_implement.ContactDAOImplement;
import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import model.Appointment;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contact;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class AppointmentController implements Initializable {
    
    ObservableList<Appointment> aptContactList = FXCollections.observableArrayList();
    ObservableList<Appointment> filteredAptList = FXCollections.observableArrayList();
    
    @FXML private TableView<Appointment> AptTableView;
    @FXML private TableColumn<Appointment, Integer> Appointment_ID;
    @FXML private TableColumn<Appointment, String> Title;
    @FXML private TableColumn<Appointment, String> Description;
    @FXML private TableColumn<Appointment, String> Location;
    @FXML private TableColumn<Appointment, String> Contact;
    @FXML private TableColumn<Appointment, String> Type;
    @FXML private TableColumn<Appointment, Timestamp> Start;
    @FXML private TableColumn<Appointment, Timestamp> End;
    @FXML private TableColumn<Appointment, Integer> Customer_ID;
    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb RB
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       aptContactList = getAptContactList();//get a list of appointments with contact names.
       setToTableView(aptContactList);

    }    
    /** this method returns user to the customer main page. 
     @exception IOException stage switch
     @param event event*/
    @FXML
    void getBackToCustomer(ActionEvent event) throws IOException {
        String viewFilePath ="/view/CustomerFXML.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event); 
    }
    /** this method displays all appointments with contacts. 
     @param event event.*/
    @FXML
    void filterAll(ActionEvent event) {
        aptContactList = getAptContactList();//get a list of appointments with contact names.
        setToTableView(aptContactList);
    }
    /** this method utilizes Lambda Stream to filters appointments and display by current month.
     * it gets call when selects the Month radio button.
     @param event event.*/
    @FXML
    void filterMonthly(ActionEvent event) {
        aptContactList = getAptContactList();//get an updated list of appointments with contact names.
        ObservableList<Appointment> filterMonthList= FXCollections.observableArrayList();
        //Use Lambda Stream to filter the current month.
        Stream<Appointment> filterMonth = aptContactList.stream().filter(apt -> apt.getStart().toLocalDateTime().getMonth().equals(LocalDate.now().getMonth()));
        filterMonth.forEach(apt-> filterMonthList.add(apt));
        setToTableView(filterMonthList);
    }
    /** this method displays a filtered appointment list by week, it uses switch statements to determine the current week from the current day. 
     @param event event*/
    @FXML
    void filterWeekly(ActionEvent event) {
        aptContactList = getAptContactList();//get the most updated list of appointments with contact names.
        ObservableList<Appointment> filterWeekList= FXCollections.observableArrayList();
        //use switch statement to determine the week from current date.
        switch(LocalDateTime.now().getDayOfWeek()){
            case SUNDAY:
                //this method takes two lists, a plusday and a minusday int from current date
                //adds a filtered list to the tableview within a week from the current date.
                filterWeek(aptContactList, filterWeekList, 0, 7);
                break;     
            case MONDAY: 
                filterWeek(aptContactList, filterWeekList, 6, 2); 
                break;
            case TUESDAY: 
                filterWeek(aptContactList, filterWeekList, 5, 3);
                break;    
            case WEDNESDAY:
                filterWeek(aptContactList, filterWeekList, 4, 4);
                break;    
            case THURSDAY:
                filterWeek(aptContactList, filterWeekList, 3, 5);
                break;              
            case FRIDAY:
                filterWeek(aptContactList, filterWeekList, 2, 6);
                break;              
            case SATURDAY:
                filterWeek(aptContactList, filterWeekList, 1, 7);
                break;        
        }     
        setToTableView(filterWeekList);   
    }
    /** this is a utility method that filters the week based on int plusDays minusDays. 
     @param appointList appointment list.
     @param WeekList a filtered week list
     @param plusDays plus days from today
     @param minusDays minus the days from today. */
    private void filterWeek(ObservableList<Appointment> appointList, ObservableList<Appointment> WeekList, int plusDays, int minusDays){
        for(Appointment apt: appointList){
                    LocalDate startDate = LocalDate.from(apt.getStart().toLocalDateTime());
                    if(startDate.isEqual(LocalDate.now())){
                        WeekList.add(apt);
                    }else if(startDate.isBefore(LocalDate.now().plusDays(plusDays)) && startDate.isAfter(LocalDate.now().minusDays(minusDays))){
                        WeekList.add(apt);
                    }
                } 
    }
    /** get a list of appointments with contact information. 
     @return a list of appointments.*/
    private ObservableList<Appointment> getAptContactList(){    
        ObservableList<Appointment> aptList = FXCollections.observableArrayList();
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        ObservableList<Appointment> contactAptList= FXCollections.observableArrayList();
        
        AppointDAOImplement appObj = new AppointDAOImplement();
        aptList =appObj.getAllAppointments();
        
        ContactDAOImplement contactObj = new ContactDAOImplement();
        contactList = contactObj.getAllContacts();
        
        for(Appointment apt: aptList){
            for(Contact c: contactList){
                if(apt.getContact_ID() == c.getContact_ID()){
                    Appointment newApt = new Appointment(apt.getTitle(), apt.getDescription(), apt.getLocation(), apt.getType(), c.getContact_Name(), apt.getAppointment_ID(), apt.getCustomer_ID(), apt.getUser_ID(), apt.getContact_ID(), apt.getStart(), apt.getEnd());
                    contactAptList.add(newApt);
                }
            }
        }    
        return contactAptList;
    }
    /** This method sets data to the FXML tableView. 
     @param listApt a list contains appointments with related contacts*/
    private void setToTableView(ObservableList<Appointment> listApt){  
        AptTableView.setItems(listApt);        
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//"Name" matches the model field name"
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type")); 
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<>("End"));
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID")); 
    }
    /** this method takes the user to an appointment creation form. 
     @param event event
     @exception IOException .*/
    @FXML
    void createAppointment(ActionEvent event) throws IOException {
        String viewFilePath ="/view/AddAppointment.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event); 

    }
    
}
