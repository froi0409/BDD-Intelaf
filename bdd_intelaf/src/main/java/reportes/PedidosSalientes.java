package reportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTextArea;

/**
 *
 * @author froi-pc
 */
public class PedidosSalientes extends ReportePedido{
    
    @Override
    public void cargarReporte(Connection connection, String codigo_tienda, JTextArea txa){
        
        Funciones f = new Funciones();
        
        String query = "SELECT codigo_pedido,anticipo,precio_final,fecha,tienda_origen,tienda_destino,NIT_cliente FROM PEDIDO WHERE tienda_origen = ? AND estado = 'SIN_ENTREGAR'"; //Query que nos permite hallar la información de los pedidos que llegarán a la tienda
        
        f.cargarReporte(connection, codigo_tienda, txa, query);
        
    }
    
}
