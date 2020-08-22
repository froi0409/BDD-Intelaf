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
public class Producto {
    
    public static void ingresoProductoArchivo(Connection connection, String nombre, String fabricante, String codigo, String cantidad, String precio, String tienda){
        
        String codigoDesc = codigo+tienda;
        String query = "INSERT INTO PRODUCTO (nombre,fabricante,codigo_producto,precio) VALUES (?,?,?,?)";
        String query2 = "INSERT INTO EXISTENCIAS (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query);
            PreparedStatement preSt2 = connection.prepareStatement(query2)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, fabricante);
            preSt.setString(3, codigo);
            preSt.setString(4, precio);
           
            preSt2.setString(1, codigoDesc);
            preSt2.setString(2, cantidad);
            preSt2.setString(3, codigo);
            preSt2.setString(4, tienda);

            preSt.executeUpdate();
            preSt2.executeUpdate();
            
            preSt.close();
            preSt2.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}
