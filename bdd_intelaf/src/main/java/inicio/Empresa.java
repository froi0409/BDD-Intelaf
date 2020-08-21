package inicio;
import interfazGrafica.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import analizadores.*;
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
    
    //Metodo encargado de leer archivos
    public void leerArchivo(FileReader file){
        
        ArrayList<String> errores = new ArrayList<>();
        int cont = 0;
        try{
            String linea;
            BufferedReader lector = new BufferedReader(file);
            
            do{
                cont++;
                linea = lector.readLine();
                
                //Esta condición se encarga de evitar que el archivo sobrepase las lineas que tiene escritas
                if(linea != null){
                    //System.out.println(linea);
                    if(!analizarLinea(linea)){
                        System.out.println("Error de entrada en la linea: " + cont);
                    } else {
                        System.out.println("ENTRADA VALIDA en linea " + cont);
                    }
                }
            }while(linea != null);
            
        } catch(IOException e){
            
        }
        
        //for(String e: errores){
        //    System.out.println(e);
        //}
    }
    
    private boolean analizarLinea(String lineas){
        AnalizadorPalabra a1 = new AnalizadorPalabra();
        String linea = lineas;
        linea+=",";
        //P - palabra
    //N - numero
    //. - punto
    //- - guion
    //S - String
        if("TIENDA".equals(linea.substring(0, 6))) {
            //Si en el archivo se ingresa una tienda, acá van las condiciones que debe tener la tienda para ser ingresada
            return a1.analizar("S,S,P-N,N,", linea.substring(7,linea.length()));
        } else if (linea.substring(0, 6).equals("TIEMPO")){
            //Si en el archivo de texto se ingresa un tiempo, acá van las condicions que debe tener el tiempo para ser ingresado
            return a1.analizar("P-N,P-N,N,", linea.substring(7,linea.length()));
        } else if(linea.substring(0, 8).equals("PRODUCTO")){
            //Si en el archivo de texto se ingresa un producto, acá van las condiciones que debe tener un producto para ser ingresado
            return a1.analizar("S,S,P-N,N,N.N,P-N,", linea.substring(9,linea.length()));
        } else if(linea.substring(0,8).equals("EMPLEADO")){
            //Si en el archivo de texto se ingresa un empleado, acá van las condiciones que debe tener un empleado para ser ingresado
            return a1.analizar("S,N,N,N,", linea.substring(9,linea.length()));
        } else if(linea.substring(0, 7).equals("CLIENTE")){
            //Si en el archivo de texto se ingresa un cliente, acá van las condiciones que debe tener un cliente para ser ingresado
            return a1.analizar("S,P-N,N,N,", linea.substring(8,linea.length()));
        } else if(linea.substring(0,6).equals("PEDIDO")){
            //Si en el archivo de texto se ingresa un pedido, acá van las condiciones que debe tener un pedido para ser ingresado
           
            if(a1.analizar("N,P-N,P-N,N-N-N,P-N,P-N,N,N.N,N,", linea.substring(7,linea.length())) || a1.analizar("N,P-N,P-N,N-N-N,P-N,P-N,N,N.N,N.N,", linea.substring(7,linea.length())) )
                    return true;
            else
                return false;
        
        } else{
            return false;
        }
        
    }
    
    
}
