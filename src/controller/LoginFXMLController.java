package controller;

import com.yong.dao_implement.UserDAOImplement;
import com.yong.functional_interface.SceneSwitch;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author yongl
 */
/** This is a controller class for the login form page. */
public class LoginFXMLController implements Initializable {
    
    @FXML private Label usernameLabel;
    @FXML private TextField UsernameTxt;
    @FXML private Label PasswordLabel;
    @FXML private TextField PasswordTxt;
    @FXML private Button LoginBtnLabel;
    @FXML private Label locationLabel;
    @FXML private Label UserLocation;
    @FXML private Button quitLabel;
    @FXML private Label ErrorMsg;
    
    private String emptyInput=""; //login empty error message String
    private String invalidInput="";//login invalid error message String
    private ObservableList<User> userList;
    Stage stage;
    Parent scene;
   
    /** This initialize method runs when a stage starts.it is also able to translate text to French.
     * @param url not currently using
     * @param rb a resourceBundle reference*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize the user data from database and save it to a local userList.
        UserDAOImplement allUsers = new UserDAOImplement();
        userList = allUsers.getAllUsers();
        
        //create a reference for the zoneID default location
        ZoneId localLocation = ZoneId.of(TimeZone.getDefault().getID());
        //convert the ZoneId type to a string
        UserLocation.setText(String.valueOf(localLocation));
        
        //translate the login page to French when system locale is set to French
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

    /** This method terminates the program. 
     * @param event ActionEvent reference*/
    @FXML
    private void exitApp(ActionEvent event) {
        System.exit(0);
    }
    
    /** This action event method responsible for tracking user login activities. 
     * @param event ActionEvent reference 
     * @throws java.io.IOException handles file IOException*/
    @FXML
    void loginHandler(ActionEvent event) throws IOException {
        //track user login activity and append them to a txt file.
        LoginActivity userLogin = new LoginActivity();
        userLogin.writeToFile(isLoginValid(), UsernameTxt.getText());
        //if user input is valid, proceed to the next scene, otherwise displays an error message.
        if(isLoginValid()){
            System.out.println("login = 1");
                stage = (Stage)((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/CustomerFXML.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();           
           
        }else if(UsernameTxt.getText().isEmpty() || PasswordTxt.getText().isEmpty()){
            ErrorMsg.setText(emptyInput);
        }else{
            ErrorMsg.setText(invalidInput);
        }
        

    } 
    /** this method returns true when user inputs match the query result. 
     * @return returns a Boolean*/
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
