/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import java.util.ArrayList;

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
        
        String subEstructura = "", comparador = "";
        
        if(!estructura.equals("S")){
            
            //ciclo que sirve para identificar cada termino de la cadena
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
            for(int i = 0; i < subEstructura.length()-1; i++){
               if(esPunto(subEstructura.charAt(i+1))){
                   comparador+='.';
               }
               else if(esGuion(subEstructura.charAt(i+1))){
                   comparador+='-';
               }
               else if (subEstructura.charAt(i) != subEstructura.charAt(i+1)){
                   comparador += subEstructura.charAt(i+1);
               }
            }
            
            
        }else{
            comparador+='S';
        }
        
        return estructura.equals(comparador);
    }
    
    private ArrayList descomponerPalabras(String linea){
        
        ArrayList<String> palabras = new ArrayList<>();
        int start = 0, end = 0;
        
        for(int i = 0; i < linea.length(); i++){
            if(linea.charAt(i) == ','){
                
                palabras.add(linea);
                
            }
        }
        
        return palabras;
    }
    
    public void ingreso(String tipo, String linea){
        switch(tipo){
            case "TIENDA":
                
                break;
            case "TIEMPO":
                
                break;
            case "PRODUCTO":
                
                break;
            case "EMPLEADO0":
                
                break;
            case "CLIENTE":
                
                break;
            case "PEDIDO":
                
                break;
            default:
                break;
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
