/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PC
 */
public class KoneksiDatabase {
     private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String dbURL = "jdbc:mysql://0.tcp.ap.ngrok.io:10827/db_uas_pbo2";
                String username = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(dbURL, username, password);
                System.out.println("Koneksi berhasil");
            } catch (Exception e) {
                System.out.println("Error : " + e);
                e.printStackTrace();
            }
        }
        return koneksi;
    }
    
    public static void main(String[] args) {
        getKoneksi();
    }
}
