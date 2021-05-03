package com.yong.functional_interface;
import model.Country;
/** This is a functional interface which gets the country from Division using foreign keys.
 * @author yongl
 */
public interface GetCountry {
    /** This abstract method will take an int of country ID and return a country object. 
     * It can be used as a Lambda expression.
     * @param CountryID country id.
     * @return a country object.
     */
    public Country getCountry(int CountryID);
    
}
