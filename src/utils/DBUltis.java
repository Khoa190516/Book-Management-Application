/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author DELLA
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBUltis implements Serializable{
    
    public static Connection openConnection()
        {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url ="jdbc:sqlserver://127.0.0.1:1433; databaseName= LAB_P4;";
                Connection con = DriverManager.getConnection(url,"sa","sa");
                return con;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
        }
    
}
