package com.yong.utility;

import java.io.*;
import java.time.LocalDateTime;
/** This class logs user login activities. */
public class LoginActivity{
    /** This method writes user login attempts to a file. It tracks both fail and success attempts.
     * @param isValidLogin determines whether user has input login info correctly.
     * @param username pass the username from database to write it a file with timestamp
     * @throws java.io.IOException handles file IOException*/
    public void writeToFile(Boolean isValidLogin, String username) throws IOException{
      //create a FileWriter reference to append data to a file  
      FileWriter fw = new FileWriter("login_activity.txt", true);
      PrintWriter pw = new PrintWriter(fw);
      //utilize LocalDateTime class to load user's current time
      LocalDateTime localTime = LocalDateTime.now();
      
        if(isValidLogin){
            pw.println("The user ["+username+"] successfully logged in at "+localTime);
            pw.close();
        }else{
            pw.println("The user has entered an invalid login at "+localTime);
            pw.close();           
        }
    }   
}
