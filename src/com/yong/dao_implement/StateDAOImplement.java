/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yong.dao_implement;
import com.yong.dao_interface.StateDataAccess;
import com.yong.utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

/** An implement method for States data access.
 * @author yongl
 */
public class StateDAOImplement implements StateDataAccess{

    @Override
    public ObservableList<Division> getAllStates() {
        ObservableList<Division> stateList = FXCollections.observableArrayList();   
        try {
            String sql = "select * from first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //create state objects using users data from database
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = (int)rs.getInt("COUNTRY_ID");
                Division state = new Division(divisionID, divisionName, countryID);
                //add the user objects to the stateList
                stateList.add(state);
            }         
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return stateList;
    }
}
