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

    public void ingresoDescripcion(Connection connection, String total, String cantidad, String codigo_producto, String codigo_pedido){
        String insert = "INSERT INTO DESCRIPCION_PEDIDO (total,cantidad,codigo_producto,codigo_pedido) VALUES (?,?,?,?)";
        try (PreparedStatement preSt = connection.prepareStatement(insert)) {
            
            //Ingresamos valores a incógnitas
            preSt.setString(1,total);
            preSt.setString(2,cantidad);
            preSt.setString(3,codigo_producto);
            preSt.setString(4,codigo_pedido);
            
            preSt.executeUpdate();
            
        } catch (Exception e) {
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
