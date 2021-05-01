package model;

/** This is a first level divisions (states) model.
 * @author yongl
 */
public class State {
    private final int Division_ID;
    private final String Division;
    private final int Country_ID;
    
    /** State constructor with all parameters.
     * @param Division_ID Division ID
     * @param Division Division
     * @param Country_ID Country ID */
    public State(int Division_ID, String Division, int Country_ID) {
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country_ID = Country_ID;
    }
   
    /** Division ID getter. 
     * @return the Division_ID
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /** Division getter.
     * @return the Division
     */
    public String getDivision() {
        return Division;
    }

    /** Country ID getter.
     * @return the Country_ID
     */
    public int getCountry_ID() {
        return Country_ID;
    }
    
}
