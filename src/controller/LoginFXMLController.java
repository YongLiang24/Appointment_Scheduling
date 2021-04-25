package controller;

import com.yong.dao_implement.UserDaoImplement;
import com.yong.utility.LoginActivity;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;

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
    private ObservableList<User> userList;
    

   

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize the user data from database and save it to a local userList.
        UserDaoImplement allUsers = new UserDaoImplement();
        userList = allUsers.getAllUsers();
        
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
            //when Locale is not in fr, use the default error messages in English
            emptyInput ="Warning: Username or Password field can not be empty, please try again.";
            invalidInput ="Warning: Username or Password is invalid, please try again";  
        }
    }    

    @FXML
    private void exitApp(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void loginHandler(ActionEvent event) throws IOException {
        //track user login activity and append them to a txt file.
        LoginActivity userLogin = new LoginActivity();
        userLogin.writeToFile(true);
        //System.out.println(isLoginValid());
        //if user input is valid, proceed to the next scene, otherwise displays an error message.
        if(isLoginValid()){
            System.out.println("proceed to the next scene.");
        }else if(UsernameTxt.getText().isEmpty() || PasswordTxt.getText().isEmpty()){
            System.out.println(emptyInput);
        }else{
            System.out.println(invalidInput);
        }
        

    } 
    //this method returns true when user inputs match the query result.
    private boolean isLoginValid(){   
        String inputName = UsernameTxt.getText();//set user inputs this local variable.
        String inputPass = PasswordTxt.getText();//set user inputs this local variable.
        for(User user: userList){
            //compare user inputs with query results
            if(user.getUsername().equals(inputName) && user.getPassword().equals(inputPass)){
                return true;
            }
        }
       return false;
    }
    
}
