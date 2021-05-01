package com.yong.dao_implement;
import com.yong.dao_interface.CountryDataAccess;
import com.yong.utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

/** This an the implementation for the country database access class.
 * @author yongl
 */
public class CountryDAOImplement implements CountryDataAccess {
    /** This method gets all countries from database. 
     @return Returns an observableList containing all country objects
     */
    @Override
    public ObservableList<Country> getAllCountries() {
        //create an observeableList to hold Country objects.
        ObservableList<Country> countryList = FXCollections.observableArrayList();   
        try {
            String sql = "select * from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //create user objects using countries data from database
                String countryName = rs.getString("Country");
                int countryID = (int)rs.getInt("Country_ID");
                Country country = new Country(countryName, countryID);
                //add the country objects to the userList
                countryList.add(country);
            }         
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return countryList;
    }
    
    
}
