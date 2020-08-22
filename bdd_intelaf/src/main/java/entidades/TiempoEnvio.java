/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author froi-pc
 */
public class TiempoEnvio {
    
    public void ingresoTiempoArchivo(Connection connection, String tienda1, String tienda2, String tiempo){
        
        String query = "INSERT INTO TIEMPO VALUES (?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, tiempo);
            preSt.setString(2, tienda1);
            prest.setString(3, tienda2);
            
            preSt.executeUpdate();
            
            preSt.close();
            
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}
