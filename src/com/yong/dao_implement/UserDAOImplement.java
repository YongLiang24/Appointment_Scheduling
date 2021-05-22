package com.yong.dao_implement;

import model.User;
import com.yong.utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import com.yong.dao_interface.UserDataAccess;

/** This class implements the user data access interface.
 * @author yongl
 */
public class UserDAOImplement implements UserDataAccess{
    /** This method gets all users from database. 
     @return Returns an observableList containing all user objects
     */
    @Override
    public ObservableList<User> getAllUsers() {
        //create an observeableList to hold User objects.
        ObservableList<User> userList = FXCollections.observableArrayList();   
        try {
            String sql = "select * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //create user objects using users data from database
                User user = new User(rs.getString("User_Name"),rs.getString("Password"), rs.getInt("User_ID"));
                //add the user objects to the userList
                userList.add(user);
            }         
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        
        return userList;
    }   

    @Override
    public User getUserByID(int userID) {
        User user=null;
        try {
            String sql = "select * from users where User_ID ="+userID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                user = new User(rs.getString("User_Name"), rs.getString("Password"), rs.getInt("User_ID"));
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return user;
    }
    
}
