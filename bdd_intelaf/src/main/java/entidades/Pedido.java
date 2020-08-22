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
        
        
        String query = "INSERT INTO PEDIDO (codigo_pedido,tienda_origen,tienda_destino,fecha,NIT_cliente,anticipo,precio_final,tiempo_envio,numero_descripciones) VALUES (?,?,?,?,?,?,?,?,?)";
        String query2 = "INSERT INTO DESCRIPCION_PEDIDO (total,cantidad,codigo_producto,codigo_pedido) VALUES (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query);
                PreparedStatement preSt2 = connection.prepareStatement(query2)){
            
            preSt.setString(1,codigo_pedido);
            preSt.setString(2,tienda_origen);
            preSt.setString(3,tienda_destino);
            preSt.setString(4,fecha);
            preSt.setString(5,cliente);
            preSt.setString(6,anticipo);
            preSt.setString(7,total);
            preSt.setString(8, TiempoEnvio.obtenerTiempo(connection, tienda_origen, tienda_destino));
            preSt.setString(9,"1");
            
            preSt2.setString(1, total);
            preSt2.setString(2, cantidad);
            preSt2.setString(3, articulo);
            preSt2.setString(4, codigo_pedido);
            
            preSt.executeUpdate();
            preSt2.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
    
}