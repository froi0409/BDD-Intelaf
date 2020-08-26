package inicio;
import interfazGrafica.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import analizadores.*;
import entidades.Cliente;
import entidades.Empleado;
import entidades.Pedido;
import entidades.Producto;
import entidades.TiempoEnvio;
import entidades.Tienda;
/**
 *
 * @author froi-pc
 */
public class Empresa {
    
    public void inicio(){
        
        Conexion connection =  new Conexion();
        connection.crearConexion();
        
        PantallaInicial p1 = new PantallaInicial(this);
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
                    if(!analizarLinea(linea)){
                        System.out.println("Error de entrada en la linea: " + cont);
                    } else {
                        System.out.println("ENTRADA VALIDA en linea " + cont);
                    }
                }
            }while(linea != null);
            
        } catch(IOException e){
            
        }
        
    }
    
    public void desfragmentarPalabra(String cadena, String[] array){
        
        //cont que sirve para determinar las posiciones del array a devolver
        int cont = 0, start = 0, end = 0;
        for(int i = 0; i < cadena.length(); i++){
            
            if(cadena.charAt(i) == ','){
                
                array[cont] = cadena.substring(start,i);
                cont++;
                start = i + 1;
                
            }
            
        }
        
    }
    
    private boolean analizarLinea(String lineas){
        boolean retorno = true;
        AnalizadorPalabra a1 = new AnalizadorPalabra();
        String linea = lineas;
        //Nos sirve para dividir el numero de bloques que tendrá la linea
        String[] subcadenas;
        
        //Se añade una , a la cadena para detectar el final del último bloque del texto 
        linea+=",";
        //P - palabra
        //N - numero
        //. - punto
        //- - guion
        //S - String
        if("TIENDA".equals(linea.substring(0, 6))) {
            //Si en el archivo se ingresa una tienda, acá van las condiciones que debe tener la tienda para ser ingresada
            if(a1.analizar("S,S,P-N,N,", linea.substring(7,linea.length()))){
                subcadenas = new String[4];
                desfragmentarPalabra(linea.substring(7,linea.length()),subcadenas);
                Tienda.ingresoTiendaArchivo(Conexion.getConnection(), subcadenas[0], subcadenas[1], subcadenas[2], subcadenas[3]);
            }
        } else if (linea.substring(0, 6).equals("TIEMPO")){
            //Si en el archivo de texto se ingresa un tiempo, acá van las condicions que debe tener el tiempo para ser ingresado
            
            if (a1.analizar("P-N,P-N,N,", linea.substring(7,linea.length()))){
                
                subcadenas = new String[3];
                desfragmentarPalabra(linea.substring(7,linea.length()), subcadenas);
                TiempoEnvio.ingresoTiempoArchivo(Conexion.getConnection(), subcadenas[0], subcadenas[1], subcadenas[2]);
                
            }
        } else if(linea.substring(0, 8).equals("PRODUCTO")){
            //Si en el archivo de texto se ingresa un producto, acá van las condiciones que debe tener un producto para ser ingresado
            if (a1.analizar("S,S,P-N,N,N.N,P-N,", linea.substring(9,linea.length()))){
                
                subcadenas = new String[6];
                desfragmentarPalabra(linea.substring(9,linea.length()), subcadenas);
                Producto.ingresoProductoArchivo(Conexion.getConnection(), subcadenas[0], subcadenas[1], subcadenas[2], subcadenas[3], subcadenas[4], subcadenas[5]);
                
            }
        } else if(linea.substring(0,8).equals("EMPLEADO")){
            //Si en el archivo de texto se ingresa un empleado, acá van las condiciones que debe tener un empleado para ser ingresado
            if (a1.analizar("S,N,N,N,", linea.substring(9,linea.length()))){
                
                subcadenas = new String[4];
                desfragmentarPalabra(linea.substring(9,linea.length()), subcadenas);
                Empleado.ingresoEmpleadoArchivo(Conexion.getConnection(), subcadenas[0], subcadenas[1], subcadenas[2], subcadenas[3]);
                
            }
        } else if(linea.substring(0, 7).equals("CLIENTE")){
            //Si en el archivo de texto se ingresa un cliente, acá van las condiciones que debe tener un cliente para ser ingresado
            if (a1.analizar("S,P-N,N,N,", linea.substring(8,linea.length()))){
                
                subcadenas = new String[4];
                desfragmentarPalabra(linea.substring(8,linea.length()), subcadenas);
                Cliente.ingresoClienteArchivo(Conexion.getConnection(), subcadenas[0], subcadenas[1], subcadenas[2], subcadenas[3]);
                
            }
        } else if(linea.substring(0,6).equals("PEDIDO")){
            //Si en el archivo de texto se ingresa un pedido, acá van las condiciones que debe tener un pedido para ser ingresado
           
            if(a1.analizar("N,P-N,P-N,N-N-N,P-N,P-N,N,N.N,N,", linea.substring(7,linea.length())) || a1.analizar("N,P-N,P-N,N-N-N,P-N,P-N,N,N.N,N.N,", linea.substring(7,linea.length())) ){
                
                subcadenas = new String[9];
                desfragmentarPalabra(linea.substring(7,linea.length()),subcadenas);
                Pedido.ingresoPedidoArchivo(Conexion.getConnection(), subcadenas[0], subcadenas[1], subcadenas[2], subcadenas[3], subcadenas[4], subcadenas[5], subcadenas[6], subcadenas[7], subcadenas[8]);
                
            }    
            else
                return false;
        
        } else{
            return false;
        }
        return retorno;
        
    }
    
}
