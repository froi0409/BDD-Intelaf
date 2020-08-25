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
public class DescripcionPedido {
    
    private String total;
    private String cantidad;
    private String codigo_producto;
    
    public DescripcionPedido(String total, String cantidad, String codigo_producto){
        //Damos valor a los atributos
        this.total = total;
        this.cantidad = cantidad;
        this.codigo_producto = codigo_producto;
    }

    public void ingresoDescripcion(Connection connection, String total, String cantidad, String codigo_producto, String codigo_pedido, String codigo_tienda){
        String insert = "INSERT INTO DESCRIPCION_PEDIDO (total,cantidad,codigo_producto,codigo_pedido) VALUES (?,?,?,?)"; //Agregamos una nueva descripción de pedido
        String update = "UPDATE EXISTENCIAS SET cantidad = ? WHERE codigo_tienda = ? AND codigo_producto = ?"; //Actualizamos las existencias de la tienda origen
        String query = "SELECT cantidad FROM EXISTENCIAS WHERE (codigo_producto = ? AND codigo_tienda = ?)"; //Obtenemos la cantidad de artículos del producto a pedir de la tienda origen
        
        
        
        int cant = Integer.parseInt(cantidad);
        try (PreparedStatement preSt = connection.prepareStatement(insert);
             PreparedStatement preSt2 = connection.prepareStatement(update);
             PreparedStatement preSt3 = connection.prepareStatement(query)) {
            
            //Ingresamos el valor de la incógnita del query
            preSt3.setString(1, codigo_producto);
            preSt3.setString(2, codigo_tienda);

            ResultSet result3 = preSt3.executeQuery();
            result3.next();
            
            int cantidadF = result3.getInt("cantidad") - cant;
            //Ingresamos valores a incógnitas del insert
            preSt.setString(1,total);
            preSt.setString(2,cantidad);
            preSt.setString(3,codigo_producto);
            preSt.setString(4,codigo_pedido);
            
            
            
            //Ingresamos calores a las incógnitas del update
            preSt2.setInt(1, cantidadF); //Ingresamos la cantidad de existencias que quedarán en la tienda origen después de que los productos sean retirados para pasar al pedido
            preSt2.setString(2, codigo_tienda);
            preSt2.setString(3, codigo_producto);
            
            preSt.executeUpdate();
            preSt2.executeUpdate();
            
            
        } catch (Exception e) {
            System.out.println("ERROR desc: "+ e.getMessage());
        }
    }
    
    //Getters
    public String getTotal(){
        return total;
    }
    
    public String getCantidad(){
        return cantidad;
    }
    
    public String getCodigoProducto(){
        return codigo_producto;
    }
    
    @Override
    public String toString(){
        return "Producto: " + codigo_producto + " Cantidad: " + cantidad + " Total: " + total;
    }
    
}
