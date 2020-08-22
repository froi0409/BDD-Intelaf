package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author froi-pc
 */
public class Pedido {
    
    public static void ingresoPedidoArchivo(Connection connection, String codigo_pedido, String tienda_origen, String tienda_destino, String fecha, String cliente, String articulo, String cantidad, String total, String anticipo){
        
        String query = "INSERT INTO PEDIDO (codigo_pedido,tienda_origen,tienda_destino,fecha,NIT_cliente,anticipo) VALUES (?,?,?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1,codigo_pedido);
            preSt.setString(2,tienda_origen);
            preSt.setString(3,tienda_destino);
            preSt.setString(4,fecha);
            preSt.setString(5,cliente);
            preSt.setString(6,anticipo);
            
            preSt.executeUpdate();
            preSt.close();
            
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
    
}