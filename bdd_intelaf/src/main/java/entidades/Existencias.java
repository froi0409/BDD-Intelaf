package entidades;

import excepciones.InsuficienciaDeProductos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author froi-pc
 */
public class Existencias {
    
    public boolean verificarExistencias(Connection connection, String codigo_tienda, String codigo_producto, String cantidad){
 
        int cant = Integer.parseInt(cantidad);
        String query = "SELECT cantidad FROM EXISTENCIAS WHERE codigo_tienda = ? AND codigo_producto = ?";
        try (PreparedStatement preSt = connection.prepareStatement(query)) {
            preSt.setString(1, codigo_tienda);
            preSt.setString(2, codigo_producto);
            
            ResultSet result = preSt.executeQuery();
            result.next();//Obtenemos la cantidad de existencias
            
            if(result.getInt(1) < cant){
                //En el caso de que la cantidad de productos exigidos sea mayor a la de productos existentes,
                //Esta condición arrojará una excepcion que ha sido creada específicamente para estos casos
                throw new InsuficienciaDeProductos();
            }
            else 
                System.out.println(result.getInt(1));
                System.out.println("Verificacion de cantidad de productos realizada con éxito");
                return true;
        } catch (Exception e) {
            System.out.println("Error: asdf" + e.getMessage());
            return false;
        }
        
    }
    
}
