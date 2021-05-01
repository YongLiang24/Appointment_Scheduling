package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.State;

/** First level division interface.
 * @author yongl
 */
public interface StateDataAccess {
     /** A getAllStates abstract method. 
     * @return  returns an observableList of State type*/
    public ObservableList<State> getAllStates();
    
}
