/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yumXpress.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author prach
 */
public class DBConnection {
    private static Connection conn;
    static{
       try{
          
          conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","yumXpress","foodie");
          //JOptionPane.showMessageDialog(null,"Succesfully Connection Done!");
       }
     catch(SQLException ex){
        
         JOptionPane.showMessageDialog(null,"Cannot open the connection.");
        ex.printStackTrace();
     }
    }
    public static Connection getConnection(){
       return conn;
    }
    public static void closeConnection(){
     try{
      conn.close();
     }
     catch(SQLException ex){
       JOptionPane.showMessageDialog(null,"Cannot able to close the connection!");
       ex.printStackTrace();
     }
    }
    
}
