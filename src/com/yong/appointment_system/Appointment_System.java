/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yong.appointment_system;

import com.yong.utility.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author yongl
 */
public class Appointment_System extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
          try {
            String sql = "select * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("User_Name"));
                
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        launch(args);
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage st) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/IndexFXML.fxml"));
        Scene sce = new Scene(root);
        st.setScene(sce);
        st.setTitle("Main Menu");
        st.setHeight(600);
        st.setWidth(900);
        st.show();
    }
    
}
