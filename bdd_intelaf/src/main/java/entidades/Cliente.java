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
public class Cliente extends Persona {
    @Override
    public void consultaDatos(){
        
    }
    
    @Override
    public void registro(){
        
    }
    
    public void cambioDatos(){
        
    }
    
    public void monitoreoPedido(){
        
    }
    
    public static void ingresoClienteArchivo(Connection connection, String nombre, String NIT, String telefono, String credito){
        
        String query = "INSERT INTO CLIENTE (nombre,NIT,telefono,credito) VALUES (?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, NIT);
            preSt.setString(3, telefono);
            preSt.setString(4, credito);
            
            preSt.executeUpdate();
            
            preSt.close();
            
        } catch(SQLException e){
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
    }
    
    public void mostrarClientes(Connection connection, JTable tabla, DefaultTableModel dtm, int filtro){
        
        String query = "SELECT * FROM CLIENTE ORDER BY ?";
        String query2 = "SELECT COUNT(*) FROM CLIENTE"; //Query que sirve para obtener la cantidad de tuplas que hay en la tabla cliente
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2)){

            preSt.setInt(1, filtro);
            
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next(); //Movemos el cursor para obtener la cantidad de tuplas
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
                
                dtm.addRow(atributos[cont]);
                cont++;
            }
             
            tabla.setModel(dtm);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void ingresarCliente(Connection connection, String NIT, String DPI, String nombre, String telefono, String correo_electronico, String direccion){
        
        String insert = "INSERT INTO CLIENTE (NIT,DPI,nombre,telefono,correo_electronico,direccion, credito) VALUES (?,?,?,?,?,?,0.00)"; //Inserta los valores necesarios, ya que el crédito no puede ser ingresado de la forma manual, únicamente puede ser midificado a través del retraso de un pedido
        
        try (PreparedStatement preSt = connection.prepareStatement(insert)) {
            
            preSt.setString(1, NIT);
            preSt.setString(2, DPI);
            preSt.setString(3, nombre);
            preSt.setString(4, telefono);
            preSt.setString(5, correo_electronico);
            preSt.setString(6, direccion);
            
            preSt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
    
    public void comboBoxClientes(Connection connection, JComboBox combo){
        
        String query = "SELECT NIT FROM CLIENTE";
        try (PreparedStatement preSt = connection.prepareStatement(query)) {
            
            ResultSet result = preSt.executeQuery();
            
            //Llenamos el combobox con los diferentes nits de los diferentes clientes 
            while(result.next()){
                combo.addItem(result.getString(1));
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
}
