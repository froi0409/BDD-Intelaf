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
                    if(!analisisEstructural(estructura.substring(start,end), linea.substring(start2, end2))){ //llama a la función
                        return false;
                    }
                    start2 = end2 + 1;
                    start = i + 1;
                
                }
                
                
            }
            if(end2 != linea.length()-1)
                return false;
        } catch(Exception e){
            return false;
        }
        
        return true;
    }
    
    //comprueba que el bloque cumpla las condiciones
    private boolean analisisEstructural(String estructura, String linea){
        
        boolean analizador = true;       
        String subEstructura = "", comparador = "";
        
        if(!estructura.equals("S")){
            
            for(int i = 0; i < linea.length(); i++){
                if(esPunto(linea.charAt(i)))
                    subEstructura+='.';
                else if(esGuion(linea.charAt(i)))
                    subEstructura+='-';
                else if(esNumero(linea.charAt(i)))
                    subEstructura+='N';
                else if(esLetra(linea.charAt(i)))
                    subEstructura+='P';
                else
                    return false;
            }
            
            comparador+=subEstructura.charAt(0);
            int aux;
            char cursor = subEstructura.charAt(0);
            for(int i = 0; i < subEstructura.length()-1; i++){
               if(esPunto(subEstructura.charAt(i+1))){
                   comparador+='.';
                   //cursor = '.';
               }
               else if(esGuion(subEstructura.charAt(i+1))){
                   comparador+='-';
                   //cursor = '-';
               }
               else if (subEstructura.charAt(i) != subEstructura.charAt(i+1)){
                   //cursor = subEstructura.charAt(i+1);
                   comparador += subEstructura.charAt(i+1);
               }
            }
            
            
        }else{
            comparador+='S';
        }
        
        if(!estructura.equals(comparador)){
            return false;
        } else{
            return true;
        }
    }
    
    
    
    private boolean esLetra(char a){
        return Character.isLetter(a);
    }
    
    private boolean esPunto(char a){
        return a == '.';
    }
    
    private boolean esGuion(char a){
        return a == '-';
    }
    
    private boolean esNumero(char a){
        return a == '0' || a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9';
    }
    
    
}
