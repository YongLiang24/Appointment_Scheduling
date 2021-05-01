package model;

/** A customer model class.
 *
 * @author yongl
 */
public class Customer {
    private int Customer_ID;
    private String Customer_name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    //Created_Date, Created_By, Last_Update, Last_Updated_By
    private int Division_ID;
    /** Empty constructor to allow creating this class without parameters. */
    public Customer(){}
    /** A constructor with all necessary parameters.
     * @param Customer_ID customer ID
     * @param Customer_name customer name
     * @param Address customer address
     * @param Postal_Code customer postal 
     * @param Phone customer phone
     * @param Division_ID first level division ID*/
    public Customer(int Customer_ID, String Customer_name, String Address, String Postal_Code, String Phone, int Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_name = Customer_name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
    }

    /** Customer name getter.
     * @return  get customer name*/
    public String getCustomer_name() {
        return Customer_name;
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
    
}
