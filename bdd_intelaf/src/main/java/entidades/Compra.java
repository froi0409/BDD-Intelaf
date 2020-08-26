/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author froi-pc
 */
public class Compra {
    
    public void registrarCompra(Connection connection, String codigo_pedido, String codigo_tienda, String NIT_cliente, String fecha){
        
        String insert = "INSERT INTO COMPRA (fecha,nombre_comprador,codigo_tienda,codigo_pedido,NIT_cliente) VALUES (?,?,?,?,?)";
        String query = "SELECT nombre FROM CLIENTE WHERE NIT = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(insert);
             PreparedStatement preSt2 = connection.prepareStatement(query)) {
            
            preSt2.setString(1, NIT_cliente);
            
            
            ResultSet result2 = preSt2.executeQuery();
            result2.next();
            
            preSt.setString(1, fecha);
            preSt.setString(2, result2.getString(1));
            preSt.setString(3, codigo_tienda);
            preSt.setString(4, codigo_pedido);
            preSt.setString(5, NIT_cliente);
            
            System.out.println("HOLAAA");
            
            preSt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error: compra" + e.getMessage());
        }
        
    }
    
    public void definirPedidoComprado(Connection connection, String codigo_pedido){
        
        String update = "UPDATE PEDIDO SET estado = 'COMPRADO' WHERE codigo_pedido = ?";
        
        try(PreparedStatement preSt = connection.prepareStatement(update)) {
            preSt.setString(1, codigo_pedido);
            preSt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}
