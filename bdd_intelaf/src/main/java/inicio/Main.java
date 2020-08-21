package inicio;
import analizadores.*;
/**
 *
 * @author froi-pc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Empresa e1 = new Empresa();
        AnalizadorPalabra a1 = new AnalizadorPalabra();
        if(a1.analizar("S-NN,NN.E,HOLA,dsf,S,", "1-55,2,3,4,5,"))
            System.out.println("SE CUMPLIÓ");
//e1.pantallaCargaArchivo();
        
    }
    
}
