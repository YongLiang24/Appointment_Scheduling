package model;

/** This is an user model class.
 *
 * @author yongl
 */
public class User {
    private String username;
    private String password;
    /** An empty constructor to allow to instantiate the class without passing arguments. */
    public User(){}
    /** A constructor for setting values for username and password private variables.
     * @param username username
     * @param password password*/
    public User(String username, String password){
        this.username = username;
        this.password = password;
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
    
    
    
}
