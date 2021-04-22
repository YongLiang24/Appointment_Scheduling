/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yong.dao_implement;

import com.yong.dao_interface.UserDao;
import model.Users;
import com.yong.utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yongl
 */
public class UserDaoImplement implements UserDao{

    @Override
    public ObservableList<Users> getAllUsers() {
        
        ObservableList<Users> userList = FXCollections.observableArrayList();
        
        try {
            String sql = "select * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        
        return userList;
    }
    
    
    
}
