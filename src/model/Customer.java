package model;

/** A customer model class.
 * @author yongl
 */
public class Customer {
    private int Customer_ID;
    private String Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private int Division_ID;
    private String Division;
    private String Country;
    private int Country_ID;
    /** Empty constructor to allow creating this class without parameters. */
    public Customer(){}
    /** A constructor with all necessary parameters.
     * @param Customer_ID customer ID
     * @param Customer_Name customer name
     * @param Address customer address
     * @param Postal_Code customer postal 
     * @param Phone customer phone
     * @param Division_ID first level division ID*/
    public Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
    }
    //a constructor with country and division for FXML tableview.
    public Customer(String Name, String Address, String Postal_Code, String Phone, String Division, String Country, int Customer_ID, int Division_ID, int Country_ID) {
        this.Name = Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division = Division;
        this.Country = Country;
        this.Customer_ID=Customer_ID;
        this.Division_ID=Division_ID;
        this.Country_ID=Country_ID;
    }
    //this constructor is for appointment update.
    public Customer(int Customer_ID, String Name) {
        this.Customer_ID = Customer_ID;
        this.Name = Name;
    }


    
    
    
    /** Customer name getter.
     * @return  get customer name*/
    public String getName() {
        return Name;
    }
    /** Address getter.
     * @return get address */
    public String getAddress() {
        return Address;
    }
    /** postal getter.
     * @return  get postal*/
    public String getPostal_Code() {
        return Postal_Code;
    }
    /** phone getter.
     * @return  get phone*/
    public String getPhone() {
        return Phone;
    }
    /** divisionID getter.
     * @return get division ID */
    public int getDivision_ID() {
        return Division_ID;
    }

    /** customer ID getter. 
     * @return the Customer_ID
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }
    /** Division getter. 
     * @return the Customer_ID
     */
    public String getDivision() {
        return Division;
    }
    /** Country getter. 
     * @return the Country_Name
     */
    public String getCountry() {
        return Country;
    }
    /** Country ID getter. 
     * @return the Country_ID
     */
    public int getCountry_ID() {
        return Country_ID;
    }
    
    /** override the default toString to display customer name for the combo box
     * @return customer name*/
    @Override
    public String toString(){
        return Name+" - ID: "+Customer_ID;
    }
    
    
}
