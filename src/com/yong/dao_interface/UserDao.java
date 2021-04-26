package com.yong.dao_interface;

import model.User;
import javafx.collections.ObservableList;

/**
 *
 * @author yongl
 */
/** This interface defines an ObservableList of User type method. */
public interface UserDao {
    public ObservableList<User> getAllUsers();
    
}
