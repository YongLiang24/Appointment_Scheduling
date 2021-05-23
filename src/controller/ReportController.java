package controller;

import com.yong.dao_implement.AppointDAOImplement;
import com.yong.dao_implement.ContactDAOImplement;
import com.yong.dao_implement.CountryDAOImplement;
import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Customer;
import model.MonthTypeReport;
import java.sql.Timestamp;
import model.CountryCustomerReport;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class ReportController implements Initializable {
    //Appointment month type fields
    @FXML private TableView<MonthTypeReport> AptReportView;
    @FXML private TableColumn<MonthTypeReport, String> AptType;
    @FXML private TableColumn<MonthTypeReport, Integer> AptCount;
    ObservableList<Appointment> aptList = FXCollections.observableArrayList();
    @FXML private Label ReportWarning;
    
    //Contact Report fields
    @FXML private ComboBox<Contact> ContactCombo;
    @FXML private TableView<Appointment> ContactReportView;
    ObservableList<Contact> contactList = FXCollections.observableArrayList();
    @FXML private TableColumn<Appointment, Integer> apt_id;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, Timestamp> start_date;
    @FXML private TableColumn<Appointment, Timestamp> end_date;
    @FXML private TableColumn<Appointment, Integer> customer_id;
    
    //Customer Country Report fields
    @FXML private ComboBox<Country> CountryCombo;
    @FXML private TableView<CountryCustomerReport> CustomerReportView;
    @FXML private TableColumn<CountryCustomerReport, String> country;
    @FXML private TableColumn<CountryCustomerReport, String> division;
    @FXML private TableColumn<CountryCustomerReport, Integer> customer_count;
    ObservableList<Country> countryList=FXCollections.observableArrayList();
    ObservableList<Customer> customerDivisionList = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb RB
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aptList = getAllAppointments();
        
        contactList = getAllContacts();
        ContactCombo.setItems(contactList);
        
        countryList = getAllCountries();
        CountryCombo.setItems(countryList);
        customerDivisionList = CustomerFXMLController.CustomerDivisionList;
        
        
    }    
    /** This method uses HashMap to increase counts for duplicate entries. 
     it converts the HashMap to an observableList for the ViewList display.
     @param event event object. */
    @FXML
    void getAppointReport(ActionEvent event) {
        ContactCombo.setVisible(false);
        ContactReportView.setVisible(false);
        CountryCombo.setVisible(false);
        CustomerReportView.setVisible(false);
        AptReportView.setVisible(true);
        HashMap<String, Integer> aptHashMap= new HashMap();
        ObservableList<MonthTypeReport> monthTypeList = FXCollections.observableArrayList();
        int count =1;
        //use hashmap to increase counts for duplicates.
        for(Appointment apt: aptList){
            String monthType = apt.getStart().toLocalDateTime().getMonth()+" - Type: "+apt.getType();
            if(aptHashMap.containsKey(monthType)){
                aptHashMap.put(monthType, aptHashMap.get(monthType)+1);
            }else{
                aptHashMap.put(monthType, count);
            }       
        }
        //iterate the hashmap to create an observableList of MonthTypeReport.
        for(Map.Entry aptElement : aptHashMap.entrySet()){
            String mtString = (String) aptElement.getKey();
            int countValue = (int) aptElement.getValue();
            MonthTypeReport mtObj = new MonthTypeReport(mtString, countValue);
            monthTypeList.add(mtObj);
        }
        //set view values to the tableview
        AptReportView.setItems(monthTypeList);
        AptType.setCellValueFactory(new PropertyValueFactory<>("monthType"));
        AptCount.setCellValueFactory(new PropertyValueFactory<>("count"));
    }
    /** Discussion of Lambda. This method uses Lambda stream to help filter appointments that are associated with a contact.
     @param event event obj*/
    @FXML
    void getContactReport(ActionEvent event) {
        AptReportView.setVisible(false);
        CountryCombo.setVisible(false);
        CustomerReportView.setVisible(false);
        ContactCombo.setVisible(true);
        ContactReportView.setVisible(true);    
    }
    
    @FXML
    void selectContact(ActionEvent event) {
        try{
            Contact selectedContact = ContactCombo.getSelectionModel().getSelectedItem();
            ObservableList<Appointment> contactAppointList = FXCollections.observableArrayList();
            //get appointments that are belong to a contact.
            aptList.stream().filter((apt) -> (apt.getContact_ID() == selectedContact.getContact_ID())).forEachOrdered((apt) -> {
                contactAppointList.add(apt);
            });
            ContactReportView.setItems(contactAppointList);
            apt_id.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            title.setCellValueFactory(new PropertyValueFactory<>("Title"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            description.setCellValueFactory(new PropertyValueFactory<>("Description"));
            start_date.setCellValueFactory(new PropertyValueFactory<>("Start"));
            end_date.setCellValueFactory(new PropertyValueFactory<>("End"));
            customer_id.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        }catch(NullPointerException e){
            ReportWarning.setText("Warning: Please select a contact to report.");
        }
    }

    @FXML
    void getCountryReport(ActionEvent event) {
        AptReportView.setVisible(false);
        ContactCombo.setVisible(false);
        ContactReportView.setVisible(false);
        CountryCombo.setVisible(true);
        CustomerReportView.setVisible(true);   
    }
    
    @FXML
    void selectCountry(ActionEvent event) {
        //get the divisions that belong to the selected country
        Country selectedCountry = CountryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Customer> customerCountryList = FXCollections.observableArrayList();
        ObservableList<CountryCustomerReport> divisionViewList = FXCollections.observableArrayList();
        
        for(Customer c: customerDivisionList){
            if(selectedCountry.getCountry_ID() == c.getCountry_ID()){
                customerCountryList.add(c);
            }
        }
        //save the division and counts. Increase counts if there are duplicates.
        int count =1;
        String country_name="";
        HashMap<String, Integer> divisionCustomerHash = new HashMap();
        for(Customer c: customerCountryList){
            country_name=c.getCountry();
            if(divisionCustomerHash.containsKey(c.getDivision())){
                divisionCustomerHash.put(c.getDivision(), divisionCustomerHash.get(c.getDivision())+1);
            }else{
                divisionCustomerHash.put(c.getDivision(), count);
            }
        }
        //iterate the hashmap to set country, division and customer counts to the view.
        for(Map.Entry divisionElement: divisionCustomerHash.entrySet()){
            String division_name = (String) divisionElement.getKey();
            int countValue = (int) divisionElement.getValue();
            CountryCustomerReport ccrObj = new CountryCustomerReport(country_name, division_name, countValue);
            divisionViewList.add(ccrObj);  
        }
        CustomerReportView.setItems(divisionViewList);
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        customer_count.setCellValueFactory(new PropertyValueFactory<>("customer_count"));
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        String viewFilePath ="/view/CustomerFXML.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event);
    }
    
    private ObservableList<Appointment> getAllAppointments(){
        AppointDAOImplement aptObj = new AppointDAOImplement();
        return aptObj.getAllAppointments();
    }
    
    private ObservableList<Contact> getAllContacts(){
        ContactDAOImplement contactObj = new ContactDAOImplement();
        return contactObj.getAllContacts();
    }
    
    private ObservableList<Country> getAllCountries(){
        CountryDAOImplement countryObj = new CountryDAOImplement();
        return countryObj.getAllCountries();
    }
}
