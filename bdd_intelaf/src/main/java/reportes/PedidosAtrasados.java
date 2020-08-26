/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author froi-pc
 */
public class PedidosAtrasados extends ReportePedido {
    
    private Funciones f = new Funciones();
    private String query = "SELECT codigo_pedido,anticipo,precio_final,fecha,tienda_origen,tienda_destino,NIT_cliente FROM PEDIDO WHERE DATEDIFF(NOW(),fecha) > tiempo_envio AND tienda_destino = ? AND estado = 'SIN_ENTREGAR'";
        
    @Override
    public void cargarReporte(Connection connection, String codigo_tienda, JTextArea txa){
        
        f.cargarReporte(connection, codigo_tienda, txa, query);
        
    }
    
    public void exportarReporte(Connection connection, String codigo_tienda, String ruta){
        try {
            f.exportarReporte(connection, codigo_tienda, ruta, query);
        } catch (IOException ex) {
            Logger.getLogger(PedidosAtrasados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
