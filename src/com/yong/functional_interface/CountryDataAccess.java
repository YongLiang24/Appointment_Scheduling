package com.yong.functional_interface;

import javafx.collections.ObservableList;
import model.Country;

/** Country interface, designed to be a functional interface.
 * @author yongl
 */
public interface CountryDataAccess {
    /** A getAllCountries abstract method. 
     * @return  returns an observableList of Country type*/
    public ObservableList<Country> getAllCountries();
    
}
