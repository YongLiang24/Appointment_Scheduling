package controller;
import com.yong.functional_interface.SetComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.DragEvent;
import model.Country;
/**
 * FXML Customer Creation Controller class. 
 * @author yongl
 */
public class AddCustomerController implements Initializable {
    
    @FXML private ComboBox<Country> Country_Combo;
    @FXML private ComboBox<?> Division_Combo;
    
    /** #3 Utilized Lambda expression to set country names to the combo box, this expression may be used again for another combo box type. 
     * Initializes the controller class.
     * @param url URL reference
     * @param rb ReourceBundle reference*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //copy the country names to a local countryNameList.
        //CustomerFXMLController.CountryList.forEach((c) -> {countryNameList.add(c.getCountry());});
        //Lambda Expression to set countryNameList to the country combo box.
        SetComboBox setCountryCombo = cb -> cb.setItems(CustomerFXMLController.CountryList);
        setCountryCombo.setListToCombo(Country_Combo);
    }    
    
    @FXML
    void selectCountry(ActionEvent event) {
        Country_Combo.getSelectionModel().getSelectedItem();
        System.out.println(Country_Combo.getSelectionModel().getSelectedItem().getCountry_ID());

    }

    @FXML
    void selectDivision(ActionEvent event) {
//        if(Country_Combo.getSelectionModel().isEmpty()){
//            System.out.println("select a country first");
//        }
        System.out.println("controller.AddCustomerController.selectDivision()");
    }
    
}
