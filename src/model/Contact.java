package model;

/**
 * Contact Model class with constructor and getter methods.
 * @author yongl
 */
public class Contact {
    private String Contact_Name, Email;
    private int Contact_ID;

    public Contact(String Contact_Name, String Email, int Contact_ID) {
        this.Contact_Name = Contact_Name;
        this.Email = Email;
        this.Contact_ID = Contact_ID;
    }
    

    public int getContact_ID() {
        return Contact_ID;
    }
    
    public String getContact_Name() {
        return Contact_Name;
    }

    public String getEmail() {
        return Email;
    }
    
    /** override the default toString to display contact name for the combo box
     * @return contact name*/
    @Override
    public String toString(){
        return Contact_Name+" - ID: "+Contact_ID;
    }
    
    
}
