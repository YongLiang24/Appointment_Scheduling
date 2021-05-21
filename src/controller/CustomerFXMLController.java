package controller;
import com.yong.dao_implement.AppointDAOImplement;
import com.yong.dao_implement.CountryDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.dao_implement.StateDAOImplement;
import com.yong.functional_interface.GetCountry;
import com.yong.utility.AlertConfirmation;
import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Country;
import model.Customer;
import model.Division;
import model.Appointment;

/**
 * FXML Customer Display Controller class. 
 * @author yongl*/
/** This is a customer model controller class. */
public class CustomerFXMLController implements Initializable {
    /** A static list that contains only the selected customer.
     * Making it a static member to allow it to use between scenes and stages.*/
    public static ObservableList<Customer> listCustomer;
    /** Making CountryList static as they are only used for read only, other controller classes may also access it. */
    public static ObservableList<Country> CountryList;
    /** Making DivisionyList static as they are only used for read only, other controller classes may also access it. */
    public static ObservableList<Division> DivisionList;
    /** This static variable is used to pass the selected customer object to the update controller. */
    public static Customer updateCustomerObj;
    //Scene Builder reference variables.
    @FXML private TableView<Customer> tableview;
    @FXML private TableColumn<Customer, String> Customer;
    @FXML private TableColumn<Customer, String> Address;
    @FXML private TableColumn<Customer, String> Postal_Code;
    @FXML private TableColumn<Customer, String> Phone;
    @FXML private TableColumn<Customer, String> Division;
    @FXML private TableColumn<Customer, String> Country;
    @FXML private Label Customer_Message;
    @FXML private AnchorPane Customer_AnchorPane;
    @FXML private ListView<Appointment> AlertList;
    @FXML private Label AlertMsg;
    //local fields
    private String divisionName;
    private int countryID;
    private String countryName;
    private ObservableList<Customer> CustomerList;
    private ObservableList<Customer> CustomerDivisionList;
    /** Initializes the controller class. Load data from database into the models.
     * get divisions and countries data for customer's table view.
     * @param url URL reference
     * @param rb ResourceBundle reference*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load countries data from database
        CountryDAOImplement getAllCountries = new CountryDAOImplement();
        CountryList = getAllCountries.getAllCountries();
        //load states data from database
        StateDAOImplement getAllStates = new StateDAOImplement();
        DivisionList = getAllStates.getAllStates();

        setCustomersToTableView();
        listCustomer=tableview.getSelectionModel().getSelectedItems();
        
        getAlertMessage();
    }
    /** This action event will switch to a customer creation page scene. 
     @param event event reference.
     @exception IOException stage switch*/
    @FXML
    void createCustomer(ActionEvent event) throws IOException {
        //Create a stage switch utility class reference and call its method.
        String viewFilePath ="/view/AddCustomer.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event);    
    }
    /** takes the user to the update customer form. 
     @param event event.*/
    @FXML
    void updateCustomer(ActionEvent event) {
        
        try{
            updateCustomerObj = listCustomer.get(0);
            //Create a stage switch utility class reference and call its method.
            String viewFilePath ="/view/UpdateCustomer.fxml";
            StageSwitch newStage = new StageSwitch();
            newStage.switchStage(viewFilePath, event);            
        }
        catch(Exception e){
            Customer_Message.setText("Please select a customer first.");
            System.out.println("check here");
        } 

    }
    /** this method deletes a selected customer, all appointments associate with the customer are also deleted. 
     @param event event */
    @FXML
    void deleteCustomer(ActionEvent event) {
        int deleteResult=0;
        listCustomer=tableview.getSelectionModel().getSelectedItems();
        String alertMessage ="Are you sure to delete this customer? ";
        
        AlertConfirmation alert = new AlertConfirmation();//pop a confirmation box.
        Optional<ButtonType> buttonType = null;
        //if 'OK' is selected, the user is taken back to the login form page.
        try{
        System.out.println(listCustomer.get(0));
        buttonType = alert.alertConfirmation(Customer_AnchorPane, alertMessage, "default");
        }catch(IndexOutOfBoundsException e){
            Customer_Message.setText("Message: Please select a customer first.");
        }
        try{
        if(buttonType.get() == ButtonType.OK){           
            
            //create a customer DAO object and call the delete method.
            CustomerDAOImplement deleteCustomer = new CustomerDAOImplement();
            deleteResult = deleteCustomer.deleteCustomer(listCustomer.get(0).getCustomer_ID());
                if(deleteResult ==1){
                    Customer_Message.setText("Message: A customer has been deleted.");
                    //call this method to refresh/update the tableview
                    setCustomersToTableView();                  
                }else{
                    Customer_Message.setText("Message: Connection error, customer was not deleted.");
                }
            }
           
        } catch(Exception e){
                Customer_Message.setText("Message: Please select a customer first.");
            }
    }
    /** This method gets called when user clicks on the logout button. 
     * It prompts user for confirmation.
     @param event an event reference.
     @throws IOException IOException*/
    @FXML
    void logOut(ActionEvent event) throws IOException {
        String alertMessage ="Would you like to logout?";
        AlertConfirmation alert = new AlertConfirmation();
        Optional<ButtonType> buttonType = alert.alertConfirmation(Customer_AnchorPane, alertMessage, "default");
        //if 'OK' is selected, the user is taken back to the login form page.
        if(buttonType.get() == ButtonType.OK){
             //Create a stage switch utility class reference and call its method.
              String viewFilePath ="/view/LoginFXML.fxml";
              StageSwitch newStage = new StageSwitch();
              newStage.switchStage(viewFilePath, event);
        }
    }
    
    /** This method utilizes Lambda Expressions to retrieve division and country data related to the customers. 
     It generates customer objects with the correct divisions and countries and return an observableList.
     * @param customerList a list contains all customer objects
     * @param divisionList a list contains all division objects
     * @param countryList a list contains all countries object
     * @return returns a list of customer objects that contains both divisions and countries data.
     */
    private ObservableList<Customer> CustomerDivisionList(ObservableList<Customer> customerList, ObservableList<Division> divisionList, ObservableList <Country>countryList){
       ObservableList<Customer> CustDiviList=FXCollections.observableArrayList();
        for(Customer c : customerList){
         /** #1 Using Lambda Stream filter Predicate to get the division that is belong to a particular user. 
         This Lambda expression simplifies the implementation and reliable.*/
        Stream<Division> filterDivision=divisionList.stream().filter(p-> p.getDivision_ID()==c.getDivision_ID());
         /** iterate through the collection to get the division and its IDs. */
        filterDivision.forEach(d ->{
            divisionName = d.getDivision();
            countryID = d.getCountry_ID();
        });  
        
         /** #2 Using a custom interface for Lambda Expression to return a country name. 
         * Returns a country based on the division foreign key ID.*/
        GetCountry getCountry= ct ->{
            for(Country country : countryList){
                if(country.getCountry_ID() == ct){ return country;}
            }
            return null;
            };
          countryName= getCountry.getCountry(countryID).getCountry();
        //instantiate a new customer with divisions and countries data.
          Customer custDivision = new Customer(c.getName(), c.getAddress(), c.getPostal_Code(),c.getPhone(), divisionName, countryName, c.getCustomer_ID(), c.getDivision_ID(), countryID);
          CustDiviList.add(custDivision);
        }
        return CustDiviList;
    }
    /** This method sets data to the FXML tableView. 
     @param CustomerDivisionList a list contains customer objects with divisions and countries.*/
    private void setTableView(ObservableList CustomerDivisionList){
        tableview.setItems(CustomerDivisionList);
        Customer.setCellValueFactory(new PropertyValueFactory<>("Name"));//"Name" matches the model field name"
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Division.setCellValueFactory(new PropertyValueFactory<>("Division"));
        Country.setCellValueFactory(new PropertyValueFactory<>("Country")); 
    }  
    /** this method sets a list of customers to the table view. */
    private void setCustomersToTableView(){
        //load customer data from database
        CustomerDAOImplement getAllCustomers = new CustomerDAOImplement();
        CustomerList = getAllCustomers.getAllCustomers(); 
        //generate a list of customers with division and country data.
        CustomerDivisionList = CustomerDivisionList(CustomerList, DivisionList, CountryList);
         //call the set tableview method to set data into the table view using CustomerDivisionList.
        setTableView(CustomerDivisionList);
    }
    /** this method takes user to the main appointment page. 
     @exception IOException stage switch
     @param event event*/
    @FXML
    void getAppointments(ActionEvent event) throws IOException {
        String viewFilePath ="/view/Appointment.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event); 

    }
    
    private void getAlertMessage(){
        //get current datetime
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("currenttime: "+currentDateTime);
        ObservableList<Appointment> aptList = FXCollections.observableArrayList();
        ObservableList<Appointment> alertViewList = FXCollections.observableArrayList();
        //get all appointments to an observable list.
        AppointDAOImplement appointmentObj = new AppointDAOImplement();
        aptList = appointmentObj.getAllAppointments();
        //compare the current datetime with the appointment start datetime for the alert event.
        for(Appointment apt: aptList){
            LocalDateTime aptDateTime = apt.getStart().toLocalDateTime();
            boolean isSameDate =currentDateTime.toLocalDate().equals(aptDateTime.toLocalDate());
            boolean isSameHour = (currentDateTime.getHour() == aptDateTime.getHour());
            long timeDifference = ChronoUnit.MINUTES.between(currentDateTime, aptDateTime);
            //System.out.println("time: "+timeDifference);
            boolean isAlertMinute = (timeDifference+1) >=0 && (timeDifference+1) <=15;
            if(isSameDate && isSameHour && isAlertMinute){
                AlertMsg.setText(" You have the following appointments within 15 minutes:");
                alertViewList.add(apt);   
            }else{
                AlertMsg.setText(" You have no upcoming appointments.");
            }
        }
        //set the alert appointments to the list.
        AlertList.setItems(alertViewList);
        
        
        
    }
}
