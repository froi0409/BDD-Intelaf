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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author froi-pc
 */
public class PedidosEnCurso {
    
    public void cargarReporte(Connection connection, JTextArea txa, String NIT){
        
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE NIT_cliente = ? AND estado = 'SIN_ENTREGAR'";
        String query2 = "SELECT codigo_producto FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ?";
        String query3 = "SELECT codigo_producto,nombre,fabricante FROM PRODUCTO WHERE codigo_producto = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2);
             PreparedStatement preSt3 = connection.prepareStatement(query3)){
            
            preSt.setString(1, NIT);
            ResultSet result = preSt.executeQuery();
            
            while(result.next()){
                
                txa.append("Codigo Pedido: " + result.getString(1) + "\n\n");
                
                preSt2.setString(1, result.getString(1));
                ResultSet result2 = preSt2.executeQuery();
                
                while(result2.next()){
                    
                    preSt3.setString(1, result2.getString(1));
                    ResultSet result3 = preSt3.executeQuery();
                    
                    result3.next();
                    
                    txa.append("Código Producto: " + result3.getString(1) + "\n");
                    txa.append("Nombre: " + result3.getString(2) + "\n");
                    txa.append("Fabricante: " + result3.getString(3) + "\n\n");
                    
                }
                
                txa.append("\n\n\n");
                
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void exportarReporte(Connection connection, String nit, String ruta) throws IOException{
        
        FileWriter fw = new FileWriter(ruta+".html");
        BufferedWriter linea = new BufferedWriter(fw);
        
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE NIT_cliente = ? AND estado = 'SIN_ENTREGAR'";
        String query2 = "SELECT codigo_producto FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ?";
        String query3 = "SELECT codigo_producto,nombre,fabricante FROM PRODUCTO WHERE codigo_producto = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2);
             PreparedStatement preSt3 = connection.prepareStatement(query3)){
            
            
            preSt.setString(1, nit);
            ResultSet result = preSt.executeQuery();
            
             linea.write("<html>\n" +
                        "<head>\n" +
                        "  <title></title>\n" +
                        "</head>\n" +
                        "<body>\n");
            
            
            while(result.next()){
                
                linea.write("Codigo Pedido: " + result.getString(1) + "\n\n");
                
                preSt2.setString(1, result.getString(1));
                ResultSet result2 = preSt2.executeQuery();
                
                while(result2.next()){
                    
                    preSt3.setString(1, result2.getString(1));
                    ResultSet result3 = preSt3.executeQuery();
                    
                    result3.next();
                    
                    linea.write("Código Producto: " + result3.getString(1) + "\n");
                    linea.write("Nombre: " + result3.getString(2) + "\n");
                    linea.write("Fabricante: " + result3.getString(3) + "\n\n");
                    
                }
                
                linea.write("<p><p><p>");
                
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }finally {
            
            linea.write("</body>\n" +
                        "</html>");
            linea.close();
            
            JOptionPane.showMessageDialog(null, "Archivo exportado en carpeta\ndel proyecto como: \n" + ruta + ".html");
            
        }
    }
    
}
