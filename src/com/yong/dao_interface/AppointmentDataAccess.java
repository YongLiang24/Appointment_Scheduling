package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Appointment;

/**
 * Appointment data access interface.
 * @author yongl
 */
public interface AppointmentDataAccess {
     /** A getAllAppointments abstract method. 
     * @return  returns an observableList of Appointments type*/
    public ObservableList<Appointment> getAllAppointments();
    
}
