package com.yong.appointment_system;
import com.yong.utility.DBConnection;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author yongl
 */
/** This is the application's main class. */
public class Appointment_System extends Application{

    /** This main method starts a connection, launch the passing argument and closes a connection. 
    *@param args the command line arguments
    */
    public static void main(String[] args) {
        
       DBConnection.startConnection();
       launch(args);
       DBConnection.closeConnection();
    }

    /** This method loads the view loginFXML file and display a scene.  
     * @param st a Stage object reference
     */
    @Override
    public void start(Stage st) throws Exception { 
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginFXML.fxml"));
        Scene sce = new Scene(root);
        st.setScene(sce);
        st.setTitle("Main Menu");
        st.show();
    }  
}
