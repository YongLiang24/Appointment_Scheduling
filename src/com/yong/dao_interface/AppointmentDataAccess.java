package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Appointment;
import java.time.LocalDateTime;

/**
 * Appointment data access interface.
 * @author yongl
 */
public interface AppointmentDataAccess {
     /** A getAllAppointments abstract method. 
     * @return  returns an observableList of Appointments type*/
    public ObservableList<Appointment> getAllAppointments();
    
    /** Create an appointment method.
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startTime startTime
     * @param endTime endTime
     * @param user created by
     * @param customer_id customer id
     * @param user_id user id
     * @param contact_id contact id
     * @return  the result 1 or 0;*/
    public int createAppointment(String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, String user, int customer_id, int user_id, int contact_id);
    /** delete a selected appointment. 
     @param appointmentID appointment id. */
    public int deleteAppointment(int appointmentID);
    
}
