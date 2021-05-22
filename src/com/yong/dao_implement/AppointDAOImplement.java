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
import java.time.LocalDateTime;
import com.yong.functional_interface.ConvertToTimestamp;

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
    @Override
    public int createAppointment(String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, String user, int customer_id, int user_id, int contact_id){
        int returnResult=0;
        //using lambda expression to get the current time as timestamp object.
        ConvertToTimestamp currentStamp = ()-> Timestamp.valueOf(LocalDateTime.now());
        Timestamp localNow =currentStamp.getTimestamp();
        
        //convert the LocalDateTime to Timestamp using Lambda
        ConvertToTimestamp startStamp = ()-> Timestamp.valueOf(startTime);
        Timestamp start =startStamp.getTimestamp();
        
        ConvertToTimestamp endStamp = ()-> Timestamp.valueOf(endTime);
        Timestamp end = endStamp.getTimestamp();
        
        try {
            String sql="insert into appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Customer_ID, User_ID, Contact_ID) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setTimestamp(7, localNow);
            ps.setString(8, user);
            ps.setInt(9, customer_id);
            ps.setInt(10, user_id);
            ps.setInt(11, contact_id);
            returnResult = ps.executeUpdate();      
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnResult;
    }
    /** delete a selected appointment. 
    @param appointmentID appointment id.
     * @return  a result of 1 or 0 */
    @Override
    public int deleteAppointment(int appointmentID) {
        int result=0;
        try {
            String sql ="delete from appointments where Appointment_ID =?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);
            result = ps.executeUpdate();
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
          return result;
    }

    @Override
    public int updateAppointment(int appointment_id, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, String user, int customer_id, int contact_id, int user_id) {
        int updateResult=0;
        try {
            String sql ="UPDATE appointments SET Title = ?, Description = ?, Location =?, Type =?, Start=?, End=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = "+appointment_id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);       
            ps.setTimestamp(5, Timestamp.valueOf(startTime));
            ps.setTimestamp(6, Timestamp.valueOf(endTime));
            ps.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, user);
            ps.setInt(9, customer_id);
            ps.setInt(10, contact_id);
            ps.setInt(11, user_id);
            updateResult = ps.executeUpdate();     
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        return updateResult;
    }


    
}
