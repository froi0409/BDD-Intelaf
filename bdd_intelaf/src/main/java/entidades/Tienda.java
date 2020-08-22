/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    
}
