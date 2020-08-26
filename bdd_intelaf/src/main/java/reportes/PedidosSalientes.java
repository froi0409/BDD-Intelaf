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
public class PedidosSalientes extends ReportePedido{
    
    private Funciones f = new Funciones();
    private String query = "SELECT codigo_pedido,anticipo,precio_final,fecha,tienda_origen,tienda_destino,NIT_cliente FROM PEDIDO WHERE tienda_origen = ? AND estado = 'SIN_ENTREGAR'"; //Query que nos permite hallar la información de los pedidos que llegarán a la tienda
        
    @Override
    public void cargarReporte(Connection connection, String codigo_tienda, JTextArea txa){
        
                f.cargarReporte(connection, codigo_tienda, txa, query);
        
    }

    @Override
    public void exportarReporte(Connection connection, String codigo_tienda, String ruta) {
        try {
            f.exportarReporte(connection, codigo_tienda, ruta, query);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
