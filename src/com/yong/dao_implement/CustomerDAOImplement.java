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
import model.Appointment;

/** Customer data access implementation class.
 * @author yongl
 */
public class CustomerDAOImplement implements CustomerDataAccess{
    
    LocalDateTime dateTimeNow = LocalDateTime.now();
    Timestamp ts = Timestamp.valueOf(dateTimeNow);
    public static int hasCustomerCreated;
    ObservableList<Appointment> aptList;
    
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
    /** create customers method implementation.
     * @param name name
     * @param address address
     * @param postal postal
     * @param phone phone
     * @param user user
     * @param divisionID division id
     */
    @Override
    public void createCustomer(String name, String address, String postal, String phone, String user, int divisionID) {    
        try {
            String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Create_date, Created_By, Last_Update, Last_Updated_By, Division_ID) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setTimestamp(5,ts); //timestamp object
            ps.setString(6, user);
            ps.setTimestamp(7, ts); //timestamp object
            ps.setString(8, user);
            ps.setString(9, String.valueOf(divisionID));
            hasCustomerCreated = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * delete customer method
     * @param customerID customer ID
     * @return query result.
     */
    @Override
    public int deleteCustomer(int customerID) {
        deleteCustomerAppointment(customerID); //delete appointments associate with a customer.
        int result=0;
        try {
            String sql ="delete from customers where Customer_ID =?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            result = ps.executeUpdate();
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
          return result;
    }
    
    @Override
    public int deleteCustomerAppointment(int customerID) {
        int result =0;
        String sql ="delete from appointments where Customer_ID =?";
        AppointDAOImplement appointList = new AppointDAOImplement();
        aptList = appointList.getAllAppointments();
        for(Appointment apt: aptList){
            try {
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setInt(1, customerID);
                result = ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
     /**
     * update customer method
     * @param Customer_Name update customer name
     * @param Address update address
     * @param Postal_Code update postal code
     * @param Division_ID update the division ID
     * @param Phone update Phone
     * @param Customer_ID match the customer ID
     * @param User Logged in User
     * @return query result.
     */
    @Override
    public int updateCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, String User, int Division_ID, int Customer_ID) {
        int updateResult=0;
        try {
            String sql ="UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code =?, Phone =?, Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, Customer_Name);
            ps.setString(2, Address);
            ps.setString(3, Postal_Code);
            ps.setString(4, Phone);
            ps.setTimestamp(5, ts);
            ps.setString(6, User);
            ps.setInt(7, Division_ID);
            ps.setInt(8, Customer_ID);
            updateResult = ps.executeUpdate();     
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        return updateResult;
    }

    @Override
    public Customer getCustomerByID(int customerID) {  
        Customer customer=null;
        try {
            String sql = "select * from customers where Customer_ID ="+customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            customer = new Customer(rs.getInt("Customer_ID"),rs.getString("Customer_Name"));
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        
        return customer;
    } 
}

