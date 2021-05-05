package model;

/** Country class. 
 *
 * @author yongl
 */
public class Country {
    private final String Country;
    private final int Country_ID;
    /** Country constructor.
     * @param Country Country names
     * @param Country_ID Country ID*/
    public Country(String Country, int Country_ID) {
        this.Country = Country;
        this.Country_ID = Country_ID;
    }
    
    /** Country getter method. 
     * @return the Country
     */
    public String getCountry() {
        return Country;
    }

    /** Country_ID getter method. 
     * @return the Country_ID
     */
    public int getCountry_ID() {
        return Country_ID;
    }
    /** override the default toString to display country name for the combo box
     * @return a country name*/
    @Override
    public String toString(){
        return Country;
    }
    
    
}
