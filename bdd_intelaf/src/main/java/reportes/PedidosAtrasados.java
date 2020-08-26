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
public class PedidosAtrasados extends ReportePedido {
    
    @Override
    public void cargarReporte(Connection connection, String codigo_tienda, JTextArea txa){
        
        Funciones f = new Funciones();
        String query = "SELECT codigo_pedido,anticipo,precio_final,fecha,tienda_origen,tienda_destino,NIT_cliente FROM PEDIDO WHERE DATEDIFF(NOW(),fecha) > tiempo_envio AND tienda_destino = ? AND estado = 'SIN_ENTREGAR'";
        f.cargarReporte(connection, codigo_tienda, txa, query);
        
    }
    
}
