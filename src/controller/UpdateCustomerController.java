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
 * UpdateCustomer Controller class
 * @author yongl
 */
public class UpdateCustomerController implements Initializable {
    @FXML private ComboBox<Country> UpdateCountryCombo;
    @FXML private ComboBox<Division> UpdateDivisionCombo; 
    @FXML private TextField UpdateName;
    @FXML private TextField UpdateAddress;
    @FXML private TextField UpdatePostal;
    @FXML private TextField UpdatePhone;
    @FXML private Label UpdateMsg;
 
    private ObservableList<Division> divisionsByCountryList= FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Using Lambda Expression to set countryList to the country combo box.
        SetComboBox setCountryCombo = cb -> cb.setItems(CustomerFXMLController.CountryList);
        setCountryCombo.setListToCombo(UpdateCountryCombo);
        //create a filtered division list using country ID and set it to the division combo box.
        divisionsByCountryList = getDivisionsByCountry(CustomerFXMLController.updateCustomerObj.getCountry_ID());
        UpdateDivisionCombo.setItems(divisionsByCountryList);
        //set the default country and division when update page is initialized.
        UpdateCountryCombo.setValue(getDefaultCountry());
        UpdateDivisionCombo.setValue(getDefaultDivision());
        //set default field values
        UpdateName.setText(CustomerFXMLController.updateCustomerObj.getName());
        UpdateAddress.setText(CustomerFXMLController.updateCustomerObj.getAddress());
        UpdatePostal.setText(CustomerFXMLController.updateCustomerObj.getPostal_Code());
        UpdatePhone.setText(CustomerFXMLController.updateCustomerObj.getPhone());
    }    
    
    @FXML
    void updateSelectCountry(ActionEvent event) {
        //create a filtered division list using country ID and set it to the division combo box.
        divisionsByCountryList = getDivisionsByCountry(UpdateCountryCombo.getSelectionModel().getSelectedItem().getCountry_ID());
        UpdateDivisionCombo.setItems(divisionsByCountryList);

    }

    @FXML
    void updateSelectDivision(ActionEvent event) {
        try{
        System.out.println(UpdateDivisionCombo.getSelectionModel().getSelectedItem().getDivision_ID());//null exception
        }
        catch(NullPointerException e){
            
        }
    }
    /** This method returns a list of divisions that is related to the country being selected. 
     * @param countryID country id
     * @return listDivision a list of filtered divisions.
     */
    private ObservableList<Division> getDivisionsByCountry(int countryID){
        ObservableList<Division> listDivision= FXCollections.observableArrayList();
        //iterate the static DivisionList to filter the divisions by country ID.
        for(Division d: CustomerFXMLController.DivisionList){
            if(countryID == d.getCountry_ID()){
                listDivision.add(d);
            }
        }
        return listDivision;
    }
    /** This method returns a default country object selected by user.
     @return returns a country or null*/
    private Country getDefaultCountry(){
        for(Country c: CustomerFXMLController.CountryList){
            if(c.getCountry_ID() == CustomerFXMLController.updateCustomerObj.getCountry_ID()){
                return c;
            }
        }
        return null;
    }
    /** This method returns a default division object selected by user.
     @return returns a division or null*/
    private Division getDefaultDivision(){
        for(Division d: CustomerFXMLController.DivisionList){
            if(d.getDivision_ID()== CustomerFXMLController.updateCustomerObj.getDivision_ID()){
                return d;
            }
        }
        return null;     
    }
    
    @FXML
    void cancelBtn(ActionEvent event) throws IOException {
        //call the switchStage utility method
        String viewFilePath ="/view/CustomerFXML.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event);

    }

    @FXML
    void updateBtn(ActionEvent event) {
        if(fillTextFields()){
            String Customer_Name = UpdateName.getText();
            String Address = UpdateAddress.getText();
            String Postal_Code = UpdatePostal.getText();
            String Phone = UpdatePhone.getText();
            String User = LoginFXMLController.loggedUser;
            int Division_ID=UpdateDivisionCombo.getSelectionModel().getSelectedItem().getDivision_ID();
            int Customer_ID=CustomerFXMLController.updateCustomerObj.getCustomer_ID();
            CustomerDAOImplement updateCustomer = new CustomerDAOImplement();
            int updateResult = updateCustomer.updateCustomer(Customer_Name, Address, Postal_Code, Phone, User, Division_ID, Customer_ID);
            if(updateResult==1){
                UpdateMsg.setText("Message: Customer Updated Successfully.");
            }else{
                UpdateMsg.setText("Message: Failed to update customer.");
            }
            
        }else{
            UpdateMsg.setText("Message: All Text Fields MUST BE FILLED BEFORE UPDATING.");
        }

    }
    
    private boolean fillTextFields(){
        if(UpdateName.getText().isEmpty() || UpdateAddress.getText().isEmpty() || UpdatePostal.getText().isEmpty() || UpdatePhone.getText().isEmpty()){
            return false;
        }
        return true;
    }
    
}
