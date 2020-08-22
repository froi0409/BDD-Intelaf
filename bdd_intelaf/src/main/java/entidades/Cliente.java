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
public class Cliente extends Persona {
    @Override
    public void consultaDatos(){
        
    }
    
    @Override
    public void registro(){
        
    }
    
    public void cambioDatos(){
        
    }
    
    public void monitoreoPedido(){
        
    }
    
    public void ingresoClienteArchivo(Connection connection, String nombre, String NIT, String telefono, String credito){
        
        String query = "INSERT INTO CLIENTE (nombre,NIT,telefono,credito) VALUES (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, NIT);
            preSt.setString(3, telefono);
            preSt.setString(4, credito);
            
            preSt.executeUpdate();
            
            preSt.close();
            
        } catch(SQLException e){
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
    }
    
}
