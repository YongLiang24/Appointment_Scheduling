/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yong.dao_interface;

import model.Users;
import javafx.collections.ObservableList;

/**
 *
 * @author yongl
 */
public interface UserDao {
    
    public ObservableList<Users> getAllUsers();
    
}
