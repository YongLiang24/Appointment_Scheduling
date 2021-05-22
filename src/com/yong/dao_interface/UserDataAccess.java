package com.yong.dao_interface;

import model.User;
import javafx.collections.ObservableList;
/** This interface defines an ObservableList of User type method.
 * @author yongl
 */
public interface UserDataAccess {
    /** A getALlUsers abstract method. 
     * @return  returns an observableList of user type*/
    public ObservableList<User> getAllUsers();
    
    public User getUserByID(int userID);
    
}
