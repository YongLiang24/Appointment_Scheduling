package com.yong.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yongl
 */
/** This class utilizes JDBC to connect to a database and establish a connection. */
public class DBConnection {
    
    private static final String CONNECTION_TYPE="jdbc";
    private static final String SQL_TYPE=":mysql:";
    private static final String SERVER_NAME="//wgudb.ucertify.com:3306/";
    private static final String DB_NAME ="WJ06PaL";
    //this is the full path for the JDBC URL
    private static final String JDBC_URL =CONNECTION_TYPE+SQL_TYPE+SERVER_NAME+DB_NAME;
    private static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    private static final String USER_NAME="U06PaL";
    private static final String PASSWORD="53688828640";
    private static Connection CONN= null;
    /** This method start the connection. 
     * @return Returns a Connection reference */
    public static Connection startConnection(){
        try{
        Class.forName(JDBC_DRIVER);
        CONN = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
            System.out.println("Connection has started.");
        }
        catch(SQLException | ClassNotFoundException e){
            e.getStackTrace();
        }
        return CONN;
    }
    /** This method gets a Connection reference. 
     * @return Returns a CONN reference. */
    public static Connection getConnection(){
        return CONN;
    }
    
    public static void closeConnection(){
        try{
            CONN.close();
            System.out.println("Connection has been closed.");
        }
        catch(SQLException e){
            
        }
    }
    
}
