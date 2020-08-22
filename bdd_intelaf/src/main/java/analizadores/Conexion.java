
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
    
    public void crearConexion(){
        
        String url = "jdbc:mysql://localhost:3306/INTELAF?useSSL=false";
        String user = "usuarioIntelaf";
        String password = "intelaf123";
        
        try{
            connection = DriverManager.getConnection(url, user, password);
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public static Connection getConection(){
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
    
}
