/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazGrafica;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author froi-pc
 */
public class MyTableModel extends AbstractTableModel{

    private int filas, columnas;
    private String[][] datos;
    
    public MyTableModel(int filas, int columnas, String[][] datos){
        this.filas = filas;
        this.columnas = columnas;
        this.datos = datos;
    }
    
    @Override
    public int getRowCount() {
        return filas;
    }

    @Override
    public int getColumnCount() {
        return columnas;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return datos[rowIndex][columnIndex];
    }
    
    
    
    
}
