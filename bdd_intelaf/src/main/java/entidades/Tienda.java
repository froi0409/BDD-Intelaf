/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 *
 * @author froi-pc
 */
public class Tienda {
    
    public static void ingresoTiendaArchivo(Connection connection, String nombre, String direccion, String codigo_tienda, String telefono1){
        
        String query = "INSERT INTO TIENDA (nombre,direccion,codigo_tienda,telefono1) VALUES (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1,nombre);
            preSt.setString(2,direccion);
            preSt.setString(3,codigo_tienda);
            preSt.setString(4,telefono1);
            
            preSt.executeUpdate();
            preSt.close();
            
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void seleccionTiendas(Connection connection, JComboBox jcom){
        
        jcom.addItem("");
        String query = "SELECT codigo_tienda,nombre FROM TIENDA";
        try (PreparedStatement preSt = connection.prepareStatement(query)){
            
            ResultSet result = preSt.executeQuery();
            
            while(result.next()){
                jcom.addItem(result.getString(1) + "    " + result.getString(2));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
        
        
    }
    
}
