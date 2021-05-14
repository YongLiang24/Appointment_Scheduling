package com.yong.dao_interface;

import javafx.collections.ObservableList;
import model.Country;

/** Country interface.
 * @author yongl
 */
public interface CountryDataAccess {
    /** A getAllCountries abstract method. 
     * @return  returns an observableList of Country type*/
    public ObservableList<Country> getAllCountries();
    
}
