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
public class ProductosPorTienda {
    
    public void cargarReporte(Connection connection, JTextArea txa, String tienda){
        
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE estado = 'COMPRADO' AND tienda_destino = ?";
        String query2 = "SELECT codigo_producto FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ? ORDER BY cantidad ASC LIMIT 5";
        String query3 = "SELECT codigo_producto,nombre,fabricante FROM PRODUCTO WHERE codigo_producto = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2);
             PreparedStatement preSt3 = connection.prepareStatement(query3)) {
            
            preSt.setString(1, tienda);
            ResultSet result = preSt.executeQuery();
            result.next();
            
            preSt2.setString(1, result.getString(1));
            ResultSet result2 = preSt2.executeQuery();
            int cont = 1;
            
            
            
            while(result2.next()){
                
                preSt3.setString(1, result2.getString(1));
                ResultSet result3 = preSt3.executeQuery();
                result3.next();
                
                
                txa.append("Numero " + cont + "\n");
                txa.append("Codigo: " + result3.getString(1) + "\n");
                txa.append("Nombre: " + result3.getString(2) + "\n");
                txa.append("Fabricante: " + result3.getString(3) + "\n");
                txa.append("\n\n");
                cont++;
                
                
            }
            
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public void exportarArchivo(Connection connection, String tienda, String ruta) throws IOException{
        
        FileWriter fw = new FileWriter(ruta + ".html");
        BufferedWriter linea  = new BufferedWriter(fw);
        
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE estado = 'COMPRADO' AND tienda_destino = ?";
        String query2 = "SELECT codigo_producto FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ? ORDER BY cantidad ASC LIMIT 5";
        String query3 = "SELECT codigo_producto,nombre,fabricante FROM PRODUCTO WHERE codigo_producto = ?";
        
        try (PreparedStatement preSt = connection.prepareStatement(query);
             PreparedStatement preSt2 = connection.prepareStatement(query2);
             PreparedStatement preSt3 = connection.prepareStatement(query3)) {
            
            preSt.setString(1, tienda);
            ResultSet result = preSt.executeQuery();
            result.next();
            
            
            preSt2.setString(1, result.getString(1));
            ResultSet result2 = preSt2.executeQuery();
            
            int cont = 1;
            
             linea.write("<html>\n" +
                        "<head>\n" +
                        "  <title></title>\n" +
                        "</head>\n" +
                        "<body>\n");
            
            
            while(result2.next()){
                
                preSt3.setString(1, result2.getString(1));
                ResultSet result3 = preSt3.executeQuery();
                result3.next();
                
                linea.write("Numero " + cont + "\n");
                linea.write("Codigo: " + result3.getString(1) + "\n");
                linea.write("Nombre: " + result3.getString(2) + "\n");
                linea.write("Fabricante: " + result3.getString(3) + "\n");
                linea.write("\n\n");
                cont++;
                
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
