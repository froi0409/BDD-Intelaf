/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author froi-pc
 */
public class DatosNoPermitidos extends Exception {
    
    /**
     *
     * @return
     */
    @Override
    public String getMessage(){
        return "Los Datos no coinciden"; 
    }
    
}
