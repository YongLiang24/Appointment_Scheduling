package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Customer;

/** Customer data access interface.
 * @author yongl
 */
public interface CustomerDataAccess {
    /** Get all customer methods.. 
    * @return  returns an observableList of Customer type*/
    public ObservableList<Customer> getAllCustomers();
    /** create customers interface method.
     * @param name name
     * @param address address
     * @param postal postal
     * @param phone phone
     * @param user user
     * @param divisionID division id
     */
    public void createCustomer(String name, String address, String postal, String phone, String user, int divisionID);

    /**
     * delete customer method
     * @param customerID customer ID
     * @return query result.
     */
    public int deleteCustomer(int customerID);
    public int deleteCustomerAppointment(int customerID);
    
     /**
     * update customer method
     * @param Customer_Name update customer name
     * @param Address update address
     * @param Postal_Code update postal code
     * @param Division_ID update the division ID
     * @param User Logged in User
     * @param Phone update Phone
     * @param Customer_ID match the customer ID
     * @return query result.
     */
    public int updateCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, String User, int Division_ID, int Customer_ID);
    
    
    
}
