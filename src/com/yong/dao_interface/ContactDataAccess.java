/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * Contact data access interface.
 * @author yongl
 */
public interface ContactDataAccess {
     /** A getAllContacts abstract method. 
     * @return  returns an observableList of Contact type*/
    public ObservableList<Contact> getAllContacts();
}
