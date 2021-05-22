package model;

/** This is an user model class.
 *
 * @author yongl
 */
public class User {
    private String username;
    private String password;
    private int User_ID;
    /** An empty constructor to allow to instantiate the class without passing arguments. */
    public User(){}
    /** A constructor for setting values for username and password private variables.
     * @param username username
     * @param password password*/
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int User_ID) {
        this.username = username;
        this.password = password;
        this.User_ID = User_ID;
    }
    
    

    public int getUser_ID() {
        return User_ID;
    }
    
    /** A getter method to get username.
     * @return  username*/
    public String getUsername(){
        return this.username;
    }
    /** A getter method to get the password.
     * @return  password*/
    public String getPassword(){
        return this.password;
    }
    
    //override the toString method for the objects to display the division names.
    @Override
    public String toString(){
        return "ID: "+User_ID+" - "+username;
    }
    
}
