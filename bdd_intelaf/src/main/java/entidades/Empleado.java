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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author froi-pc
 */
public class Empleado extends Persona{
    
    @Override
    public void consultaDatos(){
        
    }
    @Override
    public void registro(){
        
    }
    
    public void cambioDatos(){
        
    }
    
    public static void ingresoEmpleadoArchivo(Connection connection, String nombre, String codigo, String telefono, String DPI){
        
        String query = "INSERT INTO EMPLEADO (nombre,codigo_empleado,telefono,DPI) VALUES(?,?,?,?)";
        
        try(PreparedStatement preSt = connection.prepareStatement(query)){
            
            preSt.setString(1, nombre);
            preSt.setString(2, codigo);
            preSt.setString(3, telefono);
            preSt.setString(4, DPI);
            
            preSt.executeUpdate();
            preSt.close();
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    public void mostrarEmpleados(Connection connection, JTable tabla, DefaultTableModel dtm, int filtro){
        
        String query = "SELECT * FROM EMPLEADO ORDER BY ?";
        String query2 = "SELECT COUNT(*) FROM EMPLEADO";//Obtenemos el numero de tuplas que tiene la tabla empleado
        
        try(PreparedStatement preSt = connection.prepareStatement(query);
            PreparedStatement preSt2 = connection.prepareCall(query2)){
            
            preSt.setInt(1, filtro); //Ordenamos la tabla respecto a algun atributo
            
            ResultSet result = preSt.executeQuery();
            ResultSet result2 = preSt2.executeQuery();
            
            result2.next(); // Movemos el cursor para obtener la cantidad de tuplas
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
    
    public void ingresoEmpleado(Connection connection, String codigo_empleado, String nombre, String telefono, String DPI, String NIT, String correo_electronico, String direccion){
        String insert = "INSERT INTO EMPLEADO VALUES (?,?,?,?,?,?,?)"; //Insertamos los valores de los empleados
        try (PreparedStatement preSt = connection.prepareStatement(insert)) {
            
            preSt.setString(1, codigo_empleado);
            preSt.setString(2, nombre);
            preSt.setString(3, telefono);
            preSt.setString(4, DPI);
            preSt.setString(5, NIT);
            preSt.setString(6, correo_electronico);
            preSt.setString(7, direccion);
            
            preSt.executeLargeUpdate(); //Ingresamos los datos a la tabla empleado
            
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public boolean verificarEmpleado(Connection connection, String codigo_empleado){
        
        String query = "SELECT codigo_empleado FROM EMPLEADO WHERE codigo_empleado = ?";
        try (PreparedStatement preSt = connection.prepareStatement(query)) {
            
            preSt.setString(1, codigo_empleado);
            
            ResultSet result = preSt.executeQuery();
            
            if(result.next()){
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        
    }
    
}
