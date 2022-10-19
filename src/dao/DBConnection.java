/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pol
 */
public class DBConnection {
    
    protected Connection connection;
    private String dataBase = "src\\ddbb\\ddbbProyecto.db";

    public DBConnection() {        
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            //DriverManager.registerDriver(new org.sqlite.JDBC()); // posem uno o l'altre
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
        } catch (SQLException ex) {
            System.out.println("No s'ha pogut connectar. " + ex.toString());
        } catch (ClassNotFoundException ex){
            System.out.println("No s'ha pogut carregar el driver" + ex.toString());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("No s'ha pogut tancar la connexió. " + ex.toString());
        }
    }
    
}
