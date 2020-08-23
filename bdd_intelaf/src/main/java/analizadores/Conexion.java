
package analizadores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entidades.*;

/**
 *
 * @author froi-pc
 */
public class Conexion {
    
    private static Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/INTELAF?useSSL=false";
    private final String user = "usuarioIntelaf";
    private final String password = "intelaf123";
    //Metodo que sirve para iniciar la conexión
    public void crearConexion(){
        
   
        
        try{
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("CONEXIÓN INICIADA");
        }catch(SQLException e){
            System.out.println("CONEXION SIN INICIAR");
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public static Connection getConnection(){
        return connection;
    }
    
    public void query(Connection connection, String busqueda){
        
        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(busqueda)){
                
            if(resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
            statement.close();
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public String getUser(){
        return user;
    }
    
}
