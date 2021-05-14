package com.yong.dao_implement;

import com.yong.dao_interface.ContactDataAccess;
import com.yong.utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

/**
 * Contact data access implementation.
 * @author yongl
 */
public class ContactDAOImplement implements ContactDataAccess{
     /** A getAllContacts abstract method. 
     * @return  returns an observableList of Contact type*/
    @Override
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try {
            String sql = "select * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Contact newContact = new Contact(rs.getString("Contact_Name"), rs.getString("Email"), rs.getInt("Contact_ID"));
                contactList.add(newContact);          
            }         
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return contactList;
    }
    
}
