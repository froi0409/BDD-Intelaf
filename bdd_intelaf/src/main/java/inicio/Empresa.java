package inicio;
import interfazGrafica.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author froi-pc
 */
public class Empresa {
    public void inicio(){
        PantallaInicial p1 = new PantallaInicial();
        p1.setVisible(true);
    }
    
    public void pantallaCargaArchivo(){
        CargaDeArchivo c1 = new CargaDeArchivo(this);
        c1.setVisible(true);
        
    }
    
    public void leerArchivo(FileReader file){
        
        try{
            String linea;
            BufferedReader lector = new BufferedReader(file);
            
            do{
                
                linea = lector.readLine();
                System.out.println(linea);
                
            }while(linea != null);
            
        } catch(IOException e){
            
        }
        
    }
}
