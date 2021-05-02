package controller;

import com.yong.dao_implement.CountryDAOImplement;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.dao_implement.StateDAOImplement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.Country;
import model.Customer;
import model.State;
/**
 * FXML Controller class. 
 * @author yongl
 */
/** This is a customer model controller class. */
public class CustomerFXMLController implements Initializable {
    
    private ObservableList<Country> CountryList;
    private ObservableList<State> StateList;
    private ObservableList<Customer> CustomerList;
    
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
        StateList = getAllStates.getAllStates();
        //load customer data from database
        CustomerDAOImplement getAllCustomers = new CustomerDAOImplement();
        CustomerList = getAllCustomers.getAllCustomers();
    }    
    
}
