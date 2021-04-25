/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class LoginFXMLController implements Initializable {
    
    @FXML private Label usernameLabel;
    @FXML private TextField UsernameTxt;
    @FXML private Label PasswordLabel;
    @FXML private TextField PasswordTxt;
    @FXML private Button LoginBtnLabel;
    @FXML private Label locationLabel;
    @FXML private Label UserLocation;
    @FXML private Button quitLabel;
    
    private String emptyInput=""; //login empty error message String
    private String invalidInput="";//login invalid error message String
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //create a reference for the zoneID default location
        ZoneId localLocation = ZoneId.of(TimeZone.getDefault().getID());
        //convert the ZoneId type to a string
        String userLocation = String.valueOf(localLocation);
        //pass the default location to the Label Userlocation
        UserLocation.setText(userLocation);
        
        //translate the login page to French when system locale set to French
        if(Locale.getDefault().getLanguage().equals("fr")){
            rb = ResourceBundle.getBundle("controller/Lan", Locale.getDefault());
            usernameLabel.setText(rb.getString("username"));
            PasswordLabel.setText(rb.getString("password"));
            LoginBtnLabel.setText(rb.getString("login"));
            locationLabel.setText(rb.getString("currentLocation"));
            quitLabel.setText(rb.getString("quit"));   
            emptyInput =rb.getString("emptyInput");
            invalidInput = rb.getString("invalidInput");
        }else{
            emptyInput ="Warning: Username or Password field can not be empty, please try again.";
            invalidInput ="Warning: Username or Password is invalid, please try again";  
        }
    }    

    @FXML
    private void exitApp(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void loginHandler(ActionEvent event) {
        System.out.println(UsernameTxt.getText());
        System.out.println(PasswordTxt.getText());
        

    }

   
    
}
