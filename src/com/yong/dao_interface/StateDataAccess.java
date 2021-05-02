package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Division;

/** First level division interface.
 * @author yongl
 */
public interface StateDataAccess {
     /** A getAllStates abstract method. 
     * @return  returns an observableList of Division type*/
    public ObservableList<Division> getAllStates();
    
}
