package entidades;

import analizadores.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author froi-pc
 */
public class Pedido {
    
    public static void ingresoPedidoArchivo(Connection connection, String codigo_pedido, String tienda_origen, String tienda_destino, String fecha, String cliente, String articulo, String cantidad, String total, String anticipo){
        
        
        String query = "INSERT INTO PEDIDO (codigo_pedido,tienda_origen,tienda_destino,fecha,NIT_cliente,anticipo,precio_final,tiempo_envio) VALUES (?,?,?,?,?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1,codigo_pedido);
            preSt.setString(2,tienda_origen);
            preSt.setString(3,tienda_destino);
            preSt.setString(4,fecha);
            preSt.setString(5,cliente);
            preSt.setString(6,anticipo);
            preSt.setString(7,total);
            preSt.setString(8, TiempoEnvio.obtenerTiempo(connection, tienda_origen, tienda_destino));
           
            preSt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            
            //Debido a que los pedidos pueden tener "repetición de código", este finally ejecuta  un nuevo insert, el cual ingresa las "repeticiones" del pedido como una llave foranea en
            //une entidad que describe el pedido 
            String query2 = "INSERT INTO DESCRIPCION_PEDIDO (total,cantidad,codigo_producto,codigo_pedido) VALUES (?,?,?,?)";
            
            try (PreparedStatement preSt2 = Conexion.getConnection().prepareStatement(query2)) {
                
                preSt2.setString(1, total);
                preSt2.setString(2, cantidad);
                preSt2.setString(3, articulo);
                preSt2.setString(4, codigo_pedido);
                  
                preSt2.executeUpdate();
                
                
                
            } catch (SQLException e) {
                System.out.println("Error2: " + e.getMessage());
            }
            
        }
    }
    
    
}