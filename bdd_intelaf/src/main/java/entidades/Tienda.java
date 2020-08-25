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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author froi-pc
 */
public class Tienda {
    
    public static void ingresoTiendaArchivo(Connection connection, String nombre, String direccion, String codigo_tienda, String telefono1){
        
        String query = "INSERT INTO TIENDA (nombre,direccion,codigo_tienda,telefono1) VALUES (?,?,?,?)"; //Inserta valores en la tabla tienda
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1,nombre);
            preSt.setString(2,direccion);
            preSt.setString(3,codigo_tienda);
            preSt.setString(4,telefono1);
            
            preSt.executeUpdate();
            preSt.close();
            
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void seleccionTiendas(Connection connection, JComboBox jcom){
        
        String query = "SELECT codigo_tienda,nombre FROM TIENDA";
        try (PreparedStatement preSt = connection.prepareStatement(query)){
            
            ResultSet result = preSt.executeQuery();
            
            while(result.next()){
                
                jcom.addItem(result.getString(1) + "    " + result.getString(2)); //Añade items al JComboBox
            }
            
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
        
    }
    
    public void mostrarTiendas(Connection connection, JTable tabla, DefaultTableModel tmb, int filtro){
        
        String query = "SELECT * FROM TIENDA ORDER BY ?";
        String cantTuplas = "SELECT COUNT(*) FROM TIENDA";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(cantTuplas)){
            
            preSt.setInt(1, filtro); //Asignamos valor a incógnita
            
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next(); //Obtenemos la cantidad de tuplas;
            String[][] atributos = new String[result2.getInt(1)][7];
            int cont = 0;
            
            while (result.next()){
                
                atributos[cont][0] = result.getString(1);
                atributos[cont][1] = result.getString(2);
                atributos[cont][2] = result.getString(3);
                atributos[cont][3] = result.getString(4);
                atributos[cont][4] = result.getString(5);
                atributos[cont][5] = result.getString(6);
                atributos[cont][6] = result.getString(7);
                
                tmb.addRow(atributos[cont]);
                cont++;
                
            }
            
            tabla.setModel(tmb);
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        
    }
    
    public void insertarTiendas(Connection connection, String codigo_tienda, String nombre, String direccion, String telefono1, String telefono2, String correo_electronico, String horario){
        
        String insert = "INSERT INTO TIENDA VALUES (?,?,?,?,?,?,?)";
        
        try (PreparedStatement preSt = connection.prepareStatement(insert)) {
            
            preSt.setString(1, codigo_tienda);
            preSt.setString(2, nombre);
            preSt.setString(3, direccion);
            preSt.setString(4, telefono1);
            preSt.setString(5, telefono2);
            preSt.setString(6, correo_electronico);
            preSt.setString(7, horario);
            
            preSt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}
