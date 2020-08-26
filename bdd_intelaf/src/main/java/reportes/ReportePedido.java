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
public abstract class ReportePedido {
    
    public abstract void cargarReporte(Connection connection, String codigo_tienda, JTextArea txa);
    
}
