package model;
import java.sql.Timestamp;
/**
 * Appointment model class with constructor and getter methods.
 * @author yongl
 */
public class Appointment {
    private String Title, Description, Location, Type, Contact;
    private int Appointment_ID, Customer_ID, User_ID, Contact_ID;
    private Timestamp Start, End;

    public Appointment(int Appointment_ID, String Title, String Description, String Location, String Type, Timestamp Start, Timestamp End, int Customer_ID, int User_ID, int Contact_ID) {
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Appointment_ID = Appointment_ID;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
        this.Start = Start;
        this.End = End;
    }

    public Appointment(String Title, String Description, String Location, String Type, String Contact, int Appointment_ID, int Customer_ID, int User_ID, int Contact_ID, Timestamp Start, Timestamp End) {
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Contact = Contact;
        this.Appointment_ID = Appointment_ID;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
        this.Start = Start;
        this.End = End;
    }

    public String getContact() {
        return Contact;
    }
    
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public Timestamp getStart() {
        return Start;
    }

    public Timestamp getEnd() {
        return End;
    }
    

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getLocation() {
        return Location;
    }

    public String getType() {
        return Type;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }
    
    /** override the toString to display appointment id and DateTime. 
     * @return contact name*/
    @Override
    public String toString(){
        return "ID: "+Appointment_ID+" - Time: "+Start;
    }
}
