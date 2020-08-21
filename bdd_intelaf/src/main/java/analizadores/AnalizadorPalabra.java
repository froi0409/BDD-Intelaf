/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

/**
 *
 * @author froi-pc
 */
public class AnalizadorPalabra {
    //P - palabra
    //E  - entero
    //N - numero
    //. - punto
    //- - guion
    //S - String
    public boolean analizar(String estructura, String linea){
        
        int start = 0, end;
        int start2 = 0, end2 = 0;
        try{
            //Ciclo que analiza cada bloque de estructura
            for(int i = 0; i < estructura.length(); i++){
            
                if(estructura.charAt(i) == ','){
                
                    end = i;
                
                      //ciclo que analiza cada bloque de 
                    do{
                      end2++;
                    }while(linea.charAt(end2) != ',');
                    if(!analisisEstructural(estructura.substring(start,end), linea.substring(start2, end2))){
                        return false;
                    }
                    start2 = end2 + 1;
                    start = i + 1;
                
                }
                
                
            }
            if(end2 != linea.length()-1)
                return false;
        } catch(Exception e){
            System.out.println("NO SE CUMPLE");
            return false;
        }
        
        return true;
    }
    
    private boolean analisisEstructural(String estructura, String linea){
        System.out.println(estructura + "  " + linea);
        return true;
    }
}
