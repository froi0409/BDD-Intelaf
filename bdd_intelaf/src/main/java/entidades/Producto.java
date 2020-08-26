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
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, fabricante);
            preSt.setString(3, codigo);
            preSt.setString(4, precio);
           

            preSt.executeUpdate();
            
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            
            try (PreparedStatement preSt2 = connection.prepareStatement(query2)){
                
                preSt2.setString(1, codigoDesc);
                preSt2.setString(2, cantidad);
                preSt2.setString(3, codigo);
                preSt2.setString(4, tienda);
                
                preSt2.executeUpdate();
            
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        }
        
    }
    
    public void mostrarProductos(Connection connection, JTable tabla, DefaultTableModel dtm){
        
        String query = "SELECT P.codigo_producto,P.nombre,P.fabricante,P.precio,P.descripcion,P.garantia,E.cantidad,E.codigo_tienda FROM EXISTENCIAS E INNER JOIN PRODUCTO P ON E.codigo_producto = P.codigo_producto";
        String cantTuplas = "SELECT COUNT(*) FROM EXISTENCIAS";
        String nombreTienda = "SELECT nombre FROM TIENDA WHERE codigo_tienda = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
            PreparedStatement preSt2 = connection.prepareStatement(cantTuplas);
            PreparedStatement preSt3 = connection.prepareStatement(nombreTienda)){
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next(); //Encontramos el numero de tuplas que hay en la tabla PRODUCTO
            
            String[][] atributos = new String[result2.getInt(1)][8];
            int cont = 0;
            
            while(result.next()){
                
                preSt3.setString(1, result.getString(8));
                ResultSet result3 = preSt3.executeQuery();
                result3.next();
                
                atributos[cont][0] = result3.getString(1);
                atributos[cont][1] = result.getString(1);
                atributos[cont][2] = result.getString(2);
                atributos[cont][3] = result.getString(3);
                atributos[cont][4] = "Q " + result.getString(4);
                atributos[cont][5] = result.getString(5);
                atributos[cont][6] = result.getString(6);
                atributos[cont][7] = result.getString(7);
                
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
        String nombreTienda = "SELECT nombre FROM TIENDA WHERE codigo_tienda = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2);
             PreparedStatement preSt3 = connection.prepareStatement(cantTuplas);
             PreparedStatement preSt4 = connection.prepareStatement(nombreTienda)){

            preSt.setString(1, tienda);
            preSt3.setString(1, tienda); //añadimos codigo de tienda la busqueda de cantidad de tuplas
            preSt4.setString(1, tienda); //añadimos codigo de tienda a la busqueda del nombre de la tienda
            
            ResultSet result = preSt.executeQuery();
            ResultSet result3 = preSt3.executeQuery();
            ResultSet result4 = preSt4.executeQuery();
            
            
            result3.next(); //Movemos el cursor para hallar el numero de tuplas
            result4.next(); //Movemos el cursor para obtener el nombre de la tienda
            
            String[][] atributos = new String[result3.getInt(1)][8];
            int cont = 0;
            
            
            
            while(result.next()){
                preSt2.setString(1, result.getString(1));
                ResultSet result2 = preSt2.executeQuery();
            
                result2.next();
                
                //Añadimos datos al array de atributos
                atributos[cont][0] = result4.getString(1);
                atributos[cont][1] = result2.getString(1);
                atributos[cont][2] = result2.getString(2);
                atributos[cont][3] = result2.getString(3);
                atributos[cont][4] = "Q " + result2.getString(4);
                atributos[cont][5] = result2.getString(5);
                atributos[cont][6] = result2.getString(6);
                dtm.addRow(atributos[cont]);//agregamos atributos a una fila del modelo de tabla
                
                cont++;
            }
            
            tabla.setModel(dtm); //añadimos los datos a la tabla
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void ingresarProducto(Connection connection, String codigo, String nombre, String fabricante, String precio, String descripcion, String garantia, String cantidad, String tienda){
        
        String codigo_existencia = codigo+tienda;
        String insert = "INSERT INTO PRODUCTO VALUES (?,?,?,?,?,?)";
        String insert2 = "INSERT INTO EXISTENCIAS VALUES (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(insert);
            PreparedStatement preSt2 = connection.prepareStatement(insert2)) {
            
            preSt.setString(1, codigo);
            preSt.setString(2, nombre);
            preSt.setString(3, fabricante);
            preSt.setString(4, precio);
            preSt.setString(5, descripcion);
            preSt.setString(6, garantia);
            
            preSt2.setString(1, codigo_existencia);
            preSt2.setString(2, cantidad);
            preSt2.setString(3, codigo);
            preSt2.setString(4, tienda);
            
            preSt.executeUpdate();
            preSt2.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public String getPrecio(Connection connection, String cantidad, String codigo_producto){
        
        int cant = Integer.parseInt(cantidad);
        String query = "SELECT precio FROM PRODUCTO WHERE codigo_producto = ?";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            //Le asignamos valor a la incógnita
            preSt.setString(1, codigo_producto);
            ResultSet result = preSt.executeQuery();
            
            result.next(); //Movemos el cursor al precio
            
            String retorno = ""; //Creamos variable de retorno
            
            retorno += cant*result.getDouble(1); //Añadimos resultado a la cadena de retorno
            
            System.out.println(retorno);
            
            return retorno;
            
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        
    }
    
}
