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

/**
 *
 * @author froi-pc
 */
public class TiempoEnvio {
    
    public static void ingresoTiempoArchivo(Connection connection, String tienda1, String tienda2, String tiempo){
        
        String query = "INSERT INTO TIEMPO_ENVIO VALUES (?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, tiempo);
            preSt.setString(2, tienda1);
            preSt.setString(3, tienda2);
            
            preSt.executeUpdate();
            
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public static String obtenerTiempo(Connection connection, String tienda1, String tienda2){
        
        String retorno = "";
        String query = "SELECT tiempo FROM TIEMPO_ENVIO WHERE (tienda1 = ? AND tienda2 = ?) OR (tienda2 = ? AND tienda1 = ?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            preSt.setString(1, tienda1);
            preSt.setString(2, tienda2);
            preSt.setString(3, tienda1);
            preSt.setString(4, tienda2);
            
            ResultSet result = preSt.executeQuery();
            
            result.next(); //pasamos el apuntador de la primera tabla a la primera (y debería ser única) tupla;
            
            return result.getString(1);
            
            
        } catch (SQLException e){
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
        return retorno;
    }
    
}
