package controller;
import com.yong.dao_implement.CountryDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.dao_implement.StateDAOImplement;
import com.yong.functional_interface.GetCountry;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Country;
import model.Customer;
import model.Division;

/**
 * FXML Controller class. 
 * @author yongl*/
/** This is a customer model controller class. */
public class CustomerFXMLController implements Initializable {
    
    private ObservableList<Country> CountryList;
    private ObservableList<Division> DivisionList;
    private ObservableList<Customer> CustomerList;
    private ObservableList<Customer> CustomerDivisionList;
    /**
     *this is a list that contains only the selected customer.
     * Making it a static member to allow it to use between scenes and stages.
     */
    public static ObservableList<Customer> listCustomer;
    
    @FXML private TableView<Customer> tableview;
    @FXML private TableColumn<Customer, String> Customer;
    @FXML private TableColumn<Customer, String> Address;
    @FXML private TableColumn<Customer, String> Postal_Code;
    @FXML private TableColumn<Customer, String> Phone;
    @FXML private TableColumn<Customer, String> Division;
    @FXML private TableColumn<Customer, String> Country;
    @FXML private Label Customer_Message;
    
    private String divisionName;
    private int countryID;
    private String countryName;
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
        //load customer data from database
        CustomerDAOImplement getAllCustomers = new CustomerDAOImplement();
        CustomerList = getAllCustomers.getAllCustomers();
        
        //generate a list of customers with division and country data.
        CustomerDivisionList = CustomerDivisionList(CustomerList, DivisionList, CountryList);
        //call the set tableview method to set data into the table view using CustomerDivisionList.
        setTableView(CustomerDivisionList);          
    }    
    @FXML
    void selectCustomer(ActionEvent event) {
        listCustomer=tableview.getSelectionModel().getSelectedItems();
        try{
        System.out.println(listCustomer.get(0).getCountry().isEmpty());
        }
        catch(Exception e){
            Customer_Message.setText("Please select a customer first.");
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
        
         /** #2 Using a custom interface for Lambda Expression that is implemented on the fly. 
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
    
}
