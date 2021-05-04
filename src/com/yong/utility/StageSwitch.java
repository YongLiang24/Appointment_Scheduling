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
    /** This method takes a file path and an event reference to switch to another stage.
     * @param viewFilePath path of a view file.
     * @param event an event object reference.
     * @throws java.io.IOException IOException.*/
    public void switchStage(String viewFilePath, ActionEvent event) throws IOException{
            Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource(viewFilePath));
            stage.setScene(new Scene(scene));
            stage.show(); 
    }
}
