/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTextArea;

/**
 *
 * @author froi-pc
 */
public class Funciones {
    
    public void cargarReporte(Connection connection, String codigo_tienda,JTextArea txa, String query){
        
        
        try (PreparedStatement preSt = connection.prepareStatement(query)) {
            preSt.setString(1, codigo_tienda);
            ResultSet result = preSt.executeQuery();
            
            txa.setText("");
            
            while(result.next()){
                txa.append("Info Pedido: ");
                txa.append("Cod: "+result.getString(1) + "  ");
                txa.append("Anticipo: "+result.getString(2) + "  ");
                txa.append("Total: "+result.getString(3) + "  ");
                txa.append("Fecha: "+ result.getString(4) + "  ");
                txa.append("Tie. Origen: "+result.getString(5) + "  ");
                txa.append("Tie. Destino: "+result.getString(6) + "  ");
                txa.append("NIT Cliente: "+result.getString(7) + "  ");
                
                txa.append("\n\nDescripción del Pedido:\n");
                
                String subquery = "Select codigo_producto,cantidad,total FROM DESCRIPCION_PEDIDO WHERE codigo_pedido = ?"; //hallamos los productos relacionados al pedido
                try(PreparedStatement preSt2 = connection.prepareStatement(subquery)) {
                    
                    preSt2.setString(1, result.getString(1));
                    ResultSet result2 = preSt2.executeQuery();
                    while(result2.next()){
                    
                        //Añadimos al text area las cualidades 
                    txa.append("Código producto:"+result2.getString(1) + "\n");
                    txa.append("Cantidad: "+result2.getString(2) + "\n");
                    txa.append("Total: "+result2.getString(3) + "\n");
                    txa.append("\n");
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error interno: " + e.getMessage());
                }
                
                txa.append("\n\n\n");
                
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
