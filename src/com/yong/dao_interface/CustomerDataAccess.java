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
    
    
}
