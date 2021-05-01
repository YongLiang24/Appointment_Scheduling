package com.yong.functional_interface;

import javafx.collections.ObservableList;
import model.State;

/** First level division functional interface.
 * @author yongl
 */
public interface StateDataAccess {
    public ObservableList<State> getAllStates();
    
}
