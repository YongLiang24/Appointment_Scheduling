package com.yong.dao_implement;

import com.yong.dao_interface.CustomerDataAccess;
import com.yong.utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

/** Customer data access implementation class.
 * @author yongl
 */
public class CustomerDAOImplement implements CustomerDataAccess{
    
    LocalDateTime dateTimeNow = LocalDateTime.now();
    Timestamp ts = Timestamp.valueOf(dateTimeNow);
    public static int hasCustomerCreated;
    
    /** This method gets all customers from database. 
     @return Returns an observableList containing all customer objects
     */
    @Override
    public ObservableList<Customer> getAllCustomers() {
           ObservableList<Customer> customerList = FXCollections.observableArrayList();   
        try {
            String sql = "select * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //save customer data from database to model
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name=rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                int Division_ID = rs.getInt("DIVISION_ID");
                Customer customer = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID);
                //add the customer objects to the stateList
                customerList.add(customer);
            }         
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return customerList;
    }

    @Override
    public void createCustomer(String name, String address, String postal, String phone, String user, int divisionID) {    
        try {
            String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Create_date, Created_By, Last_Update, Last_Updated_By, Division_ID) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setString(5, String.valueOf(ts)); //timestamp object
            ps.setString(6, user);
            ps.setString(7, String.valueOf(ts)); //timestamp object
            ps.setString(8, user);
            ps.setString(9, String.valueOf(divisionID));
            hasCustomerCreated = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
