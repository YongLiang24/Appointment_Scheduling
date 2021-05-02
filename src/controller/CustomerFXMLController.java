package controller;
import com.yong.dao_implement.CountryDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.dao_implement.StateDAOImplement;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    @FXML private TableView<Customer> tableview;
    @FXML private TableColumn<Customer, String> Customer;
    @FXML private TableColumn<Customer, String> Address;
    @FXML private TableColumn<Customer, String> Postal_Code;
    @FXML private TableColumn<Customer, String> Phone;
    @FXML private TableColumn<Customer, String> Division;
    @FXML private TableColumn<Customer, String> Country;
    
    /** Initializes the controller class. Load data from database into the models.
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
        
       
            
        tableview.setItems(CustomerList);
        Customer.setCellValueFactory(new PropertyValueFactory<>("Name"));//"Name" matches the model field name"
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        
        
        Stream<Division> filterDivision=DivisionList.stream().filter(p-> p.getDivision_ID()==63);
        
        filterDivision.forEach(d -> System.out.println(d.getDivision()));
        
    }    
    
}
