package com.yong.utility;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** This utility method alerts a confirmation box and return an optional button type based on user's selection, 'OK' or 'CANCEL'. 
 * @author yongl
 */
public class AlertConfirmation {
    /** This method returns an Optional ButtonType based on user's selection. 
     * @param anchorpane Accepts an AnchorPane from scene builder.
     * @param alertText Accepts a custom text for the confirmation box description.
     * @param alertType a type of alert.
     * @return  An Optional ButtonType*/
    public Optional<ButtonType> alertConfirmation(AnchorPane anchorpane, String alertText, String alertType){
        //generate a stage window for the anchorpane parameter.
        Stage st = (Stage) anchorpane.getScene().getWindow();
        Alert alert;
        switch(alertType){
            case "information" : alert = new Alert(Alert.AlertType.INFORMATION, ""); break; 
            case "warning": alert = new Alert(Alert.AlertType.WARNING, ""); break;          
            default: alert = new Alert(Alert.AlertType.CONFIRMATION, "");       
        }     
        //generate a MODAL box.
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(st);
        //set the alert text message from the parameter.
        alert.getDialogPane().setContentText("");
        alert.getDialogPane().setHeaderText(alertText);
        //returns 'OK' or 'CANCEL' based on user's selection.
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType;
    };
    
}
