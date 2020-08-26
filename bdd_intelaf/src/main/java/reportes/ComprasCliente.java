/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author froi-pc
 */
public class ComprasCliente {
    
    public void cargarReporte(Connection connection, JTextArea txa, String NIT){
        
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE NIT_cliente = ? AND estado = 'COMPRADO'";
        String query2 = "SELECT codigo_producto FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ?";
        String query3 = "SELECT codigo_producto,nombre,fabricante FROM PRODUCTO WHERE codigo_producto = ?";
        try(PreparedStatement preSt = connection.prepareStatement(query);
                PreparedStatement preSt2 = connection.prepareStatement(query2);
                PreparedStatement preSt3 = connection.prepareStatement(query3)) {
            
            preSt.setString(1, NIT);
            ResultSet result = preSt.executeQuery();
            result.next(); //Obtenemos el codigo del pedido
            
            preSt2.setString(1, result.getString(1));
                
            ResultSet result2 = preSt2.executeQuery();
           
            while(result2.next()){
                preSt3.setString(1, result2.getString(1));
                ResultSet result3 = preSt3.executeQuery();
                result3.next();
                
                txa.append("Codigo: " + result3.getString(1) +"\n");
                txa.append("Nombre: " + result3.getString(2) + "\n");
                txa.append("Fabricante: " + result3.getString(3) + "\n");
                
                txa.append("\n\n");
            }
                    
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        
    }
    
    public void exportarReporte(Connection connection, String NIT, String ruta) throws IOException{
        
        FileWriter salida = new FileWriter(ruta +".html");
        BufferedWriter linea = new BufferedWriter(salida);
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE NIT_cliente = ? AND estado = 'COMPRADO'";
        String query2 = "SELECT codigo_producto FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ?";
        String query3 = "SELECT codigo_producto,nombre,fabricante FROM PRODUCTO WHERE codigo_producto = ?";
        
        
        
        try(PreparedStatement preSt = connection.prepareStatement(query);
                PreparedStatement preSt2 = connection.prepareStatement(query2);
                PreparedStatement preSt3 = connection.prepareStatement(query3)) {
            
            linea.write("<html>\n" +
                        "<head>\n" +
                        "  <title></title>\n" +
                        "</head>\n" +
                        "<body>\n");
            
            preSt.setString(1, NIT);
            ResultSet result = preSt.executeQuery();
            result.next(); //Obtenemos el codigo del pedido
            
            preSt2.setString(1, result.getString(1));
                
            ResultSet result2 = preSt2.executeQuery();
           
            while(result2.next()){
                preSt3.setString(1, result2.getString(1));
                ResultSet result3 = preSt3.executeQuery();
                result3.next();
                
                linea.write("<p>Codigo: " + result3.getString(1) +"</p>\n");
                linea.write("<p>Nombre: " + result3.getString(2) + "</p>\n");
                linea.write("<p>Fabricante: " + result3.getString(3) + "</p>\n");
                
                linea.write("\n\n");
            }
                    
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        } finally {
            
            linea.write("</body>\n" +
                        "</html>");
            linea.close();
            
            JOptionPane.showMessageDialog(null, "Archivo exportado en carpeta\ndel proyecto como: \n" + ruta + ".html");
            
        }
    }
    
}
