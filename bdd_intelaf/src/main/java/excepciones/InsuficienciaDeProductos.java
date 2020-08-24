/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author froi-pc
 */
public class InsuficienciaDeProductos extends Exception {
    
    @Override
    public String getMessage(){
        return "No es posible realizar la transferencia, debido a que,\nno hay suficientes productos en alguna tienda";
    }
    
}
