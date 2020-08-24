/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

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
    
}
