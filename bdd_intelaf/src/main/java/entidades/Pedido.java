package entidades;

import analizadores.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author froi-pc
 */
public class Pedido {
    
    public static void ingresoPedidoArchivo(Connection connection, String codigo_pedido, String tienda_origen, String tienda_destino, String fecha, String cliente, String articulo, String cantidad, String total, String anticipo){
        
        
        String query = "INSERT INTO PEDIDO (codigo_pedido,tienda_origen,tienda_destino,fecha,NIT_cliente,anticipo,precio_final,tiempo_envio) VALUES (?,?,?,?,?,?,?,?)";
        TiempoEnvio te = new TiempoEnvio();
        String tiempo = te.obtenerTiempo(Conexion.getConnection(), tienda_origen, tienda_destino);
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1,codigo_pedido);
            preSt.setString(2,tienda_origen);
            preSt.setString(3,tienda_destino);
            preSt.setString(4,fecha);
            preSt.setString(5,cliente);
            preSt.setString(6,anticipo);
            preSt.setString(7,total);
            preSt.setString(8, tiempo);
           
            preSt.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            
            //Debido a que los pedidos pueden tener "repetición de código", este finally ejecuta  un nuevo insert, el cual ingresa las "repeticiones" del pedido como una llave foranea en
            //una entidad que describe el pedido 
            String query2 = "INSERT INTO DESCRIPCION_PEDIDO (total,cantidad,codigo_producto,codigo_pedido) VALUES (?,?,?,?)";
            
            try (PreparedStatement preSt2 = Conexion.getConnection().prepareStatement(query2)) {
                
                preSt2.setString(1, total);
                preSt2.setString(2, cantidad);
                preSt2.setString(3, articulo);
                preSt2.setString(4, codigo_pedido);
                  
                preSt2.executeUpdate();
                
                
            } catch (SQLException e) {
                System.out.println("Error2: " + e.getMessage());
            } finally{
                actualizarTotalPedido(connection);
            }
            
        }
    }
    
    //Funcion que sirve para actualizar los precios totales del pedido
    public static void actualizarTotalPedido(Connection connection){
        
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
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
    }
    
    public void mostrarPedidosCurso(Connection connection, JTable tabla, DefaultTableModel dtmp){
        
        String query = "SELECT codigo_pedido,anticipo,precio_final,fecha,tienda_origen,tienda_destino,NIT_cliente FROM PEDIDO WHERE estado = 'SIN_ENTREGAR'";
        String cantTuplas = "SELECT COUNT(*) FROM PEDIDO";
        
        try(PreparedStatement preSt = connection.prepareStatement(query);
            PreparedStatement preSt2 = connection.prepareStatement(cantTuplas)) {
            
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next(); //Hallamos la cantidad de tuplas que tendrá la tabla
            String[][] atributos = new String[result2.getInt(1)][7];
            int cont = 0;
            
            while(result.next()){
                
                atributos[cont][0] = result.getString(1);
                atributos[cont][1] = result.getString(2);
                atributos[cont][2] = result.getString(3);
                atributos[cont][3] = result.getString(4);
                atributos[cont][4] = result.getString(5);
                atributos[cont][5] = result.getString(6);
                atributos[cont][6] = result.getString(7);
                
                dtmp.addRow(atributos[cont]);
                cont++;
                
            }
            
            tabla.setModel(dtmp);
            
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
     
        
    }
    
    public void ingresoPedido(Connection connection, String codigo_pedido, String anticipo, String precio_final, String fecha, String tienda_origen, String tienda_destino, String NIT_cliente){
        String insertP = "INSERT INTO PEDIDO (codigo_pedido,anticipo,precio_final,fecha,tienda_origen,tienda_destino,NIT_cliente,tiempo_envio) VALUES(?,?,?,?,?,?,?,?)";
        TiempoEnvio te = new TiempoEnvio();
        String tiempo = te.obtenerTiempo(Conexion.getConnection(), tienda_origen, tienda_destino);
        
        try (PreparedStatement preSt = connection.prepareStatement(insertP)){
            
            //Agregamos valores a las incógnitas del insert
            preSt.setString(1, codigo_pedido);
            preSt.setString(2, anticipo);
            preSt.setString(3, precio_final);
            preSt.setString(4, fecha);
            preSt.setString(5, tienda_origen);
            preSt.setString(6, tienda_destino);
            preSt.setString(7, NIT_cliente);
            preSt.setString(8, tiempo);
            
            preSt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("ERROR: unu" + e.getMessage());
        }
    }
    
    public void ingresarPedidoTienda(Connection connection, String codigo_pedido){
        
        String update = "UPDATE PEDIDO SET estado = 'EN_TIENDA' WHERE codigo_pedido = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(update)){
            
            preSt.setString(1, codigo_pedido);
            preSt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}