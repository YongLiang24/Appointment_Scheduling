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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import model.Appointment;
import java.sql.Timestamp;
import java.time.LocalDate;
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
   
    @FXML private RadioButton FilterAll;
    @FXML private RadioButton FilterMonthly;
    @FXML private RadioButton FilterWeekly;
    
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
    

    @FXML
    void getBackToCustomer(ActionEvent event) throws IOException {
        String viewFilePath ="/view/CustomerFXML.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event); 

    }
    
    @FXML
    void filterAll(ActionEvent event) {
        aptContactList = getAptContactList();//get a list of appointments with contact names.
        setToTableView(aptContactList);
    }

    @FXML
    void filterMonthly(ActionEvent event) {
        for(Appointment apt: aptContactList){
            System.out.println(apt.getStart().toLocalDateTime().getMonth().equals(LocalDate.now().getMonth()));
            
            System.out.println(apt.getStart().toLocalDateTime().getDayOfWeek());
        }
        

    }

    @FXML
    void filterWeekly(ActionEvent event) {
        System.out.println("Week");
        //use switch statement to determine the week from current date.
    }
    
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
    
    @FXML
    void createAppointment(ActionEvent event) throws IOException {
        String viewFilePath ="/view/AddAppointment.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event); 

    }
    
}
