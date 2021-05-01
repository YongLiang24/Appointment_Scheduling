package com.yong.utility;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** This is an utility class that performs stages switching. 
 * @author yongl
 */
public class StageSwitch {
    
    public void switchStage(String viewFilePath, ActionEvent event) throws IOException{
            Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerFXML.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); 
    }
}
