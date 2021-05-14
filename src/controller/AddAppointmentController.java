/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.yong.utility.StageSwitch;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yongl
 */
public class AddAppointmentController implements Initializable {
    @FXML private DatePicker DatePicker;
    //@FXML private ChoiceBox<Integer> StartHour;  
    @FXML private ComboBox<Integer> StartMinute;
    @FXML private ComboBox<Integer> EndHour;  
    @FXML private ComboBox<Integer> EndMinute;
    
    @FXML private TextField Title;
    @FXML private TextField Description;
    @FXML private TextField Location;
    @FXML private ComboBox<?> ContactCombo;
    @FXML private TextField Type;
    @FXML private ComboBox<?> CustomerCombo;
    @FXML private Label StringStartTime;
    @FXML private Label StringEndTime;
    @FXML private Label APTWarningMsg;
    
    @FXML
    private ComboBox<Integer> StartHour;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Integer> StartHourList = FXCollections.observableArrayList(8,9,10,11,12,13,14,15,16,17,18,19,20);      
        StartHour.setItems(StartHourList);
        ObservableList<Integer> StartMinuteList = FXCollections.observableArrayList(0,5,10,15,20,25,30,35,40,45,50,55);      
        StartMinute.setItems(StartMinuteList);
        
        ObservableList<Integer> EndHourList = FXCollections.observableArrayList(8,9,10,11,12,13,14,15,16,17,18,19,20);      
        EndHour.setItems(EndHourList);
        ObservableList<Integer> EndMinuteList = FXCollections.observableArrayList(0,5,10,15,20,25,30,35,40,45,50,55);      
        EndMinute.setItems(EndMinuteList);
        
    }    
    
    @FXML
    void createAppointmentBtn(ActionEvent event) {

    }

    @FXML
    void goBackBtn(ActionEvent event) throws IOException {
        //call the switchStage utility method
        String viewFilePath ="/view/Appointment.fxml";
        StageSwitch newStage = new StageSwitch();
        newStage.switchStage(viewFilePath, event);
    }
    
    @FXML
    void selectStartHour(ActionEvent event){      
        timeFormatter(StartHour, StartMinute, StringStartTime); //a customer hour minute formatter for the create appointment.  
        if(!validateEndHour()){
            APTWarningMsg.setText("Message: Starting hour must be earlier than or the same as the ending hour.");
        }
    }

    @FXML
    void selectStartMinute(ActionEvent event){
        timeFormatter(StartHour, StartMinute, StringStartTime); //a customer hour minute formatter for the create appointment.
        if(!validateEndMinute()){
            APTWarningMsg.setText("Message: Starting Minute must be earlier than the ending minute.");
        }
    }
    
    @FXML
    void selectEndHour(ActionEvent event){
        timeFormatter(EndHour, EndMinute, StringEndTime); //a customer hour minute formatter for the create appointment. 
        if(!validateEndHour()){
            APTWarningMsg.setText("Message: Starting hour must be earlier than or the same as the ending hour.");
        }
    }

    @FXML
    void selectEndMinute(ActionEvent event){
        timeFormatter(EndHour, EndMinute, StringEndTime); //a customer hour minute formatter for the create appointment. 
        if(!validateEndMinute()){
            APTWarningMsg.setText("Message: Starting Minute must be earlier than the ending minute.");
        }
    }
    
    private void timeFormatter(ComboBox<Integer> hour, ComboBox <Integer>min, Label timeTxt)throws NullPointerException{
        if(min.getSelectionModel().getSelectedItem() != null && hour.getSelectionModel().getSelectedItem() != null){
            if(min.getSelectionModel().getSelectedItem()<10){
                timeTxt.setText(String.valueOf("["+hour.getSelectionModel().getSelectedItem())+" : 0"+String.valueOf(min.getSelectionModel().getSelectedItem())+"]");
            }else{
                timeTxt.setText(String.valueOf("["+hour.getSelectionModel().getSelectedItem())+" : "+String.valueOf(min.getSelectionModel().getSelectedItem())+"]");
            } 
        }
    }
    
    private boolean validateEndHour(){
        try{
        if(StartHour.getSelectionModel().getSelectedItem() > EndHour.getSelectionModel().getSelectedItem()){
            return false;
        }
        }catch(NullPointerException e){}
      return true;
    }
    
    private boolean validateEndMinute(){
        try{
        if(StartHour.getSelectionModel().getSelectedItem() == EndHour.getSelectionModel().getSelectedItem() && StartMinute.getSelectionModel().getSelectedItem() >= EndMinute.getSelectionModel().getSelectedItem()){         
            return false;
        }
        }catch(NullPointerException e){}
        return true;
    }

    
    
}
