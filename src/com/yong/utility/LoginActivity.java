package com.yong.utility;

import java.io.*;
import java.time.LocalDateTime;


public class LoginActivity{
    
    public void writeToFile(Boolean isValidLogin) throws IOException{
      //create a FileWriter reference to append data to a file  
      FileWriter fw = new FileWriter("login_activity.txt", true);
      PrintWriter pw = new PrintWriter(fw);
      //utilize LocalDateTime class to load user's current time
      LocalDateTime localTime = LocalDateTime.now();
      
        if(isValidLogin){
            pw.println("car"+localTime);
            pw.close();
        }else{
            pw.println("car"+localTime);
            pw.close();           
        }
    }   
}
