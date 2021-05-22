package controller;
import com.yong.dao_implement.CustomerDAOImplement;
import com.yong.functional_interface.SetComboBox;
import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.Division;
/**
 * FXML Customer Creation Controller class. 
 * @author yongl
 */
public class AddCustomerController implements Initializable {
    
    @FXML public ComboBox<Country> Country_Combo;
    @FXML public ComboBox<Division> Division_Combo;
    @FXML private Label WarningMsg;
    @FXML private TextField CustomerName;
    @FXML private TextField Address;
    @FXML private TextField PostalCode;
    @FXML private TextField PhoneNumber;
    private final ObservableList<Division> divisionList=FXCollections.observableArrayList();
    private int DivisionID;

    /** Discussion of Lambda: Utilized Lambda expression to set a list of countries to the combo box.
     * this expression may be used again for another combo box type. 
     * Initializes the controller class.
     * @param url URL reference
     * @param rb ReourceBundle reference*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Using Lambda Expression to set countryList to the country combo box.
        SetComboBox setCountryCombo = cb -> cb.setItems(CustomerFXMLController.CountryList);
        setCountryCombo.setListToCombo(Country_Combo);
    }    
    /** Discussion of Lambda: this method uses Lambda Expression to set divisionList to the division combo box.
     adds divisions to the combo box based on the country select.
     @param event event*/
    @FXML
    void selectCountry(ActionEvent event) {
        divisionList.clear();//empty the list each time a different country is selected.
        //iterate the DivisionList and add divisions related to the selected country.
        for(Division d :CustomerFXMLController.DivisionList){
            if(d.getCountry_ID() == Country_Combo.getSelectionModel().getSelectedItem().getCountry_ID()){
                divisionList.add(d);
            }
        }
        //Using Lambda Expression to set divisionList to the division combo box.
       SetComboBox setCountryCombo = cb -> cb.setItems(divisionList);
       setCountryCombo.setListToCombo(Division_Combo);
       Division_Combo.setDisable(false);//enable the division combo box once a country is selected.
        
    }
    /** this method gets selected the division id. 
     @param event event*/
    @FXML
    void selectDivision(ActionEvent event) {
        if(Division_Combo.getSelectionModel().getSelectedItem() != null){
            DivisionID = Division_Combo.getSelectionModel().getSelectedItem().getDivision_ID();  
        }    
    }
    /** this method validates user inputs before creating a customer. 
     @param event event*/
    @FXML
    void createCustomerBtn(ActionEvent event) {
        if(hasFieldsFilled()){
        CustomerDAOImplement createCustomer = new CustomerDAOImplement();
        createCustomer.createCustomer(CustomerName.getText(), Address.getText(), PostalCode.getText(), PhoneNumber.getText(), LoginFXMLController.loggedUser, DivisionID);
        if(CustomerDAOImplement.hasCustomerCreated ==1){
           WarningMsg.setText("Message: A new customer has been created successfully.");
           emptyFormInputs();
        }else{
           WarningMsg.setText("Message: Failed to create a new customer. Please try again.");
           emptyFormInputs();
        }
        }
    }
    /** this method returns user to the customer main page. 
     @exception IOException stage switch
     @param event event*/
    @FXML
    void returnBtn(ActionEvent event) throws IOException {
        //call the switchStage utility method
        String viewFilePath ="/view/CustomerFXML.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event);

    }
    /** this method validates any empty fields. 
     @return returns a Boolean*/
    private boolean hasFieldsFilled(){
        if(CustomerName.getText().isEmpty() || Address.getText().isEmpty() || PostalCode.getText().isEmpty() || PhoneNumber.getText().isEmpty() || Division_Combo.getSelectionModel().getSelectedItem()== null || Country_Combo.getSelectionModel().getSelectedItem() == null){
            WarningMsg.setText("Message: All fields must be filled.");
            return false;
        }
        return true;
    }
    /** this method empties the form after user has created a customer. */
    private void emptyFormInputs(){
        CustomerName.clear(); 
        Address.clear(); 
        PostalCode.clear(); 
        PhoneNumber.clear();  
    }
    
}
