/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

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
public class TiempoEnvio {
    
    public static void ingresoTiempoArchivo(Connection connection, String tienda1, String tienda2, String tiempo){
        
        String query = "INSERT INTO TIEMPO_ENVIO VALUES (?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, tiempo);
            preSt.setString(2, tienda1);
            preSt.setString(3, tienda2);
            
            preSt.executeUpdate();
            
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public String obtenerTiempo(Connection connection, String tienda1, String tienda2){
        
        String retorno = "";
        String query = "SELECT tiempo FROM TIEMPO_ENVIO WHERE (tienda1 = ? AND tienda2 = ?) OR (tienda2 = ? AND tienda1 = ?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            preSt.setString(1, tienda1);
            preSt.setString(2, tienda2);
            preSt.setString(3, tienda1);
            preSt.setString(4, tienda2);
            
            ResultSet result = preSt.executeQuery();
            
            result.next(); //pasamos el apuntador del principo de la  tabla a la primera tupla (y debería ser única) tupla;
            
            return result.getString(1);
            
            
        } catch (SQLException e){
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
        return retorno;
    }
    
    public void mostrarTiempos(Connection connection, JTable tabla, DefaultTableModel dtm, String tienda){
        
        String query = "SELECT * FROM TIEMPO_ENVIO WHERE tienda1 = ? OR tienda2 = ? ORDER BY tiempo"; //Obtenemos los tiemposde envío en los que está involucrada la tienda
        String query2 = "SELECT COUNT(*) FROM TIEMPO_ENCIO WHERE tienda1 = ? OR tienda2 = ?"; //Obtenemos la cantidad de tuplas en las cuales se encuentra la tienda involucrada como atributo
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2)) {
            
            
            //Asignamos valor a cada una da las incógnitas de cada una de las querys
           preSt.setString(1, tienda);
           preSt.setString(2, tienda);
           preSt2.setString(1, tienda);
           preSt2.setString(2, tienda);
           
           ResultSet result = preSt.executeQuery();
           ResultSet result2 = preSt2.executeQuery();
           
           result2.next(); //Movemos el cursor para obtener la cantidad de tuplas en las cuales está involucrada la tienda que se está manejando
           String[][] atributos = new String[result2.getInt(1)][3];
           int cont = 0;
           
           while(result.next()){
               
               atributos[cont][0] = result.getString(1);
               atributos[cont][1] = result.getString(2);
               atributos[cont][2] = result.getString(3);
               
               dtm.addRow(atributos[cont]);
               cont++;
               
           }
            
           tabla.setModel(dtm);
           
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public boolean getTiempoEntreFechas(Connection connection, String codigo_pedido){
        String query = "SELECT DATEDIFF(NOW(),fecha) FROM PEDIDO WHERE codigo_pedido = ?";
        String query2 = "SELECT tienda_origen,tienda_destino FROM PEDIDO WHERE codigo_pedido = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2)) {
            
            preSt.setString(1, codigo_pedido);
            preSt2.setString(1, codigo_pedido);
            
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            
            result.next();
            result2.next();
            
            int tiempoPedido = Integer.parseInt(obtenerTiempo(connection, result2.getString(1), result2.getString(2))); //Obtenemos el tiempo que debería durar la entrega del producto
            
            if(tiempoPedido < result.getInt(1))
                return true;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        
        
        return false;
        
    }
    
    public void definirTiempoEntreTiendas(Connection connection, String tiempo, String tienda1, String tienda2){
        
        //URGENTE
        
    }
    
}
