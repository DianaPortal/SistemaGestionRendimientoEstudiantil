package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConectarBD {
//declaramos variables
	// private static String url="jdbc:mysql://localhost:3306/smartedu_db";
	private static String url = "jdbc:mysql://localhost:3306/smartedu_db?serverTimezone=America/Lima&useUnicode=true&characterEncoding=UTF-8";
	private static String usuario="root";
	//private static String password="";
	private static String password="mysql";
	private static Connection con;
	
	//creamos el metodo
	public static Connection getConexion(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,usuario,password);
			
			//emitimos mensaje
			//if(con!=null) JOptionPane.showMessageDialog(null,"hay conexion con BD","MENSAJE",JOptionPane.INFORMATION_MESSAGE);	
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    return con;
		
	}  //fin del metodo....
	
}  //fin de la clase.....
