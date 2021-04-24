/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class LoginFXMLController implements Initializable {
    
    @FXML
    private Label UserLocation;
    @FXML
    private TextField userNameTxt;
    @FXML
    private TextField passwordTxt;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //create a reference for the zoneID default location
        ZoneId localLocation = ZoneId.of(TimeZone.getDefault().getID());
        //convert the ZoneId type to a string
        String userLocation = String.valueOf(localLocation);
        //pass the default location to the Label Userlocation
        UserLocation.setText(userLocation);
        
       
    }    

    @FXML
    private void exitApp(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void loginHandler(ActionEvent event) {
        System.out.println(userNameTxt.getText());
        System.out.println(passwordTxt.getText());
        

    }

   
    
}
