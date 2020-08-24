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
            String query2 = "INSERT INTO DESCRIPCION_PEDIDO (total,cantidad,codigo_producto,codigo_pedido,anticipo) VALUES (?,?,?,?,?)";
            
            try (PreparedStatement preSt2 = Conexion.getConnection().prepareStatement(query2)) {
                
                preSt2.setString(1, total);
                preSt2.setString(2, cantidad);
                preSt2.setString(3, articulo);
                preSt2.setString(4, codigo_pedido);
                preSt2.setString(5, anticipo);
                  
                preSt2.executeUpdate();
                
                
            } catch (SQLException e) {
                System.out.println("Error2: " + e.getMessage());
            } finally{
                actualizarTotalPedido(connection);
            }
            
        }
    }
    
    //Funcion que sirve para actualizar los precios totales del pedido
    private static void actualizarTotalPedido(Connection connection){
        
        String query = "SELECT P.codigo_pedido,SUM(D.total) FROM PEDIDO P LEFT JOIN DESCRIPCION_PEDIDO D ON P.codigo_pedido = D.codigo_pedido GROUP BY P.codigo_pedido ORDER BY P.codigo_pedido ASC;";
        String cantTuplasP = "SELECT COUNT(*) FROM PEDIDO ";
        String update = "UPDATE PEDIDO SET precio_final = ? WHERE codigo_pedido = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query); //pest que relaciona el precio total con el codigo del producto
             PreparedStatement preSt2 = connection.prepareStatement(cantTuplasP); //prest que nos resuelve la cantidad de tuplas que tendremos que actualizar
             PreparedStatement preSt3 = connection.prepareStatement(update)){ //prest que nos hará la actualización de las tuplas correspondientes
            
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next();
            int cantTuplas = result2.getInt(1); 
            for(int i = 0; i < cantTuplas; i++){
                
                result.next();
                
                preSt3.setString(1, result.getString(2));
                preSt3.setString(2, result.getString(1));
                preSt3.executeUpdate();

            }
            
        } catch (Exception e) {
        }
        
    }
    
    
}