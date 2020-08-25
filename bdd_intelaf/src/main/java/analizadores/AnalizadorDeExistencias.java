/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author froi-pc
 */
public class AnalizadorDeExistencias {
    
    //Conexion de la base de datos
    private Connection connection;
    
    public AnalizadorDeExistencias(Connection connection){
        this.connection = connection;
    }
    
    public boolean analizarExistenciasTabla(String tabla){
        
        //Busqueda utilizada para verificar si la tabla seleccionada posee exixtencias
        try (PreparedStatement preSt = connection.prepareStatement(tabla)){
            
            ResultSet result = preSt.executeQuery();
            
            result.next();
            
            return result.getInt(1) > 0;
            
        } catch (SQLException e) {
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
        return false;
    }
    
    public boolean analizarExistenciaDeDatos(){
        //Metodo que servirá para comprobar si la base de datos está vacia
        
        if(analizarExistenciasTabla("SELECT COUNT(*) FROM TIENDA"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM EMPLEADO"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM PRODUCTO"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM EXISTENCIAS"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM CLIENTE"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM TIEMPO_ENVIO"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM PEDIDO"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM DESCRIPCION_PEDIDO"))
            return true;
        else if (analizarExistenciasTabla("SELECT COUNT(*) FROM COMPRA"))
            return true;
        
        return false;
        
    }
    
}
