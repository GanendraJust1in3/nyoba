/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */ 
package Frame;

import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author nizarislamudin
 */
public class Koneksi {
    private static java.sql.Connection koneksi;
     public static java.sql.Connection getKoneksi(){
         if (koneksi == null){
             try{
                 String url = "jdbc:mysql://localhost:/db_bluescope";
                 String user = "root";
                 String password = "";  
                 DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                 koneksi = DriverManager.getConnection(url, user, password);
                 System.out.println("Connection Successfully");
             }catch(SQLException e){
                 System.out.println("error");
             }
         }
         return koneksi;
     }
     public static void main(String args[]){
        getKoneksi();
     }
     
     private static String user;
     
     public static String getUsername(){
        return user;
     }
     
     public static void setUsername(String username){
        Koneksi.user = username;
     }
}
