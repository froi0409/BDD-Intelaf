/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author froi-pc
 */
public class Empleado extends Persona{
    
    @Override
    public void consultaDatos(){
        
    }
    @Override
    public void registro(){
        
    }
    
    public void cambioDatos(){
        
    }
    
    public static void ingresoEmpleadoArchivo(Connection connection, String nombre, String codigo, String telefono, String DPI){
        
        String query = "INSERT INTO EMPLEADO (nombre,codigo_empleado,telefono,DPI) VALUES(?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, codigo);
            preSt.setString(3, telefono);
            preSt.setString(4, DPI);
            
            preSt.executeUpdate();
            preSt.close();
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
}
