package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Customer;

/** Customer data access interface.
 * @author yongl
 */
public interface CustomerDataAccess {
    /** A getAllCustomer abstract method. 
    * @return  returns an observableList of Customer type*/
    public ObservableList<Customer> getAllCustomers();
}
