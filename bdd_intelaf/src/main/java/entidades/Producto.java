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
import javax.swing.table.TableModel;

/**
 *
 * @author froi-pc
 */
public class Producto {
    
    public static void ingresoProductoArchivo(Connection connection, String nombre, String fabricante, String codigo, String cantidad, String precio, String tienda){
        
        String codigoDesc = codigo+tienda;
        String query = "INSERT INTO PRODUCTO (nombre,fabricante,codigo_producto,precio) VALUES (?,?,?,?)";
        String query2 = "INSERT INTO EXISTENCIAS VALUES (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query);
            PreparedStatement preSt2 = connection.prepareStatement(query2)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, fabricante);
            preSt.setString(3, codigo);
            preSt.setString(4, precio);
           
            preSt2.setString(1, codigoDesc);
            preSt2.setString(2, cantidad);
            preSt2.setString(3, codigo);
            preSt2.setString(4, tienda);

            preSt.executeUpdate();
            preSt2.executeUpdate();
            
            preSt.close();
            preSt2.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void mostrarProductos(Connection connection, JTable tabla, DefaultTableModel dtm){
        
        String query = "SELECT * FROM PRODUCTO";
        String cantTuplas = "SELECT COUNT(*) FROM PRODUCTO";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
                PreparedStatement preSt2 = connection.prepareStatement(cantTuplas)){
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next(); //Encontramos el numero de tuplas que hay en la tabla PRODUCTO
            
            String[][] atributos = new String[result2.getInt(1)][6];
            int cont = 0;
            
            while(result.next()){
                
                
                atributos[cont][0] = result.getString(1);
                atributos[cont][1] = result.getString(2);
                atributos[cont][2] = result.getString(3);
                atributos[cont][3] = result.getString(4);
                atributos[cont][4] = result.getString(5);
                atributos[cont][5] = result.getString(6);
                
                dtm.addRow(atributos[cont]);
                cont++;
            }
            
            tabla.setModel(dtm);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void mostrarProductosTienda(Connection connection, JTable tabla, DefaultTableModel dtm, String tienda){
        
        String query = "SELECT codigo_producto FROM EXISTENCIAS WHERE codigo_tienda = ?"; //Obtiene el codigo del producto que existe en la tienda
        String query2 = "SELECT * FROM PRODUCTO WHERE codigo_producto = ?"; //Obtiene las propiedades del producto obtenido en la consulta anterior
        String cantTuplas = "SELECT COUNT(*) FROM EXISTENCIAS WHERE codigo_tienda = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2);
             PreparedStatement preSt3 = connection.prepareStatement(cantTuplas)){

            preSt.setString(1, tienda);
            preSt3.setString(1, tienda); //añadimos codigo de tienda la busqueda de cantidad de tuplas
            
            
            ResultSet result = preSt.executeQuery();
            ResultSet result3 = preSt3.executeQuery();
            
            
            result3.next(); //Hallamos el numero de tuplas
            
            String[][] atributos = new String[result3.getInt(1)][6];
            int cont = 0;
            
            
            
            while(result.next()){
                preSt2.setString(1, result.getString(1));
                ResultSet result2 = preSt2.executeQuery();
            
                result2.next();
                
                //Añadimos datos al array de atributos
                atributos[cont][0] = result2.getString(1);
                atributos[cont][1] = result2.getString(2);
                atributos[cont][2] = result2.getString(3);
                atributos[cont][3] = result2.getString(4);
                atributos[cont][4] = result2.getString(5);
                atributos[cont][5] = result2.getString(6);
                dtm.addRow(atributos[cont]);//agregamos atributos a una fila del modelo de tabla
                
                cont++;
            }
            
            tabla.setModel(dtm); //añadimos los datos a la tabla
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}
