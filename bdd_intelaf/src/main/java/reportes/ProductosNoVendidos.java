/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.sql.Connection;
import javax.swing.JTextArea;

/**
 *
 * @author froi-pc
 */
public class ProductosNoVendidos {
    
    public void cargarReporte(Connection connection, JTextArea txa){
        
        String query = "SELECT codigo_pedido FROM PEDIDO WHERE estado = 'COMPRADO'";
        String query2 = "";
        
    }
    
}
