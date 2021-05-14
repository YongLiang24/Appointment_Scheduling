package com.yong.dao_implement;

import com.yong.dao_interface.AppointmentDataAccess;
import com.yong.utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.Timestamp;

/**
 * implement data access for appointment interface.
 * @author yongl
 */
public class AppointDAOImplement implements AppointmentDataAccess{
    /** A getAllAppointments abstract method. 
     * @return  returns an observableList of Appointments type*/
    @Override
    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointList = FXCollections.observableArrayList();
        try {
            String sql = "select * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            int appointment_id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description= rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customer_id = rs.getInt("Customer_ID");
            int user_id = rs.getInt("User_ID");
            int contact_id = rs.getInt("Contact_ID");
            Appointment newAppoinment = new Appointment(appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id);
            appointList.add(newAppoinment);      
            }         
        } catch (SQLException ex) {
            ex.getStackTrace();
        }       
        return appointList;
    }
    
}
